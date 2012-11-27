package org.kuali.mobility.dining.helpercu;

import java.util.ArrayList ;

public class Station {

String title ;
ArrayList<Entree> items ;

public String getTitle() {

return title;
}

public void setTitle(String name) {

this.title = name;
}

public ArrayList<Entree> getItems() {

return items;
}

public void setItems(ArrayList<Entree> entrees) {

this.items = entrees;
}

public void appendEntree (Entree yummyFood) {

if (items == null)
	items = new ArrayList<Entree>() ;

items.add(yummyFood) ;
}

}
