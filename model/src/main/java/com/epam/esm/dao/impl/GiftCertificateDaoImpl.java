package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.creator.QueryCreator;
import com.epam.esm.entity.GiftCertificate;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDao {


    private static final String QUERY_SELECT_ALL_CERTIFICATES = "select gc from GiftCertificate as gc ";
    private final String QUERY_GET_GC_BY_NAME = "select gc from GiftCertificate as gc INNER JOIN gc.tags t where t.id =:tag_id";
    private final String QUERY_GET_GC_BY_TAG_ID = "select gc from GiftCertificate as gc INNER JOIN gc.tags t where t.id =:tag_id";

    @PersistenceContext
    protected EntityManager entityManager;

    protected final QueryCreator<GiftCertificate> queryCreator;

    public GiftCertificateDaoImpl(QueryCreator<GiftCertificate> queryCreator) {
        this.queryCreator = queryCreator;
    }


    @Override
    public GiftCertificate insert(GiftCertificate giftCertificate) {
        entityManager.persist(giftCertificate);
        return giftCertificate;
    }

    @Override
    public Optional<GiftCertificate> getById(long id) {
        return Optional.ofNullable(entityManager.find(GiftCertificate.class, id));
    }

    @Override
    public List<GiftCertificate> getAll(Pageable pageable) {
        return entityManager.createQuery(QUERY_SELECT_ALL_CERTIFICATES, GiftCertificate.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }


    @Override
    @Transactional
    public void removeById(long id) {
        GiftCertificate entity = entityManager.find(GiftCertificate.class, id);
        entityManager.remove(entity);
    }

    @Override
    public Optional<GiftCertificate> getByName(String name) {
        return entityManager.createQuery(QUERY_GET_GC_BY_NAME, GiftCertificate.class)
                .setParameter("name", name)
                .getResultList().stream()
                .findFirst();
    }

    @Override
    @Transactional
    public GiftCertificate update(GiftCertificate updateCertificate) {
        return entityManager.merge(updateCertificate);
    }

    @Override
    public List<GiftCertificate> doFilter(MultiValueMap<String, String> params, Pageable pageable) {
        CriteriaQuery<GiftCertificate> criteriaQuery = queryCreator.createFilteringGetQuery(
                params,
                entityManager.getCriteriaBuilder()
        );
        return entityManager.createQuery(criteriaQuery)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultStream()
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public boolean giftCertificatesHasTagByTagID(long tagId) {
        return entityManager.createQuery(QUERY_GET_GC_BY_TAG_ID, GiftCertificate.class)
                .setParameter("tag_id", tagId)
                .getResultList().stream()
                .findFirst().isPresent();
    }

}
