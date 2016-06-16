package model.prvky;

public class Okienko {

	/** Okienko má v sebe bubliny */
	private Bublina[] bubliny;

	/** Okienko má v sebe postavy */
	private Postava[] postavy;

	/** Každé okienko má svoje pozadie */
	private Pozadie pozadie = new Pozadie();

	/** Okienka sú oèíslované */
	private int cisloPanelu;

	public Bublina[] getBubliny() {
		return bubliny;
	}

	public void setBubliny(Bublina[] bubliny) {
		this.bubliny = bubliny;
	}

	public Postava[] getPostavy() {
		return postavy;
	}

	public void setPostavy(Postava[] postavy) {
		this.postavy = postavy;
	}

	public Pozadie getPozadie() {
		return pozadie;
	}

	public void setPozadie(Pozadie pozadie) {
		this.pozadie = pozadie;
	}

	public int getCisloPanelu() {
		return cisloPanelu;
	}

	public void setCisloPanelu(int cisloPanelu) {
		this.cisloPanelu = cisloPanelu;
	}

	/** Vytvorí sa miesto pre poèet bublín a postáv v okienku */
	public Okienko(int pocetBublin, int pocetPostav) {

		bubliny = new Bublina[pocetBublin];
		postavy = new Postava[pocetPostav];
	}

}