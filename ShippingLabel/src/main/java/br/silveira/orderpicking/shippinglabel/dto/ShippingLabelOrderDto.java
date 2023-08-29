package br.silveira.orderpicking.shippinglabel.dto;

import br.silveira.orderpicking.common.constants.MktPlaceEnum;
import br.silveira.orderpicking.common.templates.PdfShippingLabelResultTemplateDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShippingLabelOrderDto {

    private Long companyId;
    private MktPlaceEnum mktPlace;
    private String sellerId;
    private String mktPlaceOrderid;
    private String status;
    private LocalDateTime orderCreationDate;
    private String receiverCityName;
    private String receiverZipcode;
    private String shippingId;
    private String zplCodeLabel;
    private double totalPrice;
    private List<ShippingLabelOrderItemDto> orderItem;

    public ShippingLabelOrderItemDto getFirstOrderItem(){
        return CollectionUtils.isEmpty(orderItem) ? null : orderItem.get(0);
    }

    @Data
    @NoArgsConstructor
    public static class ShippingLabelOrderItemDto {
        private String id;
        private String title;
        private String sellerSku;
        private Integer quantity;
        private double unitPrice;
        private double totalPrice;

        public ShippingLabelOrderItemDto(String id, String title, String sellerSku, Integer quantity, double unitPrice, double totalPrice) {
            this.id = id;
            this.title = title;
            this.sellerSku = sellerSku;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
            this.totalPrice = totalPrice;
        }
    }
}
