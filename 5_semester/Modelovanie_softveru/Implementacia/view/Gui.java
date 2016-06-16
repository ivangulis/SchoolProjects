package view;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;

import model.PremiovyPouzivatel;
import controller.BlackMarket;
import controller.Bojisko;
import controller.SpravcaObjednavok;

public class Gui {
	/** Vöetko ku hlavnÈmu oknu */
	private JFrame oHlavne = new JFrame("Hlavne Okno");
	private JLabel lMeno0 = new JLabel("Prez˝vka:");
	private JTextField tMeno = new JTextField(10);
	private JButton bPrihl·s = new JButton("Login");
	private JLabel lStatus0 = new JLabel("Status:");
	private JLabel lStatus = new JLabel("PrÈmiov˝ pouûÌvateæ");
	private JLabel lSerpenty = new JLabel("Stav strieborn˝ch serpentov:");
	private JLabel lSerpentyPocet = new JLabel("0");
	private JLabel lDublony = new JLabel("Stav zlat˝ch dublÛnov:");
	private JLabel lDublonyPocet = new JLabel("0");
	private JLabel lInventar = new JLabel("Invent·r:");
	private JComboBox<String> cInventar = new JComboBox<String>();
	private JButton bInfoPredmet = new JButton("Info o predmete");
	private JButton bObjednaj = new JButton("Objednaj dublÛny");
	private JButton bTrh = new JButton("Nakupuj v Black Markete");
	private JButton bClear = new JButton("Clear");
	private JScrollPane scroll = new JScrollPane();
	private JTextArea vypis = new JTextArea(25, 60);
	
	/** Vöetko k oknu objednaj */
	private JFrame oObjednaj = new JFrame("Objedn·vky");
	private JButton bNova = new JButton("Nov· objedn·vka");
	private JLabel lMena = new JLabel("Vyber platobn˙ menu");
	private JComboBox<String> cMena = new JComboBox<String>();
	private JLabel lHodnota = new JLabel("Vyber mnoûstvo dublÛnov");
	private JComboBox<String> cHodnota = new JComboBox<String>();
	private JLabel lTypPlatby = new JLabel("Vyber typ platby");
	private JComboBox<String> cTypPlatby = new JComboBox<String>();
	private JLabel lPIN = new JLabel("Zadaj kÛd");
	private JTextField tPIN = new JTextField(10);
	private JButton bSMSodosli = new JButton("Poslaù si SMS kÛd na ËÌslo:");
	private JTextField tCislo = new JTextField(10);
	private JButton bOdosli = new JButton("Vybav objedn·vku");
	private JScrollPane scroll2 = new JScrollPane();
	private JTextArea vypisObjednavka = new JTextArea(20, 50);
	
