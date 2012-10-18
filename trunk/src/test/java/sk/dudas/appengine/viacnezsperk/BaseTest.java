package sk.dudas.appengine.viacnezsperk;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import org.junit.After;
import org.junit.Before;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * Created by IntelliJ IDEA.
 * User: oli
 * Date: 28.4.2011
 * Time: 23:18
 * To change this template use File | Settings | File Templates.
 */
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@ContextConfiguration("classpath*:applicationContext-test.xml")
public abstract class BaseTest extends AbstractJUnit4SpringContextTests {

    protected LocalServiceTestHelper helper;

    @Before
    public void setUp() {
        LocalDatastoreServiceTestConfig localDatastoreServiceTestConfig = new LocalDatastoreServiceTestConfig();
        localDatastoreServiceTestConfig.setBackingStoreLocation("local_db.bin");
        localDatastoreServiceTestConfig.setNoStorage(false);
        localDatastoreServiceTestConfig.setNoIndexAutoGen(false);
        helper = new LocalServiceTestHelper(localDatastoreServiceTestConfig);
        helper.setUp();
    }

    @After
    public void tearDown() {
        // remove tearDown to save test persistent actions
//        helper.tearDown();
    }
}