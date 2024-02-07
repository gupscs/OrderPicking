package br.silveira.orderpicking.outbound.dto;

import br.silveira.orderpicking.outbound.entity.PickingDetail;
import br.silveira.orderpicking.outbound.entity.PickingOrderItem;
import lombok.Data;

@Data
public class OrderItemDto {

    private Long orderId;
    private Long orderItemId;
    private String mktPlaceOrderId;
    private String mktPlaceShippingId;
    private String itemCd;
    private String itemDesc;
    private Integer quantity;

    public PickingOrderItem toPickingOrderItem(Long companyId, PickingDetail pickingDetail) {
        return  new PickingOrderItem(companyId, pickingDetail, orderId, orderItemId, mktPlaceShippingId);
    }
}
