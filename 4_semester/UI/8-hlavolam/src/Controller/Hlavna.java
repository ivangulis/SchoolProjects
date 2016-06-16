package Controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

import Model.UzolH1;
import Model.UzolH2;
import View.Gui;

public class Hlavna {
	
	/** Singleton */
	private static Hlavna instance = null;
	private Hlavna() {}
	public static Hlavna getInstance() {
		if (instance == null)
			instance = new Hlavna();
		return instance;
	}
	
	/**Rozmery tabulky*/
	public static int riadky;
	public static int stlpce;
	
	/**PoËiatoËn˝ a koncov˝ stav*/
	public static String start;
	public static String ciel;
	
	/**PoËiatok a koniec ako pole*/
	public static char[][] startPole;
	public static char[][] cielPole;
	
	/**Umiestnenie medzery*/
	public static int mRiadok;
	public static int mStlpec;
	
	/**NaËÌta vstup z 4 TextFieldov*/
	public String nacitaj(String r, String s, String st, String cl){
		StringBuilder celyVypis = new StringBuilder();
		int pocet = -1;
		/**Inicializ·cia glob·lnych premenn˝ch*/
		riadky = Integer.parseInt(r);
		stlpce = Integer.parseInt(s);
		start = st;
		ciel = cl;
		startPole = new char[Integer.parseInt(r)][Integer.parseInt(s)];
		cielPole = new char[Integer.parseInt(r)][Integer.parseInt(s)];
		for (int i = 0;i<riadky;i++)
			for (int j = 0;j<stlpce;j++){
				pocet++;
				startPole[i][j] = start.charAt(pocet);
				cielPole[i][j] = ciel.charAt(pocet);
				if (startPole[i][j] == '0'){
					mRiadok = i; //tu si uloûÌm medzeru riadok
					mStlpec = j; //a stÂpec
				}
			}
		
		/**⁄vodn˝ v˝pis*/
		celyVypis.append("Veækosù priestoru(riadky x stÂpce): " + riadky + "x" + stlpce + "\n");
		celyVypis.append("PoËiatoËn˝ stav" + "\n");
		for (int i = 0;i<riadky;i++){
			for (int j = 0;j<stlpce;j++){
				celyVypis.append(startPole[i][j] + " ");
			}
			celyVypis.append("\n");
		}
		celyVypis.append("\n");
		celyVypis.append("Cieæov˝ stav" + "\n");
		for (int i = 0;i<riadky;i++){
			for (int j = 0;j<stlpce;j++){
				celyVypis.append(cielPole[i][j] + " ");
			}
			celyVypis.append("\n");
		}
		celyVypis.append("\n");
		celyVypis.append("\n");
		return celyVypis.toString();
		
	}
	
