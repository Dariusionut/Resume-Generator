package com.spring.cvgenerator.dao;

import com.spring.cvgenerator.dto.filter.UserFilter;
import com.spring.cvgenerator.model.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.Table;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository extends BaseDao<AppUser> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void afterPropertiesSet() {
        this.setEntity(AppUser.class);
        this.setTable(AppUser.class.getAnnotation(Table.class).name());
    }

    @SuppressWarnings("unchecked")
    public List<AppUser> findAll(UserFilter userFilter) {
        logger.info("--------------------findAll with param: filter = {}", userFilter);
        return getQuery(userFilter, false)
                .setFirstResult((userFilter.getPage() - 1) * userFilter.getPageSize())
                .setMaxResults(userFilter.getPageSize())
                .getResultList();
    }

    public Long countAll(UserFilter userFilter) {
        logger.info("--------------------countAll with param: filter = {}", userFilter);
        return Long.parseLong(this.getQuery(userFilter, true).getSingleResult().toString());
    }

    public void deleteAllInactive() {
        this.entityManager.createNativeQuery("DELETE FROM app_user WHERE is_enabled = :isEnabled")
                .setParameter("isEnabled", false)
                .executeUpdate();
    }


    private Query getQuery(UserFilter filter, boolean count) {

        StringBuilder sb = new StringBuilder("SELECT ")
                .append(count ? "COUNT(u.*) " : "u.* ")
                .append("FROM user_role ur ")
                .append("LEFT JOIN app_user u ON ur.user_id = u.id ")
                .append("LEFT JOIN role r ON ur.role_id = r.id ")
                .append("LEFT JOIN gender g ON u.gender_id = g.id ");

        sb.append(" WHERE 1 = 1 ");

        if (filter.getName() != null) {
            sb.append("AND ( LOWER(u.first_name) LIKE LOWER(:name) ")
                    .append("OR LOWER (u.last_name) LIKE LOWER(:name) ")
                    .append("OR LOWER(CONCAT(u.first_name, u.last_name)) LIKE LOWER(REGEXP_REPLACE(:name, '\\s', '', 'g')) ")
                    .append("OR LOWER(CONCAT(u.last_name, u.first_name)) LIKE LOWER(REGEXP_REPLACE(:name, '\\s', '', 'g')) ")
                    .append(")");
        }

        if (filter.getDob() != null) {
            sb.append("AND u.dob >= :dob ");
        }

        if (filter.getGenderId() != null) {
            sb.append("AND u.gender_id = :genderId ");
        }

        if (filter.getRoleId() != null) {
            sb.append("AND r.id = :roleId ");
        }

        Query query;

        if (count) {
            query = this.entityManager.createNativeQuery(sb.toString());
        } else {
            if (filter.getOrder() != null) {
                sb.append(" ORDER BY ").append(filter.getOrder()).append(filter.getIsAsc() ? " ASC " : " DESC ");
            }
            query = this.entityManager.createNativeQuery(sb.toString(), this.entity);
        }

        if (filter.getName() != null) {
            query.setParameter("name", MessageFormat.format("%{0}%", filter.getName()));
        }

        if (filter.getDob() != null) {
            query.setParameter("dob", filter.getDob());
        }

        if (filter.getGenderId() != null) {
            query.setParameter("genderId", filter.getGenderId());
        }

        if (filter.getRoleId() != null) {
            query.setParameter("roleId", filter.getRoleId());
        }

        return query;
    }

    public Optional<AppUser> findByUsernameOrEmail(String usernameOrEmail) {

        return Optional.ofNullable((AppUser)
                this.entityManager
                        .createNativeQuery("SELECT * FROM app_user WHERE username = :username OR email = :email", this.entity)
                        .setParameter("username", usernameOrEmail)
                        .setParameter("email", usernameOrEmail)
                        .getSingleResult()
        );
    }


}
