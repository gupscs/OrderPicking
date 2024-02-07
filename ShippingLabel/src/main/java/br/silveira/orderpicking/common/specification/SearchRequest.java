package br.silveira.orderpicking.common.specification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {

    private List<SearchCriteria> searchCriteriaList;
    private String dataOption;
    private List<SearchSorter> sortBy;

    public Sort getSorter(){
        if(sortBy==null || sortBy.isEmpty()){
            return Sort.by(Sort.Direction.DESC, "id");
        }
        List<Sort.Order> orders = new ArrayList<>();
        for(SearchSorter searchSorter : sortBy){
            boolean isDesc = searchSorter.getDesc() != null && searchSorter.getDesc();
            orders.add(new Sort.Order(isDesc? Sort.Direction.DESC: Sort.Direction.ASC, searchSorter.getField()));
        }
        return Sort.by(orders);
    }
}
