package util;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * ServletContextListener that manages the lifecycle of the
 * EntityManagerFactory.
 *
 * This listener is responsible for creating and closing the
 * EntityManagerFactory used in the application. It ensures that the
 * EntityManagerFactory is created when the web application is started and
 * closed when the web application is shut down.
 *
 * The EntityManagerFactory is created for the "RFCipher" persistence unit as
 * defined in the persistence.xml.
 *
 * @author Magdalena Koncowicz
 * @version 1.0
 */
@WebListener
public class EntityManagerFactoryListener implements ServletContextListener {

    /**
     * Static instance of EntityManagerFactory used across the application.
     *
     * This instance is initialized when the web application context is started
     * and is closed when the context is destroyed.
     */
    private static EntityManagerFactory emf;

    /**
     * Initializes the EntityManagerFactory when the web application context is
     * initialized.
     *
     * @param sce ServletContextEvent provided by the container.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        emf = Persistence.createEntityManagerFactory("RFCipher");
    }

    /**
     * Closes the EntityManagerFactory when the web application context is
     * destroyed.
     *
     * @param sce ServletContextEvent provided by the container.
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (emf != null) {
            emf.close();
        }
    }

    /**
     * Provides the EntityManagerFactory instance.
     *
     * @return The EntityManagerFactory instance used for database operations.
     */
    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
}