	public String heuristika1(){
		/**Inicializ·cie*/
		long time1 = System.currentTimeMillis(); //ËasovaË
		long time2;
		HashSet<String> hashTable = new HashSet<String>(); //ak·si jednoduchöia hash tabuka so stringami(stavy)
		char[][] pom;
		char maRiesenie = 0;
		StringBuilder celyVypis = new StringBuilder();
		int poËetPrejdenychStavov = 0;
		int vygeneroval = 0;
		
		/**Inicializ·cia prvÈho uzla*/
		UzolH1 prvy = new UzolH1();
		UzolH1 act = new UzolH1();
		PriorityQueue<UzolH1> que = new PriorityQueue<UzolH1>();
		prvy.setStav(start);
		prvy.setStavPole(startPole);
		prvy.setPocetZlych(pocetZlych(startPole));
		prvy.setmRiadok(mRiadok);
		prvy.setmStlpec(mStlpec);
		hashTable.add(prvy.getStav());
		que.add(prvy);
		
		while (que.isEmpty() == false){ //hlavn˝ cyklus
			
			act = que.poll();
			poËetPrejdenychStavov++;
			if (act.getPocetZlych() == 0) { //ak som naöiel cieæ, skonËÌm
				maRiesenie = 1;
				break;
			}
			/**Nasleduj˙ 4 podmienky, posun dole, hore, dolava, doprava*/
			if (act.getmRiadok() != 0 && act.getPoslednyOperator().compareTo("HORE") != 0){ //pr·zdny nie je na hornom kraji
				pom = new char[riadky][stlpce];
				UzolH1 novy = new UzolH1();
				novy.setmRiadok(act.getmRiadok()-1); //nova pozicia medzery
				novy.setmStlpec(act.getmStlpec());
				novy.setRodic(act);
				pom = naPole(act.getStav()); //premenÌm rodiËov string na novÈ pole, ktorÈ mÙûem upravovaù
				pom[act.getmRiadok()][act.getmStlpec()] = pom[act.getmRiadok()-1][act.getmStlpec()]; //pos˙vanÈ ËÌslo dole
				pom[act.getmRiadok()-1][act.getmStlpec()] = '0'; //medzera posun hore
				novy.setStavPole(pom);
				novy.setStav(naString(pom)); //zmenenÌ pole na string
				novy.setPoslednyOperator("DOLE");
				novy.setPocetZlych(pocetZlych(novy.getStavPole())); //setne poËet nesediacich poliËok
				
				if (hashTable.contains(novy.getStav()) == false) { //ak sa v hash tabulke nenach·dza
					hashTable.add(novy.getStav());
					que.add(novy);
					vygeneroval++;
				}
			}
			
			if (act.getmRiadok() != riadky-1 && act.getPoslednyOperator().compareTo("DOLE") != 0){ //pr·zdny nie je na dolnom kraji
				pom = new char[riadky][stlpce];
				UzolH1 novy = new UzolH1();
				novy.setmRiadok(act.getmRiadok()+1);
				novy.setmStlpec(act.getmStlpec());
				novy.setRodic(act);
				pom = naPole(act.getStav());
				pom[act.getmRiadok()][act.getmStlpec()] = pom[act.getmRiadok()+1][act.getmStlpec()]; //pos˙vanÈ ËÌslo hore
				pom[act.getmRiadok()+1][act.getmStlpec()] = '0'; //medzera posun dole
				novy.setStavPole(pom);
				novy.setStav(naString(pom));
				novy.setPoslednyOperator("HORE");
				novy.setPocetZlych(pocetZlych(novy.getStavPole()));
				
				if (hashTable.contains(novy.getStav()) == false) { //ak sa v hash tabulke nenach·dza	
					hashTable.add(novy.getStav());
					que.add(novy);
					vygeneroval++;
				}
			}
			
			if (act.getmStlpec() != 0 && act.getPoslednyOperator().compareTo("VLAVO") != 0){ //pr·zdny nie je na lavom kraji
				pom = new char[riadky][stlpce];
				UzolH1 novy = new UzolH1();
				novy.setmRiadok(act.getmRiadok());
				novy.setmStlpec(act.getmStlpec()-1);
				novy.setRodic(act);
				pom = naPole(act.getStav());
				pom[act.getmRiadok()][act.getmStlpec()] = pom[act.getmRiadok()][act.getmStlpec()-1]; //pos˙vanÈ ËÌslo doprava
				pom[act.getmRiadok()][act.getmStlpec()-1] = '0'; //medzera posun dolava
				novy.setStavPole(pom);
				novy.setStav(naString(pom));
				novy.setPoslednyOperator("VPRAVO");
				novy.setPocetZlych(pocetZlych(novy.getStavPole()));
				
				if (hashTable.contains(novy.getStav()) == false) { //ak sa v hash tabulke nenach·dza
					hashTable.add(novy.getStav());
					que.add(novy);
					vygeneroval++;
				}
			}
			
			if (act.getmStlpec() != stlpce-1 && act.getPoslednyOperator().compareTo("VPRAVO") != 0){ //pr·zdny nie je na pravom kraji
				pom = new char[riadky][stlpce];
				UzolH1 novy = new UzolH1();
				novy.setmRiadok(act.getmRiadok());
				novy.setmStlpec(act.getmStlpec()+1);
				novy.setRodic(act);
				pom = naPole(act.getStav());
				pom[act.getmRiadok()][act.getmStlpec()] = pom[act.getmRiadok()][act.getmStlpec()+1]; //pos˙vanÈ ËÌslo dolava
				pom[act.getmRiadok()][act.getmStlpec()+1] = '0'; //medzera posun doprava
				novy.setStavPole(pom);
				novy.setStav(naString(pom));
				novy.setPoslednyOperator("VLAVO");
				novy.setPocetZlych(pocetZlych(novy.getStavPole()));
				
				if (hashTable.contains(novy.getStav()) == false) { //ak sa v hash tabulke nenach·dza
					hashTable.add(novy.getStav());
					que.add(novy);
					vygeneroval++;
				}
			}
			
			
		}
		time2 = System.currentTimeMillis();
		if (maRiesenie == 1){	//naöiel som rieöenie
			List<String> vypisCesty = new ArrayList<String>();
			celyVypis.append("Heuristika 1." + "\n");
			celyVypis.append("Kroky:" + "\n");
			UzolH1 pomUzol;
			pomUzol = act;
			vypisCesty.add(pomUzol.getPoslednyOperator());
			while (pomUzol.getRodic().getRodic() != null){ //vraciam sa k rodiËovi
				pomUzol = pomUzol.getRodic();
				vypisCesty.add(pomUzol.getPoslednyOperator());
			}
			for (int i = vypisCesty.size()-1;i>=0;i--){ //vypÌöem cestu odzadu
				celyVypis.append(vypisCesty.get(i) + " ");
			}
			
			celyVypis.append("\n");
			celyVypis.append("DÂûka cesty: " + vypisCesty.size() +  "\n");
			celyVypis.append("Stavov prejden˝ch: " + poËetPrejdenychStavov + "\n");
			celyVypis.append("Stavov vygenerovan˝ch: " + vygeneroval +  "\n");
			celyVypis.append("»as: " + (time2-time1) + " ms" + "\n");
			celyVypis.append("\n");
		}
		
		if (maRiesenie == 0)
			celyVypis.append("Nepodarilo sa n·jsù rieöenie" + "\n");
		return celyVypis.toString();
	}
	
