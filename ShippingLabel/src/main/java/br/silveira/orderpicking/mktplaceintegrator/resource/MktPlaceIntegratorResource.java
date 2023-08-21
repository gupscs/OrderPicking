package br.silveira.orderpicking.mktplaceintegrator.resource;

import br.silveira.orderpicking.mktplaceintegrator.dto.OrderDto;
import br.silveira.orderpicking.mktplaceintegrator.dto.SearchOrderDto;
import br.silveira.orderpicking.mktplaceintegrator.mktplaces.mercadolivre.repository.MercadoLivreClientRestRepository;
import br.silveira.orderpicking.mktplaceintegrator.mktplaces.mercadolivre.service.MercadoLivreService;
import br.silveira.orderpicking.mktplaceintegrator.service.OrderSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@Slf4j
public class MktPlaceIntegratorResource {
    @Autowired
    @Qualifier("OrderSearchMercadoLivreServiceImpl")
    private OrderSearchService OrderSearchMercadoLivreService;

    @Autowired
    private MercadoLivreClientRestRepository mercadoLivreClientRestRepository;

    @Autowired
    private MercadoLivreService mercadoLivreService;


    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto>> getOrders(@Valid @RequestBody SearchOrderDto search){
        try {
            List<OrderDto> ret = OrderSearchMercadoLivreService.getOrdersFromMarketPlacesApi(search); //TODO CREATE A FACTORY
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

    public String getZplCodeShippingLabel(String sellerId, String shippingId) {
        //TODO CREATE SERVICE WITH FACTORY
        Object ret = mercadoLivreClientRestRepository.getShipmentLabelsInTypeZpl2(mercadoLivreService.getToken(sellerId), shippingId);
        return ret.toString();
    }
}
