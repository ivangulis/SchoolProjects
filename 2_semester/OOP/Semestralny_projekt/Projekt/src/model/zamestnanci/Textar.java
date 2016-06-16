package model.zamestnanci;

import model.prvky.*;

public class Textar extends Umelec implements TextarVie {

	/** Vlastn� viac atramentu ako ostatn� umelci */
	public Textar() {
		this.atrament += 5000;
	}

	/** Zni�uje svoju z�sobu atramentu */
	public void znizAtrament() {
		this.atrament -= spotrebaAtramentuText;
	}

	/** Zni�uje svoju z�sobu energie */
	public void znizEnergiu() {
		this.energia -= spotrebaEnergieText;
	}

	/** Vyp�e svoj stav atrib�tov atrament a energia */
	public String svojStav() {
		return ("Zost�va mi u� len " + this.atrament + " atramentu a "
				+ this.energia + " energie." + "\n");
	}

	/** Ka�d� umelec kresl� nie�o in�, tento p�e text do bubl�n */
	public void nakresli(Kniha komix, int stranaCislo, int panelCislo,
			int bublinaCislo, String s) {
		komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
				.getBubliny()[bublinaCislo - 1] = new Bublina();
		komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
				.getBubliny()[bublinaCislo - 1].setText(s);
		znizAtrament();
		znizEnergiu();
	}

	/** Len text�r dok�e p�sat text mimo okienok */
	public void napisTextMimo(Kniha komix, int stranaCislo, String s) {
		komix.getStrany().get(stranaCislo - 1).setTextMimo(s);
		znizAtrament();
		znizEnergiu();
	}

}
