package freemarker.debug;

import junit.framework.TestCase;

public class DebuggerServiceRegistryTest extends TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        System.clearProperty("freemarker.debug.password");
        DebuggerServiceRegistry.init();
    }
    public void testNopebugger() throws Exception {
        DebuggerService service = DebuggerServiceRegistry.getService();
        assertEquals("freemarker.debug.impl.NoOpDebuggerService", service.getClass().getName());
    }

    
    public void testRMIDebugger() throws Exception {
        System.setProperty("freemarker.debug.password", "xxx");
        DebuggerService service = DebuggerServiceRegistry.getService();
        assertEquals("freemarker.debug.impl.RmiDebuggerService", service.getClass().getName());
    }
}
