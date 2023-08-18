package br.silveira.orderpicking.mktplaceintegrator.service;

import br.silveira.orderpicking.mktplaceintegrator.dto.OrderDto;
import br.silveira.orderpicking.mktplaceintegrator.dto.SearchOrderDto;

import java.util.List;

public interface OrderSearchService {
    public List<OrderDto> getOrdersFromMarketPlacesApi(SearchOrderDto search);
}
