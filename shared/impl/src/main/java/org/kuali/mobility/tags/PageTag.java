package org.kuali.mobility.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class PageTag extends SimpleTagSupport {

    private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(PageTag.class);

    private String id;
    private String title;
    private boolean homeButton;
    private boolean backButton;
    private String backButtonURL;
    private boolean preferencesButton;
    private String preferencesButtonURL;
    private String cssFilename;
    private String jsFilename;
    
    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setHomeButton(boolean homeButton) {
        this.homeButton = homeButton;
    }
    
    public void setBackButton(boolean backButton) {
        this.backButton = backButton;
    }
    
    public void setBackButtonURL(String backButtonURL) {
        this.backButtonURL = backButtonURL;
    }
    
    public void setPreferencesButton(boolean preferencesButton) {
		this.preferencesButton = preferencesButton;
	}

	public void setPreferencesButtonURL(String preferencesButtonURL) {
		this.preferencesButtonURL = preferencesButtonURL;
	}

	public void setCssFilename(String cssFilename) {
		this.cssFilename = cssFilename;
	}
    
    public void setJsFilename(String jsFilename) {
        this.jsFilename = jsFilename;
    }

	public void doTag() throws JspException {
        PageContext pageContext = (PageContext) getJspContext();
        String contextPath = pageContext.getServletContext().getContextPath();
        JspWriter out = pageContext.getOut();
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>" + title + "</title>");
            out.println("<link href=\"" + contextPath + "/css/jquery.mobile-1.0b1.css\" rel=\"stylesheet\" type=\"text/css\" />");
            out.println("<link href=\"" + contextPath + "/css/jquery-mobile-fluid960.css\" rel=\"stylesheet\" type=\"text/css\" />");
            out.println("<link href=\"" + contextPath + "/css/custom.css\" rel=\"stylesheet\" type=\"text/css\" />");
            if (cssFilename != null && !cssFilename.trim().equals("")) {
            	out.println("<link href=\"" + contextPath + "/css/" + cssFilename + ".css\" rel=\"stylesheet\" type=\"text/css\" />");
            }
            out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/jquery-1.6.1.min.js\"></script>");
            out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/custom.js\"></script>");
            out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/jquery.mobile-1.0b1.js\"></script>");
            out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/jquery.tmpl.js\"></script>");
            out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/jquery.validate.js\"></script>");
            out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/jquery.validate.ready.js\"></script>");
            out.println("<script type=\"text/javascript\" src=\"http://maps.google.com/maps/api/js?sensor=true\"></script>");
            if (jsFilename != null && !jsFilename.trim().equals("")) {
                out.println("<script type=\"text/javascript\" src=\"" + contextPath + "/js/" + jsFilename + ".js\"></script>");
            }
            out.println("<meta name=\"viewport\" content=\"width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;\">");
            out.println("</head>");
            out.println("<body>");
            out.println("<div data-role=\"page\" id=\"" + id + "\">");
            out.println("<div data-role=\"header\">");
            if (backButton) {
                out.println("<a href=\"" + (backButtonURL != null ? backButtonURL : "javascript: history.go(-1)") + "\" class=\"ui-btn-left\" data-icon=\"back\" data-iconpos=\"notext\">Back</a>");
            }
            out.println("<h1>" + title + "</h1>");
            if (preferencesButton) {
                out.println("<a href=\"" + (preferencesButtonURL != null ? preferencesButtonURL : contextPath + "/preferences") + "\" class=\"ui-btn-right\" data-icon=\"gear\" data-iconpos=\"notext\">Preferences</a>");
            }
            if (homeButton) {
                out.println("<a href=\"" + contextPath + "/home\" class=\"ui-btn-right\" data-icon=\"home\" data-iconpos=\"notext\">Home</a>");
            }
            out.println("</div>");
            getJspBody().invoke(out);
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
}