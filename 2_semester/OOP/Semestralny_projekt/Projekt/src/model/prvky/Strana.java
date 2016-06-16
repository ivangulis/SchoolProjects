package model.prvky;

public class Strana {

	/** Strana má okienka */
	private Okienko panely[];

	/** Strany sú oèíslované */
	private int cislo;

	/** Na každej je miesto pre text mimo okienok */
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
