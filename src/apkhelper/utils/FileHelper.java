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
    public boolean fileExists(String file){
        return fileExists(new File(file));
    }
    
    public boolean fileExists(File file){
        return file.exists();
    }
    
    // ======
    
    public boolean isDirectory(String file){
        return isDirectory(new File(file));
    }
    public boolean isDirectory(File file){
        if(file.exists()){
            return file.isDirectory();
        }
        else{
            return false;
        }
    }
    
    
    public boolean isAPK(String file){
        return isAPK(new File(file));
    }
    public boolean isAPK(File file){
        if(file.exists()){
            return file.getAbsolutePath().endsWith(".apk");
        }else{
            return false;
        }
    }
    public File stringToFile(String s){
        return new File(s);
    }
    
    public File getFileFromSrc(String fileName){
        return getFileFromSrc("", fileName);
    }
    
    public File getFileFromSrc(String path, String fileName){
        if(!path.equals("")){
            path+="/";
        }
        File file = new File("src/"+path+fileName);
        if(file.exists()){
            return file;
        }
        else{
            System.out.println(file.getAbsolutePath());
            System.out.println("File "+fileName+" does not exist");
            return null;
        }   
    }
    
    public boolean isDecompiledApkDir(String dir){
        return isDecompiledApkDir(new File(dir));
    }
    
    public boolean isDecompiledApkDir(File file){
        if(file.isDirectory()){
            File manifest = new File(file.getAbsolutePath()+
                    "/AndroidManifest.xml");
            File res = new File(file.getAbsolutePath()+"/res/");
            
            return (manifest.isFile() && manifest.exists() 
                    && res.isDirectory() && res.exists());
            
        }
        else{
            return false;
        }
    }
}