package view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controller.Proces;

public class Gui {

	/** Vöetko ku hlavnÈmu oknu */
	private JFrame oHlavne = new JFrame("Hlavne Okno");
	private JLabel lHlavneHlasenie = new JLabel(
			"Najprv musÌ zaËaù riaditeæ zaloûenÌm vonkajöej ötrukt˙ry");
	private JButton bDirector = new JButton("Riaditeæ");
	private JButton bV˝pisy = new JButton("V˝pisy");
	private JButton bTextar = new JButton("Text·r");
	private JButton bMaliarPostavy = new JButton("Maliar Postavy");
	private JButton bMaliarPozadia = new JButton("Maliar Pozadia");

	/** Vöetko k oknu oUser */
	private JFrame oRiaditel = new JFrame("Riaditeæ");
	private JTextArea vypisNazvuDiela = new JTextArea(4, 15);
	private JLabel lNazovDiela = new JLabel("Zadaj n·zov diela");
	private JTextField tDielo = new JTextField(10);
	private JButton bNazovDiela = new JButton("Uloû n·zov diela");
	private JButton bPridajStranu = new JButton("Pridaj stranu");
	private JFrame oNovaStrana = new JFrame("Nov· Strana");

	/** Vöetko z novÈho okna z oUsera */
	private JTextArea vypisOStrane = new JTextArea(20, 30);
	private JLabel lStranaCislo = new JLabel("Na stranu ËÌslo:");
	private JTextField tStranaCislo = new JTextField(3);
	private JLabel lOkienkoCislo = new JLabel("Na okienko ËÌslo:");
	private JTextField tOkienkoCislo = new JTextField(3);
	private JLabel lPocetBublin = new JLabel("Pridaj bublÌn:");
	private JTextField tBubliny = new JTextField(3);
	private JLabel lPocetPostav = new JLabel("Pridaj post·v:");
	private JTextField tPostavy = new JTextField(3);
	private JButton bPocetBublinPostav = new JButton(
			"Uloû poËet bublÌn a post·v");
	private JLabel lPocetOkienok = new JLabel(
			"Zadaj poËet okienok na novej strane");
	private JTextField tOkienka = new JTextField(3);
	private JButton bPocetOkienok = new JButton("Uloû poËet okienok");

	/** Vöetko k oknu oTextar */
	private JFrame oTextar = new JFrame();
	private JButton bTextarVyplnBubliny = new JButton("VypæÚaj bubliny");
	private JTextField tTextMimo = new JTextField(50);
	private JButton bTextarVyplnTextMimo = new JButton(
			"VypæÚaj text mimo okienok");
	private JTextField tTextBubliny = new JTextField(50);
	private JLabel lStranaCisloT = new JLabel("Na stranu ËÌslo:");
	private JTextField tStranaCisloT = new JTextField(3);
	private JButton bUlozTextMimo = new JButton("Uloû tento text");
	private JTextArea vypisTextuMimo = new JTextArea(10, 30);
	private JButton bSp‰tTextar2 = new JButton("Vr·ù sa do v˝beru");

	/** Vöetko z novÈho okna z oTextara */
	private JTextField tStranaCisloB = new JTextField(3);
	private JTextField tPanelCisloB = new JTextField(3);
	private JTextField tBublinaCisloB = new JTextField(3);
	private JLabel lStranaCisloB = new JLabel("Na stranu ËÌslo:");
	private JLabel lOkienkoCisloB = new JLabel("Na okienko ËÌslo:");
	private JLabel lBublinaCislo = new JLabel("Do bubliny ËÌslo:");
	private JLabel lHranata = new JLabel("Hranat·?");
	private JCheckBox checkBox = new JCheckBox();
	private JButton bUlozBublinu = new JButton("Uloû bublinu");
	private JTextArea vypisUlozeniaBubliny = new JTextArea(10, 30);
	private JButton bSp‰tTextar = new JButton("Vr·ù sa do v˝beru");

	/** Vöetko k oknu oVypisy */
	private JFrame oVypisy = new JFrame();
	private JButton bPoËetVsetkychStran = new JButton("Koæko m· komix str·n?");
	private JButton bNazovCelehoDiela = new JButton("Ako sa vol· komix?");
	private JButton bExistujucePostavy = new JButton(
			"Ake postavy vedia maliari kresliù?");
	private JTextArea vypisVsetkehoDruhu = new JTextArea(20, 30);

