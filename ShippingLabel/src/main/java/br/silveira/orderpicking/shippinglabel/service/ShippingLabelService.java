package br.silveira.orderpicking.shippinglabel.service;

import br.silveira.orderpicking.shippinglabel.dto.ShippingLabelOrderDto;
import br.silveira.orderpicking.shippinglabel.dto.ShippingLabelZipFileDto;

import java.io.IOException;
import java.util.List;

public interface ShippingLabelService {

    List<ShippingLabelOrderDto> getShippingLabelOrdered(List<ShippingLabelOrderDto> orders, String ordered, Boolean mixMktPlace);

    List<ShippingLabelOrderDto> erinchShippingLabelWithZplCode(List<ShippingLabelOrderDto> orders);

    List<ShippingLabelOrderDto> getShippingLabelWithZplCodeOrdered(List<ShippingLabelOrderDto> orders, String ordered, Boolean mixMktPlace);

    String getZplCodeOrdered(List<ShippingLabelOrderDto> orders, String ordered, Boolean mixMktPlace);

    ShippingLabelZipFileDto getShippingLabelWithZplCodeOrderedInFile(List<ShippingLabelOrderDto> orders, String ordered, Boolean mixMktPlace) throws Exception;
}
