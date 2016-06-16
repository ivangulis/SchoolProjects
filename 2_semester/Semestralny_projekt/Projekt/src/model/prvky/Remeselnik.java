package model.prvky;

public class Remeselnik extends Postava {

	/** Upravujem atribúty, každý má iné */
	public Remeselnik() {
		this.vekPriemerne = 40;
		this.vzdelanie = "vyuèený";
		this.bojaschopný = false;
	}

	/** Každý si vezme vlastnú zbraò */
	public void ozbroj() {
		this.výzbroj = "kladivo";
		this.bojaschopný = true;
	}
}
