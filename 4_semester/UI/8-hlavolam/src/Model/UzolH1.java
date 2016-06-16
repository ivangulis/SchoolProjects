package Model;

public class UzolH1 implements Comparable<UzolH1>{
	private UzolH1 rodic = null; //smerník na rodièa
	private String stav; //súèastný stav
	private char[][] stavPole;
	
	/**kde sa nachádza medzera*/
	private int mRiadok;
	private int mStlpec;
	
	private String poslednyOperator = " "; //pre zloženie cesty postupnosti operatorov
	private int pocetZlych; //heuristika 1
	
	/**Nasledujú gettery a settery*/
	public int getmRiadok() {
		return mRiadok;
	}
	public void setmRiadok(int mRiadok) {
		this.mRiadok = mRiadok;
	}
	public int getmStlpec() {
		return mStlpec;
	}
	public void setmStlpec(int mStlpec) {
		this.mStlpec = mStlpec;
	}

	public char[][] getStavPole() {
		return stavPole;
	}
	public void setStavPole(char[][] stavPole) {
		this.stavPole = stavPole;
	}
	public UzolH1 getRodic() {
		return rodic;
	}
	public void setRodic(UzolH1 rodic) {
		this.rodic = rodic;
	}
	public String getPoslednyOperator() {
		return poslednyOperator;
	}
	public void setPoslednyOperator(String poslednyOperator) {
		this.poslednyOperator = poslednyOperator;
	}
	
	public String getStav() {
		return stav;
	}
	public void setStav(String stav) {
		this.stav = stav;
	}

	public int getPocetZlych() {
		return pocetZlych;
	}
	public void setPocetZlych(int pocetZlych) {
		this.pocetZlych = pocetZlych;
	}
	
	public int compareTo(UzolH1 o) { //pre priority que
			return this.pocetZlych - o.getPocetZlych(); 
	}
	

}
