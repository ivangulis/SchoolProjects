package model.prvky;

public class Bojovnik extends Postava {

	/** Upravujem atribúty, každý má iné */
	public Bojovnik() {
		this.vekPriemerne = 30;
		this.vzdelanie = "nevzdelaný";
		this.bojaschopný = false;
	}

	/** Každý si vezme vlastnú zbraò */
	public void ozbroj() {
		this.výzbroj = "katana";
		this.bojaschopný = true;
	}
}
