package br.silveira.orderpicking.mktplaceintegrator.resource;

import br.silveira.orderpicking.mktplaceintegrator.dto.OrderDto;
import br.silveira.orderpicking.mktplaceintegrator.dto.SearchOrderDto;
import br.silveira.orderpicking.mktplaceintegrator.service.OrderSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/mktplace-integrator")
public class MktPlaceIntegratorResource {
    private static final Logger log = LoggerFactory.getLogger(MktPlaceIntegratorResource.class);
    @Autowired
    private OrderSearchService orderSearchService;


    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto>> getOrders(@Valid @RequestBody SearchOrderDto search){
        try {
            List<OrderDto> ret = orderSearchService.getOrdersFromMarketPlacesApi(search);
            if (CollectionUtils.isEmpty(ret)) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(ret);
            }
        }catch(Exception e ){
            log.error("Error to select the Orders", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
