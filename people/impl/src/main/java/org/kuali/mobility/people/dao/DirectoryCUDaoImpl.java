package org.kuali.mobility.people.dao ;

import java.util.ArrayList ;
import java.util.Hashtable ;
import java.util.List ;

import javax.naming.Context ;
import javax.naming.NamingEnumeration ;
import javax.naming.NameNotFoundException ;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext ;
import javax.naming.directory.InitialDirContext ;
import javax.naming.directory.SearchResult ;
import javax.naming.directory.SearchControls ;

import org.kuali.mobility.people.entity.DirectoryEntry ;
import org.kuali.mobility.people.entity.Group ;
import org.kuali.mobility.people.entity.Person ;
import org.kuali.mobility.people.entity.PersonImpl;
import org.kuali.mobility.people.entity.SearchCriteria ;

/** 
 * It's basic, but enough to do the searches we need. The LDAP connection does an
 * "open/close", as it is doing an anonymous bind. The attribute mapping is done 
 * "by hand", as adress and phone is selected via a preferred order.
 */
/* ------------------------------------------------------------------------- */
public class DirectoryCUDaoImpl implements DirectoryDao {

private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(DirectoryCUDaoImpl.class);

private DirContext dsCtx = null ;
private String directoryUrl ;
private String searchBase ;

/* ------------------------------------------------------------------------- */
public List<DirectoryEntry> findEntries (SearchCriteria search) {

String sFilter = makeFilter(search.getSearchText()) ;
List<DirectoryEntry> alDe = new ArrayList<DirectoryEntry>() ;
try {
	connect() ;	
	SearchControls sc = new SearchControls() ;
	sc.setReturningAttributes(null) ;
	sc.setReturningObjFlag(true) ;
	sc.setSearchScope(SearchControls.SUBTREE_SCOPE) ;
	NamingEnumeration<SearchResult> neResp = dsCtx.search(searchBase, sFilter, sc) ;
	while (neResp.hasMoreElements())
		{
		PersonImpl p = new PersonImpl();
		SearchResult sr = (SearchResult)neResp.next() ;
 		mapResults(sr, p) ;
 		alDe.add(p) ;
		}
	}
catch (NameNotFoundException nnfe)
	{
	}
catch (Exception e)
	{
	LOG.error(e) ;
	}
finally
	{
	close() ;
	}

return (alDe) ;
}


/* ------------------------------------------------------------------------- */
public Person lookupPerson (String personId) {

Person p = new PersonImpl();

try {
	connect() ;

	String sFilter = "uid=" + personId ;
	SearchControls sc = new SearchControls() ;
	sc.setReturningAttributes(null) ;
	sc.setReturningObjFlag(true) ;
	sc.setSearchScope(SearchControls.SUBTREE_SCOPE) ;
	NamingEnumeration<SearchResult> neResp = dsCtx.search(searchBase, sFilter, sc) ;
	if (neResp.hasMoreElements())
		{
		SearchResult sr = (SearchResult)neResp.next() ;
		mapResults(sr, p) ;
		}
	}
catch (NameNotFoundException nnfe)
	{
	}
catch (Exception e)
	{
	LOG.error(e) ;
	}
finally
	{
	close() ;
	}

return (p) ;
}

/* ------------------------------------------------------------------------- */
public String makeFilter (String sSearch) {

String sFilter = "" ;
sSearch = sSearch.trim() ;

String sFirst = "" ;
String sMiddle = "" ;
String sLast = "" ;

if (sSearch.isEmpty() == false)
	{
	if (sSearch.contains(" "))
		{
		// multiple tokens
		String saNames[] = sSearch.split(" ") ;
		
		ArrayList<String> alTokens = new ArrayList<String>() ;
		int iTokenIndex = 0 ;
		
		// trim everything
		int iLength = saNames.length ;
		for (int i=0; i<iLength; i++)
			{
			saNames[i] = saNames[i].trim();
			if (saNames[i].isEmpty() == false)
				{
				alTokens.add(iTokenIndex, saNames[i]) ;
				iTokenIndex++ ;
				}
	
			}

		if (alTokens.size() == 2)
			{
			sFirst = alTokens.get(0) ;
			sLast = alTokens.get(1) ;
			sFilter = "(&(sn=" + sLast + "*)(givenName=" + sFirst + "*))" ;
			}
		else if (alTokens.size() > 2)
			{
			sFirst = alTokens.get(0) ;
			sMiddle = alTokens.get(1) ;
			sLast = alTokens.get(2) ;
			// sFilter = "(&(sn=" + sLast + "*)(givenName=" + sFirst + "*))" ;
			sFilter = "(&(sn=" + sLast + "*)(givenName=" + sFirst + "*)(cornelledumiddlename=" + sMiddle + "*))" ;
			}
		}
	else
		{
		if (sSearch.contains("@"))
			sFilter = "mail=" + sSearch + "*" ;
		else if (isFuzzyNetId(sSearch))
			sFilter = "uid=" + sSearch + "*" ;
		else
			{
			sFilter = "(|(sn=" + sSearch + "*)(givenName=" + sSearch + "*))" ;
			}
		}
	}

return (sFilter) ;
}

/* ------------------------------------------------------------------------- */
public boolean isFuzzyNetId (String sNetId) {

boolean bValid = false ;
boolean bDigitsOkay = false ;
int iLeadingChars = 0 ;
int iTrailingChars = 0 ;
for (int i = 0; i < sNetId.length(); i++)
	{
	char c = sNetId.charAt(i);		
	if (Character.isDigit(c))
		{
		if ((iLeadingChars == 2) || (iLeadingChars == 3))
			{
			bDigitsOkay = true ;
			}
		}
	else
		{
		if (bDigitsOkay)
			iTrailingChars++ ;
		else
			iLeadingChars++ ;
		}
	}

if (bDigitsOkay && (iTrailingChars == 0))
	bValid = true ;
		
return (bValid) ;
}

/* ------------------------------------------------------------------------- */
public void mapResults (SearchResult sr, Person p) throws Exception {

Attributes attrs = sr.getAttributes() ;

Attribute attr = attrs.get("givenName");
if (attr != null)
	p.setFirstName((String)attr.get()) ;

attr = attrs.get("sn");
if (attr != null)
	p.setLastName((String)attr.get()) ;

attr = attrs.get("cornelledumiddlename");
if (attr != null)
	p.setMiddleName((String)attr.get()) ;

attr = attrs.get("displayName");
if (attr != null)
	p.setDisplayName((String)attr.get()) ;

attr = attrs.get("cornelledunetid") ;
if (attr != null)
	p.setUserName((String)attr.get()) ;

attr = attrs.get("mail") ;
if (attr != null)
	p.setEmail((String)attr.get()) ;

attr = attrs.get("edupersonprimaryaffiliation") ;
if (attr != null)
	{
	String sAffiliation = (String)attr.get() ;
	ArrayList<String> lsAffiliation = new ArrayList<String>() ;
	lsAffiliation.add(sAffiliation) ;
	p.setAffiliations(lsAffiliation) ;
	}

ArrayList<String> alDepartments = new ArrayList<String>() ;
attr = attrs.get("cornelledudeptname1") ;
if (attr != null)
	{
	String sDepartment = (String)attr.get() ;
	sDepartment = sDepartment.trim() ;
	if (sDepartment.isEmpty() == false)
		alDepartments.add(sDepartment) ;
	}

attr = attrs.get("cornelledudeptname2") ;
if (attr != null)
	{
	String sDepartment = (String)attr.get() ;
	sDepartment = sDepartment.trim() ;
	if (sDepartment.isEmpty() == false)
		alDepartments.add(sDepartment) ;
	}

if (alDepartments.size() > 0)
	p.setDepartments(alDepartments) ;

// Fun with phones 
attr = attrs.get("cornelleducampusphone") ;
if (attr != null)
	{
	p.setPhone((String)attr.get()) ;
	}
else
	{
	attr = attrs.get("cornelledulocalphone") ;
	if (attr != null)
		{
		p.setPhone((String)attr.get()) ;
		}
	else
		{
		attr = attrs.get("mobile") ;
		if (attr != null)
			{
			p.setPhone((String)attr.get()) ;
			}
		else
			{
			attr = attrs.get("homephone") ;
			if (attr != null)
				{
				p.setPhone((String)attr.get()) ;
				}			
			}
		}
	}

// Fun with addresses
attr = attrs.get("cornelleducampusaddress") ;
if (attr != null)
	{
	p.setAddress((String)attr.get()) ;
	}
else
	{
	attr = attrs.get("cornelledulocaladdress") ;
	if (attr != null)
		{
		p.setAddress((String)attr.get()) ;
		}
	else
		{
		attr = attrs.get("cornelledunvlocaladdress") ;
		if (attr != null)
			{
			p.setAddress((String)attr.get()) ;
			}
		else
			{
			attr = attrs.get("cornelledunvhomeaddress") ;
			if (attr != null)
				{
				p.setAddress((String)attr.get()) ;
				}			
			}		
		}
	}
}

/* ------------------------------------------------------------------------- */
private void connect () throws Exception {

if (dsCtx == null)
	{
	Hashtable <String, String> ht = new Hashtable<String, String>() ;

	ht.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory") ;
	ht.put(Context.PROVIDER_URL, directoryUrl) ;
	ht.put(Context.SECURITY_AUTHENTICATION, "none") ;
	dsCtx = new InitialDirContext(ht) ;
	}
}

/* ------------------------------------------------------------------------- */
private void close () {

if (dsCtx != null)
	{
	try {
		dsCtx.close() ;
		}
	catch (Exception e)
		{
		LOG.error(e) ;
		}
	finally 
		{
		dsCtx = null ;
		}
	}
}

/**
 * We will not be doing any group searches. I'm returning an empty ArrayList 
 * here, as this method is called from PeopleController.postSimpleSearch().
 */
/* ------------------------------------------------------------------------- */
public List<Group> findSimpleGroup(String groupId) {

ArrayList<Group> alGroup = new ArrayList<Group>() ;

return alGroup ;
}

/* ------------------------------------------------------------------------- */
public Group lookupGroup(String groupId) {

throw new UnsupportedOperationException();
}

/* ------------------------------------------------------------------------- */
public String getDirectoryUrl() {

return directoryUrl;
}

/* ------------------------------------------------------------------------- */
public void setDirectoryUrl(String directoryUrl) {

this.directoryUrl = directoryUrl;
}

/* ------------------------------------------------------------------------- */
public String getSearchBase() {

return searchBase;
}

/* ------------------------------------------------------------------------- */
public void setSearchBase(String searchBase) {

this.searchBase = searchBase;
}

}