	/** Vöetko k oknu oMaliarPostavy */
	private JFrame oMaliarPostavy = new JFrame();
	private JButton bNovyMaliarPostavy = new JButton(
			"Vytvoriù novÈho maliara postavy");
	private JLabel lMaliarPostavyCislo = new JLabel(
			"Pre maliara postavy s ËÌslom:");
	private JTextField tMaliarPostavyCislo = new JTextField(15);
	private JLabel lMenoPostavy = new JLabel(
			"Zadaj meno postavy, ktor˙ bude maliar kresliù(kaûdy maliar max jednu):");
	private JTextField tMenoPostava = new JTextField(15);
	private String[] povolanie = { "bojovnÌk", "remeselnÌk", "starec",
			"teenager" };
	private JComboBox<String> cPovolanie = new JComboBox<String>(povolanie);
	private JButton bUlozMenoPostavy = new JButton("Uloz postavu pre maliara");
	private JButton bNamaluj = new JButton("Malovanie");
	private JLabel lOzbrojit = new JLabel("Ozbrojiù?");
	private JCheckBox checkBoxZbrane = new JCheckBox();

	/** Vöetko z novÈho okna z oTextara */
	private JFrame oMalovanie = new JFrame();
	private JLabel lMaliarCislo = new JLabel("Maliar postavy ËÌslo:");
	private JTextField tMaliarCislo = new JTextField(3);
	private JLabel lStranaPostavy = new JLabel("Na stranu ËÌslo:");
	private JTextField tStranaPostavy = new JTextField(3);
	private JLabel lOkienkoPostavy = new JLabel("Na okienko ËÌslo:");
	private JTextField tOnienkoPostavy = new JTextField(3);
	private JLabel lCisloPostavy = new JLabel("Na miesto postavy s ËÌslom:");
	private JTextField tCisloPostavy = new JTextField(3);
	private JButton bNamalujPostavu = new JButton("Namaluj postavu");
	private JTextArea vypisPostavy = new JTextArea(10, 30);

	/** Vöetko k oknu oMaliarPozadia */
	private JFrame oMaliarPozadia = new JFrame();
	private JTextField tPozadie = new JTextField(50);
	private JTextField tStranaCisloP = new JTextField(3);
	private JLabel lStranaCisloP = new JLabel("Na stranu ËÌslo:");
	private JTextField tPanelCisloP = new JTextField(3);
	private JLabel lOkienkoCisloP = new JLabel("Na okienko ËÌslo:");
	private JButton bNamalujPozadie = new JButton("Namaluj Pozadie");
	private JTextArea vypisPozadia = new JTextArea(10, 30);

