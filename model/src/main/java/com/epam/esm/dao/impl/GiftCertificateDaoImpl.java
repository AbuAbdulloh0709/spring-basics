package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.mapper.GiftCertificateMapper;
import com.epam.esm.dao.mapper.TagMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DaoException;
import com.epam.esm.extractor.GiftCertificateFieldExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.epam.esm.exception.ExceptionDaoMessageCodes.*;

@Repository
public class GiftCertificateDaoImpl implements GiftCertificateDAO {


    private static final String QUERY_INSERT = "INSERT INTO gift_certificates(name, description, price, duration, create_date, " +
            "last_update_date) VALUES(?, ?, ?, ?, ?, ?)";
    private static final String QUERY_INSERT_TAGS = "INSERT INTO gift_certificates_tags (gift_certificate_id,tag_id) VALUES(?,?)";
    private static final String QUERY_SELECT_ALL_CERTIFICATES = "select * from gift_certificates";
    private static final String QUERY_SELECT_ALL_ACCOCIATED_TAGS = "select t.* from gift_certificates_tags gct\n" +
            "         left join tags t on t.id = gct.tag_id\n" +
            "where gift_certificate_id = ?";
    private final String QUERY_GET_GC_BY_ID = "select * from gift_certificates where id =?;";
    private final String QUERY_DELETE_GC_BY_ID = "delete from gift_certificates where id = ?";
    private static final String QUERY_DELETE_ASSOCIATED_TAGS = "delete from gift_certificates_tags where gift_certificate_id = ?";
    private static final String QUERY_INSERT_NEW_TAGS_TO_CERTIFICATE = "insert into gift_certificates_tags(gift_certificate_id, tag_id) values (?, ?);";


    private final JdbcTemplate jdbcTemplate;
    private final GiftCertificateMapper rowMapper;
    private final TagMapper tagMapper;
    private final TagDAO tagDao;
    private final GiftCertificateFieldExtractor extractor;

    @Autowired
    public GiftCertificateDaoImpl(JdbcTemplate jdbcTemplate, GiftCertificateMapper rowMapper, TagMapper tagMapper, TagDAO tagDao, GiftCertificateFieldExtractor extractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = rowMapper;
        this.tagMapper = tagMapper;
        this.tagDao = tagDao;
        this.extractor = extractor;
    }

    @Override
    @Transactional
    public void insert(GiftCertificate giftCertificate) throws DaoException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(QUERY_INSERT, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, giftCertificate.getName());
                ps.setString(2, giftCertificate.getDescription());
                ps.setBigDecimal(3, giftCertificate.getPrice());
                ps.setInt(4, giftCertificate.getDuration());
                ps.setString(5, giftCertificate.getCreateDate());
                ps.setString(6, giftCertificate.getLastUpdateDate());
                return ps;
            }, keyHolder);
            giftCertificate.setId(getNewCertificateId(keyHolder));
            addNewTagsToCertificate(giftCertificate);
        } catch (DataAccessException e) {
            System.out.println(e.getLocalizedMessage());
            throw new DaoException(SAVING_ERROR);
        }
    }

    @Override
    public GiftCertificate getById(long id) throws DaoException {
        List<GiftCertificate> giftCertificates = jdbcTemplate.query(QUERY_GET_GC_BY_ID, rowMapper, id);
        if (giftCertificates.size() != 0) {
            GiftCertificate giftCertificate = giftCertificates.get(0);
            giftCertificate.setTags(getAccociatedTags(giftCertificate.getId()));
            return giftCertificate;
        } else {
            throw new DaoException(NO_ENTITY_WITH_ID);
        }
    }

    private List<Tag> getAccociatedTags(long gc_id) {
        return jdbcTemplate.query(QUERY_SELECT_ALL_ACCOCIATED_TAGS, tagMapper, gc_id);
    }

    @Override
    public List<GiftCertificate> getAll() throws DaoException {
        try {
            List<GiftCertificate> list = jdbcTemplate.query(QUERY_SELECT_ALL_CERTIFICATES, rowMapper);
            list.forEach(giftCertificate -> giftCertificate.setTags(getAccociatedTags(giftCertificate.getId())));
            return list;
        } catch (DataAccessException e) {
            throw new DaoException(NO_ENTITY);
        }
    }

    @Override
    public void removeById(long id) throws DaoException {
        try {
            jdbcTemplate.update(QUERY_DELETE_GC_BY_ID, id);
        } catch (DataAccessException e) {
            throw new DaoException(SAVING_ERROR);
        }
    }

    @Override
    public void update(GiftCertificate updateCertificate) throws DaoException {
        Map<String, String> params = extractor.extractData(updateCertificate);
        String updateQuery = updateQuery(params);
        int affectedRows = jdbcTemplate.update(updateQuery);
        if (affectedRows == 0) {
            throw new DaoException(NO_ENTITY_WITH_ID);
        }
        if (updateCertificate.getTags() != null) {
            updateCertificateTags(updateCertificate);
        }
    }

    private void updateCertificateTags(GiftCertificate item) throws DaoException {
        List<Tag> newTags = createTagsWithId(item.getTags());
        jdbcTemplate.update(QUERY_DELETE_ASSOCIATED_TAGS, item.getId());
        newTags.forEach((newTag) ->
                jdbcTemplate.update(
                        QUERY_INSERT_NEW_TAGS_TO_CERTIFICATE,
                        item.getId(),
                        newTag.getId()
                )
        );
    }

    private List<Tag> createTagsWithId(List<Tag> requestTags) throws DaoException {
        List<Tag> newTagsWithId = new ArrayList<>(requestTags.size());
        for (Tag requestTag : requestTags) {
            Tag tagWithId = tagDao.getByName(requestTag.getName());
            newTagsWithId.add(tagWithId);
        }
        return newTagsWithId;
    }

    private String updateQuery(Map<String, String> params) {
        StringBuilder queryBuilder = new StringBuilder("UPDATE gift_certificates SET ");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            queryBuilder.append(entry.getKey()).append(" = '").append(entry.getValue()).append("', ");
        }
        queryBuilder.delete(queryBuilder.length() - 2, queryBuilder.length());
        queryBuilder.append(" where id=").append(params.get("id"));
        return queryBuilder.toString();
    }

    private void addNewTagsToCertificate(GiftCertificate item) {
        if (item.getTags() == null) {
            return;
        }
        for (Tag tag : item.getTags()) {
            jdbcTemplate.update(QUERY_INSERT_TAGS, item.getId(), tag.getId());
        }
    }

    private long getNewCertificateId(KeyHolder keyHolder) throws DaoException {
        Map<String, Object> keys = keyHolder.getKeys();
        if (keys == null) {
            throw new DaoException(SAVING_ERROR);
        }
        return (long) keys.get("id");
    }
}
