package model.prvky;

public class Bojovnik extends Postava {

	/** Upravujem atrib�ty, ka�d� m� in� */
	public Bojovnik() {
		this.vekPriemerne = 30;
		this.vzdelanie = "nevzdelan�";
		this.bojaschopn� = false;
	}

	/** Ka�d� si vezme vlastn� zbra� */
	public void ozbroj() {
		this.v�zbroj = "katana";
		this.bojaschopn� = true;
	}
}
