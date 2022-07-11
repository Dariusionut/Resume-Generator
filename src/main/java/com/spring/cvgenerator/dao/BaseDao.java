package com.spring.cvgenerator.dao;

import com.spring.cvgenerator.model.BaseModel;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Data
public abstract class BaseDao<T extends BaseModel> implements InitializingBean, Serializable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext
    protected EntityManager entityManager;

    protected Class<T> entity;

    protected String table;

    public void persist(T t){
        this.entityManager.persist(t);
        this.entityManager.flush();
    }
    @Transactional
    public T merge(T t) {
        T saved = this.entityManager.merge(t);
        this.entityManager.flush();

        return saved;
    }

    public void delete(T t) {

        this.entityManager.remove(t);
    }

    public Optional<T> findById(Long id) {
        return Optional.ofNullable(this.entityManager
                .createQuery("SELECT c FROM " + this.entity.getName() + " c WHERE c.id = :id", this.entity)
                .setParameter("id", id)
                .getSingleResult());
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        return this.entityManager
                .createQuery("SELECT c FROM " + this.getClass().getName() + " c")
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll(int limit, String orderCol, boolean isAsc) {
        logger.info("------------------findAll with parameters: limit = {}; orderCol = {}", limit, orderCol);
        return (List<T>) this.entityManager
                .createNativeQuery("SELECT u.* FROM " + this.table + " u " +
                        " ORDER BY " + orderCol, this.entity)
                .setMaxResults(limit)
                .getResultList();
    }

    public Long countAll() {
        return this.entityManager
                .createQuery("SELECT COUNT(u) FROM " + this.entity.getName() + " u", Long.class)
                .getSingleResult();
    }
}
