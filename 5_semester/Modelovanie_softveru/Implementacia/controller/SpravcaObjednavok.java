package controller;

import java.time.LocalDate;
import java.time.LocalTime;

import model.BeznyPouzivatel;
import model.DatabazaObjednavky;
import model.Objednavka;
import model.PremiovyPouzivatel;


public class SpravcaObjednavok {
	/** Singleton */
	private static SpravcaObjednavok instance = null;
	private SpravcaObjednavok() {}
	public static SpravcaObjednavok getInstance() {
		if (instance == null)
			instance = new SpravcaObjednavok();
		return instance;
	}
	
	/** Aktualne vybavovana objedn�vka */
	private Objednavka vybavovana;
	
	/** Datab�za objedn�vok */
	private DatabazaObjednavky datObjednavky = new DatabazaObjednavky();
	
	public Objednavka getVybavovana() {
		return vybavovana;
	}
	public void setVybavovana(Objednavka vybavovana) {
		this.vybavovana = vybavovana;
	}
	public DatabazaObjednavky getDatObjednavky() {
		return datObjednavky;
	}
	public void setDatObjednavky(DatabazaObjednavky datObjednavky) {
		this.datObjednavky = datObjednavky;
	}
	/** Vytvor� objedn�vku a inicializuje ju */
	public void vytvorObjednavku(BeznyPouzivatel p){
		vybavovana = new Objednavka();
		vybavovana.setCasOdoslania(LocalTime.now());
		vybavovana.setCislo(datObjednavky.getHistoria().size());
		vybavovana.setDatumOdoslania(LocalDate.now());
		vybavovana.setMeno(p.getPrezyvka());
		vybavovana.setVybavena(false);
		vybavovana.setZaplatena(false);
	}
	
	/** Pri��ta dubl�ny, dopln� par inform�cii objedn�vke a archivuje objedn�vku do hist�rie */
	public int vybavObjednavku(PremiovyPouzivatel p, int sumaDublonov, int t){
		vybavovana.setSumaDublonov(sumaDublonov);
		vybavovana.setTypPlatby(t);
		p.setDublony(p.getDublony()+sumaDublonov);
		vybavovana.setVybavena(true);
		vybavovana.setZaplatena(true);
		datObjednavky.getHistoria().add(vybavovana);
		return sumaDublonov;
	}
	
}
