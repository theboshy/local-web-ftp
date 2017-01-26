/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opd.server.localftp.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author pdgomezl
 */
public class ZipController {

    private static ZipOutputStream zipOutputStream;
    private static String type = ".7z";

    public static void folderToZip(String fileName)
            throws IOException, FileNotFoundException {
        File file = new File(fileName);
        zipOutputStream = new ZipOutputStream(new FileOutputStream(file + type));
        recurseFiles(file);
        zipOutputStream.close();
    }

    private static void recurseFiles(File file)
            throws IOException, FileNotFoundException {
        if (file.isDirectory()) {
            String[] fileNames = file.list();
            if (fileNames != null) {
                for (String fileName : fileNames) {
                    recurseFiles(new File(file, fileName));
                }
            }
        } else {
            byte[] buf = new byte[1024];
            int len;
            ZipEntry zipEntry = new ZipEntry(file.toString());
            FileInputStream fin = new FileInputStream(file);
            try (BufferedInputStream in = new BufferedInputStream(fin)) {
                zipOutputStream.putNextEntry(zipEntry);
                while ((len = in.read(buf)) >= 0) {
                    zipOutputStream.write(buf, 0, len);
                }
            }
            zipOutputStream.closeEntry();
        }
    }

    public static String getType() {
        return type;
    }

    public static void setType(String aType) {
        type = aType;
    }

}
