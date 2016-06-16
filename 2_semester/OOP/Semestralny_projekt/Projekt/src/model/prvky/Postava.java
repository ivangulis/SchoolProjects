package model.prvky;

public class Postava {

	/** Postava má svoje meno */
	protected String meno;

	/** Postava má svoj vek */
	protected int vekPriemerne;

	/** Postava má svoje dosiehnuté vzdelanie */
	protected String vzdelanie;

	/** Vlastní obrannı predmet */
	protected String vızbroj = "niè";

	/** Vie bojova? */
	protected boolean bojaschopnı;

	public void ozbroj() {
	}

	public String getMeno() {
		return meno;
	}

	public void setMeno(String meno) {
		this.meno = meno;
	}

	public String getVızbroj() {
		return vızbroj;
	}

	public void setVızbroj(String vızbroj) {
		this.vızbroj = vızbroj;
	}

}
