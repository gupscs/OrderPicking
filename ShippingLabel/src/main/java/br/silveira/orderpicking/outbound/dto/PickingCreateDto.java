package br.silveira.orderpicking.outbound.dto;

import br.silveira.orderpicking.outbound.entity.*;
import lombok.*;

import java.util.*;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PickingCreateDto {

    private List<PickingCreateOrderDto> pickingCreateOrderDtos;

    private List<Employee> employees;

    @Getter(AccessLevel.NONE)
    private Integer totalQty;

    public PickingCreateDto(Employee employee) {
        this.pickingCreateOrderDtos = new ArrayList<>();
        employees = new ArrayList<>();
        employees.add(employee);
    }

    public Integer getTotalQty() {
        if (totalQty == null) totalQty = getPickingCreateOrderDtos().stream().mapToInt(o -> o.getTotalOrderQty()).sum();
        return totalQty;
    }

    public List<PickingCreateOrderDto> getPickingCreateOrderDtoSorted(Comparator<PickingCreateOrderDto> sorterLogic) {
        return pickingCreateOrderDtos.stream().sorted(sorterLogic).collect(Collectors.toList());
    }

    public void addPickingCreateOrderDto(PickingCreateOrderDto pickingCreateOrderDto) {
        if (pickingCreateOrderDtos == null) pickingCreateOrderDtos = new ArrayList<>();
        pickingCreateOrderDtos.add(pickingCreateOrderDto);
        totalQty = totalQty + pickingCreateOrderDto.getTotalOrderQty();
    }

    public Integer getQtyOfEmployees() {
        return employees.size();
    }

    public List<PickingCreateOrderDto> getPickingCreateOrderDtoSortedByItemDescAndQuantityReversed() {
        Comparator<PickingCreateOrderDto> sortedByItemDescAndQuantityReversed = Comparator
                .comparing(PickingCreateOrderDto::getFirstItemDesc)
                .thenComparing(PickingCreateOrderDto::getTotalOrderQty).reversed();
        return getPickingCreateOrderDtoSorted(sortedByItemDescAndQuantityReversed);
    }

    public List<Employee> getEmployeesSortedByName() {
        return getEmployees().stream().sorted((o1, o2) -> o1.getName().compareTo(o2.getName())).collect(Collectors.toList());
    }

    public Picking toPicking(Long companyId, Outbound outbound) {
        Picking picking = new Picking();
        picking.setOutbound(outbound);
        picking.setCompanyId(companyId);
        picking.setEmployee(getEmployees().get(0));
        picking.setPickingStatus(PickingStatus.PENDING);

        Map<String, PickingDetail> mapPickingDetail = new HashMap<>();
        pickingCreateOrderDtos.forEach(o -> o.getOrderItemDtos().forEach(i -> {
            if (!mapPickingDetail.containsKey(i.getItemCd())) {
                PickingDetail newPickingDetail = new PickingDetail(companyId, picking, i.getItemCd(), i.getItemDesc());
                mapPickingDetail.put(i.getItemCd(), newPickingDetail);
            }
            PickingDetail pickingDetail = mapPickingDetail.get(i.getItemCd());
            pickingDetail.addPickingOrderItemAndSumPickingQty(i.toPickingOrderItem(companyId, pickingDetail), i.getQuantity());
            mapPickingDetail.put(i.getItemCd(), pickingDetail);
        }));

        picking.setPickingDetail(mapPickingDetail.values().stream().collect(Collectors.toList()));
        return picking;
    }
}
