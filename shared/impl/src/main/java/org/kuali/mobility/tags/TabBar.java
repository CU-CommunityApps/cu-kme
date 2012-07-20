/*
 * Copyright 2011 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */
package org.kuali.mobility.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.apache.log4j.Logger;

/**
 *
 * @author Joe Swanson <joseswan@umich.edu>
 */
public class TabBar extends SimpleTagSupport {

    private static Logger LOG = Logger.getLogger( TabBar.class );

    private String id;
    private boolean showIcons;
    private String iconPosition;
    private String cssClass;
    private boolean footer;

    @Override
    public void doTag() throws JspException {
        PageContext pageContext = (PageContext)getJspContext();
        JspWriter out = pageContext.getOut();
        try {
            if( isFooter() ) {
                out.println( "<div data-role=\"footer\" data-position=\"fixed\">" );
            }
            out.println( "<div data-role=\"navbar\"" + (id != null && !"".equals(id.trim()) ? " id=\"" + id.trim() + "\"" : "") + (cssClass != null && !"".equals(cssClass.trim()) ? " class=\"" + cssClass.trim() + "\"" : "") + ( getShowIcons() && null != getIconPosition() ? " data-iconpos=\"" + getIconPosition().trim() + "\"" : "" ) + "><ul>" );
            getJspBody().invoke( out );

            out.println( "</ul></div>" );
            if( isFooter() ) {
                out.println( "</div>" );
            }
        } catch( Exception e ) {
            LOG.error( e.getLocalizedMessage(), e );
        }
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the iconPosition
     */
    public String getIconPosition() {
        return iconPosition;
    }

    /**
     * @param iconPosition the iconPosition to set
     */
    public void setIconPosition(String iconPosition) {
        this.iconPosition = iconPosition;
    }

    /**
     * @return the cssClass
     */
    public String getCssClass() {
        return cssClass;
    }

    /**
     * @param cssClass the cssClass to set
     */
    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    /**
     * @return the isFooter
     */
    public boolean isFooter() {
        return footer;
    }

    /**
     * @param isFooter the isFooter to set
     */
    public void setFooter(boolean isFooter) {
        this.footer = isFooter;
    }

    /**
     * @return the showIcons
     */
    public boolean getShowIcons() {
        return showIcons;
    }

    /**
     * @param showIcons the showIcons to set
     */
    public void setShowIcons(boolean showIcons) {
        this.showIcons = showIcons;
    }
}
