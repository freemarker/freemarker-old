package freemarker.core;

import freemarker.template.*;
import java.util.*;
import junit.framework.TestCase;
import java.io.*;

public class DateFormatTest extends TestCase {

	private Configuration cfg;
	private Date date = null;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		Configuration cfg = new Configuration();
        cfg.setDirectoryForTemplateLoading(new File("src/test/java/freemarker/core/"));
        cfg.setObjectWrapper(new DefaultObjectWrapper());
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
		cfg.setTimeZone(TimeZone.getTimeZone("GMT"));
		cfg.setLocale(Locale.UK);
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2014);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		date = cal.getTime();
	}

	private void contentEquals(Map<String, Object> dataMap, String expectedContent) throws Exception {
		StringWriter writer = new StringWriter();
		Template template = new Template("<date>", new StringReader("${date?string}"), cfg);
		
		Environment env = template.createProcessingEnvironment(dataMap, writer);
		// Set TimeZone to Alaska Daylight Time (-08:00)
		env.setTimeZone(TimeZone.getTimeZone("AST"));
		env.process();
		
		String actual = writer.toString();
		assertEquals(expectedContent, actual);
	}
	
	public void testSQLDate() throws Exception {
        Map root = new HashMap();
        root.put("date", new java.sql.Date(date.getTime()));
		contentEquals(root, "01-Jan-2014");
	}

}