package freemarker.debug;

public interface DebuggerServiceProvider {

    boolean isAvailable();

    DebuggerService getService();

}
