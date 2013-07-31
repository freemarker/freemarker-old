package freemarker.debug;

import java.lang.reflect.Method;
import java.util.Iterator;

import freemarker.log.Logger;

/**
 * JDK ServiceLoader is used to load services declared in the
 * META-INF/services/MyClass. Switch JDK using, it uses:
 * <ul>
 * <li><b>java.util.ServiceLoader</b> if XDocReport works on Java6. For example
 * :
 * <p>
 * <code>Iterator<Discovery> discoveries =
                ServiceLoader.load( registryType, getClass().getClassLoader() ).iterator();</code>
 * </p>
 * </li>
 * <li><b>javax.imageio.spi.ServiceRegistry</b> if XDocReport works on Java5.
 * For example :
 * <p>
 * <code>Iterator<Discovery> discoveries =
                ServiceRegistry.lookupProviders( registryType, getClass().getClassLoader() );</code>
 * </p>
 * </li>
 * </ul>
 */
public abstract class JDKServiceLoader {
    private static final Logger LOGGER = Logger.getLogger("freemarker.debug");

    // The JDK Service loader to use.
    private static JDKServiceLoader JDK_SERVICE_LOADER;

    static {
        ClassLoader classLoader = JDKServiceLoader.class.getClassLoader();
        try {
            // At first, try to use JDK6 java.util.ServiceLoader
            JDK_SERVICE_LOADER = new JDK6ServiceLoader(classLoader);
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Uses JDK6 java.util.ServiceLoader to load services.");
            }
        } catch (Throwable e) {
            // JDK6 is not used here, uses the JDK5
            // javax.imageio.spi.ServiceRegistry
            try {
                JDK_SERVICE_LOADER = new JDK5ServiceLoader(classLoader);
                if (LOGGER.isInfoEnabled()) {
                    LOGGER.info("Uses JDK5 javax.imageio.spi.ServiceRegistry to load services.");
                }
            } catch (Throwable e1) {
                // Should never thrown.
                if (LOGGER.isErrorEnabled()) {
                    LOGGER.error("Error while initialization of JDKServiceLoader", e1);
                }
            }
        }
    }

    public static Iterator lookupProviders(Class providerClass, ClassLoader loader) {
        try {
            return JDK_SERVICE_LOADER.lookupProvidersFromJDK(providerClass, loader);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract Iterator lookupProvidersFromJDK(Class providerClass, ClassLoader loader) throws Exception;

    private static class JDK5ServiceLoader extends JDKServiceLoader {
        private final Method lookupProvidersMethod;

        public JDK5ServiceLoader(ClassLoader classLoader) throws ClassNotFoundException, SecurityException,
                NoSuchMethodException {
            Class slc = classLoader.loadClass("javax.imageio.spi.ServiceRegistry");
            lookupProvidersMethod = slc.getMethod("lookupProviders", new Class[] { Class.class, ClassLoader.class });
        }

        // @SuppressWarnings("unchecked")
        // @Override
        protected Iterator lookupProvidersFromJDK(Class providerClass, ClassLoader loader) throws Exception {
            return (Iterator) lookupProvidersMethod.invoke(null, new Object[] { providerClass, loader });
        }
    }

    private static class JDK6ServiceLoader extends JDKServiceLoader {
        private Method loadMethod;

        private Method iteratorMethod;

        public JDK6ServiceLoader(ClassLoader classLoader) throws ClassNotFoundException, SecurityException,
                NoSuchMethodException {
            Class slc = classLoader.loadClass("java.util.ServiceLoader");
            loadMethod = slc.getMethod("load", new Class[] {Class.class, ClassLoader.class});
            iteratorMethod = slc.getMethod("iterator", new Class[] {});
        }

        // @SuppressWarnings("unchecked")
        // @Override
        protected Iterator lookupProvidersFromJDK(Class providerClass, ClassLoader loader) throws Exception {
            Object serviceLoader = loadMethod.invoke(null, new Object[] {providerClass, loader});
            return (Iterator) iteratorMethod.invoke(serviceLoader, new Class[] {});
        }
    }
}
