package freemarker.debug.impl;

import freemarker.debug.DebuggerService;
import freemarker.debug.DebuggerServiceProvider;
import freemarker.template.utility.SecurityUtilities;

public class RmiDebuggerServiceProvider implements DebuggerServiceProvider {

    public boolean isAvailable() {
        return SecurityUtilities.getSystemProperty("freemarker.debug.password") != null;
    }

    public DebuggerService getService() {
        return new RmiDebuggerService();
    }

}
