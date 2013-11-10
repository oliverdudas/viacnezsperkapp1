package sk.dudas.appengine.viacnezsperk.dao;

import sk.dudas.appengine.viacnezsperk.domain.BaseEntity;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: oli
 * Date: 8.3.2011
 * Time: 22:36
 * To change this template use File | Settings | File Templates.
 */
public interface BaseDao<K, E> {

    void persist(E entity);

    <T extends BaseEntity> void persistEntity(T entity);

    void remove(K id);

    void remove(long id);

    <T extends BaseEntity> void removeEntity(T entity);

    <T extends BaseEntity, I> void removeEntity(Class<T> clazz, I id);

    E merge(E entity);

    <T extends BaseEntity> T mergeEntity(T entity);

    void refresh(E entity);

    E findById(K id);

    E findById(long id);

    <T extends BaseEntity> T findEntityById(T entity);

    <T extends BaseEntity, I> T findEntityById(Class<T> clazz, I id);

    E flush(E entity);

    List<E> findAll();

    <T extends BaseEntity> List<T> findAllEntities();

    Integer removeAll();

    List<E> find(int count);
}
