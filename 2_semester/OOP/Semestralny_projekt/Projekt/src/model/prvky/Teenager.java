package model.prvky;

public class Teenager extends Postava {

	/** Upravujem atrib�ty, ka�d� m� in� */
	public Teenager() {
		this.vekPriemerne = 18;
		this.vzdelanie = "�tudent";
		this.bojaschopn� = false;
	}

	/** Ka�d� si vezme vlastn� zbra� */
	public void ozbroj() {

		this.v�zbroj = "�print";
	}

}
