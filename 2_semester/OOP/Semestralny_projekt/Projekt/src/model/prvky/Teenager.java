package model.prvky;

public class Teenager extends Postava {

	/** Upravujem atribúty, každý má iné */
	public Teenager() {
		this.vekPriemerne = 18;
		this.vzdelanie = "študent";
		this.bojaschopný = false;
	}

	/** Každý si vezme vlastnú zbraò */
	public void ozbroj() {

		this.výzbroj = "šprint";
	}

}
