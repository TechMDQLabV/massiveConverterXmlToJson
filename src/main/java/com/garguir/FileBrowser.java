package com.garguir;

import org.json.JSONObject;
import org.json.XML;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Logger;

public class FileBrowser {
    private static final Logger LOGGER = Logger.getLogger("FileConverter");
    private static final String USER_DIR = System.getProperty("user.dir");
    private static final String PATH_XML_FILES = USER_DIR+"\\src\\main\\resources";
    private static final String PATH_JSON_FILES = USER_DIR+"\\json\\resources";
    private static final String LINE_ASTERISKS =   "***************************************************************************************************";
    private static final String LINE_EQUALS =      "===================================================================================================";
    private static final String LINE_UNDERLINEDS = "___________________________________________________________________________________________________";
    private static final String LINE_MIDDLE =      "---------------------------------------------------------------------------------------------------";
    File filesRoute;

    public FileBrowser(String route) {
        this.filesRoute = new File(route);
    }

    public void listFiles(String path) throws IOException {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();

            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    String fileName = files[i].getName();
                    if (files[i].isFile() && fileName.substring(fileName.lastIndexOf(".")+1).equals("xml") ) {
                        JSONObject json = XML.toJSONObject(convertXmlToString(files[i]));
                        String newPath = files[i].getParent().replace(PATH_XML_FILES, "");
                        try (PrintWriter out = new PrintWriter(new FileWriter(PATH_JSON_FILES+"\\"+newPath+"\\"+files[i].getName().replace("xml", "json")))) {
                            out.write(json.toString());
                            LOGGER.info("Escribiendo JSON File: " + files[i].getName());
                        } catch (Exception e) {
                            LOGGER.warning("Error al escribir el archivo: "+e);
                        }
                    } else if (files[i].isDirectory()) {
                        createDir(files[i]);
                        listFiles(files[i].getAbsolutePath());
                    }
                }
            }
        } else {
            System.out.println("El directorio o la ruta no existen.");
        }
    }

    private static String convertXmlToString(File xmlFile) throws IOException {
        Scanner scanner = new Scanner(xmlFile.getAbsoluteFile());
        StringBuilder xmlString = new StringBuilder();
        while(scanner.hasNextLine()){
            xmlString.append(scanner.nextLine());
        }
        return xmlString.toString();
    }

    private static void createDir(File file){
        File newDir = new File(PATH_JSON_FILES + file.getParent().replace(PATH_XML_FILES, "") + "\\"+file.getName());
        if(!newDir.exists()){
            if(!newDir.mkdir()){
                LOGGER.warning("createDir >>>>>>>>>>>>>>>>>>>>>>>>>>> Error al crear Directorio "+newDir.getAbsolutePath());
            }
        }
    }
}
