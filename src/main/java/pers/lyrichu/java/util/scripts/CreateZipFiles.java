package pers.lyrichu.java.util.scripts;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.zip.CRC32;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CreateZipFiles {
    private static String basePath = "src/main/resources";
    private static String[] files = (String[]) Arrays.asList(basePath+"/stopwords.txt",
            basePath+"/test.pdf",basePath+"/test.txt").toArray();
    private static String zipFilePath = basePath + "/test.zip";

    public static void main(String[] args) {
        createZip(files,zipFilePath);
        System.out.printf("create %s successfully!\n",zipFilePath);
    }

    private static void createZip(String[] files,String zipFilePath) {
        try {
            File zipFile = new File(zipFilePath);
            FileOutputStream out = new FileOutputStream(zipFile);
            ZipOutputStream zipOut = new ZipOutputStream(out);
            int byteRead;
            byte[] buffer = new byte[1024];
            CRC32 crc = new CRC32();
            for (String file:files) {
                File f = new File(file);
                if (!f.exists()) {
                    System.err.printf("%s doesn't exist,skip it!\n",file);
                    continue;
                }
                BufferedInputStream bufferedInputStream = new BufferedInputStream(
                        new FileInputStream(f)
                );
                crc.reset();
                while ((byteRead = bufferedInputStream.read(buffer)) != -1) {
                    crc.update(buffer,0,byteRead);
                }
                bufferedInputStream.close();
                bufferedInputStream = new BufferedInputStream(
                        new FileInputStream(f)
                );
                ZipEntry zipEntry = new ZipEntry(file);
                zipEntry.setMethod(ZipEntry.STORED);
                zipEntry.setCompressedSize(f.length());
                zipEntry.setSize(f.length());
                zipEntry.setCrc(crc.getValue());
                zipOut.putNextEntry(zipEntry);
                while ((byteRead = bufferedInputStream.read(buffer)) != -1) {
                    zipOut.write(buffer,0,byteRead);
                }
                bufferedInputStream.close();
            }
            zipOut.close();
            out.close();
        } catch (Exception e) {
            System.err.println("error:"+e);
        }
    }
}
