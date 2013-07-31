package freemarker.debug;

import java.util.Iterator;

import freemarker.debug.impl.NoOpDebuggerService;

public class DebuggerServiceRegistry {

    private static DebuggerService defaultDebuggerService = null;

    public static DebuggerService getService() {
        if (defaultDebuggerService == null) {
            defaultDebuggerService = findService();
        }
        return defaultDebuggerService;
    }

    private static DebuggerService findService() {
        Iterator providers = JDKServiceLoader.lookupProviders(DebuggerServiceProvider.class,
                DebuggerServiceRegistry.class.getClassLoader());
        while (providers.hasNext()) {
            DebuggerServiceProvider provider = (DebuggerServiceProvider) providers.next();
            if (provider.isAvailable()) {
                return provider.getService();
            }
        }
        return new NoOpDebuggerService();
    }
    
    public static void init() {
        defaultDebuggerService = null;
    }
}
