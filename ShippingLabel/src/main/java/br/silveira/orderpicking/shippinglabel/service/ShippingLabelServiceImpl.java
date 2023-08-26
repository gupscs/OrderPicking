package br.silveira.orderpicking.shippinglabel.service;

import br.silveira.orderpicking.common.service.CommonService;
import br.silveira.orderpicking.common.templates.PdfShippingLabelResultTemplateDto;
import br.silveira.orderpicking.common.util.FileUtil;
import br.silveira.orderpicking.common.util.PDFUtil;
import br.silveira.orderpicking.mktplaceintegrator.resource.MktPlaceIntegratorResource;
import br.silveira.orderpicking.shippinglabel.dto.ShippingLabelOrderDto;
import br.silveira.orderpicking.shippinglabel.dto.ShippingLabelZipFileDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ShippingLabelServiceImpl extends CommonService implements ShippingLabelService {

    public static final String TITLE = "title";
    public static final String SELLER_SKU = "sellerSku";
    public static final String RECEIVER_ZIPCODE = "receiverZipcode";
    public static final String RECEIVER_CITY_NAME = "receiverCityName";
    private static final String ZIPFILE_FILENAME = "LABEL_ORDERED_%s_%s.zip";
    private static final String TMP_ZIPFILE_FILENAME = "%s_TMP_LABEL_ORDERED_%s";

    @Autowired
    private MktPlaceIntegratorResource mktPlaceIntegratorResource;

    @Override
    public List<ShippingLabelOrderDto> getShippingLabelOrdered(List<ShippingLabelOrderDto> orders, String ordered, Boolean mixMktPlace) {
        List<ShippingLabelOrderDto> ordersSorted = orders.stream().sorted(getShippingLabelComparator(ordered, mixMktPlace)).collect(Collectors.toList());
        return ordersSorted;
    }

    @Override
    public List<ShippingLabelOrderDto> erinchShippingLabelWithZplCode(List<ShippingLabelOrderDto> orders) {
        orders.forEach(o -> {
            String zplCodeLabel = mktPlaceIntegratorResource.getZplCodeShippingLabel(o.getSellerId(), o.getShippingId()); //TODO VERIFICAR SE PODE EXISTIR (N) ORDERS PARA (1) SHIPPING_ID
            o.setZplCodeLabel(zplCodeLabel);
        });
        return orders;
    }

    @Override
    public List<ShippingLabelOrderDto> getShippingLabelWithZplCodeOrdered(List<ShippingLabelOrderDto> orders, String ordered, Boolean mixMktPlace) {
        return erinchShippingLabelWithZplCode(getShippingLabelOrdered(orders, ordered, mixMktPlace));
    }

    @Override
    public String getZplCodeOrdered(List<ShippingLabelOrderDto> orders, String ordered, Boolean mixMktPlace) {
        List<ShippingLabelOrderDto> labels = getShippingLabelWithZplCodeOrdered(orders, ordered, mixMktPlace);
        return concatenateAllZplCodeLabel(labels);
    }

    @Override
    public ShippingLabelZipFileDto getShippingLabelWithZplCodeOrderedInFile(List<ShippingLabelOrderDto> orders, String ordered, Boolean mixMktPlace) throws Exception {
        LocalDate now = LocalDate.now();
        Long companyId = getCompanyId();
        List<ShippingLabelOrderDto> labels = getShippingLabelWithZplCodeOrdered(orders, ordered, mixMktPlace);
        //create pdf template object data
        PdfShippingLabelResultTemplateDto templateDto = getPdfShippingLabelResultTemplateDto(orders);
        //create pdf file
        ByteArrayResource pdfBytes = PDFUtil.generatePdfShippingLabelResult(templateDto);
        //create txt with zpl code
        File labelTxt = FileUtil.createTxtFile("filename", concatenateAllZplCodeLabel(orders));
        //create zip file

        //Encapsulate return object
        File zipFile = File.createTempFile(String.format(TMP_ZIPFILE_FILENAME, companyId, now.toString()), ".zip");
        ShippingLabelZipFileDto ret = new ShippingLabelZipFileDto();
        ret.setShippingLabelOrder(orders);
        ret.setFileName(String.format(ZIPFILE_FILENAME, companyId, now.toString()));
        ret.setContent(new ByteArrayResource(Files.readAllBytes(zipFile.toPath())));
        ret.setFileLenght(zipFile.length());

        return ret;

    }

    private PdfShippingLabelResultTemplateDto getPdfShippingLabelResultTemplateDto(List<ShippingLabelOrderDto> orders) {
        Integer itemTotalQty = 0;
        Double orderTotalAmt = 0.0;
        Integer skuQty = 0;
        Set<String> labelsQty = new HashSet<>();
        Map<String, Integer> summarySku = new HashMap<String, Integer>();
        List<PdfShippingLabelResultTemplateDto.Detail> details = new ArrayList<>();

        for (ShippingLabelOrderDto o : orders) {
            skuQty += o.getOrderItem().size();
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
        PdfShippingLabelResultTemplateDto ret = PdfShippingLabelResultTemplateDto.builder()
                .companyId(getCompanyId()).orderTotalQty(orders.size()).itemTotalQty(itemTotalQty).skuQty(summarySku.keySet().size())
                .labelsQty(labelsQty.size()).orderTotalAmt(orderTotalAmt).details(details).summary(list).build();

        return ret;
    }

    private String concatenateAllZplCodeLabel(List<ShippingLabelOrderDto> labels) {
        StringBuffer ret = new StringBuffer();
        labels.forEach(l -> ret.append(l.getZplCodeLabel()).append("\n"));
        return ret.toString();
    }

    private Comparator<ShippingLabelOrderDto> getShippingLabelComparator(String ordered, Boolean mixMktPlace) {
        Comparator<ShippingLabelOrderDto> comparator = Comparator.comparing(c -> c.getMktPlace());
        Function<ShippingLabelOrderDto, String> function;

        switch (ordered.toLowerCase()) {
            case TITLE:
                function = c -> c.getFirstOrderItem().getTitle();
                break;
            case SELLER_SKU:
                function = c -> c.getFirstOrderItem().getSellerSku();
                break;
            case RECEIVER_ZIPCODE:
                function = c -> c.getReceiverZipcode();
                break;
            case RECEIVER_CITY_NAME:
                function = c -> c.getReceiverCityName();
                break;
            default:
                throw new IllegalArgumentException(String.format("Ordered invalid - valid ordered %s|%s|%s|%s", TITLE, SELLER_SKU, RECEIVER_ZIPCODE, RECEIVER_CITY_NAME));
        }
        comparator = mixMktPlace ? Comparator.comparing(function) : comparator.thenComparing(function);
        return comparator;
    }
}
