package br.silveira.orderpicking.common.util;

import br.silveira.orderpicking.common.templates.PdfShippingLabelResultTemplateDto;
import br.silveira.orderpicking.shippinglabel.dto.ShippingLabelOrderDto;
import org.springframework.core.io.ByteArrayResource;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class PDFUtil {



    public static File generatePdfShippingLabelResult(PdfShippingLabelResultTemplateDto dto) throws Exception {
        String html = TemplateUtil.generateHtml(dto);
        String strNow = LocalDateTime.now().toString();

        File file = FileUtil.createTmpFile("PDF_TEMP_");
        OutputStream outputStream = new FileOutputStream(file);

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();

        return file;
    }


    public static PdfShippingLabelResultTemplateDto getPdfShippingLabelResultTemplateDto(List<ShippingLabelOrderDto> orders) {
        Integer itemTotalQty = 0;
        Double orderTotalAmt = 0.0;
        Set<String> labelsQty = new HashSet<>();
        Map<String, Integer> summarySku = new HashMap<String, Integer>();
        List<PdfShippingLabelResultTemplateDto.Detail> details = new ArrayList<>();

        for (ShippingLabelOrderDto o : orders) {
            orderTotalAmt += o.getTotalPrice();
            labelsQty.add(o.getShippingId());
            for (ShippingLabelOrderDto.ShippingLabelOrderItemDto i : o.getOrderItem()) {
                itemTotalQty += i.getQuantity();
                if (!summarySku.containsKey(i.getTitle())) {
                    summarySku.put(i.getTitle(), 0);
                }
                summarySku.put(i.getTitle(), i.getQuantity() + summarySku.get(i.getTitle()));
                details.add(new PdfShippingLabelResultTemplateDto.Detail(o.getMktPlace().getDesc(), o.getMktPlaceOrderid(), i.getTitle(), i.getQuantity()));
            }
        }

        List<PdfShippingLabelResultTemplateDto.Summary> list = summarySku.keySet().stream().map(p -> new PdfShippingLabelResultTemplateDto.Summary(p, summarySku.get(p))).collect(Collectors.toList());
        PdfShippingLabelResultTemplateDto ret = new PdfShippingLabelResultTemplateDto(orders.get(0).getCompanyId(), orders.size(), itemTotalQty, summarySku.keySet().size(), labelsQty.size(), orderTotalAmt, list, details);
        return ret;
    }
}
