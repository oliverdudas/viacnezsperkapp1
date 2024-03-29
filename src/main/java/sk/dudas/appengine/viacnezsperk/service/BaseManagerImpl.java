package sk.dudas.appengine.viacnezsperk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sk.dudas.appengine.viacnezsperk.dao.BaseDao;
import sk.dudas.appengine.viacnezsperk.domain.BaseEntity;
import sk.dudas.appengine.viacnezsperk.service.cache.CacheHolder;

import javax.cache.Cache;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: oli
 * Date: 8.3.2011
 * Time: 22:47
 * To change this template use File | Settings | File Templates.
 */

@Transactional(propagation = Propagation.REQUIRED)
public abstract class BaseManagerImpl<K, E extends BaseEntity> implements BaseManager<K, E> {

    private BaseDao<K, E> baseDao;

//    @PersistenceContext
//    protected EntityManager entityManager;

    private Cache cache;

    protected void setBaseDao(BaseDao<K, E> baseDao) {
        this.baseDao = baseDao;
    }

    @Autowired
    private void setPictureCache(@Qualifier("cacheHolder") CacheHolder cacheHolder) {
        this.cache = cacheHolder.getCache();
    }

    @SuppressWarnings(value = "unchecked")
    protected <C> void putObjectToCache(String key, C value) {
        cache.put(key, value);
    }

    @SuppressWarnings(value = "unchecked")
    protected  <C> C getObjectFromCache(String key) {
        return (C) cache.get(key);
    }

    public void persist(E entity) {
        baseDao.persist(entity);
    }

    public void persistOrMerge(E entity) {
        if (entity.getKey() == null) {
            baseDao.persist(entity);
        } else {
            merge(entity);
        }
    }

    public <T extends BaseEntity> void persistEntity(T entity) {
        if (entity.getKey() == null) {
            baseDao.persistEntity(entity);
        } else {
            mergeEntity(entity);
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void persistNonTransactional(E entity) {
        baseDao.persist(entity);
    }

    public void remove(K id) {
        baseDao.remove(id);
    }

    public void remove(long id) {
        baseDao.remove(id);
    }

    public <T extends BaseEntity> void removeEntity(T entity) {
        baseDao.removeEntity(entity);
    }

    public <T extends BaseEntity, I> void removeEntity(Class<T> clazz, I id) {
        baseDao.removeEntity(clazz, id);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public E mergeNonTransactional(E entity) {
        return baseDao.merge(entity);
    }

    public E merge(E entity) {
        return baseDao.merge(entity);
    }

    public <T extends BaseEntity> T mergeEntity(T entity) {
        return baseDao.mergeEntity(entity);
    }

    public void refresh(E entity) {
        baseDao.refresh(entity);
    }

    public E findById(K id) {
        return baseDao.findById(id);
    }

    public E findById(long id) {
        return baseDao.findById(id);
    }

    public <T extends BaseEntity> T findEntityById(T entity) {
        return baseDao.findEntityById(entity);
    }

    public <T extends BaseEntity, I> T findEntityById(Class<T> clazz, I id) {
        return baseDao.findEntityById(clazz, id);
    }

    public E flush(E entity) {
        return baseDao.flush(entity);
    }

    public List<E> findAll() {
        return baseDao.findAll();
    }

    public List<E> find(int count) {
        return baseDao.find(count);
    }

    public <T extends BaseEntity> List<T> findAllEntities() {
        return baseDao.findAllEntities();
    }

    public Integer removeAll() {
        return baseDao.removeAll();
    }
}
