package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Mapa {
	
	private int[][] mapa;
	private int rozmerR;
	private int rozmerS;
	private int startriadok;
	private int startstlpec;
	private List<Poklad> poklady = new ArrayList<Poklad>();
	private int pocetPokladov = 0;
	
	public Mapa(){
		try {
			Scanner scanner = new Scanner(new File("mapa.txt"));
			rozmerR = scanner.nextInt();
			rozmerS = scanner.nextInt();
			mapa = new int[rozmerR][rozmerS];
			String s;
			String pom = null;
			int pocet = 0;
			while (scanner.hasNext() == true){
				s = scanner.next();
				for (int i=0;i<rozmerS;i++){
					pom = "" + s.charAt(i);
					mapa[pocet][i] = Integer.parseInt(pom);
					if (mapa[pocet][i] == 1){
						pocetPokladov++;
						poklady.add(new Poklad(pocet,i));
					}
					if (mapa[pocet][i] == 2){
						startriadok = pocet;
						startstlpec = i;
					}
				}
				pocet++;
			}
			rozmerR--;
			rozmerS--;
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int[][] getMapa() {
		return mapa;
	}

	public void setMapa(int[][] mapa) {
		this.mapa = mapa;
	}

	public int getRozmerR() {
		return rozmerR;
	}

	public void setRozmerR(int rozmerR) {
		this.rozmerR = rozmerR;
	}

	public int getRozmerS() {
		return rozmerS;
	}

	public void setRozmerS(int rozmerS) {
		this.rozmerS = rozmerS;
	}

	public int getStartriadok() {
		return startriadok;
	}

	public void setStartriadok(int startriadok) {
		this.startriadok = startriadok;
	}

	public int getStartstlpec() {
		return startstlpec;
	}

	public void setStartstlpec(int startstlpec) {
		this.startstlpec = startstlpec;
	}

	public List<Poklad> getPoklady() {
		return poklady;
	}

	public void setPoklady(List<Poklad> poklady) {
		this.poklady = poklady;
	}

	public int getPocetPokladov() {
		return pocetPokladov;
	}

	public void setPocetPokladov(int pocetPokladov) {
		this.pocetPokladov = pocetPokladov;
	}
	
	
	

}
