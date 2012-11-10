package sk.dudas.appengine.viacnezsperk.controller;

import com.google.appengine.api.datastore.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: oli
 * Date: 10.11.2012
 * Time: 19:24
 * To change this template use File | Settings | File Templates.
 */
@Controller("sessionCleanupController")
public class SessionCleanupController {

    private static final Logger logger = Logger.getLogger(SessionCleanupController.class.getName());

    @RequestMapping(value = "/sessioncleanup")
    public void cleanup(HttpServletRequest request, HttpServletResponse response) {
        String numString = request.getParameter("num");
        int limit = numString == null ? 375 : Integer.parseInt(numString);
        Query query = new Query("_ah_SESSION");
        query.addFilter("_expires", Query.FilterOperator.LESS_THAN,
                System.currentTimeMillis() - 604800000/*7*24*3600*1000*/);
        query.setKeysOnly();
        ArrayList<Key> killList = new ArrayList<Key>();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        PreparedQuery pq = datastore.prepare(query);
        Iterable<Entity> entities = pq.asIterable(FetchOptions.Builder.withLimit(limit));
        for (Entity expiredSession : entities) {
            Key key = expiredSession.getKey();
            killList.add(key);
        }

        try {
            datastore.delete(killList);
        } catch (DatastoreTimeoutException e) {
            response.setStatus(200);
            String message = (new StringBuilder()).append("DatastoreTimeoutException on ").append(killList.size()).append("expired sessions.").toString();
            logger.info(message);
            return;
        }

        response.setStatus(200);
        String message = (new StringBuilder()).append("Cleared ").append(killList.size()).append(" expired sessions.").toString();
        logger.info(message);
    }
}
