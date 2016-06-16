package controller;

import java.util.ArrayList;
import java.util.List;

import model.prvky.Kniha;
import model.prvky.Okienko;
import model.prvky.Strana;
import model.zamestnanci.MaliarPostavy;
import model.zamestnanci.MaliarPozadia;
import model.zamestnanci.Textar;
import model.zamestnanci.Umelec;
import view.Gui;

/** Hlavná class */
public class Proces {

	/**
	 * Vdy, keï sa vytvorí nová strana, pridá sa do jej atributu toto èíslo, a
	 * zvıši sa +1
	 */
	private int cislovanieStran = 1;
	/** Celá základná kniha */
	private Kniha komix = new Kniha();
	/** Je len jeden textár */
	private Umelec textar = new Textar();
	/** Je len jeden maliar pozadia */
	private Umelec maliarPozadia = new MaliarPozadia();
	/** Maliarov postáv je viac, kadı na svoju postavièku */
	private List<Umelec> maliariPostav = new ArrayList<>();
	/** Existujúce postavy v jednom stringu */
	private String zoznamPostav = "\n" + "Maliari vedia kresli postavy: "
			+ "\n";

	/** Singleton */
	private static Proces instance = null;

	private Proces() {
	}

	public static Proces getInstance() {
		if (instance == null)
			instance = new Proces();
		return instance;
	}

	/** Najmutie nového maliara postavy */
	public void novyMaliarPostavy() {
		maliariPostav.add(new MaliarPostavy());
	}

	/** Vıpis poètu strán */
	public String pocetVsetkychStran() {
		return ("Poèet všetkych stran komixu je: "
				+ this.komix.getStrany().size() + "\n");
	}

	/** Vıpis maliar bol vytvorenı */
	public String vypisMaliarVytvoreny() {
		return ("Bol vytvorenı novı maliar postavy" + "\n");
	}

	/** Urèí, akú postavu má danı maliar kresli */
	public void pomenujPostavuMaliara(int pocet, String s) {
		((MaliarPostavy) maliariPostav.get(pocet - 1)).setMenoPostavyMaliara(s);
		setZoznamPostav(" " + s);
	}

	/** Danı maliar kreslí bojovníka */
	public void bojovnik(int pocet) {
		((MaliarPostavy) maliariPostav.get(pocet - 1)).setBojovnik(true);
	}

	/** Danı maliar kreslí bojovníka */
	public void remeselnik(int pocet) {

		((MaliarPostavy) maliariPostav.get(pocet - 1)).setRemeselnik(true);
	}

	/** Danı maliar kreslí bojovníka */
	public void starec(int pocet) {

		((MaliarPostavy) maliariPostav.get(pocet - 1)).setStarec(true);
	}

	/** Danı maliar kreslí bojovníka */
	public void teenager(int pocet) {
		((MaliarPostavy) maliariPostav.get(pocet - 1)).setTeenager(true);
	}

	/** Ak treba, ozbrojí */
	public void ozbrojenaPostava(int stranaCislo, int panelCislo,
			int postavaCislo) {
		this.komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
				.getPostavy()[postavaCislo - 1].ozbroj();
	}

	/** Vıpis, akú postavièku maliar kreslí */
	public String vypisMenoPostavyMaliara(int pocet, String s) {
		return ("Postava "
				+ pocet
				+ ". maliara sa volá "
				+ ((MaliarPostavy) maliariPostav.get(pocet - 1))
						.getMenoPostavyMaliara() + " a je to " + s + ".\n");
	}

	/** Zistí názov diela */
	public void nazovDiela(String s) {
		this.komix.setNazovDiela(s);
	}

	/** Vypíše názov diela */
	public String vypisNazovDiela() {
		return ("Názov diela je: " + this.komix.getNazovDiela() + "\n");
	}

	/** Pridá sa strana s poètom okienok do komixu */
	public void vytvorStranu(int pocetOkienok) {
		this.komix.getStrany().add(new Strana(pocetOkienok));
		this.komix.getStrany().get(this.cislovanieStran - 1)
				.setCislo(this.cislovanieStran);
		this.cislovanieStran++;
	}

	/** Info o práve pridanej strane */
	public String vypisOStrane() {
		return ("Vytvorená strana s èíslom: "
				+ this.komix.getStrany().get(this.cislovanieStran - 2)
						.getCislo()
				+ " a má "
				+ this.komix.getStrany().get(this.cislovanieStran - 2)
						.getPanely().length + " okienok" + "\n");
	}

