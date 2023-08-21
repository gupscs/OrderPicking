package br.silveira.orderpicking.shippinglabel.service;

import br.silveira.orderpicking.common.service.CommonService;
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
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
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
    private static final String TMP_ZIPFILE_FILENAME = "%s_TMP_LABEL_ORDERED_%s" ;

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
        return erinchShippingLabelWithZplCode(getShippingLabelOrdered(orders,ordered,mixMktPlace));
    }

    @Override
    public String getZplCodeOrdered(List<ShippingLabelOrderDto> orders, String ordered, Boolean mixMktPlace) {
        StringBuffer ret = new StringBuffer();
        List<ShippingLabelOrderDto> labels = getShippingLabelWithZplCodeOrdered(orders, ordered, mixMktPlace);
        labels.forEach( l ->  ret.append(l.getZplCodeLabel()).append("\n"));
        return ret.toString();
    }

    @Override
    public ShippingLabelZipFileDto getShippingLabelWithZplCodeOrderedInFile(List<ShippingLabelOrderDto> orders, String ordered, Boolean mixMktPlace) throws IOException {
        LocalDate now = LocalDate.now();
        Long companyId = getCompanyId();
        List<ShippingLabelOrderDto> labels = getShippingLabelWithZplCodeOrdered(orders, ordered, mixMktPlace);
        //TODO Criar pdfutil;
        //Create the PDF file with Header Summaryzed where
            //mixMktPlace = true , Column Mkt Place input a list Mercado Livre, Shopee, etc
            //MixMktPlace = false , Column Mkt Place should be a group field
            //Order by Sku Name
            //List wiht Shipping Id , Order Id, Sku Id , Sku Name, Qty

        //Create txt file with zplCode

        //Create Zip File
        File zipFile = File.createTempFile(String.format(TMP_ZIPFILE_FILENAME, companyId, now.toString()), ".zip");
        ShippingLabelZipFileDto ret = new ShippingLabelZipFileDto();
        ret.setShippingLabelOrder(orders);
        ret.setFileName(String.format(ZIPFILE_FILENAME, companyId, now.toString() ));
        ret.setContent(new ByteArrayResource(Files.readAllBytes(zipFile.toPath())));
        ret.setFileLenght(zipFile.length());

        return ret;

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
