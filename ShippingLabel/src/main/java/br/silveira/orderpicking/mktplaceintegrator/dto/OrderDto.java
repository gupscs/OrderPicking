package br.silveira.orderpicking.mktplaceintegrator.dto;

import br.silveira.orderpicking.common.constants.MktPlaceEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class OrderDto {


    private Long companyId;
    private MktPlaceEnum mktPlace;
    private String sellerId;
    private String mktPlaceOrderid;
    private String mktPlaceStatus;
    private LocalDateTime orderCreationDate;
    private String receiverCityName;
    private String receiverZipcode;
    private String shippingId;
    private List<OrderDto.OrderItemDto> orderItem;
    private double totalPrice;

    @Data
    public class OrderItemDto {
        private String mktPlaceItemId;
        private String title;
        private String sellerSku;
        private double quantity;
        private double unitPrice;
        private double totalPrice;
    }
}
