package br.silveira.orderpicking.common.specification;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CommonSpecificationBuilder<T> {
    private final List<SearchCriteria> params;

    public CommonSpecificationBuilder() {
        this.params = new ArrayList<>();
    }

    public final CommonSpecificationBuilder with(String key, String operation, Object value){
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    public final CommonSpecificationBuilder with(SearchCriteria
                                                      searchCriteria){
        params.add(searchCriteria);
        return this;
    }

    public Specification<T> build(){
        if(params.size() == 0){
            return null;
        }

        Specification<T> result = new CommonSpecification<T>(params.get(0));
        for (int idx = 1; idx < params.size(); idx++){
            SearchCriteria criteria = params.get(idx);
            result =  SearchOperation.getDataOption(criteria.getDataOption()) == SearchOperation.ALL
                    ? Specification.where(result).and(new CommonSpecification(criteria))
                    : Specification.where(result).or(new CommonSpecification(criteria));
        }
        return result;
    }
}
