package model.prvky;

public class Strana {

	/** Strana m� okienka */
	private Okienko panely[];

	/** Strany s� o��slovan� */
	private int cislo;

	/** Na ka�dej je miesto pre text mimo okienok */
	private String textMimo;

	public Okienko[] getPanely() {
		return panely;
	}

	public int getCislo() {
		return cislo;
	}

	public void setCislo(int cislo) {
		this.cislo = cislo;
	}

	public String getTextMimo() {
		return textMimo;
	}

	public void setTextMimo(String textMimo) {
		this.textMimo = textMimo;
	}

	/** Vytvoria sa okienka */
	public Strana(int pocetPanelov) {

		panely = new Okienko[pocetPanelov];
	}
}
