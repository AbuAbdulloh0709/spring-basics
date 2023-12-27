package com.epam.esm.dao.creator.impl;

import com.epam.esm.dao.creator.AbstractQueryCreator;
import com.epam.esm.dao.creator.FilterParameter;
import com.epam.esm.dao.creator.QueryCreator;
import com.epam.esm.entity.Tag;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class TagQueryCreatorImpl extends AbstractQueryCreator implements QueryCreator<Tag> {
    private static final String NAME_FIELD = "name";

    @Override
    public CriteriaQuery<Tag> createFilteringGetQuery(MultiValueMap<String, String> params, CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> root = criteriaQuery.from(Tag.class);

        List<Predicate> predicates = new ArrayList<>(params.size());
        List<Order> orders = new ArrayList<>(params.size());
        for (Map.Entry<String, List<String>> entry : params.entrySet()) {
            String filterParam = entry.getKey().toLowerCase();
            String paramValue = entry.getValue()
                    .stream()
                    .findFirst()
                    .orElse("");
            switch (filterParam) {
                case FilterParameter.TAG_NAME: {
                    predicates.add(addLikePredicate(criteriaBuilder, root.get(NAME_FIELD), paramValue));
                    break;
                }
                case FilterParameter.SORT_BY_TAG_NAME: {
                    orders.add(addOrder(criteriaBuilder, root.get(NAME_FIELD), paramValue));
                    break;
                }
            }
        }
        criteriaQuery.select(root)
                .where(predicates.toArray(new Predicate[]{}))
                .orderBy(orders);
        return criteriaQuery;
    }
}
