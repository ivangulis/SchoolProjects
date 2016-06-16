package Model;

public class UzolH2 implements Comparable<UzolH2>{
	private UzolH2 rodic = null; //smerník na rodièa
	private String stav; //súèastný stav
	private char[][] stavPole;
	
	/**kde sa nachádza medzera*/
	private int mRiadok;
	private int mStlpec;
	
	private String poslednyOperator = " "; //pre zloženie cesty postupnosti operatorov
	private int sucetOdCiela; //heuristika 2
	
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
	public UzolH2 getRodic() {
		return rodic;
	}
	public void setRodic(UzolH2 rodic) {
		this.rodic = rodic;
	}
	public String getPoslednyOperator() {
		return poslednyOperator;
	}
	public void setPoslednyOperator(String poslednyOperator) {
		this.poslednyOperator = poslednyOperator;
	}
	
	public int getSucetOdCiela() {
		return sucetOdCiela;
	}
	public void setSucetOdCiela(int sucetOdCiela) {
		this.sucetOdCiela = sucetOdCiela;
	}
	
	public String getStav() {
		return stav;
	}
	public void setStav(String stav) {
		this.stav = stav;
	}
	
	public int compareTo(UzolH2 o) {  //pre priority que
		return this.sucetOdCiela - o.getSucetOdCiela();
	}
	

}

