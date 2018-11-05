package ch.netzwerk.viadukt;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ch.netzwerk.viadukt.Menu.Tagesmenu;

public class ViaduktParser {

	public static List<Menu> fetchMenu(Menutag tag) throws IOException {
		
		List<Menu> menues = new ArrayList<>(); 

		Document doc = Jsoup.connect("http://www.restaurant-viadukt.ch/speis-trank/tagesmenue/").get();

		Elements alleMenues = doc.select(".dm-menu");

		Optional<Element> matchingMenu = alleMenues.stream()
				.filter(m -> m.selectFirst(".dm-menu-title h2").text().startsWith(tag.name().toUpperCase())).findFirst();
		
		Elements manu123 = matchingMenu.get().select(".dm-item"); 
		

		for (Element element : manu123) {
			String beschreibung = element.selectFirst(".bodytext").text(); 
			String titel = element.selectFirst("h3").text().trim(); 
			String preis  = element.selectFirst(".dm-item-price").text().trim(); 
			menues.add(new Menu(titel, beschreibung, preis));
			
		}

		return menues; 

	}
	
	static Menutag extractMenutag(LocalDate date) {
		int tag = date.getDayOfWeek().getValue(); 
		return Menutag.values()[tag-1];
	}

	public static List<Menu> fetchMenu(LocalDate date) throws IOException {
		return fetchMenu(extractMenutag(date));
	}

	public static Tagesmenu fetchTagesmenu(LocalDate date) throws IOException {
		return new Tagesmenu(fetchMenu(date)); 
	}

}
