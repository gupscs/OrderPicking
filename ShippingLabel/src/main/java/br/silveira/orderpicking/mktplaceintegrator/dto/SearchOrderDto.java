package br.silveira.orderpicking.mktplaceintegrator.dto;

import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

public class SearchOrderDto {

    private String status;
    private LocalDateTime createdFrom;
    private LocalDateTime createdTo;

    public String toMercadoLivreFilter() {
        //https://api.mercadolibre.com/orders/search?seller=$SELLER_ID&order.date_created.from=2015-07-01T00:00:00.000-00:00&order.date_created.to=2015-07-31T00:00:00.000-00:00
        StringBuffer sbFilter = new StringBuffer();
        if (StringUtils.hasText(status)) {
            sbFilter.append("order.status=");
            sbFilter.append();
        }
    }

}
