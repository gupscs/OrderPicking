package br.silveira.orderpicking.common.specification;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Objects;

public class CommonSpecification<T> implements Specification<T> {

    private final SearchCriteria searchCriteria;

    public CommonSpecification(SearchCriteria searchCriteria) {
        super();
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Specification<T> and(Specification<T> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<T> or(Specification<T> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        String strToSearch = searchCriteria.getValue().toString().toLowerCase();

        switch (Objects.requireNonNull(SearchOperation.getSimpleOperation(searchCriteria.getOperation()))) {
            case DIFF:
                 return criteriaBuilder.notEqual(root.<String>get(searchCriteria.getFilterKey()),  searchCriteria.getValue().toString());
            case LIKE:
                if (root.get(searchCriteria.getFilterKey()).getJavaType() == String.class) {
                    return criteriaBuilder.like(root.<String>get(searchCriteria.getFilterKey()),  searchCriteria.getValue().toString());
                } else {
                    return criteriaBuilder.equal(root.get(searchCriteria.getFilterKey()), searchCriteria.getValue().toString());
                }
            case EQUAL:
                return criteriaBuilder.equal(root.<String>get(searchCriteria.getFilterKey()),  searchCriteria.getValue().toString());
            case LESS_THAN:
                return criteriaBuilder.lessThan(root.<String>get(searchCriteria.getFilterKey()),  searchCriteria.getValue().toString());
            case GREATER_THAN:
                return criteriaBuilder.greaterThan(root.<String>get(searchCriteria.getFilterKey()),  searchCriteria.getValue().toString());
            case LESS_THAN_EQUAL:
                return criteriaBuilder.lessThanOrEqualTo(root.<String>get(searchCriteria.getFilterKey()),  searchCriteria.getValue().toString());
            case GREATER_THAN_EQUAL:
                return criteriaBuilder.greaterThanOrEqualTo(root.<String>get(searchCriteria.getFilterKey()),  searchCriteria.getValue().toString());
        }
        return null;
    }
}