	/**Podobne ako predch·dzaj˙ca funkcia, ale in· heuristika  */
	public String heuristika2(){		
		long time1 = System.currentTimeMillis(); //ËasovaË
		long time2;
		HashSet<String> hashTable = new HashSet<String>(); //ak·si jednoduchöia hash tabuka so stringami(stavy)
		char[][] pom;
		char maRiesenie = 0;
		StringBuilder celyVypis = new StringBuilder();
		int poËetPrejdenychStavov = 0;
		int vygeneroval = 0;
		UzolH2 prvy = new UzolH2();
		UzolH2 act = new UzolH2();
		
		PriorityQueue<UzolH2> que = new PriorityQueue<UzolH2>(); //toto que teraz porovn·va podæa suËtu vzdialenostÌ od cieæa
		prvy.setStav(start);
		prvy.setStavPole(startPole);
		prvy.setSucetOdCiela(sucetOdCiela(startPole));
		prvy.setmRiadok(mRiadok);
		prvy.setmStlpec(mStlpec);
		hashTable.add(prvy.getStav()); //hash tabuka zost·va rovnak·
		que.add(prvy);
		
		while (que.isEmpty() == false){
			
			act = que.poll();
			poËetPrejdenychStavov++;
			if (act.getSucetOdCiela() == 0) {
				maRiesenie = 1;
				break;
			}
			if (act.getmRiadok() != 0 && act.getPoslednyOperator().compareTo("HORE") != 0){ //pr·zdny nie je na hornom kraji
				pom = new char[riadky][stlpce];
				UzolH2 novy = new UzolH2();
				novy.setmRiadok(act.getmRiadok()-1);
				novy.setmStlpec(act.getmStlpec());
				novy.setRodic(act);
				pom = naPole(act.getStav());
				pom[act.getmRiadok()][act.getmStlpec()] = pom[act.getmRiadok()-1][act.getmStlpec()]; //posuvane ËÌslo dole
				pom[act.getmRiadok()-1][act.getmStlpec()] = '0'; //medzera posun hore
				novy.setStavPole(pom);
				novy.setStav(naString(pom));
				novy.setPoslednyOperator("DOLE");
				novy.setSucetOdCiela(sucetOdCiela(novy.getStavPole())); //setne suËet vzdialenostÌ polÌËok od cieæa
				
				if (hashTable.contains(novy.getStav()) == false) { //ak sa v hash tabulke nenach·dza
					hashTable.add(novy.getStav());
					que.add(novy);
					vygeneroval++;
				}
			}
			
			if (act.getmRiadok() != riadky-1 && act.getPoslednyOperator().compareTo("DOLE") != 0){ //pr·zdny nie je na dolnom kraji
				pom = new char[riadky][stlpce];
				UzolH2 novy = new UzolH2();
				novy.setmRiadok(act.getmRiadok()+1);
				novy.setmStlpec(act.getmStlpec());
				novy.setRodic(act);
				pom = naPole(act.getStav());
				pom[act.getmRiadok()][act.getmStlpec()] = pom[act.getmRiadok()+1][act.getmStlpec()]; //posuvane ËÌslo hore
				pom[act.getmRiadok()+1][act.getmStlpec()] = '0'; //medzera posun dole
				novy.setStavPole(pom);
				novy.setStav(naString(pom));
				novy.setPoslednyOperator("HORE");
				novy.setSucetOdCiela(sucetOdCiela(novy.getStavPole())); //setne suËet vzdialenostÌ polÌËok od cieæa
				
				if (hashTable.contains(novy.getStav()) == false) { //ak sa v hash tabulke nenach·dza
					hashTable.add(novy.getStav());
					que.add(novy);
					vygeneroval++;
				}
			}
			
			if (act.getmStlpec() != 0 && act.getPoslednyOperator().compareTo("VLAVO") != 0){ //pr·zdny nie je na lavom kraji
				pom = new char[riadky][stlpce];
				UzolH2 novy = new UzolH2();
				novy.setmRiadok(act.getmRiadok());
				novy.setmStlpec(act.getmStlpec()-1);
				novy.setRodic(act);
				pom = naPole(act.getStav());
				pom[act.getmRiadok()][act.getmStlpec()] = pom[act.getmRiadok()][act.getmStlpec()-1]; //posuvane ËÌslo doprava
				pom[act.getmRiadok()][act.getmStlpec()-1] = '0'; //medzera posun dolava
				novy.setStavPole(pom);
				novy.setStav(naString(pom));
				novy.setPoslednyOperator("VPRAVO");
				novy.setSucetOdCiela(sucetOdCiela(novy.getStavPole())); //setne suËet vzdialenostÌ polÌËok od cieæa
				
				if (hashTable.contains(novy.getStav()) == false) { //ak sa v hash tabulke nenach·dza
					hashTable.add(novy.getStav());
					que.add(novy);
					vygeneroval++;
				}
			}
			
			if (act.getmStlpec() != stlpce-1 && act.getPoslednyOperator().compareTo("VPRAVO") != 0){ //pr·zdny nie je na pravom kraji
				pom = new char[riadky][stlpce];
				UzolH2 novy = new UzolH2();
				novy.setmRiadok(act.getmRiadok());
				novy.setmStlpec(act.getmStlpec()+1);
				novy.setRodic(act);
				pom = naPole(act.getStav());
				pom[act.getmRiadok()][act.getmStlpec()] = pom[act.getmRiadok()][act.getmStlpec()+1]; //posuvane ËÌslo dolava
				pom[act.getmRiadok()][act.getmStlpec()+1] = '0'; //medzera posun doprava
				novy.setStavPole(pom);
				novy.setStav(naString(pom)); 
				novy.setPoslednyOperator("VLAVO");
				novy.setSucetOdCiela(sucetOdCiela(novy.getStavPole())); //setne suËet vzdialenostÌ polÌËok od cieæa
				
				if (hashTable.contains(novy.getStav()) == false) { //ak sa v hash tabulke nenach·dza
					hashTable.add(novy.getStav());
					que.add(novy);
					vygeneroval++;
				}
			}
			
			
		}
		time2 = System.currentTimeMillis();
		if (maRiesenie == 1){
			/**V˝pis*/
			List<String> vypisCesty = new ArrayList<String>();
			celyVypis.append("Heuristika 2." + "\n");
			celyVypis.append("Kroky:" + "\n");
			UzolH2 pomUzol;
			pomUzol = act;
			vypisCesty.add(pomUzol.getPoslednyOperator());
			while (pomUzol.getRodic().getRodic() != null){
				pomUzol = pomUzol.getRodic();
				vypisCesty.add(pomUzol.getPoslednyOperator());
			}
			for (int i = vypisCesty.size()-1;i>=0;i--){
				celyVypis.append(vypisCesty.get(i) + " ");
			}
			celyVypis.append("\n");
			celyVypis.append("DÂûka cesty: " + vypisCesty.size() +  "\n");
			celyVypis.append("Stavov prejden˝ch: " + poËetPrejdenychStavov + "\n");
			celyVypis.append("Stavov vygenerovan˝ch: " + vygeneroval +  "\n");
			celyVypis.append("»as: " + (time2-time1) + " ms" + "\n");
			celyVypis.append("\n");
		}
		if (maRiesenie == 0)
			celyVypis.append("Nepodarilo sa n·jsù rieöenie" + "\n");
		return celyVypis.toString();
	}
	
