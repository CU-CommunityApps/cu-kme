package org.kuali.mobility.dining.helpercu ;

/* ------------------------------------------------------------------------- */
public class Entree {

private String title ;
private String attributes[] ;
private String healthyEating[] = { "featured" } ;

/* ------------------------------------------------------------------------- */
public String getTitle() {

return title;
}

/* ------------------------------------------------------------------------- */
public void setTitle(String title) {

this.title = title;
}

/* ------------------------------------------------------------------------- */
public String[] getAttributes() {

return attributes;
}

/* ------------------------------------------------------------------------- */
public void setAttributes(String[] attributes) {

this.attributes = attributes;
}

/* ------------------------------------------------------------------------- */
public void designateHealthyEating () {

attributes = healthyEating ;
}

}
