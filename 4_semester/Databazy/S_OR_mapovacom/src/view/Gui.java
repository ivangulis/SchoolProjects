package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.table.JTableHeader;

import controller.Hlavna;

public class Gui {
	private JFrame oHlavne = new JFrame("Hlavne Okno");
	private JButton bScenar1 = new JButton("Scenar 1. - Vytvor postaviËky");
	private JButton bScenar2 = new JButton("Scenar 2. - ZmeÚ n·zov komixu");
	private JButton bScenar3 = new JButton("Scenar 3. - VyhoÔ autora");
	private JButton bScenar4 = new JButton("Scenar 4. - ätatistika");
	private JButton bScenar5 = new JButton("Scenar 5. - Vyhæadaj info o postaviËke");
	private JButton bScenar6 = new JButton("Scenar 6. - Vytvor alebo zmaû komix alebo pridaj strany");
	private JButton bScenar7 = new JButton("Scenar 7. - Prijmi novÈho zamestnanca");
	private JButton bScenar8 = new JButton("Scenar 8. - Nakresli postaviËku na stranu");
	
	/**PremennÈ k 1. scen·ru*/
	private JFrame oScenar1 = new JFrame("Okno pre 1. scen·r");
	private JLabel lMenoPostavy = new JLabel("Zadaj meno postavy:");
	private JTextField tMenoPostavy = new JTextField(40);
	private JLabel lTypPostavy = new JLabel("Zadaj typ postavy:");
	private JTextField tTypPostavy = new JTextField(30);
	private JLabel lAutorPostavy = new JLabel("Vyber autora:");
	private JComboBox<String> cAutori = new JComboBox<String>();
	private JButton bPotvrdPostavu = new JButton("PotvrÔ");
	private JLabel lMuz = new JLabel("Muû?");
	private JCheckBox chPohlavie = new JCheckBox();
	private JTable tablePostavy = new JTable();
	private JPanel panel1 = new JPanel();
	private JTableHeader header1 = tablePostavy.getTableHeader();
	
	/**PremennÈ k 2. scen·ru*/
	private JFrame oScenar2 = new JFrame("Okno pre 2. scen·r");
	private JLabel lNazvyKomixov = new JLabel("Vyber komix:");
	private JComboBox<String> cKomixy = new JComboBox<String>();
	private JLabel lNovyNazov = new JLabel("Novy nazov komixu:");
	private JTextField tNovyNazov = new JTextField(40);
	private JButton bPotvrdNazov = new JButton("PotvrÔ");
	private JTable tableKomixy = new JTable();
	private JPanel panel2 = new JPanel();
	private JTableHeader header2 = tableKomixy.getTableHeader();
	
	/**PremennÈ k 3. scen·ru*/
	private JFrame oScenar3 = new JFrame("Okno pre 3. scen·r");
	private JLabel lMenoZamestnanca = new JLabel("Vyber zamestnanca:");
	private JComboBox<String> cZamestnanci = new JComboBox<String>();
	private JLabel lMenoNahradnika = new JLabel("Vyber, kto bude kresliù jeho postaviËky:");
	private JComboBox<String> cNahradnici = new JComboBox<String>();
	private JButton bPotvrdVyhodenie = new JButton("VyhoÔ zamestnanca");
	private JLabel lAutoriSPostavami = new JLabel("Tabuæka autorov, Ëo uû kreslia nejak˙ postaviËku:");
	private JTable tableAutoriDel = new JTable();
	private JPanel panel3 = new JPanel();
	private JTableHeader header3 = tableAutoriDel.getTableHeader();
	
	/**PremennÈ k 4. scen·ru*/
	private JFrame oScenar4 = new JFrame("Okno pre 4. scen·r");
	private JButton bVypisPocetPostav = new JButton("Zobraz v˝pis so ötatistikou");
	private JLabel lPostavyMuzi = new JLabel("Koæko muûsk˝ch post·v kreslia autori:");
	private JLabel lPostavyZeny = new JLabel("Koæko ûensk˝ch post·v kreslia autori:");
	private JLabel lPostavySpolu = new JLabel("Spolu post·v:");
	private JTextArea vypisSpolu = new JTextArea(1,5);
	private JTable tableAutorPostavyCount = new JTable();
	private JTable tableAutorPostavyCount2 = new JTable();
	private JPanel panel4 = new JPanel();
	private JPanel panel41 = new JPanel();
	private JTableHeader header4 = tableAutorPostavyCount.getTableHeader();
	private JTableHeader header41 = tableAutorPostavyCount2.getTableHeader();
	
