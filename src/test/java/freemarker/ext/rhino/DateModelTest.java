package freemarker.ext.rhino;

import java.io.StringWriter;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import org.mozilla.javascript.*;

import freemarker.template.Template;

@RunWith(JUnit4.class)
public class DateModelTest {

    @Test
    public void formatDate() throws Exception {
        assertEquals("25.11.2013 0:00:00", process("new Date(2013,10,25)"));
    }

    @Test
    public void formatDateTime() throws Exception {
        assertEquals("02.04.2015 21:12:16", process("new Date(2015,03,02, 21,12,16)"));
    }

    private String process(String js) throws Exception {
        Context cx = Context.enter();
        try {
            Scriptable go = cx.initStandardObjects();
            Object o = cx.evaluateString(go, "({x:" + js + "})", null, 0, null);
            Template t = new Template(null, "${x}", null);
            StringWriter sw = new StringWriter();
            t.process(o, sw, new RhinoWrapper());
            return sw.toString();
        } finally {
            Context.exit();
        }
    }
}
