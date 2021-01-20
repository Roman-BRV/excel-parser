package ua.pp.helperzit.excelparser.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileFinder {
    
    public boolean checkDirPath(String path){
        
        File file = new File(path);

        return file.isDirectory();
    }
    
    public boolean checkFilePath(String path){
        
        if(!(path.endsWith(".xls") || path.endsWith(".xlsx"))) {
            return false;
        }
        
        File file = new File(path);

        return file.exists();
    }
    
    public List<String> getFileNames(String path){
        
        List<String> dirsFilesNames = new ArrayList<>();
        List<String> dirNames = new ArrayList<>();
        List<String> fileNames = new ArrayList<>();
        
        File dir = new File(path);
        List<File> files = Arrays.asList(dir.listFiles());
        for (File file : files) {
            if(file.isDirectory()) {
                dirNames.add(file.getName());
            } else {
                fileNames.add(file.getName());
            }
        }
        dirsFilesNames.addAll(dirNames.stream().sorted().collect(Collectors.toList()));
        dirsFilesNames.addAll(fileNames.stream().sorted().collect(Collectors.toList()));

        
        return dirsFilesNames;
    }

}
