package br.silveira.orderpicking.mktplaceintegrator.resource;

import br.silveira.orderpicking.common.resource.CommonResource;
import br.silveira.orderpicking.common.specification.CommonSpecificationBuilder;
import br.silveira.orderpicking.mktplaceintegrator.dto.OrderDto;
import br.silveira.orderpicking.mktplaceintegrator.dto.SearchOrderDto;
import br.silveira.orderpicking.mktplaceintegrator.mktplaces.mercadolivre.entity.MercadoLivreSetup;
import br.silveira.orderpicking.mktplaceintegrator.mktplaces.mercadolivre.repository.MercadoLivreClientRestRepository;
import br.silveira.orderpicking.mktplaceintegrator.mktplaces.mercadolivre.repository.MercadoLivreSetupRepository;
import br.silveira.orderpicking.mktplaceintegrator.mktplaces.mercadolivre.service.MercadoLivreService;
import br.silveira.orderpicking.mktplaceintegrator.service.OrderSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/mktplace-integrator")
@Slf4j
public class MktPlaceIntegratorResource extends CommonResource {
    @Autowired
    private List<OrderSearchService> orderSearchServices;

    @Autowired
    private MercadoLivreClientRestRepository mercadoLivreClientRestRepository;

    @Autowired
    private MercadoLivreService mercadoLivreService;

    @Autowired
    private MercadoLivreSetupRepository mercadoLivreSetupRepository;

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto>> getOrders(@Valid @RequestBody(required = false) SearchOrderDto search){
        try {
            List<OrderDto> ret = new ArrayList<>();
            orderSearchServices.forEach(s -> ret.addAll(s.getOrdersFromMarketPlacesApi(search)));
            if (CollectionUtils.isEmpty(ret)) {
                return ResponseEntity.noContent().build();
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

    @GetMapping("/checkMktPlaceAccounts")
    public ResponseEntity<Void> checkMktPlaceAccounts(){
        Specification<MercadoLivreSetup> specification = (Specification<MercadoLivreSetup>) getSpecification(null, new CommonSpecificationBuilder<MercadoLivreSetup>());
        boolean exists = mercadoLivreSetupRepository.exists(specification);
        if(exists) return ResponseEntity.ok().build();
        return ResponseEntity.noContent().build();
    }
}
