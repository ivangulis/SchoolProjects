package controller;

import java.util.ArrayList;
import java.util.List;

import model.Akcia;
import model.Fakt;
import model.Podmienka;
import model.Pravidlo;
import model.SuborFakty;
import model.SuborPravidla;
import view.Gui;

public class Main {
	/** Singleton */
	private static Main instance = null;
	private Main() {}
	public static Main getInstance() {
		if (instance == null)
			instance = new Main();
		return instance;
	}
	
	public SuborFakty suborFakty = new SuborFakty();
	public SuborPravidla suborPravidla = new SuborPravidla();
	public List<Pravidlo> instancie;
	public int aktP = 0;
	
	/**Funkcia na kotrolu podmienok a faktov*/
	public void rekurzia(Pravidlo pravidlo, int index){
		boolean bool;
		for (int i=0;i<suborFakty.getFakty().size();i++){ //pre všetky fakty
			Pravidlo p;
			bool = true;
				p = new Pravidlo(); //vytvorí sa a inicializuje sa nový objekt vstupného pravidla
				p.setMeno(pravidlo.getMeno());
			
				List<Podmienka> podmienky = new ArrayList<Podmienka>();
				List<Akcia> akcie = new ArrayList<Akcia>();
				String[] premenne = new String[26];
			
				for (int a1=0;a1<pravidlo.getPodmienky().size();a1++){
					Podmienka podmienkaNova = new Podmienka();
					String[] pole = new String[pravidlo.getPodmienky().get(a1).getPodmienka().length];
					for (int b1=0;b1<pravidlo.getPodmienky().get(a1).getPodmienka().length;b1++){
						pole[b1] = new String(pravidlo.getPodmienky().get(a1).getPodmienka()[b1]);
					}
					podmienkaNova.setPodmienka(pole);
					podmienky.add(podmienkaNova);
				}
				for (int a2=0;a2<pravidlo.getAkcie().size();a2++){
					Akcia akciaNova = new Akcia();
					String[] pole = new String[pravidlo.getAkcie().get(a2).getAkcia().length];
					for (int b1=0;b1<pravidlo.getAkcie().get(a2).getAkcia().length;b1++){
						pole[b1] = new String(pravidlo.getAkcie().get(a2).getAkcia()[b1]);
					}
					akciaNova.setAkcia(pole);
					akcie.add(pravidlo.getAkcie().get(a2));
				}
				for (int a3=0;a3<26;a3++){
					premenne[a3] = new String(pravidlo.getPremenne()[a3]);
				}
				p.setPodmienky(podmienky);
				p.setAkcie(akcie);
				p.setPremenne(premenne);
				
			/**Najskor sa kontroluje špeciálna podmienka pre rozdielne premenné*/
			if (i == 0) //aby nasiel manželou len raz
			if (p.getPodmienky().get(index).getPodmienka()[0].charAt(0) == '<' && p.getPodmienky().get(index).getPodmienka()[0].charAt(1) == '>'
				&& p.getPremenne()[p.getPodmienky().get(index).getPodmienka()[1].charAt(1) - 'A'].compareTo("0") != 0 &&
					p.getPremenne()[p.getPodmienky().get(index).getPodmienka()[2].charAt(1) - 'A'].compareTo("0") != 0){
				String meno1, meno2;
				meno1 = p.getPremenne()[p.getPodmienky().get(index).getPodmienka()[1].charAt(1) - 'A'];
				meno2 = p.getPremenne()[p.getPodmienky().get(index).getPodmienka()[2].charAt(1) - 'A'];
				p.getPodmienky().get(index).getPodmienka()[1] = meno1;
				p.getPodmienky().get(index).getPodmienka()[2] = meno2;
				if (meno1.compareTo(meno2) != 0 && index == p.getPodmienky().size()-1){ //ak je to posledná podmienka
					instancie.add(p);
				}
				else if (meno1.compareTo(meno2) != 0){ //ak nieje posledná, pokracuje ïalšou
					rekurzia(p, index+1);
				}
				
			}
			/**Ak je to bežná podmienka, najskôr sa porovná poèet slov, aby nevybiehal z pola*/
			if (p.getPodmienky().get(index).getPodmienka().length == suborFakty.getFakty().get(i).getSlova().length){
				for (int j=0;j<p.getPodmienky().get(index).getPodmienka().length;j++){ //pre všetky slová podmienky
					if (p.getPodmienky().get(index).getPodmienka()[j].charAt(0) == '?'){ //nasiel som premennu
						if (p.getPremenne()[p.getPodmienky().get(index).getPodmienka()[j].charAt(1) - 'A'].compareTo("0") == 0){
							p.getPremenne()[p.getPodmienky().get(index).getPodmienka()[j].charAt(1) - 'A'] = suborFakty.getFakty().get(i).getSlova()[j];
						}
						//aktualizuje sa mapa, a z mapy sa doplní premenná
						p.getPodmienky().get(index).getPodmienka()[j] = p.getPremenne()[p.getPodmienky().get(index).getPodmienka()[j].charAt(1) - 'A']; //dosadím					
					}
					if (p.getPodmienky().get(index).getPodmienka()[j].compareTo(suborFakty.getFakty().get(i).getSlova()[j]) != 0){ //porovnavam podmienku a fakt
						bool = false;
						break; //ak nasiel nezhodu, konèí
					}
				}
				if (bool == true && index == p.getPodmienky().size()-1){ //ak je to posledná podmienka
					instancie.add(p);
				}
				else {
					if (bool == true) { //inak pokraèuje ïalšou podmienkou
						rekurzia(p, index+1);
					}
				}
			}
		}
	}
	
