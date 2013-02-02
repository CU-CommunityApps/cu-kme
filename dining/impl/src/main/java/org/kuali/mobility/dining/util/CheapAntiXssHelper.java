package org.kuali.mobility.dining.util;

import java.util.regex.Pattern ;

/**
 * Mostly lifted from ... 
 * http://ricardozuasti.com/2012/stronger-anti-cross-site-scripting-xss-filter-for-java-web-apps/
 * 
 * Just to patch dining. We should generalize an approach to XSS within KME.
 */
/* ------------------------------------------------------------------------- */
public class CheapAntiXssHelper {

private Pattern[] patterns ;

public CheapAntiXssHelper () {

patterns = new Pattern[] {
	Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
	// src='...'
	Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
	Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
	// lonely script tags
    Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
    Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
    Pattern.compile("<title>", Pattern.CASE_INSENSITIVE),
    Pattern.compile("</title>", Pattern.CASE_INSENSITIVE),
    // eval(...)
    Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
    // expression(...)
    Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
    // javascript:...
    Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
    // vbscript:...
    Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
    // onload(...)=...
    Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
    Pattern.compile("<", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
    Pattern.compile(">", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL)
	} ;
}
 
public String stripXSS (String sInput) {

if (sInput != null) 
	{
   // NOTE: It's highly recommended to use the ESAPI library and uncomment the following line to
   // avoid encoded attacks.
   // value = ESAPI.encoder().canonicalize(value);
 
   // Avoid null characters
   sInput = sInput.replaceAll("\0", "");
 
   // Remove all sections that match a pattern
   for (Pattern scriptPattern : patterns)
	   {
	   sInput = scriptPattern.matcher(sInput).replaceAll("");
	   }
	}
        
return sInput;
}

}
