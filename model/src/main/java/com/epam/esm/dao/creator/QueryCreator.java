package com.epam.esm.dao.creator;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.springframework.util.MultiValueMap;


public interface QueryCreator<T> {
    CriteriaQuery<T> createFilteringGetQuery(MultiValueMap<String, String> params, CriteriaBuilder criteriaBuilder);
}
