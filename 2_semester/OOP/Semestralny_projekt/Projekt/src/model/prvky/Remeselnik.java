package model.prvky;

public class Remeselnik extends Postava {

	/** Upravujem atrib�ty, ka�d� m� in� */
	public Remeselnik() {
		this.vekPriemerne = 40;
		this.vzdelanie = "vyu�en�";
		this.bojaschopn� = false;
	}

	/** Ka�d� si vezme vlastn� zbra� */
	public void ozbroj() {
		this.v�zbroj = "kladivo";
		this.bojaschopn� = true;
	}
}
