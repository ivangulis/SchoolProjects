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

/** Hlavn� class */
public class Proces {

	/**
	 * V�dy, ke� sa vytvor� nov� strana, prid� sa do jej atributu toto ��slo, a
	 * zv��i sa +1
	 */
	private int cislovanieStran = 1;
	/** Cel� z�kladn� kniha */
	private Kniha komix = new Kniha();
	/** Je len jeden text�r */
	private Umelec textar = new Textar();
	/** Je len jeden maliar pozadia */
	private Umelec maliarPozadia = new MaliarPozadia();
	/** Maliarov post�v je viac, ka�d� na svoju postavi�ku */
	private List<Umelec> maliariPostav = new ArrayList<>();
	/** Existuj�ce postavy v jednom stringu */
	private String zoznamPostav = "\n" + "Maliari vedia kresli� postavy: "
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

	/** Najmutie nov�ho maliara postavy */
	public void novyMaliarPostavy() {
		maliariPostav.add(new MaliarPostavy());
	}

	/** V�pis po�tu str�n */
	public String pocetVsetkychStran() {
		return ("Po�et v�etkych stran komixu je: "
				+ this.komix.getStrany().size() + "\n");
	}

	/** V�pis maliar bol vytvoren� */
	public String vypisMaliarVytvoreny() {
		return ("Bol vytvoren� nov� maliar postavy" + "\n");
	}

	/** Ur��, ak� postavu m� dan� maliar kresli� */
	public void pomenujPostavuMaliara(int pocet, String s) {
		((MaliarPostavy) maliariPostav.get(pocet - 1)).setMenoPostavyMaliara(s);
		setZoznamPostav(" " + s);
	}

	/** Dan� maliar kresl� bojovn�ka */
	public void bojovnik(int pocet) {
		((MaliarPostavy) maliariPostav.get(pocet - 1)).setBojovnik(true);
	}

	/** Dan� maliar kresl� bojovn�ka */
	public void remeselnik(int pocet) {

		((MaliarPostavy) maliariPostav.get(pocet - 1)).setRemeselnik(true);
	}

	/** Dan� maliar kresl� bojovn�ka */
	public void starec(int pocet) {

		((MaliarPostavy) maliariPostav.get(pocet - 1)).setStarec(true);
	}

	/** Dan� maliar kresl� bojovn�ka */
	public void teenager(int pocet) {
		((MaliarPostavy) maliariPostav.get(pocet - 1)).setTeenager(true);
	}

	/** Ak treba, ozbroj� */
	public void ozbrojenaPostava(int stranaCislo, int panelCislo,
			int postavaCislo) {
		this.komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
				.getPostavy()[postavaCislo - 1].ozbroj();
	}

	/** V�pis, ak� postavi�ku maliar kresl� */
	public String vypisMenoPostavyMaliara(int pocet, String s) {
		return ("Postava "
				+ pocet
				+ ". maliara sa vol� "
				+ ((MaliarPostavy) maliariPostav.get(pocet - 1))
						.getMenoPostavyMaliara() + " a je to " + s + ".\n");
	}

	/** Zist� n�zov diela */
	public void nazovDiela(String s) {
		this.komix.setNazovDiela(s);
	}

	/** Vyp�e n�zov diela */
	public String vypisNazovDiela() {
		return ("N�zov diela je: " + this.komix.getNazovDiela() + "\n");
	}

	/** Prid� sa strana s po�tom okienok do komixu */
	public void vytvorStranu(int pocetOkienok) {
		this.komix.getStrany().add(new Strana(pocetOkienok));
		this.komix.getStrany().get(this.cislovanieStran - 1)
				.setCislo(this.cislovanieStran);
		this.cislovanieStran++;
	}

	/** Info o pr�ve pridanej strane */
	public String vypisOStrane() {
		return ("Vytvoren� strana s ��slom: "
				+ this.komix.getStrany().get(this.cislovanieStran - 2)
						.getCislo()
				+ " a m� "
				+ this.komix.getStrany().get(this.cislovanieStran - 2)
						.getPanely().length + " okienok" + "\n");
	}

	/** Vytvor� na danej strane okienko s pl�novan�m po�tom bubl�n a post�v */
	public void vytvorPanel(int stranaCislo, int panelCislo, int pocetBublin,
			int pocetPostav) {
		this.komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1] = new Okienko(
				pocetBublin, pocetPostav);
		this.komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
				.setCisloPanelu(panelCislo);
	}

	/** V�pis informacii o pr�ve pridanom okienku */
	public String vypisOBublinyPostavy(int stranaCislo, int panelCislo,
			int pocetBublin, int pocetPostav) {
		return ("Na strane "
				+ this.komix.getStrany().get(stranaCislo - 1).getCislo()
				+ " m� okienko v porad� s ��slom "
				+ this.komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
						.getCisloPanelu()
				+ " po�et bubl�n: "
				+ this.komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
						.getBubliny().length
				+ " a po�et post�v: "
				+ this.komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
						.getPostavy().length + "\n");
	}

	/** Nastavenie hranatej bubliny */
	public void hranatost(int stranaCislo, int panelCislo, int bublinaCislo) {
		this.komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
				.getBubliny()[bublinaCislo - 1].setHranata(true);
	}

	/** V�pis, �i je bublina hranat� */
	public String vypisHranost(int stranaCislo, int panelCislo, int bublinaCislo) {
		if (this.komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
				.getBubliny()[bublinaCislo - 1].isHranata() == true)
			return ("T�to bublina je hranat�." + "\n");
		else
			return ("T�to bublina nie je hranat�." + "\n");
	}

	/** Inform�cie o vytvorenej bubline, v�pis */
	public String vypisUlozeniaBubliny(int stranaCislo, int panelCislo,
			int bublinaCislo) {
		return ("Text ("
				+ komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
						.getBubliny()[bublinaCislo - 1].getText()
				+ ") bol nap�san� do bubliny na strane " + stranaCislo
				+ " do okienka ��slo " + panelCislo + " do " + bublinaCislo
				+ ".bubliny" + "\n");
	}

	/**
	 * Aby som mohol pou��va� metodu napisTextMimo(), �o vie len text�r, vol�m v
	 * buttone
	 */
	public Textar zmenTextar() {
		return (Textar) textar;
	}

	/** Ak� text sa nap�sal na stranu mimo bubl�n */
	public String vypisTextuMimo(int stranaCislo) {
		return ("Text (" + komix.getStrany().get(stranaCislo - 1).getTextMimo()
				+ ") bol nap�san� na stranu " + stranaCislo + "\n");
	}

	/** Ak� pozadie bolo pridan� */
	public String vypisPozadie(int stranaCislo, int panelCislo) {
		return ("Text ("
				+ komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
						.getPozadie().getNazov() + ") bol nap�san� na stranu "
				+ stranaCislo + " do okienka ��slo " + panelCislo + "\n");
	}

	/** Ak� postava bola pridan� a kam */
	public String vypisPostava(int stranaCislo, int panelCislo, int postavaCislo) {
		return ("Postava ("
				+ this.komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
						.getPostavy()[postavaCislo - 1].getMeno()
				+ ") bola nakreslen� na stranu "
				+ stranaCislo
				+ " do okienka ��slo "
				+ panelCislo
				+ " a jej zbra�ou je: "
				+ this.komix.getStrany().get(stranaCislo - 1).getPanely()[panelCislo - 1]
						.getPostavy()[postavaCislo - 1].getV�zbroj() + "\n");
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

		/** Na��ta sa Gui */
		new Gui();

	}
}
