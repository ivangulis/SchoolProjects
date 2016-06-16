package controller;

import java.util.Random;

import model.Generacia;
import model.Jedinec;
import model.Mapa;
import model.Cvicisko;
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
	
	public int pocetJedincov;
	public int maxPocetGeneracii = 0;
	public int aktPocetGeneracii = 0;
	public Generacia nova;
	public Generacia stara;
	public Generacia prva;
	//public Mapa mapa = new Mapa(7,7,6,3,5);
	public Mapa mapa = new Mapa();
	public Cvicisko cvicisko = new Cvicisko();
	
	public String inicializuj(int pocet){
		if (pocet<20){
			pocet = 20;
		}
		pocetJedincov = pocet;
		aktPocetGeneracii = 1;
		maxPocetGeneracii = 0;
		Jedinec novyJedinec;
		prva = new Generacia();
		String[] bunky;
		
		for (int i = 0;i<pocet;i++){
			Mapa mapaPom = new Mapa();
			novyJedinec = new Jedinec(mapaPom,1); //zatial je na starte
			bunky = novyJedinec.getBunky().clone();
			novyJedinec.setFitnes(cvicisko.behaj(novyJedinec));
			novyJedinec.setBunky(bunky);
			if (novyJedinec.getMapa().getPocetPokladov() == 0){
				return "Jedinec našiel všetky poklady, jeho postupnos krokov:\n" + novyJedinec.getPostupnost().toString() 
						+ "\nJeho poèet krokov: " + novyJedinec.getPostupnost().size()  + "\nJe z generácie èíslo " + 1 + "\n\n";
			}
			prva.getGeneracia().add(novyJedinec);
		}
		stara = prva;
		StringBuilder celaMapa = new StringBuilder();
		for (int i=0;i<=mapa.getRozmerR();i++){
			 for (int j=0;j<=mapa.getRozmerS();j++){
				 celaMapa.append(mapa.getMapa()[i][j] + " ");
			 }
			 celaMapa.append("\n");
		}
		celaMapa.append("\n");
		return celaMapa.toString();
	}
	
	public String vypisMapu(){
		StringBuilder celaMapa = new StringBuilder();
		for (int i=0;i<=mapa.getRozmerR();i++){
			 for (int j=0;j<=mapa.getRozmerS();j++){
				 celaMapa.append(mapa.getMapa()[i][j] + " ");
			 }
			 celaMapa.append("\n");
		}
		celaMapa.append("\n");
		return celaMapa.toString();
	}
	
	public String algo(int maxGen, double mutacia, int select, int pocet, boolean vyvoleny){ //0=turnaj, 1=ruleta		
		maxPocetGeneracii +=maxGen;
		if (mutacia>1.0)
			mutacia=1.0;
		String[] bunky;
		while (aktPocetGeneracii < maxPocetGeneracii){//elitarizmus 1
			nova = new Generacia();
			double sucetFit = 0;
			double max = 0.0;
			int maxIndex = 0;
			for (int i=0;i<pocetJedincov;i++) {
				if (vyvoleny == true){
					if (stara.getGeneracia().get(i).getFitnes() > max){
						max = stara.getGeneracia().get(i).getFitnes();
						maxIndex = i;
					}
				}
				sucetFit += stara.getGeneracia().get(i).getFitnes();
			}
			if (vyvoleny == true) nova.getGeneracia().add(stara.getGeneracia().get(maxIndex));
			if (select == 1){
				if (sucetFit<0)
					sucetFit = 0;
				Random rnd = new Random();
				Random rndBunky = new Random();
				int toc;
				int koho;
				int vyber;
				double k;
				
				while (nova.getGeneracia().size()!=pocetJedincov){
					vyber = 0;
					k = 0;
					toc = rnd.nextInt((int)sucetFit)+1;
					while (k<toc && vyber<99){
						k += stara.getGeneracia().get(vyber).getFitnes();
						vyber++;
					}
					Jedinec rodic1 = stara.getGeneracia().get(vyber);
					vyber = 0;
					k = 0;
					toc = rnd.nextInt((int)sucetFit)+1;
					while (k<toc && vyber<99){
						k += stara.getGeneracia().get(vyber).getFitnes();
						vyber++;
					}
					Jedinec rodic2 = stara.getGeneracia().get(vyber);
					Mapa mapaPom = new Mapa();
					Jedinec novyJedinec = new Jedinec(mapaPom);
					for (int j=0;j<64;j++){
						koho = rndBunky.nextInt(2);
						if (koho == 0)
							novyJedinec.getBunky()[j] = rodic1.getBunky()[j];
						else novyJedinec.getBunky()[j] = rodic2.getBunky()[j];
					}
					for (int i=0;i<64;i++){ //mutácia
						double random = Math.random();
						if (random<mutacia){
							StringBuilder pom = new StringBuilder();
							Random bit = new Random();
							int cislo = bit.nextInt(255);
							pom = new StringBuilder();
							for (int z = Integer.toBinaryString(cislo).length();z<8;z++)
								pom.append(0);
							pom.append(Integer.toBinaryString(cislo));
							novyJedinec.getBunky()[i] = pom.toString();
						}
					}
					bunky = novyJedinec.getBunky().clone();
					novyJedinec.setFitnes(cvicisko.behaj(novyJedinec));
					novyJedinec.setBunky(bunky);
					if (novyJedinec.getMapa().getPocetPokladov() == 0){
						maxPocetGeneracii = 0;
						int pom = aktPocetGeneracii;
						aktPocetGeneracii = 1;
						stara = prva; //resetne sa znova na inicializovane
						return "Jedinec našiel všetky poklady, jeho postupnos krokov:\n" + novyJedinec.getPostupnost().toString() 
								+ "\nJeho poèet krokov: " + novyJedinec.getPostupnost().size()  + "\nJe z generácie èíslo " + pom + "\n\n";
					}
					nova.getGeneracia().add(novyJedinec);
				}
			}
			else {
				Random rnd = new Random();
				Random rndBunky = new Random();
				int koho;
				while (nova.getGeneracia().size()!=pocetJedincov){
					int[] turnaj = new int[5];
					rnd = new Random();
					for (int j=0;j<5;j++)
						turnaj[j] = rnd.nextInt(pocetJedincov);
					Jedinec rodic1,rodic2;
					double max1 = 0,max2 = 0;
					int maxIndex1 = 0, maxIndex2 = 0;
					int rodicTurnaj1 = -1;
					for (int j=0;j<5;j++){
						if (stara.getGeneracia().get(turnaj[j]).getFitnes() > max1){
							maxIndex1 = turnaj[j];
							rodicTurnaj1 = j;
						}
					}
					rodic1 = stara.getGeneracia().get(maxIndex1);
					turnaj[rodicTurnaj1] = -1;
					for (int j=0;j<5;j++){
						if (turnaj[j] != -1 && stara.getGeneracia().get(turnaj[j]).getFitnes() > max2){
							maxIndex2 = turnaj[j];
						}
					}
					rodic2 = stara.getGeneracia().get(maxIndex2);
					Mapa mapaPom = new Mapa();
					Jedinec novyJedinec = new Jedinec(mapaPom);
					for (int j=0;j<64;j++){
						koho = rndBunky.nextInt(2);
						if (koho == 0)
							novyJedinec.getBunky()[j] = rodic1.getBunky()[j];
						else novyJedinec.getBunky()[j] = rodic2.getBunky()[j];
					}
					for (int i=0;i<64;i++){ //mutácia
						double random = Math.random();
						if (random<mutacia){
							StringBuilder pom = new StringBuilder();
							Random bit = new Random();
							int cislo = bit.nextInt(255);
							pom = new StringBuilder();
							for (int z = Integer.toBinaryString(cislo).length();z<8;z++)
								pom.append(0);
							pom.append(Integer.toBinaryString(cislo));
							novyJedinec.getBunky()[i] = pom.toString();
						}
					}
					bunky = novyJedinec.getBunky().clone();
					novyJedinec.setFitnes(cvicisko.behaj(novyJedinec));
					novyJedinec.setBunky(bunky);
					if (novyJedinec.getMapa().getPocetPokladov() == 0){
						maxPocetGeneracii = 0;
						int pom = aktPocetGeneracii;
						aktPocetGeneracii = 1;
						stara = prva; //resetne sa znova na inicializovane
						return "Jedinec našiel všetky poklady, jeho postupnos krokov:\n" + novyJedinec.getPostupnost().toString() 
								+ "\nJeho poèet krokov: " + novyJedinec.getPostupnost().size()  + "\nJe z generácie èíslo " + pom + "\n\n";
					}
					nova.getGeneracia().add(novyJedinec);
				}
			}
			stara = nova;
			aktPocetGeneracii++;
		}
		double max = 0.0;
		int maxIndex = 0;
		for (int i=0;i<pocetJedincov;i++) {
			if (stara.getGeneracia().get(i).getFitnes() > max){
				max = stara.getGeneracia().get(i).getFitnes();
				maxIndex = i;
			}
		}
		return "Už bolo vygenerovaných " + maxPocetGeneracii +
				" generácii a nikto zatial všetky poklady nenašiel.\nNajlepší jedinec našiel pokladov: " +
				(5-stara.getGeneracia().get(maxIndex).getMapa().getPocetPokladov()) + "/5" +
				"\nJeho cesta je " + stara.getGeneracia().get(maxIndex).getPostupnost().toString() +
				"\nJeho poèet krokov: " + stara.getGeneracia().get(maxIndex).getPostupnost().size() + "\nPre pokraèovanie spustite znova vybraný štýl.\n\n";
	}
	
	
	
	/**Tu sa len otvorí GUI*/
	public static void main (String[] args) {
		new Gui();		
	}
}
