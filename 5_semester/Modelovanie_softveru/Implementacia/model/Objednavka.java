package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Objednavka {
	private LocalTime casOdoslania;
	private LocalDate datumOdoslania;
	private int cislo;
	private String meno;
	private int sumaDublonov;
	private int typPlatby; //0 je PayPal, 1 je SMS
	private boolean vybavena;
	private boolean zaplatena;
	
	public LocalTime getCasOdoslania() {
		return casOdoslania;
	}
	public void setCasOdoslania(LocalTime casOdoslania) {
		this.casOdoslania = casOdoslania;
	}
	public LocalDate getDatumOdoslania() {
		return datumOdoslania;
	}
	public void setDatumOdoslania(LocalDate datumOdoslania) {
		this.datumOdoslania = datumOdoslania;
	}
	public int getCislo() {
		return cislo;
	}
	public void setCislo(int cislo) {
		this.cislo = cislo;
	}
	public String getMeno() {
		return meno;
	}
	public void setMeno(String meno) {
		this.meno = meno;
	}
	public int getSumaDublonov() {
		return sumaDublonov;
	}
	public void setSumaDublonov(int sumaDublonov) {
		this.sumaDublonov = sumaDublonov;
	}
	public int getTypPlatby() {
		return typPlatby;
	}
	public void setTypPlatby(int typPlatby) {
		this.typPlatby = typPlatby;
	}
	public boolean isVybavena() {
		return vybavena;
	}
	public void setVybavena(boolean vybavena) {
		this.vybavena = vybavena;
	}
	public boolean isZaplatena() {
		return zaplatena;
	}
	public void setZaplatena(boolean zaplatena) {
		this.zaplatena = zaplatena;
	}
}
