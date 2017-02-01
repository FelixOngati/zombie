/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zombie;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Problem: I have users who raise tickets via mail instead of using the
 * servicedesk, now I have about 900 tickets as emails, I want to extract the 
 * subject of the mails so that I can categorize the tickets accordingly and 
 * determine the date they were raised, I am using Zimbra web client for emails 
 * so I go ahead and dumb the mails to a folder on my pc.
 * 
 * You have text files dumped from a mail box, the name of the file
 * name of the file contains the subject of the file plus other characters,
 * you are required to required to loop through the folder containing the 
 * files and extract the email subjects and the date the mail was sent; which
 * is basically the created date of the mail, and write the results in a csv file.
 * This program does exactly that, without using any external libraries; apart from 
 * java inbuilt ones.
 * @author fegati
 */
public class Zombie {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
       //temporary holder for filename and date created
        List<String> filedata = new ArrayList<>();
        
        /**
         * The paths listed here are for my own pc, remember to change this
         * relative to your pc
        */
        File[] files =  new File("/home/fegati/Downloads/zombie-inbox/Inbox").listFiles();
        FileWriter fileWriter = new FileWriter("/home/fegati/NetBeansProjects/Zombie/output/data.csv");
        
        //loop through the folder and get files
        for(int i = 0; i < files.length; i++){
            if(files[i].isFile()){
                /**
                 * An example filename looks like '0000002925-Re_ [Ticket #3604] Access to DHIS2.eml'
                 * What I want is Access to DHIS2, the regex below does exactly that
                 */
                String[] tokens = (files[i].getName()).split("[-_\\];.]");
                String filename = (tokens[tokens.length - 2]).trim();
                
                filedata.add(filename);
                
                Path p = Paths.get(files[i].getAbsolutePath());
                try {
                    BasicFileAttributes attributes = Files.readAttributes(p, BasicFileAttributes.class);
                    
                    //raw date looks like 2016-11-07T06:24:14
                    String[] closeddate = ((attributes.lastModifiedTime()).toString()).split("[T]");
                    filedata.add(closeddate[0]);
                    
                    //write to csv file separating the values with a comma
                    fileWriter.write(filedata.stream().collect(Collectors.joining(",")));
                    fileWriter.write("\n");
                    filedata.clear();
                    
                    System.out.println("filename: "+filename+"   closed: "+closeddate[0]);
                } catch (IOException ex) {
                    Logger.getLogger(Zombie.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
//            System.out.println(files[i].getAbsolutePath());
        }
        fileWriter.close();
    }
    
}
