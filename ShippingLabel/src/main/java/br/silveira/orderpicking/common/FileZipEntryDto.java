package br.silveira.orderpicking.common;

import lombok.Builder;
import lombok.Data;

import java.io.File;

@Data
@Builder
public class FileZipEntryDto {

    private File file;
    private String fileNameInZip;
}
