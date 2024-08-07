package org.tester;

import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

import java.awt.Desktop;
import java.net.URI;

import java.time.LocalDate;
import java.time.LocalTime;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.net.HttpURLConnection;
import java.net.URL;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class DemoTester{
    public static void main(String[] args){

        if (args.length == 0){
            System.out.println("Please enter an argument. Arguments: takeScreenshots, compareScreenshots");
            return;
        }

        try (Playwright playwright = Playwright.create()) {
            // Replace this path by the path of your project, click on run, sit back, watch and let the magic happen
            String projectDirectory = ".";

            // Replace these paths too if needed
            String demosPath = projectDirectory + "/src/main/java";
            
            // Replace this String by the content of the pomfile of your project and delete all contents between the classname and publishname tags so they look like the two Strings below
            
            String name;

            String addressBegin = "http://localhost:8888/webapp/demos/";

            // Add every folder that contains demos here and don’t forget to also add the path to String[] paths
            String path1 = demosPath + "/addondemos/tabledemos";
            String path2 = demosPath + "/componentdemos/buttondemos";
            String path3 = demosPath + "/componentdemos/checkboxdemos";
            String path4 = demosPath + "/componentdemos/choiceboxdemos";
            String path5 = demosPath + "/componentdemos/comboboxdemos";
            String path6 = demosPath + "/componentdemos/dateeditboxdemos";
            String path7 = demosPath + "/componentdemos/dialogdemos";
            String path8 = demosPath + "/componentdemos/drawerdemos";
            String path9 = demosPath + "/componentdemos/fielddemos";
            String path10 = demosPath + "/componentdemos/labeldemos";
            String path11 = demosPath + "/componentdemos/listboxdemos";
            String path12 = demosPath + "/componentdemos/login";
            String path13 = demosPath + "/componentdemos/navigatordemos";
            String path14 = demosPath + "/componentdemos/numericboxdemos";
            String path15 = demosPath + "/componentdemos/optiondialog/confirm";
            String path16 = demosPath + "/componentdemos/optiondialog/filechooser";
            String path17 = demosPath + "/componentdemos/optiondialog/fileupload";
            String path18 = demosPath + "/componentdemos/optiondialog/input";
            String path19 = demosPath + "/componentdemos/optiondialog/message";
            String path20 = demosPath + "/componentdemos/progressbar";
            String path21 = demosPath + "/componentdemos/radiobuttondemos";
            String path22 = demosPath + "/componentdemos/sliderdemos";
            String path23 = demosPath + "/componentdemos/stringeditdemos";
            String path24 = demosPath + "/componentdemos/tabbedpanedemos";
            String path25 = demosPath + "/componentdemos/textareademos/radiobuttondemos";
            String path26 = demosPath + "/componentdemos/textareademos/sliderdemos";
            String path27 = demosPath + "/componentdemos/textareademos/stringeditdemos";
            String path28 = demosPath + "/componentdemos/textboxdemos";
            String path29 = demosPath + "/demos/webcomponents/element";
            String path30 = demosPath + "/demos/webcomponents/elementcomposite";
            String path31 = demosPath + "/layout_demos/applayout";
            String path32 = demosPath + "/layout_demos/container";
            String path33 = demosPath + "/layout_demos/flex";
            String path34 = demosPath + "/layout_demos/helper";
            String path35 = demosPath + "/layout_demos/item";
            String path36 = demosPath + "/layout_demos/splitter";
            String path37 = demosPath + "/addondemos/chartdemos";
            String path38 = demosPath + "/demos";
            String path39 = demosPath + "/layout_demos";
            
            String[] paths = {path1,path2,path3,path4,path5,path6,path7,path8,path9,path10,path11,path12,path13,path14,path15,path16,path17,path18,path19,path20,path21,path22,path23,path24,path25,path26,path27,path28,path29,path30,path31,path32,path33,path34,path35,path36,path37,path38,path39};
            
            boolean testSuccessfull = true;

                System.out.println("Creating data file...");
                File file = new File("data.txt");
                if (file.createNewFile()) {
                    System.out.println("Data file created: " + file.getName());
                } else {
                    System.out.println("Data file does already exist");
                }

                LocalDate date = LocalDate.now();
                LocalTime time = LocalTime.now().withNano(0);

                FileWriter dataWriter = new FileWriter("data.txt",true);

            if (args[0].trim().equals("compareScreenshots")){   
                dataWriter.write("**********NEW TEST SESSION**********\n");
                dataWriter.write("Date: " + date + " Time: " + time + "\n");
                dataWriter.write("************************************\n");
                dataWriter.flush();
            }

            for (int i = 0; i < paths.length - 1; i++){
                File folderPath = new File(paths[i]);
                String[] files = folderPath.list();
                
                if (files == null){
                    continue;
                }

                for (int j = 0; j < files.length - 1; j++){
                    
                    String fileName = files[j];
                    
                    boolean containsJava = fileName.contains(".java");
                    
                    if (containsJava = false){
                        continue;
                    } else {
                        name = fileName.replace(".java","");
                    }
                    
                    System.out.println("Starting demo " + name + "...");
                    
                    String address = addressBegin + name;
                    
                    Browser browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50));
                    BrowserContext context = browser.newContext();
                    Page page = context.newPage();
                    page.navigate(address);

                    System.out.println("Demo started");
                    
                    Integer ms = 0;

                    if (args.length == 2){
                        ms = Integer.parseInt(args[1]);
                    } else {
                        ms = 10000; 
                    }

                    System.out.println("Waiting " + ms + " ms for site to load...");
                    Thread.sleep(ms);

                    
                    if (args[0].trim().equals("takeScreenshots")){
                        System.out.println("Taking Screenshot...");
                        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("./screenshots/screenshot_" + name + ".png")));
                        System.out.println("Screenshot taken");
                        browser.close();
                    } else if(args[0].trim().equals("compareScreenshots")){
                        System.out.println("Comparing screenshots...");
                        byte[] nowState = page.screenshot(new Page.ScreenshotOptions());
                        byte[] shouldState = Files.readAllBytes(Paths.get("./Screenshots/screenshot_" + name + ".png"));

                        boolean equals = java.util.Arrays.equals(nowState, shouldState);

                        String status = "Failed";

                        if (equals = true){
                            status = "Success";
                        } else {
                            status = "Failed";
                        }

                        if (status == "Success"){
                            System.out.println("****************************************************");
                            System.out.println("***********************SUCCESS**********************");
                            System.out.println("****************************************************");
                        } else {
                            System.out.println("****************************************************");
                            System.out.println("***********************FAILED***********************");
                            System.out.println("****************************************************");
                            testSuccessfull = false;
                        }
                        
                        dataWriter.write("Demo: " + name + " Status: " + status + "\n");
                        dataWriter.flush();
                    } else {
                        System.out.println("Unknown argument. Arguments: takeScreenshots, compareScreenshots");
                        return;
                    }
                    /* 
                    browser = playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(50));
                    context = browser.newContext();
                    page = context.newPage();
                    page.navigate("http://desktop-fp4op26:8888/webapp/AppRemover");

                    Thread.sleep(1000);
                    
                    browser.close();
                    */
                    /* Code of the AppRemover, you need to deploy this bbjprogram first

                     * server! = BBjAPI().getAdmin("admin", "admin123").getWebAppServer()

                        apps! = server!.getAllAppNames()

                        for i = 0 to apps!.size() - 1
                            app! = apps!.getItem(i)

                            if app!.contains("AppRemover") or app!.contains("demos") then

                            else 
                                app$ = app!
                                server!.unpublish(app$)
                            endif
                        next i

                        bye
                     */

                }
            }
            dataWriter.close();

            if (testSuccessfull = true){
                dataWriter.write("*****Test was successfull*****");
            } else {
                dataWriter.write("*****Test was not successfull*****");
            }
        } catch (IOException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        
    }
}
