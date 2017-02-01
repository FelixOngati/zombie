/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zombie;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fegati
 */
public class Zombie {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ArrayList<List> fileproperties = new ArrayList<>();
        List<String> filenames = new ArrayList<>();
        File[] files =  new File("/home/fegati/Downloads/zombie-inbox/Inbox").listFiles();
        
        
        for(int i = 0; i < files.length; i++){
            if(files[i].isFile()){
                String[] tokens = (files[i].getName()).split("[-_;.]");
                String filename = (tokens[tokens.length - 2]).trim();
                filenames.add(files[i].getName());
                Path p = Paths.get(files[i].getAbsolutePath());
                try {
                    BasicFileAttributes attributes = Files.readAttributes(p, BasicFileAttributes.class);
                    String[] closeddate = ((attributes.lastModifiedTime()).toString()).split("[T]");
                    System.out.println("filename: "+filename+"   closed: "+closeddate[0]);
                } catch (IOException ex) {
                    Logger.getLogger(Zombie.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
//            System.out.println(files[i].getAbsolutePath());
        }
    }
    
}
