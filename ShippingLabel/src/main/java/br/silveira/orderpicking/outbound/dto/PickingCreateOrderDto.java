package br.silveira.orderpicking.outbound.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
public class PickingCreateOrderDto {
    private Long orderId;
    private String mktPlaceShippingId;

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private Integer totalOrderQty = 0;
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private String firstItemDesc;

    private List<OrderItemDto> orderItemDtos;

    public PickingCreateOrderDto(){}

    public PickingCreateOrderDto(Long orderId, OrderItemDto orderItemDto) {
        this.orderId = orderId;
        this.mktPlaceShippingId = orderItemDto.getMktPlaceShippingId();
        this.totalOrderQty = totalOrderQty;
        this.firstItemDesc = firstItemDesc;
        addOrderItemDto(orderItemDto);
    }

    public void addOrderItemDto( OrderItemDto orderItemDto){
        if(orderItemDtos==null){
            orderItemDtos = new ArrayList<>();
            firstItemDesc = orderItemDto.getItemDesc();
        }
        totalOrderQty = orderItemDto.getQuantity() + totalOrderQty;
        orderItemDtos.add(orderItemDto);
    }

    public Integer getTotalOrderQty() {
        if(totalOrderQty==0){
            orderItemDtos.forEach(o -> totalOrderQty = totalOrderQty + o.getQuantity());
        }
        return totalOrderQty;
    }

    public String getFirstItemDesc() {
        if(firstItemDesc == null) firstItemDesc = orderItemDtos.get(0).getItemDesc();
        return firstItemDesc;
    }
}