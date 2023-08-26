package br.silveira.orderpicking.mktplaceintegrator.service;

import br.silveira.orderpicking.common.service.CommonService;
import br.silveira.orderpicking.mktplaceintegrator.dto.OrderDto;
import br.silveira.orderpicking.mktplaceintegrator.dto.SearchOrderDto;
import br.silveira.orderpicking.mktplaceintegrator.mktplaces.mercadolivre.dto.MercadoLivreOrderDto;
import br.silveira.orderpicking.mktplaceintegrator.mktplaces.mercadolivre.entity.MercadoLivreSetup;
import br.silveira.orderpicking.mktplaceintegrator.mktplaces.mercadolivre.repository.MercadoLivreClientRestRepository;
import br.silveira.orderpicking.mktplaceintegrator.mktplaces.mercadolivre.repository.MercadoLivreSetupRepository;
import br.silveira.orderpicking.mktplaceintegrator.mktplaces.mercadolivre.service.MercadoLivreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("OrderSearchMercadoLivreServiceImpl")
@Slf4j
public class OrderSearchMercadoLivreServiceImpl extends CommonService implements OrderSearchService{

    @Autowired
    private MercadoLivreClientRestRepository mercadoLivreClientRestRepository;
    @Autowired
    private MercadoLivreService mercadoLivreService;
    @Autowired
    private MercadoLivreSetupRepository mercadoLivreSetupRepository;
    @Override
    public List<OrderDto> getOrdersFromMarketPlacesApi(SearchOrderDto search) {
        Long companyId = getCompanyId();
        log.info("Get Orders From Mercado Livre for Company Id: {} with Search: {}",companyId, search);
        List<OrderDto> ret = new ArrayList<>();
        List<MercadoLivreSetup> allByCompanyId = mercadoLivreSetupRepository.findAllByCompanyId(companyId);
        log.debug("Get Orders for Company Id {} - Total of Sellers/Accounts: {}", companyId, allByCompanyId.size());
        allByCompanyId.forEach( company -> {
            String token = mercadoLivreService.getToken(company.getSellerId().toString());
            log.debug("Successfully token read");
            List<MercadoLivreOrderDto> meliOrders = (List<MercadoLivreOrderDto>) mercadoLivreClientRestRepository.listOrder(token, company.getSellerId().toString(), getFilterParams(search));
            log.debug("Successfully orders dowloaded for Seller ID: {} - Total of Orders: {}",company.getSellerId(), meliOrders.size());
            List<OrderDto> searchRet = meliOrders.stream().map(m -> m.to()).collect(Collectors.toList());
            ret.addAll(searchRet);
        });
        log.info("Successfully Get Orders From Mercado Livre for Company Id: {} with Search: {} , Total of Orders: {}",companyId, search, ret.size());
        return ret;
    }

    private String getFilterParams(SearchOrderDto search) {
        //https://api.mercadolibre.com/orders/search?seller=$SELLER_ID&order.date_created.from=2015-07-01T00:00:00.000-00:00&order.date_created.to=2015-07-31T00:00:00.000-00:00
        StringBuffer sbFilter = new StringBuffer();
        if (search.getCreatedFrom()!= null && search.getCreatedTo()!=null) {
            sbFilter.append("order.date_created.from=");
            sbFilter.append(search.getCreatedFrom().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            sbFilter.append("order.date_created.to=");
            sbFilter.append(search.getCreatedTo().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }

        return sbFilter.toString();
    }
}