	/**Funkcia vracia poËet zle umiestnen˝ch prvkov*/
	public int pocetZlych(char[][] porovnaj){
		int pocet = 0;
		for (int i = 0;i<riadky;i++)
			for (int j = 0;j<stlpce;j++){
			if (porovnaj[i][j] != cielPole[i][j]) 
				pocet++;
		}
		return pocet;
	}
	
	/**Funkcia vracia s˙Ëet vzdialenostÌ jednotliv˝ch prvkov od cieæa*/
	public int sucetOdCiela(char[][] porovnaj){
		int pocet = 0;
		int rozdiel;
		for (int i = 0;i<riadky;i++)
			for (int j = 0;j<stlpce;j++){
				rozdiel = 0;
				for (int k = 0;k<riadky;k++)
					for (int l = 0;l<stlpce;l++)
						if (porovnaj[i][j] == cielPole[k][l]){
							if (i>=k)
								rozdiel = rozdiel + i-k;
							else rozdiel = rozdiel + k-i;
							if (j>=l)
								rozdiel = rozdiel + j-l;
							else rozdiel = rozdiel + l-j;
						}
				pocet = pocet + rozdiel;
			}
		return pocet;
	}
	
	/**VytvorÌ z pola string*/
	public String naString(char[][] pole){
		StringBuilder string = new StringBuilder();
		for (int i = 0;i<riadky;i++)
			for (int j = 0;j<stlpce;j++)
				string.append(pole[i][j]);
		return string.toString();
	}
	
	/**VytvorÌ zo stringu pole*/
	public char[][] naPole(String string){
		int pocet = -1;
		char[][] pole = new char[riadky][stlpce]; 
		for (int i = 0;i<riadky;i++)
			for (int j = 0;j<stlpce;j++){
				pocet++;
				pole[i][j] = string.charAt(pocet);
			}
		return pole;
	}
	
	
    /**Tu sa len otvorÌ GUI*/
	public static void main (String[] args) {
		new Gui();		
	}

}
