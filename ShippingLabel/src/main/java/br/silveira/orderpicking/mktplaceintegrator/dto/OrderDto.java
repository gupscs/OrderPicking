package br.silveira.orderpicking.mktplaceintegrator.dto;

import br.silveira.orderpicking.common.MktPlaceEnum;
import lombok.Data;

import java.util.Date;
import java.util.List;

public class OrderDto {


    private Long companyId;
    private MktPlaceEnum mktPlace;
    private String sellerId;
    private String mktPlaceOrderid;
    private String status;
    private Date orderCreationDate;
    private String receiverCityName;
    private String receiverZipcode;
    private String shippingId;
    private List<OrderDto.OrderItemDto> orderItem;


    @Data
    public class OrderItemDto {
        private String id;
        private String title;
        private String sellerSku;
        private double quantity;
        private double unitPrice;
    }
}
