package br.silveira.orderpicking.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileUtil {

    public static File createTmpFile(String prefix, String sufix) throws IOException {
        File file = File.createTempFile(prefix, sufix);
        file.deleteOnExit();
        return file;
    }
}
