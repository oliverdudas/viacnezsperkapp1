package sk.dudas.appengine.viacnezsperk.dao;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import sk.dudas.appengine.viacnezsperk.domain.BaseEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: oli
 * Date: 8.3.2011
 * Time: 21:37
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseDaoJpa<K, E extends BaseEntity> extends JpaDaoSupport implements BaseDao<K, E> {

    protected Class<E> entityClass;

//    @PersistenceContext
//    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public BaseDaoJpa(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    public void persist(E entity) {
        getJpaTemplate().persist(entity);
    }

    public <T extends BaseEntity> void persistEntity(T entity) {
        getJpaTemplate().persist(entity);
    }

    public void remove(K id) {
        final E entity = findById(id);
        getJpaTemplate().remove(entity);
    }

    public void remove(long id) {
        final E entity = findById(id);
        getJpaTemplate().remove(entity);
    }

    public <T extends BaseEntity> void removeEntity(T entity) {
        T attachedEntity = findEntityById(entity);
        getJpaTemplate().remove(attachedEntity);
    }

    public <T extends BaseEntity, I> void removeEntity(Class<T> clazz, I id) {
        T attachedEntity = findEntityById(clazz, id);
        getJpaTemplate().remove(attachedEntity);
    }

    public E merge(E entity) {
//        flush(entity);
        return getJpaTemplate().merge(entity);
    }

    public <T extends BaseEntity> T mergeEntity(T entity) {
        return getJpaTemplate().merge(entity);
    }

    public void refresh(E entity) {
        getJpaTemplate().refresh(entity);
    }

    public E findById(K id) {
        return getJpaTemplate().find(entityClass, id);
    }

    public E findById(long id) {
        return getJpaTemplate().find(entityClass, id);
    }

    public <T extends BaseEntity> T findEntityById(T entity) {
        return (T) findEntityById(entity.getClass(), entity.getKey());
    }

    //
    public <T extends BaseEntity, I> T findEntityById(Class<T> clazz, I id) {
        return getJpaTemplate().find(clazz, id);
    }

    public E flush(E entity) {
        getJpaTemplate().flush();
        return entity;
    }

    @SuppressWarnings("unchecked")
    public List<E> findAll() {
        return getJpaTemplate().execute(new JpaCallback<List<E>>() {

            public List<E> doInJpa(EntityManager em) throws PersistenceException {
                Query query = em.createQuery("SELECT h FROM " + entityClass.getName() + " h");
                List<E> resultList = (List<E>) query.getResultList();
                resultList.size();
                return resultList;
            }
        });
    }

    public <T extends BaseEntity> List<T> findAllEntities() {
        return getJpaTemplate().execute(new JpaCallback<List<T>>() {

            public List<T> doInJpa(EntityManager em) throws PersistenceException {
                Query query = em.createQuery("SELECT h FROM " + entityClass.getName() + " h");
                List<T> resultList = (List<T>) query.getResultList();
                resultList.size();
                return resultList;
            }
        });
    }

    @SuppressWarnings("unchecked")
    public Integer removeAll() {
        return (Integer) getJpaTemplate().execute(new JpaCallback() {
            public Object doInJpa(EntityManager em) throws PersistenceException {
                Query q = em.createQuery("DELETE FROM " + entityClass.getName() + " h");
                return q.executeUpdate();
            }
        });
    }
}
