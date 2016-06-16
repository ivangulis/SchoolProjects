package model;

public class Poklad {
	private int riadok;
	private int stlpec;

	public Poklad(){
	}
	
	public Poklad(int a, int b){
		riadok = a;
		stlpec = b;
	}
	
	public int getRiadok() {
		return riadok;
	}
	public void setRiadok(int riadok) {
		this.riadok = riadok;
	}
	public int getStlpec() {
		return stlpec;
	}
	public void setStlpec(int stlpec) {
		this.stlpec = stlpec;
	}
}
