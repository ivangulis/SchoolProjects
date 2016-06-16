package model;

import java.util.*;
import java.util.Random;

public class Jedinec {
	private String[] bunky;
	private Mapa mapa;
	private double fitnes;
	private int riadok;
	private int stlpec;
	private List<Character> postupnost = new ArrayList<Character>();
	
	public Jedinec (Mapa mapaVstup){
		riadok = mapaVstup.getStartriadok();
		stlpec = mapaVstup.getStartstlpec();
		mapa = mapaVstup;
		bunky = new String[64];
		fitnes = 1.0;
	}
	
	public Jedinec (Mapa mapaVstup, int i){
		riadok = mapaVstup.getStartriadok();
		stlpec = mapaVstup.getStartstlpec();
		mapa = mapaVstup;
		bunky = new String[64];
		fitnes = 1.0;
		StringBuilder pom;
		for (int j = 0;j<64;j++){
			//if (j<20){
				Random rnd = new Random();
				int cislo = rnd.nextInt(255);
				pom = new StringBuilder();
				for (int z = Integer.toBinaryString(cislo).length();z<8;z++)
					pom.append(0);
				pom.append(Integer.toBinaryString(cislo));
			bunky[j] = pom.toString();
		}
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
	public String[] getBunky() {
		return bunky;
	}
	public void setBunky(String[] bunky) {
		this.bunky = bunky;
	}
	public double getFitnes() {
		return fitnes;
	}
	public void setFitnes(double d) {
		this.fitnes = d;
	}

	public Mapa getMapa() {
		return mapa;
	}

	public void setMapa(Mapa mapa) {
		this.mapa = mapa;
	}

	public List<Character> getPostupnost() {
		return postupnost;
	}

	public void setPostupnost(List<Character> postupnost) {
		this.postupnost = postupnost;
	}
}
