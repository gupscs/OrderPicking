package br.silveira.orderpicking.outbound.dto;

import br.silveira.orderpicking.common.constants.MktPlaceEnum;
import br.silveira.orderpicking.outbound.entity.*;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class OutboundCreateDto {

    private MktPlaceEnum mktPlace;
    private List<OrderItemDto> orderItemDto;

    private List<Employee> employees;

    private boolean sameEmployeeForPickingAndPacking;

    private boolean splitMultiAndSingleSkuOrders;

    public Outbound toOutbound(Long companyId) {
        Outbound out = new Outbound();
        Set<Long> ordersIds = new HashSet<>();
        orderItemDto.forEach(o -> ordersIds.add(o.getOrderId())); //remove dup
        List<OutboundOrders> outOrders = new ArrayList<>();
        ordersIds.forEach(o -> {
            OutboundOrders oo = new OutboundOrders();
            oo.setCompanyId(companyId);
            oo.setOrderId(o);
            oo.setOutbound(out);
            outOrders.add(oo);
        });
        out.setMktPlace(mktPlace);
        out.setPickingStatus(PickingStatus.PENDING);
        out.setPackingStatus(PackingStatus.PENDING);
        out.setCompletedStatus(0.0);
        out.setOutboundOrders(outOrders);
        return out;
    }

    public PickingCreateDto toPickingCreateDto() {
        PickingCreateDto dto = new PickingCreateDto();
        Map<Long, PickingCreateOrderDto> pkgOrders = new HashMap<>();
        orderItemDto.forEach(o -> {
            if(!pkgOrders.containsKey(o.getOrderId())){
                pkgOrders.put(o.getOrderId(), new PickingCreateOrderDto(o.getOrderId(), o));
            }else{
                PickingCreateOrderDto orderDto = pkgOrders.get(o.getOrderId());
                orderDto.addOrderItemDto(o);
                pkgOrders.put(o.getOrderId(), orderDto);
            }
        });

        dto.setEmployees(getEmployees());
        dto.setPickingCreateOrderDtos(new ArrayList<PickingCreateOrderDto>(pkgOrders.values()));
        return dto;
    }
}