	/**PremennÈ k 5. scen·ru*/
	private JFrame oScenar5 = new JFrame("Okno pre 5. scen·r");//akym umelcom je kreslen· postaviËka
	private JLabel lMenoPostavicky = new JLabel("Vyber postaviËku:");
	private JComboBox<String> cPostavicky = new JComboBox<String>();
	private JButton bKtoKreslÌ = new JButton("Kto ju kreslÌ?");
	private JTable tablePostavySearch = new JTable();
	private JPanel panel5 = new JPanel();
	private JTableHeader header5 = tablePostavySearch.getTableHeader();
	
	/**PremennÈ k 6. scen·ru*/
	private JFrame oScenar6 = new JFrame("Okno pre 6. scen·r");
	private JLabel lZadajNazov = new JLabel("Zadaj n·zov komixu:");
	private JTextField tZadajNazov = new JTextField(40);
	private JButton bPotvrdNovyKomix = new JButton("PotvrÔ nov˝ komix");
	private JTable tableKomixovNew = new JTable();
	private JPanel panel6 = new JPanel();
	private JTableHeader header6 = tableKomixovNew.getTableHeader();
	private JLabel lVyberKomix = new JLabel("Vyber komix:");
	private JComboBox<String> cVsetkyKomixy = new JComboBox<String>();
	private JLabel lSituacia = new JLabel("PopÌö Ëo sa deje na strane:");
	private JTextField tSituacia = new JTextField(80);
	private JButton bPridajStranu = new JButton("Pridaj novu stranu");
	private JButton bZmazKomix = new JButton("Zmaû vybran˝ komix");
	
	/**PremennÈ k 7. scen·ru*/
	private JFrame oScenar7 = new JFrame("Okno pre 7. scen·r");
	private JLabel lZadajMeno = new JLabel("Najmi novÈho zamestnanca menom:");
	private JTextField tZadajMeno = new JTextField(40);
	private JButton bPotvrdZamestnanca = new JButton("PotvrÔ novÈho zamestnanca");
	private JTable tableZamestnancovNew = new JTable();
	private JPanel panel7 = new JPanel();
	private JTableHeader header7 = tableZamestnancovNew.getTableHeader();
	
	/**PremennÈ k 8. scen·ru*/
	private JFrame oScenar8 = new JFrame("Okno pre 8. scen·r");
	private JLabel lVyberKomix8 = new JLabel("Vyber komix:");
	private JComboBox<String> cVsetkyKomixy8 = new JComboBox<String>();
	private JButton bPotvrdVyberKomixu = new JButton("PotvrÔ v˝ber komixu");
	private JLabel lVyberPostavu8 = new JLabel("Vyber postaviËku:");
	private JComboBox<String> cVsetkyPostavy8 = new JComboBox<String>();
	private JLabel lVyberStranu8 = new JLabel("Vyber stranu:");
	private JComboBox<String> cStranyVKomixe8 = new JComboBox<String>();
	private JLabel lCinnost8 = new JLabel("»o robÌ postaviËka?:");
	private JTextField tZadajCinnost8 = new JTextField(40);
	private JButton bNakresli = new JButton("Nakresli");
	private JTable tableNakresleni = new JTable();
	private JPanel panel8 = new JPanel();
	private JTableHeader header8 = tableNakresleni.getTableHeader();
	
	/**NieËo m·lo ku hlavnÈmu oknu*/
	private JButton bClear = new JButton("Vymaû");
	private JScrollPane scroll = new JScrollPane();
	private JTextArea vypis = new JTextArea(20, 50);
	
