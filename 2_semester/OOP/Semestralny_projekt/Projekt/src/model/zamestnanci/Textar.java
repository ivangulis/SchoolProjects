package model.zamestnanci;

import model.prvky.*;

public class Textar extends Umelec implements TextarVie {

	/** Vlastní viac atramentu ako ostatní umelci */
	public Textar() {
		this.atrament += 5000;
	}

	/** Znižuje svoju zásobu atramentu */
	public void znizAtrament() {
		this.atrament -= spotrebaAtramentuText;
	}

	/** Znižuje svoju zásobu energie */
	public void znizEnergiu() {
		this.energia -= spotrebaEnergieText;
	}

	/** Vypíše svoj stav atribútov atrament a energia */
	public String svojStav() {
		return ("Zostáva mi už len " + this.atrament + " atramentu a "
				+ this.energia + " energie." + "\n");
	}

	/** Každý umelec kreslí nieèo iné, tento píše text do bublín */
	public void nakresli(Kniha komix, int stranaCislo, int panelCislo,
			int bublinaCislo, String s) {
		komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
				.getBubliny()[bublinaCislo - 1] = new Bublina();
		komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
				.getBubliny()[bublinaCislo - 1].setText(s);
		znizAtrament();
		znizEnergiu();
	}

	/** Len textár dokáže písat text mimo okienok */
	public void napisTextMimo(Kniha komix, int stranaCislo, String s) {
		komix.getStrany().get(stranaCislo - 1).setTextMimo(s);
		znizAtrament();
		znizEnergiu();
	}

}
