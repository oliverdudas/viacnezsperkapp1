package sk.dudas.appengine.viacnezsperk.controller.cron;

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
    private static final String SESSIONCLEANUP_VIEW = "/sessioncleanup";
    private static final String NUM = "num";
    private static final String AH_SESSION = "_ah_SESSION";
    private static final String EXPIRES = "_expires";
    private static final String EMPTY_VIEW = "templates/empty";

    @RequestMapping(value = SESSIONCLEANUP_VIEW)
    public String cleanup(HttpServletRequest request, HttpServletResponse response) {
        String numString = request.getParameter(NUM);
        int limit = numString == null ? 375 : Integer.parseInt(numString);
        Query query = new Query(AH_SESSION);
        query.addFilter(EXPIRES, Query.FilterOperator.LESS_THAN,
                System.currentTimeMillis() - 86400000); //24*3600*1000 = 1day
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
            return EMPTY_VIEW;
        }

        response.setStatus(200);
        String message = (new StringBuilder()).append("Cleared ").append(killList.size()).append(" expired sessions.").toString();
        logger.info(message);

        return EMPTY_VIEW;
    }
}
