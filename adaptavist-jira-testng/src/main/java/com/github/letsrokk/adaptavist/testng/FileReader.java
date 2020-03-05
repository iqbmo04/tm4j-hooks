package com.github.letsrokk.adaptavist.testng;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileReader {

    public List<File> getFiles(String directory, String pattern) throws Exception {
        if (!new File(directory).isDirectory()) {
            throw new Exception(MessageFormat.format("Path not found : {0}", directory));
        }
        if (!pattern.contains("*")) {
            File file = new File(directory + pattern);
            if (!file.exists()) {
                throw new FileNotFoundException(MessageFormat.format("File not found: {0}", pattern));
            }
            return Arrays.asList(file);
        }

        FileFilter filter = pathname -> pathname.isFile() && pathname.getName().equals("result_1.json");

        return Arrays.asList(new File(directory).listFiles(filter));
//
//                DirectoryScanner scanner = new DirectoryScanner();
//        scanner.setIncludes(new String[]{pattern});
//        scanner.setBasedir(directory);
//        scanner.setCaseSensitive(false);
//        scanner.scan();
//        String[] paths = scanner.getIncludedFiles();
//        List<File> files = new ArrayList<>();
//        for (String path : paths) {
//            File file = new File(directory + path);
//            if (!file.exists()) {
//                throw new FileNotFoundException(MessageFormat.format("File not found : {0}", file.getPath()));
//            }
//            files.add(file);
//        }
//        if (files.isEmpty()) {
//            throw new FileNotFoundException(MessageFormat.format("File not found : {0}", pattern));
//        }
//        return files;
    }

    public File getZip(String directory, String pattern) throws Exception {
        List<File> files = getFiles(directory, pattern);
        File zip = File.createTempFile("tm4j", "zip");
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zip));
        for (File file : files) {
            out.putNextEntry(new ZipEntry(file.getPath()));
            out.write(FileUtils.readFileToByteArray(file));
            out.closeEntry();
        }
        out.close();
        return zip;
    }
}
