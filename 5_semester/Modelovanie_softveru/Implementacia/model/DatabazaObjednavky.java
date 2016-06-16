package model;

import java.util.ArrayList;

public class DatabazaObjednavky {
	
	/** Obsahuje vybavené objednávky v systéme */
	private ArrayList<Objednavka> historia = new ArrayList<Objednavka>();
	/** Obsahuje zrušené objednávky */
	private ArrayList<Objednavka> zrusene = new ArrayList<Objednavka>();
	
	public ArrayList<Objednavka> getHistoria() {
		return historia;
	}
	public void setHistoria(ArrayList<Objednavka> historia) {
		this.historia = historia;
	}
	public ArrayList<Objednavka> getZrusene() {
		return zrusene;
	}
	public void setZrusene(ArrayList<Objednavka> zrusene) {
		this.zrusene = zrusene;
	}

}
