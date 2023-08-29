package br.silveira.orderpicking.shippinglabel.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.core.io.ByteArrayResource;

import java.util.List;

@Data
@Builder
public class ShippingLabelZipFileDto {

    private String fileName;

    private List<ShippingLabelOrderDto> shippingLabelOrder;

    private ByteArrayResource content;

    private long fileLenght;

}
