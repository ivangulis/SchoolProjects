package model.prvky;

public class Starec extends Postava {

	/** Upravujem atrib�ty, ka�d� m� in� */
	public Starec() {
		this.vekPriemerne = 70;
		this.vzdelanie = "s��tan� a m�dry";
		this.bojaschopn� = false;
	}

	/** Ka�d� si vezme vlastn� zbra� */
	public void ozbroj() {

		this.v�zbroj = "palica";
	}
}
