package br.silveira.orderpicking.mktplaceintegrator.service;

import br.silveira.orderpicking.common.service.CommonService;
import br.silveira.orderpicking.mktplaceintegrator.dto.OrderDto;
import br.silveira.orderpicking.mktplaceintegrator.dto.SearchOrderDto;
import br.silveira.orderpicking.mktplaceintegrator.mercadolivre.entity.MercadoLivreSetup;
import br.silveira.orderpicking.mktplaceintegrator.mercadolivre.repository.MercadoLivreClientRestRepository;
import br.silveira.orderpicking.mktplaceintegrator.mercadolivre.repository.MercadoLivreSetupRepository;
import br.silveira.orderpicking.mktplaceintegrator.mercadolivre.service.MercadoLivreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderSearchServiceImpl extends CommonService implements OrderSearchService{

    private static final Logger log = LoggerFactory.getLogger(OrderSearchServiceImpl.class);
    @Autowired
    private MercadoLivreClientRestRepository mercadoLivreClientRestRepository;
    @Autowired
    private MercadoLivreService mercadoLivreService;
    @Autowired
    private MercadoLivreSetupRepository mercadoLivreSetupRepository;
    @Override
    public List<OrderDto> getOrdersFromMarketPlacesApi(SearchOrderDto search) {
        List<MercadoLivreSetup> allByCompanyId = mercadoLivreSetupRepository.findAllByCompanyId(getCompanyId());
        allByCompanyId.forEach( c -> {
            String token = mercadoLivreService.getToken(c.getSellerId().toString());
            List<> meliOrders = mercadoLivreClientRestRepository.listOrder(token, c.getSellerId().toString(), search.toMercadoLivreFilter());
            meliOrders.stream().map(c -> ::to)
        });


        return null;
    }
}
