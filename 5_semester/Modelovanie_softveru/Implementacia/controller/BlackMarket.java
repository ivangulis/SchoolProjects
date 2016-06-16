package controller;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import model.BeznyPouzivatel;
import model.DatabazaBlackMarketu;
import model.Oblecenie;
import model.Predmet;
import model.PremiovyPouzivatel;
import model.Zbran;

public class BlackMarket implements SpravcaPredmetov {
	/** Singleton */
	private static BlackMarket instance = null;
	private BlackMarket() {}
	public static BlackMarket getInstance() {
		if (instance == null)
			instance = new BlackMarket();
		return instance;
	}
	
	/** Ponuka predmetov v obchode */
	private ArrayList<Predmet> ponuka = new ArrayList<Predmet>();
	private DatabazaBlackMarketu datPredmety = new DatabazaBlackMarketu();
	
	public ArrayList<Predmet> getPonuka() {
		return ponuka;
	}
	public void setPonuka(ArrayList<Predmet> ponuka) {
		this.ponuka = ponuka;
	}
	public DatabazaBlackMarketu getDatPredmety() {
		return datPredmety;
	}
	public void setDatPredmety(DatabazaBlackMarketu datPredmety) {
		this.datPredmety = datPredmety;
	}
	
	/** Vyplnenie ponuky obchodu */
	public void vytvorPonuku(){
		for (Predmet p : datPredmety.getDatabaza()){
			ponuka.add(p);
		}
	}
	
	/** N�jde predmet pod�a n�zvu */
	public Predmet najdiPredmet(String nazov){
		for (Predmet p : ponuka)
			 if (nazov.equals(p.getNazov()))
				 return p;
		return null;
	}

	/**Tvorba tabulky ponuky, filter == 1 vytv�ra tabu�ku v�etk�ch predmetov
	 * filter == 2 vytv�ra tabu�ku zbran�
	 * filter == 3 vytv�ra tabu�ku oble�enia
	 * */
	public DefaultTableModel spravTabulkuPonuka(int filter){
		final Vector<String> stlpce = new Vector<String>();
		Vector<Vector<Object>> vypln = new Vector<Vector<Object>>();
		stlpce.add("Nazov");
		stlpce.add("Hodnota v serpentoch");
		stlpce.add("Hodnota v dubl�noch");
		if (filter == 2)
			for (Predmet p : ponuka){
				Vector<Object> riadok = new Vector<Object>();
				if (p instanceof Zbran){
					riadok.add(p.getNazov());
	        		riadok.add(p.getHodnotaSerpenty());
	        		riadok.add(p.getHodnotaDublony());
	        		vypln.add(riadok);
				}
			}
		else if (filter == 3)
			for (Predmet p : ponuka){
				Vector<Object> riadok = new Vector<Object>();
				if (p instanceof Oblecenie){
					riadok.add(p.getNazov());
	        		riadok.add(p.getHodnotaSerpenty());
	        		riadok.add(p.getHodnotaDublony());
	        		vypln.add(riadok);
				}
			}
		else for (Predmet p : ponuka){
			Vector<Object> riadok = new Vector<Object>();
			riadok.add(p.getNazov());
    		riadok.add(p.getHodnotaSerpenty());
    		riadok.add(p.getHodnotaDublony());
        		vypln.add(riadok);
		}
		return new DefaultTableModel(vypln, stlpce){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int colum){
				return false;
			}
		};
	}
	
	/** Tvorba po�a n�zvov predmetov pre combobox */
	public String[] vsetkyPredmety() {
		ArrayList<String> celyVypis = new ArrayList<String>();
		for (Predmet o : ponuka)
		 celyVypis.add(o.getNazov());
		String[] pole = new String[celyVypis.size()];
		for (int i = 0;i<celyVypis.size();i++)//tvor�m pole
			pole[i]=celyVypis.get(i);
		
		return pole;
	}
	
	/** Pou��vate�ovi prid� predmet z obchodu do invent�ra za cenu dubl�nov */
	public int kupPredmetDublony(String kupujuci, String predmet, ArrayList<BeznyPouzivatel> pouzivatelia){
		for (BeznyPouzivatel p : pouzivatelia)
			if (p.getPrezyvka().equals(kupujuci)){
				p.getInventar().getPredmety().add(new Predmet(predmet, p.getID()));
				for (Predmet o : ponuka)
					if (o.getNazov().equals(predmet)){
						odcitajDublony((PremiovyPouzivatel)p,o.getHodnotaDublony());
						return o.getHodnotaDublony();
					}
			}
		return 0;
		
	}
	
	/** Pou��vate�ovi prid� predmet z obchodu do invent�ra za cenu serpentov */
	public int kupPredmetSerpenty(String kupujuci, String predmet, ArrayList<BeznyPouzivatel> pouzivatelia){
		for (BeznyPouzivatel p : pouzivatelia)
			if (p.getPrezyvka().equals(kupujuci)){
				p.getInventar().getPredmety().add(new Predmet(predmet, p.getID()));
				for (Predmet o : ponuka)
					if (o.getNazov().equals(predmet)){
						odcitajSerpenty(p,o.getHodnotaSerpenty());
						return o.getHodnotaSerpenty();
					}
			}
		return 0;
	}
	
	/** Od��ta cenu predmetu v dubl�noch od pou��vate�ov�ch dubl�nov */
	public void odcitajDublony(PremiovyPouzivatel kupujuci, int hodnota){
		kupujuci.setDublony(kupujuci.getDublony()-hodnota);
	}
	
	/** Od��ta cenu predmetu v serpentoch od pou��vate�ov�ch serpentov */
	public void odcitajSerpenty(BeznyPouzivatel kupujuci, int hodnota){
		kupujuci.setSerpenty(kupujuci.getSerpenty()-hodnota);
	}
}
