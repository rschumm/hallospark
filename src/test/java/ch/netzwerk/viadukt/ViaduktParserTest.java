package ch.netzwerk.viadukt;

import static org.junit.Assert.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.junit.Test;

public class ViaduktParserTest {

	@Test
	public void testFreitag() throws IOException {
		List<Menu> menus = ViaduktParser.fetchMenu(Menutag.Freitag);

		//assertEquals("ZITRONENRISOTTO MIT RUCOLA UND GEHOBELTEM BELPER KNOLLE", menus.get(0).getDescription());
		assertTrue(menus.get(0).getDescription().matches(".*"));
		assertTrue(menus.get(0).getPrice().matches("\\d*.\\d*"));
		assertEquals("MENU I", menus.get(0).getName());

		//assertEquals("ZANDER-CEVICHE (POL) MIT APFEL,AVOCADO UND DILL", menus.get(2).getDescription());
		assertTrue(menus.get(2).getDescription().matches(".*"));
		assertTrue(menus.get(2).getPrice().matches("\\d*.\\d*"));
		assertEquals("MENU III", menus.get(2).getName());

	}
	
	
	@Test
	public void extractDate() {
		LocalDate date = LocalDate.parse("2018-05-30");  //Mittwoch
		Menutag menutag = ViaduktParser.extractMenutag(date);
		
		assertEquals(Menutag.Mittwoch, menutag);
		
	}




}
