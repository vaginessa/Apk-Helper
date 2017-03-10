/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package apkhelper.utils;

import java.io.File;

/**
 *
 * @author Oscar (AKA Bittle)
 */
public class FileHelper {
    public static boolean fileExists(String file){
        return fileExists(new File(file));
    }
    
    public static boolean fileExists(File file){
        return file.exists();
    }
    
    // ======
    
    public static boolean isDirectory(String file){
        return isDirectory(new File(file));
    }
    public static boolean isDirectory(File file){
        if(file.exists()){
            return file.isDirectory();
        }
        else{
            return false;
        }
    }
    
    
    public static boolean fileIsApk(String file){
        return fileIsApk(new File(file));
    }
    public static boolean fileIsApk(File file){
        if(file.exists()){
            return file.isFile();
        }else{
            return false;
        }
    }
    public static File stringToFile(String s){
        return new File(s);
    }
}