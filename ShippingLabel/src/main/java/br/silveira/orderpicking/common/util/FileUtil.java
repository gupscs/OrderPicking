package br.silveira.orderpicking.common.util;

import org.springframework.core.io.ByteArrayResource;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {

    public static File createTmpFile(String prefix) throws IOException {
        File file = File.createTempFile(prefix, null);
        file.deleteOnExit();
        return file;
    }

    public static ByteArrayResource createZipFile(File ... files) throws IOException {
        File tmpZipFile = createTmpFile("ZIP_TMP_");
        final FileOutputStream fos = new FileOutputStream(tmpZipFile);
        ZipOutputStream zipOut = new ZipOutputStream(fos);

        for(File fileToZip : files){
            FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            fis.close();
        }

        zipOut.close();
        fos.close();

        ByteArrayResource byteArrayResource = new ByteArrayResource(Files.readAllBytes(tmpZipFile.toPath()));
        return byteArrayResource;

    }


    public static File createTxtFile( String concatenateAllZplCodeLabel) throws IOException {
        File file = createTmpFile("TXT_TEMP_");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(concatenateAllZplCodeLabel);
        writer.close();
        return file;
    }
}
