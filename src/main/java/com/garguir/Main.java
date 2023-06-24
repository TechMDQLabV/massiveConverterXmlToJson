package com.garguir;

import java.io.IOException;

public class Main {
    private static final String USER_DIR = System.getProperty("user.dir");
    private static final String PATH_XML_FILES = USER_DIR+"\\src\\main\\resources";

    public static void main(String[] args) throws IOException {
        System.out.println("Hello FileConverter!!!!!");
        FileBrowser fileBrowser = new FileBrowser(PATH_XML_FILES);

        fileBrowser.listFiles(PATH_XML_FILES);
    }
}