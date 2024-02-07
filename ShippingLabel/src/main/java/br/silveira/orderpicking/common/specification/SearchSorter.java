package br.silveira.orderpicking.common.specification;

import lombok.Data;

@Data
public class SearchSorter {

    private String field;
    private Boolean desc;
}