	/** Vytvorí na danej strane okienko s plánovanım poètom bublín a postáv */
	public void vytvorPanel(int stranaCislo, int panelCislo, int pocetBublin,
			int pocetPostav) {
		this.komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1] = new Okienko(
				pocetBublin, pocetPostav);
		this.komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
				.setCisloPanelu(panelCislo);
	}

	/** Vıpis informacii o práve pridanom okienku */
	public String vypisOBublinyPostavy(int stranaCislo, int panelCislo,
			int pocetBublin, int pocetPostav) {
		return ("Na strane "
				+ this.komix.getStrany().get(stranaCislo - 1).getCislo()
				+ " má okienko v poradí s èíslom "
				+ this.komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
						.getCisloPanelu()
				+ " poèet bublín: "
				+ this.komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
						.getBubliny().length
				+ " a poèet postáv: "
				+ this.komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
						.getPostavy().length + "\n");
	}

	/** Nastavenie hranatej bubliny */
	public void hranatost(int stranaCislo, int panelCislo, int bublinaCislo) {
		this.komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
				.getBubliny()[bublinaCislo - 1].setHranata(true);
	}

	/** Vıpis, èi je bublina hranatá */
	public String vypisHranost(int stranaCislo, int panelCislo, int bublinaCislo) {
		if (this.komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
				.getBubliny()[bublinaCislo - 1].isHranata() == true)
			return ("Táto bublina je hranatá." + "\n");
		else
			return ("Táto bublina nie je hranatá." + "\n");
	}

	/** Informácie o vytvorenej bubline, vıpis */
	public String vypisUlozeniaBubliny(int stranaCislo, int panelCislo,
			int bublinaCislo) {
		return ("Text ("
				+ komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
						.getBubliny()[bublinaCislo - 1].getText()
				+ ") bol napísanı do bubliny na strane " + stranaCislo
				+ " do okienka èíslo " + panelCislo + " do " + bublinaCislo
				+ ".bubliny" + "\n");
	}

	/**
	 * Aby som mohol pouíva metodu napisTextMimo(), èo vie len textár, volám v
	 * buttone
	 */
	public Textar zmenTextar() {
		return (Textar) textar;
	}

	/** Akı text sa napísal na stranu mimo bublín */
	public String vypisTextuMimo(int stranaCislo) {
		return ("Text (" + komix.getStrany().get(stranaCislo - 1).getTextMimo()
				+ ") bol napísanı na stranu " + stranaCislo + "\n");
	}

	/** Aké pozadie bolo pridané */
	public String vypisPozadie(int stranaCislo, int panelCislo) {
		return ("Text ("
				+ komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
						.getPozadie().getNazov() + ") bol napísanı na stranu "
				+ stranaCislo + " do okienka èíslo " + panelCislo + "\n");
	}

	/** Aká postava bola pridaná a kam */
	public String vypisPostava(int stranaCislo, int panelCislo, int postavaCislo) {
		return ("Postava ("
				+ this.komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
						.getPostavy()[postavaCislo - 1].getMeno()
				+ ") bola nakreslená na stranu "
				+ stranaCislo
				+ " do okienka èíslo "
				+ panelCislo
				+ " a jej zbraòou je: "
				+ this.komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
						.getPostavy()[postavaCislo - 1].getVızbroj() + "\n");
	}

	public List<Umelec> getMaliariPostav() {
		return maliariPostav;
	}

	public void setMaliariPostav(List<Umelec> maliariPostav) {
		this.maliariPostav = maliariPostav;
	}

	public String getZoznamPostav() {
		return zoznamPostav;
	}

	public void setZoznamPostav(String zoznamPostav) {
		this.zoznamPostav += zoznamPostav;
	}

	public String vypisExistujucePostavy() {
		return getZoznamPostav();
	}

	public Umelec getMaliarPozadia() {
		return maliarPozadia;
	}

	public void setMaliarPozadia(Umelec maliarPozadia) {
		this.maliarPozadia = maliarPozadia;
	}

	public int getCislovanieStran() {
		return cislovanieStran;
	}

	public void setCislovanieStran(int cislovanieStran) {
		this.cislovanieStran = cislovanieStran;
	}

	public Umelec getTextar() {
		return textar;
	}

	public void setTextar(Umelec textar) {
		this.textar = textar;
	}

	public Kniha getKomix() {
		return komix;
	}

	public void setKomix(Kniha komix) {
		this.komix = komix;
	}

	public static void main(String[] args) {

		/** Naèíta sa Gui */
		new Gui();

	}
}
