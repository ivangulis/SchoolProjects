package model.prvky;

public class Starec extends Postava {

	/** Upravujem atribúty, každý má iné */
	public Starec() {
		this.vekPriemerne = 70;
		this.vzdelanie = "sèítaný a múdry";
		this.bojaschopný = false;
	}

	/** Každý si vezme vlastnú zbraò */
	public void ozbroj() {

		this.výzbroj = "palica";
	}
}
