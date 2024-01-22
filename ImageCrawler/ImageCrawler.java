package ImageCrawler;
import java.util.ArrayList;
import java.util.Arrays;
//import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
//requires Jsoup
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

public class ImageCrawler {
    private static ArrayList<String> urlsList;
    private static ArrayList<String> blockedUrlsList;
    private static final String initialURL = "http://lounshome.com/albums/index.htm";
    private static final String[] blockedURLS = {"http://lounshome.com/albums/Easter2006/index1.htm", "http://lounshome.com/albums/Easter2006/index.htm", "http://lounshome.com/albums/Sunnyside2006/images/P5060016.jpg", "http://lounshome.com/albums/Sunnyside2006/Image5.htm"};
    private static final String fileSaveLocation = "D:\\dads images\\";

    public static void main(String[] args) {

        urlsList = new ArrayList<>();
        urlsList.add(initialURL);

        blockedUrlsList = new ArrayList<>();
        blockedUrlsList.addAll(Arrays.asList(blockedURLS));
        

        Crawl();
    }

    public static void Crawl() {
        int pointer = 0;

        while(urlsList.size() > pointer) {
            try {
                Document doc = Jsoup.connect(urlsList.get(pointer)).get();
                Elements links = doc.select("a[href]");

                for(int i =0 ; i<links.size(); i++) {
                    String absHref = links.eq(i).attr("abs:href"); //eq(i) gets a new Elements of index i in links, then parses that for its absolute href
                    //String relHref = links.eq(i).attr("href");
                    if (!urlsList.contains(absHref) && !blockedUrlsList.contains(absHref)) { //if the list already has this link, don't search it again. Also, if this is a blocked link to not be searched, definitely don't search it.
                        
                        if (absHref.toLowerCase().contains(".jpg")) { //if this link is an image, download it.
                            boolean alternate = false; //TODO: alterante is deleting the correct ones way too early, its true when it shouldnt be

                            //with the website I'm using, all of the images needed will have a bold text without an href. This will be used for the name of the file.
                            //the tag is b
                            Element html = doc.select("font > :matches(: )").first(); //shows up as <b> <a href...
                            if (html == null) { //the standard switches midway through in the website from ": " to "> "
                                html = doc.select("font > :matches(> )").first();
                                alternate = true;
                            }

                            String title = html.wholeOwnText();
                            title = html.wholeOwnText().substring(8, title.length() - 2);
                            if (alternate) {
                                title = html.wholeOwnText().substring(10, title.length() - 6); //the website has some leading and trailing text before the titles. No clue why it's 8 though, looks like 5 tops.
                            }

                            title = title.replace(":", " is");
                            title = title.replace("?", "");
                            title = title.replace("\"", "");
                            title = title.replace(" ", "_"); //by request, spaces will be converted to underscores

                            String folderTitle = absHref.substring(28);
                            folderTitle = folderTitle.substring(0, folderTitle.indexOf("/"));
                            
                            try(InputStream in = new URL(absHref).openStream()){
                                Files.copy(in, Paths.get(fileSaveLocation + "\\" + folderTitle + "\\" + title + ".jpg"));
                                System.out.println(title + " saved to " + fileSaveLocation + folderTitle + "\\" + title + ".jpg");
                            } 
                            catch(FileAlreadyExistsException e) { //by this point, the picture is unique save for any potential pictures with the same name.
                                boolean keepGoing = true;
                                int pictureNumber = 1;
                                while (keepGoing) { //keep incrementing the number and trying to replace the image with the new one
                                    pictureNumber++;
                                    try(InputStream in = new URL(absHref).openStream()){
                                        Files.copy(in, Paths.get(fileSaveLocation + "\\" + folderTitle + "\\" + title + pictureNumber + ".jpg"));
                                        System.out.println(title + " saved to " + fileSaveLocation + folderTitle + "\\" + title + + pictureNumber + ".jpg");
                                        keepGoing = false;
                                    }
                                  catch(FileAlreadyExistsException exception) {
                                        if (pictureNumber > 50) {
                                            //Something probably won't go wrong enough to need this, and it may interfere with situations with 50+ images of the same name, but I'm not going to encounter that.
                                            keepGoing = false;
                                        }
                                    }

                                }
                            }
                            catch(IOException e) {
                                System.out.println(absHref + " failed to download");
                            }
                            blockedUrlsList.add(absHref);
                        }
                        else { //the url is not an image and is new, so add it to be searched
                            urlsList.add(absHref);
                        }
                    }
                }

              //  Elements images = doc.select("img[src$=.jpg]");

            } catch(IOException e) {
                System.out.println(urlsList.get(pointer) + " failed to parse");
            }

            if (pointer == 100) {
                System.out.println("Pointer has passed 100!");
            }
            pointer++;
        }

        System.out.println("\nAll done!");
    }
}