	/** CelÈ gui */
	public Gui() {

		oHlavne.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		oHlavne.setVisible(true);
		oHlavne.setSize(400, 150);
		oHlavne.setLayout(new FlowLayout());

		oHlavne.add(lHlavneHlasenie);
		oHlavne.add(bDirector);
		oHlavne.add(bV˝pisy);
		oHlavne.add(bMaliarPostavy);
		oHlavne.add(bMaliarPozadia);
		oHlavne.add(bTextar);

		oRiaditel.add(lNazovDiela);
		oRiaditel.add(tDielo);
		oRiaditel.add(bNazovDiela);
		oRiaditel.add(bPridajStranu);
		oRiaditel.add(vypisNazvuDiela);

		oNovaStrana.add(lPocetOkienok);
		oNovaStrana.add(tOkienka);
		oNovaStrana.add(bPocetOkienok);
		oNovaStrana.add(lStranaCislo);
		oNovaStrana.add(tStranaCislo);
		oNovaStrana.add(lOkienkoCislo);
		oNovaStrana.add(tOkienkoCislo);
		oNovaStrana.add(lPocetBublin);
		oNovaStrana.add(tBubliny);
		oNovaStrana.add(lPocetPostav);
		oNovaStrana.add(tPostavy);
		oNovaStrana.add(bPocetBublinPostav);
		oNovaStrana.add(vypisOStrane);

		oTextar.add(tTextBubliny);
		oTextar.add(tTextMimo);
		oTextar.add(lStranaCisloT);
		oTextar.add(lStranaCisloB);
		oTextar.add(tStranaCisloB);
		oTextar.add(tStranaCisloT);
		oTextar.add(lOkienkoCisloB);
		oTextar.add(tPanelCisloB);
		oTextar.add(lBublinaCislo);
		oTextar.add(bTextarVyplnBubliny);
		oTextar.add(bTextarVyplnTextMimo);
		oTextar.add(tBublinaCisloB);
		oTextar.add(lHranata);
		oTextar.add(checkBox);
		oTextar.add(bUlozBublinu);
		oTextar.add(bUlozTextMimo);
		oTextar.add(vypisUlozeniaBubliny);
		oTextar.add(vypisTextuMimo);
		oTextar.add(bSp‰tTextar);
		oTextar.add(bSp‰tTextar2);

		oMaliarPozadia.add(tPozadie);
		oMaliarPozadia.add(lStranaCisloP);
		oMaliarPozadia.add(tStranaCisloP);
		oMaliarPozadia.add(lOkienkoCisloP);
		oMaliarPozadia.add(tPanelCisloP);
		oMaliarPozadia.add(bNamalujPozadie);
		oMaliarPozadia.add(vypisPozadia);

		oMaliarPostavy.add(bNovyMaliarPostavy);
		oMaliarPostavy.add(lMaliarPostavyCislo);
		oMaliarPostavy.add(tMaliarPostavyCislo);
		oMaliarPostavy.add(lMenoPostavy);
		oMaliarPostavy.add(tMenoPostava);
		oMaliarPostavy.add(cPovolanie);
		oMaliarPostavy.add(bUlozMenoPostavy);
		oMaliarPostavy.add(vypisPostavy);
		oMaliarPostavy.add(bNamaluj);

		oMalovanie.add(lMaliarCislo);
		oMalovanie.add(tMaliarCislo);
		oMalovanie.add(lStranaPostavy);
		oMalovanie.add(tStranaPostavy);
		oMalovanie.add(lOkienkoPostavy);
		oMalovanie.add(tOnienkoPostavy);
		oMalovanie.add(lCisloPostavy);
		oMalovanie.add(tCisloPostavy);
		oMalovanie.add(lOzbrojit);
		oMalovanie.add(checkBoxZbrane);
		oMalovanie.add(bNamalujPostavu);

		oVypisy.add(bNazovCelehoDiela);
		oVypisy.add(bPoËetVsetkychStran);
		oVypisy.add(bExistujucePostavy);
		oVypisy.add(vypisVsetkehoDruhu);

		/** Otv·ra okno riaditeæa */
		bDirector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bPridajStranu.setVisible(false);
				oRiaditel.setSize(500, 500);
				oRiaditel.setVisible(true);
				oRiaditel.setLayout(new FlowLayout());
			}
		});

		/** UloûÌ a vypÌöe n·zov diela */
		bNazovDiela.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Proces.getInstance().nazovDiela(tDielo.getText());
				vypisNazvuDiela.append(Proces.getInstance().vypisNazovDiela());
				bPridajStranu.setVisible(true);
			}
		});

		/** NovÈ okno, kde sa prid·vaj˙ strany */
		bPridajStranu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lStranaCislo.setVisible(false);
				lOkienkoCislo.setVisible(false);
				lPocetBublin.setVisible(false);
				lPocetPostav.setVisible(false);
				tStranaCislo.setVisible(false);
				tOkienkoCislo.setVisible(false);
				tBubliny.setVisible(false);
				tPostavy.setVisible(false);
				bPocetBublinPostav.setVisible(false);
				oNovaStrana.setSize(1000, 500);
				oNovaStrana.setVisible(true);
				oNovaStrana.setLayout(new FlowLayout());
			}
		});

		/** Do okienka sa prid· pl·novan˝ poËet post·v a bublÌn */
		bPocetBublinPostav.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Proces.getInstance().vytvorPanel(
						Integer.parseInt(tStranaCislo.getText()),
						Integer.parseInt(tOkienkoCislo.getText()),
						Integer.parseInt(tBubliny.getText()),
						Integer.parseInt(tPostavy.getText()));
				vypisOStrane.append(Proces.getInstance().vypisOBublinyPostavy(
						Integer.parseInt(tStranaCislo.getText()),
						Integer.parseInt(tOkienkoCislo.getText()),
						Integer.parseInt(tBubliny.getText()),
						Integer.parseInt(tPostavy.getText())));
			}
		});

		/** VytvorÌ sa strana s dan˝m poËtom panelov */
		bPocetOkienok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Proces.getInstance().vytvorStranu(
						Integer.parseInt(tOkienka.getText()));
				vypisOStrane.append(Proces.getInstance().vypisOStrane());

				lStranaCislo.setVisible(true);
				lOkienkoCislo.setVisible(true);
				lPocetBublin.setVisible(true);
				lPocetPostav.setVisible(true);
				tStranaCislo.setVisible(true);
				tOkienkoCislo.setVisible(true);
				tBubliny.setVisible(true);
				tPostavy.setVisible(true);
				bPocetBublinPostav.setVisible(true);
			}
		});

		/** Okno s v˝pismi */
		bV˝pisy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				oVypisy.setTitle("V˝pisy");
				oVypisy.setSize(500, 500);
				oVypisy.setVisible(true);
				oVypisy.setLayout(new FlowLayout());
				vypisVsetkehoDruhu.setVisible(true);
				bPoËetVsetkychStran.setVisible(true);
				bNazovCelehoDiela.setVisible(true);
				bExistujucePostavy.setVisible(true);
			}
		});

		/** VypÌöe poËet vytvoren˝ch str·n */
		bPoËetVsetkychStran.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vypisVsetkehoDruhu.append(Proces.getInstance()
						.pocetVsetkychStran());
			}
		});

		/** VypÌöe n·zov diela */
		bNazovCelehoDiela.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vypisVsetkehoDruhu.append(Proces.getInstance()
						.vypisNazovDiela());
			}
		});

		/** VypÌöe postavy, ktorÈ vedia maliari kresliù */
		bExistujucePostavy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vypisVsetkehoDruhu.append(Proces.getInstance()
						.vypisExistujucePostavy());
			}
		});

		/** Okno maliarov post·v */
		bMaliarPostavy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				oMaliarPostavy.setTitle("Maliar Postavy");
				oMaliarPostavy.setSize(900, 500);
				oMaliarPostavy.setVisible(true);
				oMaliarPostavy.setLayout(new FlowLayout());

				bNovyMaliarPostavy.setVisible(true);
				vypisPostavy.setVisible(true);

				lMenoPostavy.setVisible(false);
				tMenoPostava.setVisible(false);
				bUlozMenoPostavy.setVisible(false);
				lMaliarPostavyCislo.setVisible(false);
				tMaliarPostavyCislo.setVisible(false);
				bNamaluj.setVisible(false);
				cPovolanie.setVisible(false);
				checkBoxZbrane.setVisible(false);
				lOzbrojit.setVisible(false);

				lMaliarCislo.setVisible(false);
				tMaliarCislo.setVisible(false);
				lStranaPostavy.setVisible(false);
				tStranaPostavy.setVisible(false);
				lOkienkoPostavy.setVisible(false);
				tOnienkoPostavy.setVisible(false);
				bNamalujPostavu.setVisible(false);
				lCisloPostavy.setVisible(false);
				tCisloPostavy.setVisible(false);

			}
		});

		/** Najmeme novÈho maliara */
		bNovyMaliarPostavy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Proces.getInstance().novyMaliarPostavy();
				vypisPostavy
						.append(Proces.getInstance().vypisMaliarVytvoreny());
				lMenoPostavy.setVisible(true);
				tMenoPostava.setVisible(true);
				bUlozMenoPostavy.setVisible(true);
				lMaliarPostavyCislo.setVisible(true);
				tMaliarPostavyCislo.setVisible(true);
				cPovolanie.setVisible(true);
			}
		});

		/** Zad·me maliarovi ak˙ postaviËku m· kresliù */
		bUlozMenoPostavy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Proces.getInstance().pomenujPostavuMaliara(
						Integer.parseInt(tMaliarPostavyCislo.getText()),
						tMenoPostava.getText());
				if (cPovolanie.getSelectedItem().toString().equals("bojovnÌk")) {
					Proces.getInstance().bojovnik(
							Integer.parseInt(tMaliarPostavyCislo.getText()));
				}
				if (cPovolanie.getSelectedItem().toString()
						.equals("remeselnÌk")) {
					Proces.getInstance().remeselnik(
							Integer.parseInt(tMaliarPostavyCislo.getText()));
				}
				if (cPovolanie.getSelectedItem().toString().equals("starec")) {
					Proces.getInstance().starec(
							Integer.parseInt(tMaliarPostavyCislo.getText()));
				}
				if (cPovolanie.getSelectedItem().toString().equals("teenager")) {
					Proces.getInstance().teenager(
							Integer.parseInt(tMaliarPostavyCislo.getText()));
				}
				vypisPostavy.append(Proces
						.getInstance()
						.vypisMenoPostavyMaliara(
								Integer.parseInt(tMaliarPostavyCislo.getText()),
								cPovolanie.getSelectedItem().toString()));
				bNamaluj.setVisible(true);
			}
		});

		/** Okno s malovanÌm post·v */
		bNamaluj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				oMalovanie.setTitle("Malujeme");
				oMalovanie.setSize(250, 250);
				oMalovanie.setVisible(true);
				oMalovanie.setLayout(new FlowLayout());
			}
		});

		/** SprÌstupnÌ okno s kreslenÌm */
		bUlozMenoPostavy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lStranaPostavy.setVisible(true);
				tStranaPostavy.setVisible(true);
				lOkienkoPostavy.setVisible(true);
				tOnienkoPostavy.setVisible(true);
				bNamalujPostavu.setVisible(true);
				lMaliarCislo.setVisible(true);
				tMaliarCislo.setVisible(true);
				lCisloPostavy.setVisible(true);
				tCisloPostavy.setVisible(true);
				lOzbrojit.setVisible(true);
				checkBoxZbrane.setVisible(true);
			}
		});

		/** UrËen˝ maliar nakreslÌ na danÈ miesto svoju postaviËku */
		bNamalujPostavu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Proces.getInstance()
						.getMaliariPostav()
						.get(Integer.parseInt(tMaliarCislo.getText()) - 1)
						.nakresli(Proces.getInstance().getKomix(),
								Integer.parseInt(tStranaPostavy.getText()),
								Integer.parseInt(tOnienkoPostavy.getText()),
								Integer.parseInt(tCisloPostavy.getText()), null);
				if (checkBoxZbrane.isSelected())
					Proces.getInstance().ozbrojenaPostava(
							Integer.parseInt(tStranaPostavy.getText()),
							Integer.parseInt(tOnienkoPostavy.getText()),
							Integer.parseInt(tCisloPostavy.getText()));
				vypisPostavy.append(Proces.getInstance().vypisPostava(
						Integer.parseInt(tStranaPostavy.getText()),
						Integer.parseInt(tOnienkoPostavy.getText()),
						Integer.parseInt(tCisloPostavy.getText())));
				vypisPostavy.append(Proces.getInstance().getMaliariPostav()
						.get(Integer.parseInt(tMaliarCislo.getText()) - 1)
						.svojStav());
			}
		});

		/** Okno maliara pozadia */
		bMaliarPozadia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				oMaliarPozadia.setTitle("Maliar Pozadia");
				oMaliarPozadia.setSize(1000, 500);
				oMaliarPozadia.setVisible(true);
				oMaliarPozadia.setLayout(new FlowLayout());
				tPozadie.setVisible(true);
				tStranaCisloP.setVisible(true);
				lStranaCisloP.setVisible(true);
				tPanelCisloP.setVisible(true);
				lOkienkoCisloP.setVisible(true);
				bNamalujPozadie.setVisible(true);
				vypisPozadia.setVisible(true);
			}
		});

		/** Namaluje zadanÈ pozadie */
		bNamalujPozadie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Proces.getInstance()
						.getMaliarPozadia()
						.nakresli(Proces.getInstance().getKomix(),
								Integer.parseInt(tStranaCisloP.getText()),
								Integer.parseInt(tPanelCisloP.getText()), 0,
								tPozadie.getText());
				vypisPozadia.append(Proces.getInstance().vypisPozadie(
						Integer.parseInt(tStranaCisloP.getText()),
						Integer.parseInt(tPanelCisloP.getText())));
				vypisPozadia.append(Proces.getInstance().getMaliarPozadia()
						.svojStav());
			}
		});

		/** Okno text·ra */
		bTextar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				oTextar.setTitle("Text·r");
				oTextar.setSize(1000, 500);
				oTextar.setVisible(true);
				oTextar.setLayout(new FlowLayout());
				bTextarVyplnBubliny.setVisible(true);
				bTextarVyplnTextMimo.setVisible(true);
				tTextMimo.setVisible(false);
				lStranaCisloT.setVisible(false);
				tTextBubliny.setVisible(false);
				lStranaCisloB.setVisible(false);
				tStranaCisloB.setVisible(false);
				tStranaCisloT.setVisible(false);
				lOkienkoCisloB.setVisible(false);
				tPanelCisloB.setVisible(false);
				lBublinaCislo.setVisible(false);
				tBublinaCisloB.setVisible(false);
				lHranata.setVisible(false);
				checkBox.setVisible(false);
				bUlozBublinu.setVisible(false);
				bUlozTextMimo.setVisible(false);
				vypisUlozeniaBubliny.setVisible(false);
				vypisTextuMimo.setVisible(false);
				bSp‰tTextar.setVisible(false);
				bSp‰tTextar2.setVisible(false);
			}
		});

		/** Text·r bude vypÂÚaù bubliny */
		bTextarVyplnBubliny.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bTextarVyplnTextMimo.setVisible(false);
				bTextarVyplnBubliny.setVisible(false);
				tTextBubliny.setVisible(true);
				lStranaCisloB.setVisible(true);
				tStranaCisloB.setVisible(true);
				lOkienkoCisloB.setVisible(true);
				tPanelCisloB.setVisible(true);
				lBublinaCislo.setVisible(true);
				tBublinaCisloB.setVisible(true);
				lHranata.setVisible(true);
				checkBox.setVisible(true);
				bUlozBublinu.setVisible(true);
				vypisUlozeniaBubliny.setVisible(true);
				bSp‰tTextar.setVisible(true);
			}
		});

		/** Text·r bude vypÂÚaù text mimo okienok */
		bTextarVyplnTextMimo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bTextarVyplnTextMimo.setVisible(false);
				bTextarVyplnBubliny.setVisible(false);
				tTextMimo.setVisible(true);
				lStranaCisloT.setVisible(true);
				bUlozTextMimo.setVisible(true);
				tStranaCisloT.setVisible(true);
				vypisTextuMimo.setVisible(true);
				bSp‰tTextar2.setVisible(true);

			}
		});

		/** UloûÌ text mimo na dan˙ stranu */
		bUlozTextMimo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Proces.getInstance()
						.zmenTextar()
						.napisTextMimo(Proces.getInstance().getKomix(),
								Integer.parseInt(tStranaCisloT.getText()),
								tTextMimo.getText());
				vypisTextuMimo.append(Proces.getInstance().vypisTextuMimo(
						Integer.parseInt(tStranaCisloT.getText())));
				vypisTextuMimo.append(Proces.getInstance().getTextar()
						.svojStav());
			}
		});

		/** TlaËidlo sp‰ù */
		bSp‰tTextar2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bTextarVyplnTextMimo.setVisible(true);
				bTextarVyplnBubliny.setVisible(true);
				tTextMimo.setVisible(false);
				lStranaCisloT.setVisible(false);
				bUlozTextMimo.setVisible(false);
				tStranaCisloT.setVisible(false);
				vypisTextuMimo.setVisible(false);
				bSp‰tTextar2.setVisible(false);
			}
		});

		/** TlaËidlo sp‰ù */
		bSp‰tTextar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bTextarVyplnTextMimo.setVisible(true);
				bTextarVyplnBubliny.setVisible(true);
				tTextBubliny.setVisible(false);
				lStranaCisloB.setVisible(false);
				tStranaCisloB.setVisible(false);
				lOkienkoCisloB.setVisible(false);
				tPanelCisloB.setVisible(false);
				lBublinaCislo.setVisible(false);
				tBublinaCisloB.setVisible(false);
				lHranata.setVisible(false);
				checkBox.setVisible(false);
				bUlozBublinu.setVisible(false);
				vypisUlozeniaBubliny.setVisible(false);
				bSp‰tTextar.setVisible(false);
			}
		});

		/** PÌöe text do bubliny a urËÌ jej tvar */
		bUlozBublinu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Proces.getInstance()
						.getTextar()
						.nakresli(Proces.getInstance().getKomix(),
								Integer.parseInt(tStranaCisloB.getText()),
								Integer.parseInt(tPanelCisloB.getText()),
								Integer.parseInt(tBublinaCisloB.getText()),
								tTextBubliny.getText());
				vypisUlozeniaBubliny.append(Proces.getInstance()
						.vypisUlozeniaBubliny(
								Integer.parseInt(tStranaCisloB.getText()),
								Integer.parseInt(tPanelCisloB.getText()),
								Integer.parseInt(tBublinaCisloB.getText())));
				if (checkBox.isSelected())
					Proces.getInstance().hranatost(
							Integer.parseInt(tStranaCisloB.getText()),
							Integer.parseInt(tPanelCisloB.getText()),
							Integer.parseInt(tBublinaCisloB.getText()));
				vypisUlozeniaBubliny.append(Proces.getInstance().vypisHranost(
						Integer.parseInt(tStranaCisloB.getText()),
						Integer.parseInt(tPanelCisloB.getText()),
						Integer.parseInt(tBublinaCisloB.getText())));
				vypisUlozeniaBubliny.append(Proces.getInstance().getTextar()
						.svojStav());

			}
		});
	}
}