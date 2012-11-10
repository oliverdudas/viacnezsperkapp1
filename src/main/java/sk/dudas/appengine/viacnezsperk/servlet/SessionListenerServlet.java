package sk.dudas.appengine.viacnezsperk.servlet;

import com.google.appengine.api.datastore.*;

import javax.cache.*;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: oli
 * Date: 10.11.2012
 * Time: 17:44
 * To change this template use File | Settings | File Templates.
 */
@Deprecated
/**
 * Only example
 */
public class SessionListenerServlet implements HttpSessionListener {

    private static final Logger logger = Logger.getLogger(SessionListenerServlet.class.getName());

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        System.out.print(getTime() + " (session) Created:");
        System.out.println("ID=" + session.getId() + " MaxInactiveInterval="
                + session.getMaxInactiveInterval());
        clearSessions();
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        // session has been invalidated and all session data (except Id)is no longer available
        System.out.println(getTime() + " (session) Destroyed:ID="
                + session.getId());

    }

    private String getTime() {
        return new Date(System.currentTimeMillis()).toString();
    }

    private void getSessions() {
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("_ah_SESSION");
//        query.setKeysOnly();
        Set<Key> keys = new HashSet<Key>();
        for (Entity entity : ds.prepare(query).asList(FetchOptions.Builder.withLimit(500))) {
            keys.add(entity.getKey());
        }

    }

    private void clearSessions() {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("_ah_SESSION");
        PreparedQuery results = datastore.prepare(query);

        logger.info("Deleting " + results.countEntities() + " sessions from data store");

        for (Entity session : results.asIterable()) {
            Entity session1 = session;
//            datastore.delete(session.getKey());
        }
    }

// clearing everything in the cache, because sessions are also kept in memcache


    private void clearCache() throws CacheException {
        CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
        Cache cache = cacheFactory.createCache(Collections.emptyMap());

        CacheStatistics stats = cache.getCacheStatistics();
        logger.info("Clearing " + stats.getObjectCount() + " objects in cache");

        cache.clear();
    }
}