	/** Vöetko k oknu Black Market */
	private JFrame oBlackMarket = new JFrame("Black Market");
	private JLabel lPredmet = new JLabel("Vyber predmet:");
	private JComboBox<String> cPredmetyShop = new JComboBox<String>();
	private JButton bPotvrdKupuDublony = new JButton("K˙più za dublony");
	private JButton bPotvrdKupuSerpenty = new JButton("K˙più za serpenty");
	private JLabel lPonukaPredmetov = new JLabel("Ponuka predmetov:");
	private JTable tablePredmetyShopu = new JTable();
	private JPanel panel1 = new JPanel();
	private JTableHeader header1 = tablePredmetyShopu.getTableHeader();
	private JButton bLenOblecenie = new JButton("Len obleËenie");
	private JButton bLenZbrane = new JButton("Len zbrane");
	private JButton bLenVsetko = new JButton("Zobraz vöetko");
	private JScrollPane scroll1 = new JScrollPane();
	private JTextArea vypisBlackMarket = new JTextArea(25, 60);
	
	
	/** CelÈ gui */
	public Gui() {
		
		/** Inicializ·cie */
		BlackMarket.getInstance().vytvorPonuku();
		cMena.addItem("Eur·");
		cMena.addItem("»esk· koruna");
		cMena.addItem("Dol·re");
		cTypPlatby.addItem("PayPal");
		cTypPlatby.addItem("SMS");
		cHodnota.addItem("5");
		cHodnota.addItem("10");
		cHodnota.addItem("25");
		cHodnota.addItem("50");
		tMeno.setText("Bloodxy");
		tPIN.setText("12345");
		tCislo.setText("0912 234 567");
		
		/** Tvorba hlavnÈho okna */
		oHlavne.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		oHlavne.setVisible(true);
		oHlavne.setSize(500, 600);
		oHlavne.setLayout(new FlowLayout());
		oHlavne.add(lMeno0);
		oHlavne.add(tMeno);
		oHlavne.add(bPrihl·s);
		oHlavne.add(lStatus0);
		oHlavne.add(lStatus);
		oHlavne.add(lSerpenty);
		oHlavne.add(lSerpentyPocet);
		oHlavne.add(lDublony);
		oHlavne.add(lDublonyPocet);
		oHlavne.add(lInventar);
		oHlavne.add(cInventar);
		oHlavne.add(bInfoPredmet);
		oHlavne.add(bTrh);
		oHlavne.add(bObjednaj);
		oHlavne.add(bClear);
		oHlavne.add(vypis);
		oHlavne.getContentPane().add(scroll);		
		scroll.setViewportView(vypis);
		vypis.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		vypis.setLineWrap(true);
		vypis.setWrapStyleWord(true);
		lStatus0.setVisible(false);
		lStatus.setVisible(false);
		lSerpenty.setVisible(false);
		lSerpentyPocet.setVisible(false);
		lDublony.setVisible(false);
		lDublonyPocet.setVisible(false);
		lInventar.setVisible(false);
		cInventar.setVisible(false);
		bInfoPredmet.setVisible(false);
		bTrh.setVisible(false);
		bObjednaj.setVisible(false);
		
		/** Tvorba okna objednaj dublÛny */
		oObjednaj.setVisible(false);
		oObjednaj.setSize(550, 600);
		oObjednaj.setLayout(new FlowLayout());
		oObjednaj.add(bNova);
		oObjednaj.add(lMena);
		oObjednaj.add(cMena);
		oObjednaj.add(lHodnota);
		oObjednaj.add(cHodnota);
		oObjednaj.add(lTypPlatby);
		oObjednaj.add(cTypPlatby);
		oObjednaj.add(lPIN);
		oObjednaj.add(tPIN);
		oObjednaj.add(bSMSodosli);
		oObjednaj.add(tCislo);
		oObjednaj.add(bOdosli);
		oObjednaj.add(vypisObjednavka);
		oObjednaj.getContentPane().add(scroll2);	
		scroll2.setViewportView(vypisObjednavka);
		vypisObjednavka.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		vypisObjednavka.setLineWrap(true);
		vypisObjednavka.setWrapStyleWord(true);

		/** Tvorba okna obchodu */
		oBlackMarket.setVisible(false);
		oBlackMarket.setSize(600, 700);
		oBlackMarket.setLayout(new FlowLayout());
		oBlackMarket.add(lPredmet);
		oBlackMarket.add(cPredmetyShop);
		oBlackMarket.add(bPotvrdKupuDublony);
		oBlackMarket.add(bPotvrdKupuSerpenty);
		oBlackMarket.add(lPonukaPredmetov);
		oBlackMarket.add(tablePredmetyShopu);
		oBlackMarket.add(panel1);
		panel1.setLayout(new BorderLayout());
		panel1.add(header1, BorderLayout.NORTH);
		panel1.add(tablePredmetyShopu, BorderLayout.CENTER);
		oBlackMarket.add(bLenOblecenie);
		oBlackMarket.add(bLenZbrane);
		oBlackMarket.add(bLenVsetko);
		oBlackMarket.add(vypisBlackMarket);
		oBlackMarket.getContentPane().add(scroll1);		
		scroll1.setViewportView(vypisBlackMarket);
		vypisBlackMarket.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		vypisBlackMarket.setLineWrap(true);
		vypisBlackMarket.setWrapStyleWord(true);

		/** Prihl·senie, aktualizuje ˙daje v hlavnom okne */
		bPrihl·s.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cInventar.removeAllItems();
				if (tMeno.getText().length() > 0 && Bojisko.getInstance().hladaj(tMeno.getText())!=null){
					for (String a : Bojisko.getInstance().hladaj(tMeno.getText()).getInventar().vsetkyPredmety())
						cInventar.addItem(a);
					vypis.append("Ste prihl·sen˝ pod prez˝vkou: " + tMeno.getText() + "\n");
					lStatus0.setVisible(true);
					lStatus.setVisible(true);
					lSerpenty.setVisible(true);
					lSerpentyPocet.setVisible(true);
					lDublony.setVisible(true);
					lDublonyPocet.setVisible(true);
					lInventar.setVisible(true);
					cInventar.setVisible(true);
					bInfoPredmet.setVisible(true);
					bTrh.setVisible(true);
					bObjednaj.setVisible(true);
					lSerpentyPocet.setText("" + Bojisko.getInstance().hladaj(tMeno.getText()).getSerpenty());
					lDublonyPocet.setText("" + ((PremiovyPouzivatel)Bojisko.getInstance().hladaj(tMeno.getText())).getDublony());
				}
				else vypis.append("Zadali ste neplatn˙ prez˝vku" + "\n");
			}
		});
		/** Button pre info o selektnutom predmete */
		bInfoPredmet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vypis.append(Bojisko.getInstance().hladaj(tMeno.getText()).getInventar().n·jdiPredmet(cInventar.getSelectedItem().toString()).zobrazInfo());
			}
		});
		bClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vypis.setText("");
			}
		});
		/** Otv·ra formul·r s objedn·vkami */
		bObjednaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				oObjednaj.setVisible(true);
				lMena.setVisible(false);
				cMena.setVisible(false);
				lHodnota.setVisible(false);
				cHodnota.setVisible(false);
				lTypPlatby.setVisible(false);
				cTypPlatby.setVisible(false);
				lPIN.setVisible(false);
				tPIN.setVisible(false);
				bSMSodosli.setVisible(false);
				tCislo.setVisible(false);
				bOdosli.setVisible(false);
			}
		});
		/** OdhalÌ vypÂÚanie objedn·vky */
		bNova.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lMena.setVisible(true);
				cMena.setVisible(true);
				lHodnota.setVisible(true);
				cHodnota.setVisible(true);
				lTypPlatby.setVisible(true);
				cTypPlatby.setVisible(true);
				lPIN.setVisible(true);
				tPIN.setVisible(true);
				bSMSodosli.setVisible(true);
				tCislo.setVisible(true);
				bOdosli.setVisible(true);
				bNova.setVisible(false);
				vypisObjednavka.setText("");
				vypisObjednavka.setText("Kurz eur·:dublÛny je 1.5:1\nKurz ËeskÈ koruny:dublÛny je 25:1\nKurz dol·re:dublÛny je 30:1\n");
				SpravcaObjednavok.getInstance().vytvorObjednavku(Bojisko.getInstance().hladaj(tMeno.getText()));
				vypisObjednavka.append("Objedn·vka bola vytvoren·, vyplÚte ˙daje.\n");
			}
		});
		/** VypÌöe SMS kÛd */
		bSMSodosli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tCislo.getText().length() > 0)
				vypisObjednavka.append("V·ö SMS kÛd je: 12345\n");
			}
		});
		/** VybavÌ objedn·vku */
		bOdosli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tPIN.getText().equals("12345")){
					SpravcaObjednavok.getInstance().vybavObjednavku((PremiovyPouzivatel)Bojisko.getInstance().hladaj(tMeno.getText()), Integer.parseInt(cHodnota.getSelectedItem().toString()), cTypPlatby.getSelectedIndex());
					vypisObjednavka.append("Vaöa objedn·vka bola odoslan· na vybavenie, dublÛny v·m Ëoskoro prÌdu.\n");
					vypisObjednavka.append("Na ˙Ëet v·m pribudlo " + cHodnota.getSelectedItem().toString() + " dublÛnov.");
					lSerpentyPocet.setText("" + Bojisko.getInstance().hladaj(tMeno.getText()).getSerpenty());
					lDublonyPocet.setText("" + ((PremiovyPouzivatel)Bojisko.getInstance().hladaj(tMeno.getText())).getDublony());
				}
				else vypisObjednavka.append("Zadali ste zl˝ kÛd.\n");
			}
		});
		/** Okno Black Marketu */
		bTrh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				oBlackMarket.setVisible(true);
				
				cPredmetyShop.removeAllItems();
				for (String a : BlackMarket.getInstance().vsetkyPredmety())
					cPredmetyShop.addItem(a);
				
				tablePredmetyShopu.removeAll();
				tablePredmetyShopu.setModel(BlackMarket.getInstance().spravTabulkuPonuka(1));
				tablePredmetyShopu.getColumnModel().getColumn(0).setPreferredWidth(150);
				tablePredmetyShopu.getColumnModel().getColumn(1).setPreferredWidth(150);
				tablePredmetyShopu.getColumnModel().getColumn(2).setPreferredWidth(150);
			}
		});
		/** K˙pi predmet za dublony */
		bPotvrdKupuDublony.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Bojisko.getInstance().hladaj(tMeno.getText()) instanceof PremiovyPouzivatel && ((PremiovyPouzivatel)Bojisko.getInstance().hladaj(tMeno.getText())).getDublony() >= BlackMarket.getInstance().najdiPredmet(cPredmetyShop.getSelectedItem().toString()).getHodnotaDublony()){
					if (tMeno.getText().length() > 0 && Bojisko.getInstance().hladaj(tMeno.getText())!=null){
						vypisBlackMarket.append("Z v·öho ˙Ëtu bolo odr·tanÈ: " + BlackMarket.getInstance().kupPredmetDublony(tMeno.getText(), cPredmetyShop.getSelectedItem().toString(), Bojisko.getInstance().getDatPouz().getDatabaza()) + " dublÛnov.\n");
						vypisBlackMarket.append("Predmet v·m bol presunut˝ do invent·ra\n");
						cInventar.removeAllItems();
						for (String a : Bojisko.getInstance().hladaj(tMeno.getText()).getInventar().vsetkyPredmety())
							cInventar.addItem(a);
						lDublonyPocet.setText("" + ((PremiovyPouzivatel)Bojisko.getInstance().hladaj(tMeno.getText())).getDublony());
					}
				}
				else vypisBlackMarket.append("Nem·ö dostatok dublÛnov!\n");
			}
		});
		/** K˙pi predmet za serpenty */
		bPotvrdKupuSerpenty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Bojisko.getInstance().hladaj(tMeno.getText()).getSerpenty() >= BlackMarket.getInstance().najdiPredmet(cPredmetyShop.getSelectedItem().toString()).getHodnotaSerpenty()){
					if (tMeno.getText().length() > 0 && Bojisko.getInstance().hladaj(tMeno.getText())!=null){
						vypisBlackMarket.append("Z v·öho ˙Ëtu bolo odr·tanÈ: " + BlackMarket.getInstance().kupPredmetSerpenty(tMeno.getText(), cPredmetyShop.getSelectedItem().toString(), Bojisko.getInstance().getDatPouz().getDatabaza()) + " serpentov.\n");
						vypisBlackMarket.append("Predmet v·m bol presunut˝ do invent·ra\n");
						cInventar.removeAllItems();
						for (String a : Bojisko.getInstance().hladaj(tMeno.getText()).getInventar().vsetkyPredmety())
							cInventar.addItem(a);
						lSerpentyPocet.setText("" + Bojisko.getInstance().hladaj(tMeno.getText()).getSerpenty());
					}
				}
				else vypisBlackMarket.append("Nem·ö dostatok serpentov!\n");
			}
		});
		/** Filter tabuæky */
		bLenVsetko.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tablePredmetyShopu.removeAll();
				tablePredmetyShopu.setModel(BlackMarket.getInstance().spravTabulkuPonuka(1));
				tablePredmetyShopu.getColumnModel().getColumn(0).setPreferredWidth(150);
				tablePredmetyShopu.getColumnModel().getColumn(1).setPreferredWidth(150);
				tablePredmetyShopu.getColumnModel().getColumn(2).setPreferredWidth(150);
			}
		});
		/** Filter tabuæky */
		bLenZbrane.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tablePredmetyShopu.removeAll();
				tablePredmetyShopu.setModel(BlackMarket.getInstance().spravTabulkuPonuka(2));
				tablePredmetyShopu.getColumnModel().getColumn(0).setPreferredWidth(150);
				tablePredmetyShopu.getColumnModel().getColumn(1).setPreferredWidth(150);
				tablePredmetyShopu.getColumnModel().getColumn(2).setPreferredWidth(150);
			}
		});
		/** Filter tabuæky */
		bLenOblecenie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tablePredmetyShopu.removeAll();
				tablePredmetyShopu.setModel(BlackMarket.getInstance().spravTabulkuPonuka(3));
				tablePredmetyShopu.getColumnModel().getColumn(0).setPreferredWidth(150);
				tablePredmetyShopu.getColumnModel().getColumn(1).setPreferredWidth(150);
				tablePredmetyShopu.getColumnModel().getColumn(2).setPreferredWidth(150);
			}
		});
	}
}

