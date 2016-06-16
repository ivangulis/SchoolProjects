package model.prvky;

public class Okienko {

	/** Okienko m� v sebe bubliny */
	private Bublina[] bubliny;

	/** Okienko m� v sebe postavy */
	private Postava[] postavy;

	/** Ka�d� okienko m� svoje pozadie */
	private Pozadie pozadie = new Pozadie();

	/** Okienka s� o��slovan� */
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

	/** Vytvor� sa miesto pre po�et bubl�n a post�v v okienku */
	public Okienko(int pocetBublin, int pocetPostav) {

		bubliny = new Bublina[pocetBublin];
		postavy = new Postava[pocetPostav];
	}

}