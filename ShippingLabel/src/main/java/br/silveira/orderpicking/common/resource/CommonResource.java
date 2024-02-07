package br.silveira.orderpicking.common.resource;

import br.silveira.orderpicking.auth.security.UserDetailsImpl;
import br.silveira.orderpicking.common.specification.CommonSpecificationBuilder;
import br.silveira.orderpicking.common.specification.SearchRequest;
import br.silveira.orderpicking.organizational.entity.Company;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;

public class CommonResource {

    public String getLoggedUser(){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }

    public Long getCompanyId(){
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getCompanyId();
    }
    public Specification<?> getSpecification(SearchRequest searchRequest, CommonSpecificationBuilder<?> builder ) {
        builder.with("companyId", "eq", getCompanyId());
        if(searchRequest != null && searchRequest.getSearchCriteriaList() != null){
            searchRequest.getSearchCriteriaList().forEach(searchCriteria -> {
                searchCriteria.setDataOption(searchRequest.getDataOption());
                builder.with(searchCriteria);
            });
        }
        return builder.build();
    }

    public String getLogPrefix(){
        return String.format("[Login: %s;Company Id: %s]", getLoggedUser(), getCompanyId().toString());
    }
}
