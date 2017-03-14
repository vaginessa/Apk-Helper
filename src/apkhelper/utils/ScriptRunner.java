/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package apkhelper.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 * @author Oscar (AKA Bittle)
 */
public class ScriptRunner {
    private static FileHelper fileHelper = new FileHelper();
    
    public static void runJar(String jarName){
        runJar(jarName, "", "");
    }
    
    // option b = build
    // option d = decompile
    public static void runJar(String jarName, String option, String apkPath){
        String filePath = fileHelper.getFileFromSrc("jar", jarName).getAbsolutePath();
        
        ProcessBuilder pb = new ProcessBuilder("java","-jar", filePath, option,apkPath);
        pb.directory(new File("/Users/oscartorres/"));
        
        runCmd(pb);
    }
    
    //public static void runBash()
    
    public static void runCmd(String[] args, String filePath){
        ProcessBuilder pb = new ProcessBuilder(args[0], args[1], filePath, " d");
        pb.directory(new File("/Users/"));
        runCmd(pb);
    }
    
    public static void runCmd(final ProcessBuilder pb){
                try {
            Process p = pb.start();
            LogStreamReader lsr = new LogStreamReader(p.getInputStream());
            Thread thread = new Thread(lsr, "LogStreamReader");
            thread.start();
        } catch (IOException e) {
            System.out.println("== "+e.toString());
        }
    }
    
    private static class LogStreamReader implements Runnable {
        
        final private BufferedReader reader;
        
        public LogStreamReader(InputStream is) {
            this.reader = new BufferedReader(new InputStreamReader(is));
        }
        
        @Override
        public void run() {
            try {
                String line = reader.readLine();
                while (line != null) {
                    System.out.println(line);
                    line = reader.readLine();
                }
                reader.close();
                System.out.println("DONE");
            } catch (IOException e) {
                System.out.println("--"+e.toString());
            }
        }
    }
}