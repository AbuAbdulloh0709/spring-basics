package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    private static final String QUERY_SELECT_MOST_POPULAR_TAGS = "WITH OrderedTags AS (\n" +
            "    SELECT t.id, t.name, " +
            "           RANK() OVER (ORDER BY count(t.id) DESC, sum(o.price) DESC) AS tag_rank\n" +
            "    FROM (select * from orders where user_id = :user_id) as o\n" +
            "             JOIN gift_certificates gc ON o.gift_certificate_id = gc.id\n" +
            "             JOIN gift_certificates_tags gct ON gc.id = gct.gift_certificate_id\n" +
            "             JOIN tags t ON t.id = gct.tag_id GROUP BY t.id\n" +
            ") SELECT id,name FROM OrderedTags where tag_rank = 1;";

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
        return entityManager.createQuery(QUERY_SELECT_MOST_POPULAR_TAG, Tag.class)
                .setParameter("name", name)
                .getResultList().stream()
                .findFirst();
    }

    @Override
    public List<Tag> getMostPopularTagsWithHighestCostOfAllOrders(long user_id) {
        Query query = entityManager.createNativeQuery(QUERY_SELECT_MOST_POPULAR_TAGS);
        query.setParameter("user_id", user_id);
        List<Object[]> resultList = query.getResultList();
        List<Tag> tags = new ArrayList<>();
        for (Object[] result : resultList) {
            Tag tag = new Tag();
            tag.setId(((Number) result[0]).longValue());
            tag.setName((String) result[1]);
            tags.add(tag);
        }
        return tags;
    }
}
