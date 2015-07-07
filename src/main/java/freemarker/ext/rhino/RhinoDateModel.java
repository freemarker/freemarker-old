package freemarker.ext.rhino;

import freemarker.template.TemplateDateModel;
import freemarker.template.TemplateModelException;
import java.util.Date;

/**
 *
 * @author Vakhurin Sergey
 */
public class RhinoDateModel implements TemplateDateModel {

    private final Date date;

    RhinoDateModel(Date date) {
        this.date = date;
    }

    @Override
    public Date getAsDate() throws TemplateModelException {
        return date;
    }

    @Override
    public int getDateType() {
        return DATETIME;
    }
}