	public void hladaj(){
		instancie = new ArrayList<Pravidlo>(); //všetky potenciálne aplikovate¾né inštancie
		for (int z=0;z<suborPravidla.getPravidla().size();z++){
			aktP = z;
			rekurzia(suborPravidla.getPravidla().get(z),0); //postupne sa posielajú všetky pravidlá
		}
		String vykonane;
		for (int i = 0;i<instancie.size();i++){ //filtrácia
			vykonane = "0";
			for (int j = 0;j<instancie.get(i).getAkcie().size();j++){
				if (instancie.get(i).getAkcie().get(j).getAkcia()[0].compareTo("pridaj") == 0){
					vykonane = pridaj(instancie.get(i), instancie.get(i).getAkcie().get(j), false);
				}
				if (instancie.get(i).getAkcie().get(j).getAkcia()[0].compareTo("vymaz") == 0){
					vykonane = vymaz(instancie.get(i), instancie.get(i).getAkcie().get(j), false);
				}	
			}
			if (vykonane.compareTo("0") == 0) //ak nemá žiadne zmysluplné akcie, vymaže sa
				instancie.remove(i);
		}
	}
	
	/**Základný algoritmus*/
	public String algo(boolean koniec){
		StringBuilder celyVypis = new StringBuilder();
		hladaj(); //mam najdene všetky inštancie
		String vykonane;
		for (int i = 0;i<instancie.size();i++){ //pre všetky aplikovatelné inštancie
			vykonane = "0";
			for (int j = 0;j<instancie.get(i).getAkcie().size();j++){
				if (instancie.get(i).getAkcie().get(j).getAkcia()[0].compareTo("pridaj") == 0){ //akcia pridaj
					vykonane = pridaj(instancie.get(i), instancie.get(i).getAkcie().get(j), true);
					if (vykonane.compareTo("0") != 0){
						celyVypis.append("Bol pridaný fakt:\n");
						celyVypis.append(vykonane);
					}
				}
				if (instancie.get(i).getAkcie().get(j).getAkcia()[0].compareTo("vymaz") == 0){ //akcia vymaz
					vykonane = vymaz(instancie.get(i), instancie.get(i).getAkcie().get(j), true);
					if (vykonane.compareTo("0") != 0){
						celyVypis.append("Bol vymazaný fakt:\n");
						celyVypis.append(vykonane);
					}
				}
				//správa
				if (instancie.get(i).getAkcie().get(j).getAkcia()[0].compareTo("sprava") == 0 && (vykonane.compareTo("0") != 0)){
					celyVypis.append(sprava(instancie.get(i), instancie.get(i).getAkcie().get(j)));
				}	
			}
			if (koniec == false && vykonane.compareTo("0") != 0){ //ak bola vykonaná aspoò jedna akcia, a nejdeme až do konca cyklu
				break;
			}
		}
		return celyVypis.toString();
	}
	
