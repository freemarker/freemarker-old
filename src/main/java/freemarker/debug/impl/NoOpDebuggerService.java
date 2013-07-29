package freemarker.debug.impl;

import java.rmi.RemoteException;

import freemarker.core.Environment;
import freemarker.debug.Breakpoint;
import freemarker.debug.DebuggerListener;
import freemarker.debug.DebuggerService;
import freemarker.template.Template;

public class NoOpDebuggerService implements DebuggerService {

    public void addBreakpoint(Breakpoint breakpoint) {
    }

    public void removeBreakpoint(Breakpoint breakpoint) {
    }

    public boolean suspendEnvironment(Environment env, String templateName, int line) throws RemoteException {
        return false;
    }

    public void registerTemplate(Template template) {
    }

    public void removeDebuggerListener(Object id) {
    }

    public Object addDebuggerListener(DebuggerListener listener) {
        return null;
    }

    public void shutdown() {

    }

}
