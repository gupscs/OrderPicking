package br.silveira.orderpicking.shippinglabel.dto;

import br.silveira.orderpicking.common.constants.MktPlaceEnum;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Data
public class ShippingLabelOrderDto {

    private Long companyId;
    private MktPlaceEnum mktPlace;
    private String sellerId;
    private String mktPlaceOrderid;
    private String status;
    private Date orderCreationDate;
    private String receiverCityName;
    private String receiverZipcode;
    private String shippingId;
    private String zplCodeLabel;
    private List<ShippingLabelOrderItemDto> orderItem;

    public ShippingLabelOrderItemDto getFirstOrderItem(){
        return CollectionUtils.isEmpty(orderItem) ? null : orderItem.get(0);
    }


    @Data
    public class ShippingLabelOrderItemDto {
        private String id;
        private String title;
        private String sellerSku;
        private double quantity;
        private double unitPrice;
    }
}