	/**pridanie faktu*/
	public String pridaj(Pravidlo p, Akcia a, boolean naozaj){
		boolean bool = false;
		boolean nasiel = false;
		StringBuilder celyVypis = new StringBuilder();
		String[] pom = new String[a.getAkcia().length-1]; //pomocné porovnávacie pole
		for (int x=1;x<a.getAkcia().length;x++)
			pom[x-1] = a.getAkcia()[x];
		for (int x=0;x<pom.length;x++){
			if (pom[x].charAt(0) == '?')
				pom[x] = p.getPremenne()[pom[x].charAt(1) - 'A'];
		}
		
		for (int m=0;m<suborFakty.getFakty().size();m++){ //pre všetky fakty
			bool = true;
			if (pom.length == suborFakty.getFakty().get(m).getSlova().length){
				for (int n=0;n<pom.length;n++){ //pre všetky slová akcie
					if (pom[n].compareTo(suborFakty.getFakty().get(m).getSlova()[n]) != 0){ //porovnavam akciu a fakt
						bool = false;
					}
				}
				if (bool == true) { //už sa tam nachádza
					nasiel = true;
				}
			}
		}
		if (nasiel == true)//aby sa neduplikovalo
			return "0";
		else {
			if (naozaj==true){ //ak ideme skutoène pridávat fakt
				String[] s = new String[a.getAkcia().length-1];
				for (int i=0;i<pom.length;i++){
					s[i] = pom[i];
				}
				Fakt novy = new Fakt(s);
				
				for (int x=0;x<novy.getSlova().length;x++){
					celyVypis.append(novy.getSlova()[x] + " ");
				}
				celyVypis.append("\n");
				celyVypis.append("\n");
				suborFakty.getFakty().add(novy); //pridá sa do zoznamu faktov
			}
			return celyVypis.toString();
		}
	}
	/**Akcia vymazania faktu*/
	public String vymaz(Pravidlo p, Akcia a, boolean naozaj){
		boolean bool = false;
		boolean nasiel = false;
		int nasielIndex = -1;
		StringBuilder celyVypis = new StringBuilder();
		String[] pom = new String[a.getAkcia().length-1]; //pomocné porovnávacie pole
		for (int x=1;x<a.getAkcia().length;x++)
			pom[x-1] = a.getAkcia()[x];
		for (int x=0;x<pom.length;x++){
			if (pom[x].charAt(0) == '?')
				pom[x] = p.getPremenne()[pom[x].charAt(1) - 'A'];
		}
		
		for (int m=0;m<suborFakty.getFakty().size();m++){ //pre všetky fakty
			bool = true;
			if (pom.length == suborFakty.getFakty().get(m).getSlova().length){
				for (int n=0;n<pom.length;n++){ //pre všetky slová akcie
					if (pom[n].compareTo(suborFakty.getFakty().get(m).getSlova()[n]) != 0){ //porovnavam akciu a fakt
						bool = false;
					}
				}
				if (bool == true) { //už sa tam nachádza
					nasiel = true;
					nasielIndex = m;
				}
			}
		}
		if (nasiel == false) //ak sa nenachádza, nemám èo maza
			return "0";
		else {
			for (int x=0;x<pom.length;x++){
				celyVypis.append(pom[x] + " ");
			}
			celyVypis.append("\n");
			celyVypis.append("\n");
			if (naozaj==true) //chcem skutoène maza
				suborFakty.getFakty().remove(nasielIndex);
			return celyVypis.toString();
		}
	}
	/**Akcia správa*/
	public String sprava(Pravidlo p, Akcia a){
		StringBuilder celyVypis = new StringBuilder();
		String[] pom = new String[a.getAkcia().length-1];
		for (int x=1;x<a.getAkcia().length;x++)
			pom[x-1] = a.getAkcia()[x];
		for (int x=0;x<pom.length;x++){
			if (pom[x].charAt(0) == '?')
				pom[x] = p.getPremenne()[pom[x].charAt(1) - 'A'];
		}
		celyVypis.append("----------");
		for (int i=0;i<pom.length;i++){
			celyVypis.append(pom[i] + " ");
		}
		celyVypis.append("----------");
		celyVypis.append("\n");
		celyVypis.append("\n");
		return celyVypis.toString();
	}
	
	/**Tu sa len otvorí GUI*/
	public static void main (String[] args) {
		new Gui();		
	}

}
