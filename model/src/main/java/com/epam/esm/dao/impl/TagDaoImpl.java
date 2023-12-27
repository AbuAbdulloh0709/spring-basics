package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class TagDaoImpl implements TagDao {

    private static final String QUERY_GET_TAG_ALL = "select t from Tag as t";
    private static final String QUERY_GET_BY_NAME = "select t from Tag as t where t.name = :name";
    private static final String QUERY_SELECT_MOST_POPULAR_TAG = "select t from Order o " +
            "join o.giftCertificate c " +
            "join c.tags t " +
            "group by t.id order by count(t.id) desc, sum(o.price) desc";

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public Tag insert(Tag tag) {
        entityManager.persist(tag);
        return tag;
    }

    @Override
    public Optional<Tag> getById(long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public List<Tag> getAll(Pageable pageable) {
        return entityManager.createQuery(QUERY_GET_TAG_ALL, Tag.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

    @Override
    public void removeById(long id) {
        Tag tag = entityManager.find(Tag.class, id);
        entityManager.remove(tag);
    }

    @Override
    public Optional<Tag> getByName(String name) {
        return entityManager.createQuery(QUERY_GET_BY_NAME, Tag.class)
                .setParameter("name", name)
                .getResultList().stream()
                .findFirst();
    }

    @Override
    public Optional<Tag> getMostPopularTagWithHighestCostOfAllOrders() {
        return entityManager.createQuery(QUERY_SELECT_MOST_POPULAR_TAG, Tag.class)
                .getResultStream()
                .findFirst();
    }
}
