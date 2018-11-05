package ch.netzwerk.viadukt;

import java.util.List;

public class Menu {


	public static class Tagesmenu {
		public List<Menu> tagesmenu; 

		public Tagesmenu(List<Menu> fetchMenu) {
			this.tagesmenu = fetchMenu; 
		}

		public List<Menu> getTagesmenu(){
			return tagesmenu; 
		}
		
	}

	
	

	public Menu(String name, String description, String price) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}



	String name;
	String description;
	String price;

}
