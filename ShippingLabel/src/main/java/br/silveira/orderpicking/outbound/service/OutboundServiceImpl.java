package br.silveira.orderpicking.outbound.service;

import br.silveira.orderpicking.common.service.CommonService;
import br.silveira.orderpicking.order.entity.Order;
import br.silveira.orderpicking.order.entity.OrderItem;
import br.silveira.orderpicking.order.resource.OrderResource;
import br.silveira.orderpicking.outbound.dto.OutboundCreateDto;
import br.silveira.orderpicking.outbound.dto.PickingCreateDto;
import br.silveira.orderpicking.outbound.dto.PickingCreateOrderDto;
import br.silveira.orderpicking.outbound.entity.*;
import br.silveira.orderpicking.outbound.repository.OutboundRepository;
import br.silveira.orderpicking.outbound.repository.PackingRepository;
import br.silveira.orderpicking.outbound.repository.PickingRepository;
import br.silveira.orderpicking.outbound.repository.ShippingLabelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OutboundServiceImpl extends CommonService implements OutboundService{

    @Autowired
    private OutboundRepository outboundRepository;
    @Autowired
    private PickingRepository pickingRepository;
    @Autowired
    private PackingRepository packingRepository;

    @Autowired
    private OrderResource orderResource;

    @Autowired
    private ShippingLabelRepository shippingLabelRepository;

    @Override
    @Transactional
    public Long createOutbound(OutboundCreateDto dto) {
        //TODO IMPROVEMENT SPLIT TO LIST 1 FOR SINGLE SKU ORDERS AND 2 FOR MULTI SKU ORDERS (SHOULD BE IMPLEMENT ON SCREEN)
        //dto.isSplitMultiAndSingleSkuOrders()
        Outbound outbound = dto.toOutbound(getCompanyId());

        //TODO PICKING AND PACKING SHOULD BE PROCESS ON QUEUE OR BATCH
        List<Picking> pickings = createPickings(dto.toPickingCreateDto(), outbound);

        // TODO IMPLEMENT LOGIC FOR SEPARATE EMPLOYEE - CURRENT IS ALWAYS THE SAME if(dto.isSameEmployeeForPickingAndPacking())
        List<Packing> packings = createPackings(pickings, true);

        outbound = outboundRepository.save(outbound);
        pickings.forEach(o -> pickingRepository.save(o));
        packings.forEach(o -> packingRepository.save(o));

        return outbound.getId();
    }

    private List<Picking> createPickings(PickingCreateDto dto, Outbound outbound ) {
        //TODO IMPROVEMENT TO SEPARATE BY VOLUME, WHERE SHOULD CHECK THE ISIN GLOBAL INFORMATION
        //TODO IMPROVEMENT SPLIT TO LIST 1 FOR SINGLE SKU ORDERS AND 2 FOR MULTI SKU ORDERS (SHOULD BE IMPLEMENT ON SCREEN)
        //CALCULATE DIVIDE TOTAL QTY BY NUMBERS OF EMPLOYES = LIMIT FOR EACH EMPLOYEE
        Integer limitForEachEmployee = dto.getTotalQty() / dto.getQtyOfEmployees();

        //ORDER LIST BY SKY DESC
        List<PickingCreateOrderDto> pickingCreateOrderDtoSorted = dto.getPickingCreateOrderDtoSortedByItemDescAndQuantityReversed();

        //ORDER EMPLOYEE BY NAME
        List<Employee> employeesSorted = dto.getEmployeesSortedByName();

        //PICKING LOGIC FOR EACH EMPLOYEE
        Map<Employee, PickingCreateDto> pickingSeparated = processPickingCreation(pickingCreateOrderDtoSorted, employeesSorted, limitForEachEmployee);

        //CONVERT TO PICKING
        List<Picking> pickingList = new ArrayList<>();
        pickingSeparated.forEach((k, v) -> pickingList.add(v.toPicking(getCompanyId(), outbound)));
        return pickingList;
    }

    private Map<Employee, PickingCreateDto> processPickingCreation(List<PickingCreateOrderDto> pickingCreateOrderDtoSorted, List<Employee> employeesSorted, Integer limitForEachEmployee) {
        Map<Employee, PickingCreateDto> pickingSeparated = new HashMap<>();
        employeesSorted.forEach(o -> pickingSeparated.put(o, new PickingCreateDto(o)));

        pickingCreateOrderDtoSorted.forEach(o -> {
            Employee employeeWithLessLimit = null;
            int lessOverLimitDiff = 0;

            for(int i = 0 ; i < employeesSorted.size(); i++){
                Employee employee = employeesSorted.get(i);
                PickingCreateDto pickingCreateDto = pickingSeparated.get(employee);
                Integer futureQuantity = pickingCreateDto.getTotalQty() + o.getTotalOrderQty();

                if(limitForEachEmployee <= futureQuantity){
                    pickingCreateDto.addPickingCreateOrderDto(o);
                    employeeWithLessLimit = null;
                    break;
                }else {
                    int overLimit = futureQuantity - limitForEachEmployee;
                    if(overLimit < lessOverLimitDiff){
                        lessOverLimitDiff = overLimit;
                        employeeWithLessLimit = employee;
                    }
                }
            }
            if(employeeWithLessLimit != null){
                pickingSeparated.get(employeeWithLessLimit).addPickingCreateOrderDto(o);
            }
        });

        return pickingSeparated;
    }

    private List<Packing> createPackings(List<Picking> pickings, boolean isSameEmployeeForPickingAndPacking) {
        List<Packing> packings = new ArrayList<>();
        Map<String, ShippingLabel> shippingLabelMap = getShippingLabelMap(pickings);
        Map<Long, OrderItem> orderItemsMap= getOrderItemsMap(pickings);

        pickings.forEach( p -> {
            Packing packing = new Packing(p.getCompanyId(), p.getOutbound(), p.getEmployee());
            Map<String, PackingDetail> packingDetailMap = new HashMap<>();

            p.getPickingDetail().forEach(pd -> {
                pd.getPickingOrderItems().forEach( poi -> {
                    PackingDetail packingDetail;
                    if(!packingDetailMap.containsKey(poi.getMktPlaceShippingId())){
                        packingDetail = new PackingDetail(p.getCompanyId(), packing, shippingLabelMap.get(poi.getMktPlaceShippingId()));
                    }else{
                        packingDetail = packingDetailMap.get(poi.getMktPlaceShippingId());
                    }
                    packingDetail.addPackingOrderItem(poi.toPackingOrderItem(orderItemsMap.get(poi.getOrderItemId())));
                });
            });
            packing.setPackingDetail(packingDetailMap.values().stream().collect(Collectors.toList()));
            packings.add(packing);
        });

        return packings;
    }

    private Map<Long, OrderItem> getOrderItemsMap(List<Picking> pickings) {
        List<Long> orderIds = pickings.stream().flatMap(p -> p.getPickingDetail().stream()).flatMap(pd -> pd.getPickingOrderItems().stream()).map(PickingOrderItem::getOrderId).collect(Collectors.toList());
        List<Order> orders = orderResource.getOrderByIds(orderIds).getBody();
        Map<Long, OrderItem> ret = new HashMap<>();
        orders.stream().flatMap(o -> o.getOrderItems().stream()).forEach(i -> ret.put(i.getId(), i));
        return ret;
    }

    private Map<String, ShippingLabel> getShippingLabelMap(List<Picking> pickings) {
        List<String> mktPlaceShippingIds = pickings.stream().flatMap(p -> p.getPickingDetail().stream()).flatMap(pd -> pd.getPickingOrderItems().stream()).map(PickingOrderItem::getMktPlaceShippingId).collect(Collectors.toList());
        List<ShippingLabel> allByMktPlaceShippingId = shippingLabelRepository.findAllByMktPlaceShippingId(mktPlaceShippingIds);
        Map<String, ShippingLabel> ret = new HashMap<>();
        allByMktPlaceShippingId.forEach(o -> ret.put(o.getMktPlaceShippingId(), o));
        return ret;
    }
}
