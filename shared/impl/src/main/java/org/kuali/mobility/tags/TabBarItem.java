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
public class TabBarItem extends SimpleTagSupport {

    private static Logger LOG = Logger.getLogger( TabBarItem.class );

    private String url;
    private String label;

    @Override
    public void doTag() throws JspException {
        PageContext pageContext = (PageContext)getJspContext();
        JspWriter out = pageContext.getOut();
        try {
            out.println( "<li>" );
            if( null != getUrl() && !getUrl().trim().isEmpty() ) {
                out.println( "<a href=\"" + getUrl().trim() + "\">" );
            }
            if( null != getLabel() && !getLabel().trim().isEmpty() ) {
                out.println( getLabel().trim() );
            } else {
                getJspBody().invoke( out );
            }
            if( null != getUrl() && !getUrl().trim().isEmpty() ) {
                out.println( "</a>" );
            }
            out.println( "</li>" );
        } catch( Exception e ) {
            LOG.error( e.getLocalizedMessage(), e );
        }
    }


    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }


}
