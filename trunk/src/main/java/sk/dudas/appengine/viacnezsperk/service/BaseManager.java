package sk.dudas.appengine.viacnezsperk.service;

import sk.dudas.appengine.viacnezsperk.domain.BaseEntity;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: oli
 * Date: 8.3.2011
 * Time: 22:46
 * To change this template use File | Settings | File Templates.
 */
public interface BaseManager<K, E> {

    //    PERSIST
    void persist(E entity);

    void persistOrMerge(E entity);

    <T extends BaseEntity> void persistEntity(T entity);

    void persistNonTransactional(E entity);

    //    REMOVE
    Integer removeAll();

    void remove(K id);

    <T extends BaseEntity> void removeEntity(T entity);

    <T extends BaseEntity, I> void removeEntity(Class<T> clazz, I id);

    //    MERGE
    E merge(E entity);

    <T extends BaseEntity> T mergeEntity(T entity);

    //    FIND
    List<E> findAll();

    E findById(K id);

    <T extends BaseEntity> T findEntityById(T entity);

    <T extends BaseEntity, I> T findEntityById(Class<T> clazz, I id);

    <T extends BaseEntity> List<T> findAllEntities();

    //    FLUSH
    E flush(E entity);

    //    REFRESH
    void refresh(E entity);

}
