package br.silveira.orderpicking.common.util;

import br.silveira.orderpicking.common.FileZipEntryDto;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
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

    public static byte[] createZipFile(FileZipEntryDto... files) throws IOException {
        File tmpZipFile = createTmpFile("ZIP_TMP_");
        final FileOutputStream fos = new FileOutputStream(tmpZipFile);
        ZipOutputStream zipOut = new ZipOutputStream(fos);

        for(FileZipEntryDto fileToZip : files){
            FileInputStream fis = new FileInputStream(fileToZip.getFile());
            ZipEntry zipEntry = new ZipEntry(fileToZip.getFileNameInZip());
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

        return Files.readAllBytes(tmpZipFile.toPath());

    }


    public static File createTxtFile( String concatenateAllZplCodeLabel) throws IOException {
        File file = createTmpFile("TXT_TEMP_");
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(concatenateAllZplCodeLabel);
        writer.close();
        return file;
    }

    public static File rename(File file , String newName) throws IOException {
        File rename = new File(file.getParent(), newName);
        Files.move(file.toPath(), rename.toPath());
        return rename;
    }

    private static String getRandonString() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));
        return LocalDateTime.now().toString()+"-"+generatedString;
    }

}
