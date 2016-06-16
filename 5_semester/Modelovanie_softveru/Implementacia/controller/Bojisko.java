package controller;

import model.BeznyPouzivatel;
import model.DatabazaPouzivatelia;
import view.Gui;

public class Bojisko {
	/** Singleton */
	private static Bojisko instance = null;
	private Bojisko() {}
	public static Bojisko getInstance() {
		if (instance == null)
			instance = new Bojisko();
		return instance;
	}
	
	private DatabazaPouzivatelia datPouz = new DatabazaPouzivatelia();
	
	public DatabazaPouzivatelia getDatPouz() {
		return datPouz;
	}
	public void setDatPouz(DatabazaPouzivatelia datPouz) {
		this.datPouz = datPouz;
	}
	
	
	/** Vyh¾adá používate¾a z databáze pod¾a prezývky */
	public BeznyPouzivatel hladaj(String prez){
		for (BeznyPouzivatel p : datPouz.getDatabaza()){
			if (p.getPrezyvka().equals(prez)){
				return p;
			}
		}
		return null;
	}
	
	
	/**Tu sa len otvorí GUI*/
	public static void main (String[] args) {
		new Gui();		
		
	}

}
