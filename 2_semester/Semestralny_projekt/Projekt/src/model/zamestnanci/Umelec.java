package model.zamestnanci;

/** Nadradená trieda */
abstract public class Umelec implements UmelciVedia {

	/** Základné atribúty */
	protected int energia = 100;
	/** Základné atribúty */
	protected int atrament = 5000;

	/** Hodnoty spotreby pre kreslené objekty */
	protected final int spotrebaAtramentuText = 250;
	protected final int spotrebaAtramentuPostava = 125;
	protected final int spotrebaAtramentuPozadie = 250;
	protected final int spotrebaFarbyPostava = 250;
	protected final int spotrebaFarbyPozadie = 500;
	protected final int spotrebaEnergiePostava = 2;
	protected final int spotrebaEnergiePozadie = 4;
	protected final int spotrebaEnergieText = 1;
}
