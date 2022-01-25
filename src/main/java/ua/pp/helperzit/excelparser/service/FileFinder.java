package ua.pp.helperzit.excelparser.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileFinder {

    private static final Logger log = LoggerFactory.getLogger(FileFinder.class);

    public boolean checkDirectoryPath(String path){

        log.debug("Checking: is {} - is a directory.", path);

        File file = new File(path);
        boolean checkResult  = file.isDirectory();

        log.debug("{} - is a directory - {}.", path, checkResult);

        return checkResult;
    }

    public boolean checkFilePath(String path){

        log.debug("Checking: is {} - is a correct path.", path);

        File file = new File(path);
        boolean checkResult  = false;

        if(path.endsWith(".xls") || path.endsWith(".xlsx")) {
            checkResult  = file.exists();
        }

        log.debug("{} - is a correct path - {}.", path, checkResult);

        return checkResult;
    }

    public List<String> getInclusionsNames(String path){

        log.debug("Going to get names of includes in directory: {}.", path);

        List<String> inclusionsNames = new ArrayList<>();
        List<String> dirNames = new ArrayList<>();
        List<String> fileNames = new ArrayList<>();

        File dir = new File(path);
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if(file.isDirectory()) {
                    dirNames.add(file.getName());
                } else {
                    fileNames.add(file.getName());
                }
            }
        }
        inclusionsNames.addAll(dirNames.stream().sorted().collect(Collectors.toList()));
        inclusionsNames.addAll(fileNames.stream().sorted().collect(Collectors.toList()));

        log.debug("Names of includes in directory: {} have been successfully got.", path);

        return inclusionsNames;
    }

}
