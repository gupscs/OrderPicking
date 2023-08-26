package br.silveira.orderpicking.common.util;

import br.silveira.orderpicking.common.templates.PdfShippingLabelResultTemplateDto;
import org.springframework.core.io.ByteArrayResource;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.time.LocalDateTime;


public class PDFUtil {

    private static final String TMP_FILE_PREFIX = "%s_PDF_SHIPPING_LABEL_%s";


    public static ByteArrayResource generatePdfShippingLabelResult(PdfShippingLabelResultTemplateDto dto) throws Exception {
        String html = TemplateUtil.generateHtml(dto);
        String strNow = LocalDateTime.now().toString();

        File file = FileUtil.createTmpFile(String.format(TMP_FILE_PREFIX, dto.getCompanyId(), strNow), ".pdf");
        OutputStream outputStream = new FileOutputStream(file);

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();

        ByteArrayResource byteArrayResource = new ByteArrayResource(Files.readAllBytes(file.toPath()));
        return byteArrayResource;
    }


}
