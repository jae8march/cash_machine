package com.company.app.util.tld;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Custom tag.
 */
public class CustomTag extends TagSupport {

    private static final Logger LOG = Logger.getLogger(CustomTag.class);

    @Override
    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().write("<p>");
            pageContext.getOut().write("'Cash Machine', 2021");
            pageContext.getOut().write("</p>");
        } catch(IOException e) {
            LOG.error(e.getMessage());
            throw new JspException();
        }
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() {
        return SKIP_BODY;
    }
}