	public Gui(){
		oHlavne.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		oHlavne.setVisible(true);
		oHlavne.setSize(750, 500);
		oHlavne.setLayout(new FlowLayout());
		
		oHlavne.add(bScenar1);
		oHlavne.add(bScenar2);
		oHlavne.add(bScenar3);
		oHlavne.add(bScenar4);
		oHlavne.add(bScenar5);
		oHlavne.add(bScenar6);
		oHlavne.add(bScenar7);
		oHlavne.add(bScenar8);
		oHlavne.add(bClear);
		oHlavne.add(vypis);
		oHlavne.getContentPane().add(scroll);
		scroll.setViewportView(vypis);
		
		oScenar1.add(lMenoPostavy);
		oScenar1.add(tMenoPostavy);
		oScenar1.add(lTypPostavy);
		oScenar1.add(tTypPostavy);
		oScenar1.add(lMuz);
		oScenar1.add(chPohlavie);
		oScenar1.add(lAutorPostavy);
		oScenar1.add(cAutori);
		oScenar1.add(bPotvrdPostavu);
		oScenar1.add(tablePostavy);
		oScenar1.add(panel1);
		panel1.setLayout(new BorderLayout());
		panel1.add(header1, BorderLayout.NORTH);
		panel1.add(tablePostavy, BorderLayout.CENTER);
		
		oScenar2.add(lNazvyKomixov);
		oScenar2.add(cKomixy);
		oScenar2.add(lNovyNazov);
		oScenar2.add(tNovyNazov);
		oScenar2.add(bPotvrdNazov);
		oScenar2.add(panel2);
		panel2.setLayout(new BorderLayout());
		panel2.add(header2, BorderLayout.NORTH);
		panel2.add(tableKomixy, BorderLayout.CENTER);
		
		oScenar3.add(lMenoZamestnanca);
		oScenar3.add(cZamestnanci);
		oScenar3.add(lMenoNahradnika);
		oScenar3.add(cNahradnici);
		oScenar3.add(bPotvrdVyhodenie);
		oScenar3.add(lAutoriSPostavami);
		oScenar3.add(tableAutoriDel);
		oScenar3.add(panel3);
		panel3.setLayout(new BorderLayout());
		panel3.add(header3, BorderLayout.NORTH);
		panel3.add(tableAutoriDel, BorderLayout.CENTER);
		
		oScenar4.add(bVypisPocetPostav);
		oScenar4.add(lPostavyMuzi);
		oScenar4.add(tableAutorPostavyCount);
		oScenar4.add(panel4);
		panel4.setLayout(new BorderLayout());
		panel4.add(header4, BorderLayout.NORTH);
		panel4.add(tableAutorPostavyCount, BorderLayout.CENTER);
		oScenar4.add(lPostavyZeny);
		oScenar4.add(tableAutorPostavyCount2);
		oScenar4.add(panel41);
		panel41.setLayout(new BorderLayout());
		panel41.add(header41, BorderLayout.NORTH);
		panel41.add(tableAutorPostavyCount2, BorderLayout.CENTER);
		oScenar4.add(lPostavySpolu);
		oScenar4.add(vypisSpolu);
		
		oScenar5.add(lMenoPostavicky);
		oScenar5.add(cPostavicky);
		oScenar5.add(bKtoKreslÌ);
		oScenar5.add(tablePostavySearch);
		oScenar5.add(panel5);
		panel5.setLayout(new BorderLayout());
		panel5.add(header5, BorderLayout.NORTH);
		panel5.add(tablePostavySearch, BorderLayout.CENTER);
		
		oScenar6.add(lZadajNazov);
		oScenar6.add(tZadajNazov);
		oScenar6.add(bPotvrdNovyKomix);
		oScenar6.add(tableKomixovNew);
		oScenar6.add(panel6);
		panel6.setLayout(new BorderLayout());
		panel6.add(header6, BorderLayout.NORTH);
		panel6.add(tableKomixovNew, BorderLayout.CENTER);
		oScenar6.add(lVyberKomix);
		oScenar6.add(cVsetkyKomixy);
		oScenar6.add(lSituacia);
		oScenar6.add(tSituacia);
		oScenar6.add(bPridajStranu);	
		oScenar6.add(bZmazKomix);
		
		oScenar7.add(lZadajMeno);
		oScenar7.add(tZadajMeno);
		oScenar7.add(bPotvrdZamestnanca);
		oScenar7.add(tableZamestnancovNew);
		oScenar7.add(panel7);
		panel7.setLayout(new BorderLayout());
		panel7.add(header7, BorderLayout.NORTH);
		panel7.add(tableZamestnancovNew, BorderLayout.CENTER);
		
		oScenar8.add(lVyberKomix8);
		oScenar8.add(cVsetkyKomixy8);
		oScenar8.add(bPotvrdVyberKomixu);
		oScenar8.add(lVyberPostavu8);
		oScenar8.add(cVsetkyPostavy8);
		oScenar8.add(lVyberStranu8);
		oScenar8.add(cStranyVKomixe8);
		oScenar8.add(lCinnost8);
		oScenar8.add(tZadajCinnost8);
		oScenar8.add(bNakresli);
		oScenar8.add(panel8);
		panel8.setLayout(new BorderLayout());
		panel8.add(header8, BorderLayout.NORTH);
		panel8.add(tableNakresleni, BorderLayout.CENTER);

		
		bClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vypis.setText("");
			}
		});
		
		/**1. scen·r akcie*/
		bScenar1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				oScenar1.setVisible(true);
				oScenar1.setSize(700, 550);
				oScenar1.setLayout(new FlowLayout());
				cAutori.removeAllItems();
				try {
					for (String a : Hlavna.getInstance().vsetciAutori())
						cAutori.addItem(a);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				tablePostavy.removeAll();
				try {
					tablePostavy.setModel(Hlavna.getInstance().spravTabulkuPostav());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				tablePostavy.getColumnModel().getColumn(0).setPreferredWidth(150);
				tablePostavy.getColumnModel().getColumn(1).setPreferredWidth(80);
				tablePostavy.getColumnModel().getColumn(2).setPreferredWidth(150);
				tablePostavy.getColumnModel().getColumn(4).setPreferredWidth(150);
			}
		});
		
		bPotvrdPostavu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					vypis.append(Hlavna.getInstance().insertPostavu(tMenoPostavy.getText(),
							tTypPostavy.getText(), chPohlavie.isSelected(), cAutori.getSelectedItem().toString()));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				tablePostavy.removeAll();
				try {
					tablePostavy.setModel(Hlavna.getInstance().spravTabulkuPostav());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				tablePostavy.getColumnModel().getColumn(0).setPreferredWidth(150);
				tablePostavy.getColumnModel().getColumn(1).setPreferredWidth(80);
				tablePostavy.getColumnModel().getColumn(2).setPreferredWidth(150);
				tablePostavy.getColumnModel().getColumn(4).setPreferredWidth(150);
			}
		});
		
		
		
		
		/**2. scen·r akcie*/
		bScenar2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				oScenar2.setVisible(true);
				oScenar2.setSize(750, 350);
				oScenar2.setLayout(new FlowLayout());
				cKomixy.removeAllItems();
				try {
					for (String a : Hlavna.getInstance().vsetkyKomixy())
						cKomixy.addItem(a);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				tableKomixy.removeAll();
				try {
					tableKomixy.setModel(Hlavna.getInstance().spravTabulkuKomixy());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				tableKomixy.getColumnModel().getColumn(0).setPreferredWidth(250);
				tableKomixy.getColumnModel().getColumn(1).setPreferredWidth(50);
				tableKomixy.getColumnModel().getColumn(2).setPreferredWidth(100);
			}
		});
		
		bPotvrdNazov.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					vypis.append(Hlavna.getInstance().updateNazov(tNovyNazov.getText(),
							cKomixy.getSelectedItem().toString()));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				cKomixy.removeAllItems();
				try {
					for (String a : Hlavna.getInstance().vsetkyKomixy())
						cKomixy.addItem(a);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				tableKomixy.removeAll();
				try {
					tableKomixy.setModel(Hlavna.getInstance().spravTabulkuKomixy());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				tableKomixy.getColumnModel().getColumn(0).setPreferredWidth(250);
				tableKomixy.getColumnModel().getColumn(1).setPreferredWidth(50);
				tableKomixy.getColumnModel().getColumn(2).setPreferredWidth(100);
			}
		});
		
		
		
		
		/**3. scen·r akcie*/
		bScenar3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				oScenar3.setVisible(true);
				oScenar3.setSize(500, 350);
				oScenar3.setLayout(new FlowLayout());
				cZamestnanci.removeAllItems();
				cNahradnici.removeAllItems();
				try {
					for (String a : Hlavna.getInstance().vsetciAutori()){
						cZamestnanci.addItem(a);
						cNahradnici.addItem(a);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				tableAutoriDel.removeAll();
				try {
					tableAutoriDel.setModel(Hlavna.getInstance().spravTabulkuAutori());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				tableAutoriDel.getColumnModel().getColumn(0).setPreferredWidth(150);
				tableAutoriDel.getColumnModel().getColumn(1).setPreferredWidth(30);
				tableAutoriDel.getColumnModel().getColumn(2).setPreferredWidth(100);
				tableAutoriDel.getColumnModel().getColumn(3).setPreferredWidth(100);
			}
		});
		
		bPotvrdVyhodenie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (cZamestnanci.getSelectedItem().toString().compareTo(cNahradnici.getSelectedItem().toString()) != 0)
					vypis.append(Hlavna.getInstance().deleteAutor(cZamestnanci.getSelectedItem().toString(),
							cNahradnici.getSelectedItem().toString()));
					else vypis.append("MusÌö vybraù inÈho autora\n");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				cZamestnanci.removeAllItems();
				cNahradnici.removeAllItems();
				try {
					for (String a : Hlavna.getInstance().vsetciAutori()){
						cZamestnanci.addItem(a);
						cNahradnici.addItem(a);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				tableAutoriDel.removeAll();
				try {
					tableAutoriDel.setModel(Hlavna.getInstance().spravTabulkuAutori());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				tableAutoriDel.getColumnModel().getColumn(0).setPreferredWidth(150);
				tableAutoriDel.getColumnModel().getColumn(1).setPreferredWidth(30);
				tableAutoriDel.getColumnModel().getColumn(2).setPreferredWidth(100);
				tableAutoriDel.getColumnModel().getColumn(3).setPreferredWidth(100);
			}
		});
		
		
		
		
		/**4. scen·r akcie*/
		bScenar4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				oScenar4.setVisible(true);
				oScenar4.setSize(300, 550);
				oScenar4.setLayout(new FlowLayout());
			}
		});
		
		bVypisPocetPostav.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableAutorPostavyCount.removeAll();
				tableAutorPostavyCount2.removeAll();
				try {
					tableAutorPostavyCount.setModel(Hlavna.getInstance().autorPostavyCountMZ(true));
					tableAutorPostavyCount2.setModel(Hlavna.getInstance().autorPostavyCountMZ(false));
					vypisSpolu.append(Hlavna.getInstance().postavySpolu());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				tableAutorPostavyCount.getColumnModel().getColumn(0).setPreferredWidth(150);
				tableAutorPostavyCount.getColumnModel().getColumn(1).setPreferredWidth(85);
				tableAutorPostavyCount2.getColumnModel().getColumn(0).setPreferredWidth(150);
				tableAutorPostavyCount2.getColumnModel().getColumn(1).setPreferredWidth(85);
				
			}
		});
		
		
		
		
		/**5. scen·r akcie*/
		bScenar5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				oScenar5.setVisible(true);
				oScenar5.setSize(500, 350);
				oScenar5.setLayout(new FlowLayout());
				cPostavicky.removeAllItems();
				try {
					for (String a : Hlavna.getInstance().vsetkyPostavicky())
						cPostavicky.addItem(a);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		bKtoKreslÌ.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					vypis.append(Hlavna.getInstance().searchAutor(cPostavicky.getSelectedItem().toString()));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				tablePostavySearch.removeAll();
				try {
					tablePostavySearch.setModel(Hlavna.getInstance().spravTabulkuSearchAutor(cPostavicky.getSelectedItem().toString()));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				tablePostavySearch.getColumnModel().getColumn(0).setPreferredWidth(150);
				tablePostavySearch.getColumnModel().getColumn(1).setPreferredWidth(150);
				tablePostavySearch.getColumnModel().getColumn(2).setPreferredWidth(150);
			}
		});
		
		
		
		
		
		/**6. scen·r akcie*/
		bScenar6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				oScenar6.setVisible(true);
				oScenar6.setSize(1000, 500);
				oScenar6.setLayout(new FlowLayout());	
				cVsetkyKomixy.removeAllItems();
				try {
					for (String a : Hlavna.getInstance().vsetkyKomixy())
						cVsetkyKomixy.addItem(a);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				tableKomixovNew.removeAll();
				try {
					tableKomixovNew.setModel(Hlavna.getInstance().spravTabulkuKomixy());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				tableKomixovNew.getColumnModel().getColumn(0).setPreferredWidth(250);
				tableKomixovNew.getColumnModel().getColumn(1).setPreferredWidth(50);
				tableKomixovNew.getColumnModel().getColumn(2).setPreferredWidth(100);
			}
		});
		
		bPotvrdNovyKomix.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			try {
				vypis.append(Hlavna.getInstance().insertKomix(tZadajNazov.getText()));
			} catch (SQLException e1) {
				e1.printStackTrace();
			}	
			cVsetkyKomixy.removeAllItems();
			try {
				for (String a : Hlavna.getInstance().vsetkyKomixy())
					cVsetkyKomixy.addItem(a);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			tableKomixovNew.removeAll();
			try {
				tableKomixovNew.setModel(Hlavna.getInstance().spravTabulkuKomixy());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			tableKomixovNew.getColumnModel().getColumn(0).setPreferredWidth(250);
			tableKomixovNew.getColumnModel().getColumn(1).setPreferredWidth(50);
			tableKomixovNew.getColumnModel().getColumn(2).setPreferredWidth(100);
			}
		});
		
		bPridajStranu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					vypis.append(Hlavna.getInstance().insertStrana(cVsetkyKomixy.getSelectedItem().toString(), tSituacia.getText()));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		bZmazKomix.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					vypis.append(Hlavna.getInstance().deleteKomix(cVsetkyKomixy.getSelectedItem().toString()));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				cVsetkyKomixy.removeAllItems();
				try {
					for (String a : Hlavna.getInstance().vsetkyKomixy())
						cVsetkyKomixy.addItem(a);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				tableKomixovNew.removeAll();
				try {
					tableKomixovNew.setModel(Hlavna.getInstance().spravTabulkuKomixy());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				tableKomixovNew.getColumnModel().getColumn(0).setPreferredWidth(250);
				tableKomixovNew.getColumnModel().getColumn(1).setPreferredWidth(50);
				tableKomixovNew.getColumnModel().getColumn(2).setPreferredWidth(100);
			}
		});
		
		
		
		
		
		/**7. scen·r akcie*/
		bScenar7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				oScenar7.setVisible(true);
				oScenar7.setSize(600, 500);
				oScenar7.setLayout(new FlowLayout());	
				tableZamestnancovNew.removeAll();
				try {
					tableZamestnancovNew.setModel(Hlavna.getInstance().spravTabulkuAutoriSimple());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				tableZamestnancovNew.getColumnModel().getColumn(0).setPreferredWidth(150);
				tableZamestnancovNew.getColumnModel().getColumn(1).setPreferredWidth(30);
				tableZamestnancovNew.getColumnModel().getColumn(2).setPreferredWidth(150);
			}
		});
		
		bPotvrdZamestnanca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					vypis.append(Hlavna.getInstance().insertUmelec(tZadajMeno.getText()));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				tableZamestnancovNew.removeAll();
				try {
					tableZamestnancovNew.setModel(Hlavna.getInstance().spravTabulkuAutoriSimple());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				tableZamestnancovNew.getColumnModel().getColumn(0).setPreferredWidth(150);
				tableZamestnancovNew.getColumnModel().getColumn(1).setPreferredWidth(30);
				tableZamestnancovNew.getColumnModel().getColumn(2).setPreferredWidth(150);
			}
		});
		
		
		
		
		
		/**8. scen·r akcie*/
		bScenar8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				oScenar8.setVisible(true);
				oScenar8.setSize(700, 550);
				oScenar8.setLayout(new FlowLayout());
				cVsetkyKomixy8.removeAllItems();
				try {
					for (String a : Hlavna.getInstance().vsetkyKomixy())
						cVsetkyKomixy8.addItem(a);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				cVsetkyPostavy8.removeAllItems();
				try {
					for (String a : Hlavna.getInstance().vsetkyPostavicky())
						cVsetkyPostavy8.addItem(a);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		bPotvrdVyberKomixu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cStranyVKomixe8.removeAllItems();
				try {
					for (String a : Hlavna.getInstance().vsetkyStrany(cVsetkyKomixy8.getSelectedItem().toString()))
						cStranyVKomixe8.addItem(a);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				tableNakresleni.removeAll();
				try {
					tableNakresleni.setModel(Hlavna.getInstance().spravTabulkuNakreslenia(cVsetkyKomixy8.getSelectedItem().toString()));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				tableNakresleni.getColumnModel().getColumn(0).setPreferredWidth(100);
				tableNakresleni.getColumnModel().getColumn(1).setPreferredWidth(60);
				tableNakresleni.getColumnModel().getColumn(2).setPreferredWidth(120);
				tableNakresleni.getColumnModel().getColumn(3).setPreferredWidth(250);
			}
		});
		
		bNakresli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					vypis.append(Hlavna.getInstance().insertNakreslenie(
							cVsetkyPostavy8.getSelectedItem().toString(),
							cStranyVKomixe8.getSelectedItem().toString(), tZadajCinnost8.getText()));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				tableNakresleni.removeAll();
				try {
					tableNakresleni.setModel(Hlavna.getInstance().spravTabulkuNakreslenia(cVsetkyKomixy8.getSelectedItem().toString()));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				tableNakresleni.getColumnModel().getColumn(0).setPreferredWidth(100);
				tableNakresleni.getColumnModel().getColumn(1).setPreferredWidth(60);
				tableNakresleni.getColumnModel().getColumn(2).setPreferredWidth(120);
				tableNakresleni.getColumnModel().getColumn(3).setPreferredWidth(250);
			}
		});
	}
}
