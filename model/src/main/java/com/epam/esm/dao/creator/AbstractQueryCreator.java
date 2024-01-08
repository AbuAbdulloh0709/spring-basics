package com.epam.esm.dao.creator;

import jakarta.persistence.criteria.*;


public abstract class AbstractQueryCreator {
    protected Predicate addLikePredicate(CriteriaBuilder criteriaBuilder,
                                         Expression<String> expression,
                                         String parameter) {
        return criteriaBuilder.like(
                criteriaBuilder.lower(expression),
                "%" + parameter.toLowerCase() + "%");
    }

    protected Order addOrder(CriteriaBuilder criteriaBuilder,
                             Expression<String> expression,
                             String sortType) {
        Order order = null;
        switch (sortType.toLowerCase()) {
            case "asc" -> order = criteriaBuilder.asc(expression);
            case "desc" -> order = criteriaBuilder.desc(expression);
        }
        return order;
    }
}
