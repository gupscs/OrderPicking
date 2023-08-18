package br.silveira.orderpicking.common.specification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {

    private List<SearchCriteria> searchCriteriaList;
    private String dataOption;
}
