package model.prvky;

public class Postava {

	/** Postava m� svoje meno */
	protected String meno;

	/** Postava m� svoj vek */
	protected int vekPriemerne;

	/** Postava m� svoje dosiehnut� vzdelanie */
	protected String vzdelanie;

	/** Vlastn� obrann� predmet */
	protected String v�zbroj = "ni�";

	/** Vie bojova�? */
	protected boolean bojaschopn�;

	public void ozbroj() {
	}

	public String getMeno() {
		return meno;
	}

	public void setMeno(String meno) {
		this.meno = meno;
	}

	public String getV�zbroj() {
		return v�zbroj;
	}

	public void setV�zbroj(String v�zbroj) {
		this.v�zbroj = v�zbroj;
	}

}
