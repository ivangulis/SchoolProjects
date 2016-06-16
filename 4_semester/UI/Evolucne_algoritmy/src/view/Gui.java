package view;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controller.Main;

public class Gui {
	/** Vöetko ku hlavnÈmu oknu */
	private JFrame oHlavne = new JFrame("Hlavne Okno");
	private JButton bSpustTurnaj = new JButton("Spusù s turnajom");
	private JButton bSpustRulet = new JButton("Spusù s ruletou");
	private JButton bInit = new JButton("Inicializuj");
	private JButton bMapa = new JButton("Vykresli mapu");
	private JButton bClear = new JButton("Vymaû");
	private JLabel lMutacia = new JLabel("Zadaj öancu mut·cie(%):");
	private JTextField tMutacia = new JTextField(5);
	private JLabel lPocetJedincov = new JLabel("Zadaj poËet jedincov gener·cie:");
	private JTextField tPocetJedincov = new JTextField(15);
	private JLabel lMaxPocetGeneracii = new JLabel("Zadaj maxim·lny poËet gener·cii:");
	private JTextField tMaxPocetGeneracii = new JTextField(7);
	private JLabel lVyvoleny = new JLabel("Elitarizmus?");
	private JCheckBox cVyvoleny = new JCheckBox();
	
	private JScrollPane scroll = new JScrollPane();
	private JTextArea vypis = new JTextArea(35, 70);
	

	/** CelÈ gui */
	public Gui() {

		
		oHlavne.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		oHlavne.setVisible(true);
		oHlavne.setSize(525, 750);
		oHlavne.setLayout(new FlowLayout());
		
		oHlavne.add(lPocetJedincov);
		oHlavne.add(tPocetJedincov);
		oHlavne.add(lMaxPocetGeneracii);
		oHlavne.add(tMaxPocetGeneracii);
		oHlavne.add(lMutacia);
		oHlavne.add(tMutacia);
		oHlavne.add(bInit);
		oHlavne.add(lVyvoleny);
		oHlavne.add(cVyvoleny);
		oHlavne.add(bSpustTurnaj);
		oHlavne.add(bSpustRulet);
		oHlavne.add(bMapa);
		oHlavne.add(bClear);
		oHlavne.add(vypis);
		oHlavne.getContentPane().add(scroll);		
		scroll.setViewportView(vypis);
		
		vypis.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		vypis.setLineWrap(true);
		vypis.setWrapStyleWord(true);
		
		tPocetJedincov.setText("100");
		tMaxPocetGeneracii.setText("500");
		tMutacia.setText("0.025");
		cVyvoleny.setSelected(true);
		bSpustTurnaj.setVisible(false);
		bSpustRulet.setVisible(false);
		bMapa.setVisible(false);
		cVyvoleny.setVisible(false);
		lVyvoleny.setVisible(false);

		bClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vypis.setText("");
			}
		});
		
		bMapa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vypis.append(Main.getInstance().vypisMapu());
			}
		});
		
		bInit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				vypis.append(Main.getInstance().inicializuj(Integer.parseInt(tPocetJedincov.getText())));
				bSpustTurnaj.setVisible(true);
				bSpustRulet.setVisible(true);
				bMapa.setVisible(true);
				cVyvoleny.setVisible(true);
				lVyvoleny.setVisible(true);
				}
				catch (NumberFormatException er){
					JOptionPane.showMessageDialog(oHlavne, "NajskÙr musÌö zadaù poËet jedincov.");
				}
				}
		});
		
		bSpustTurnaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				vypis.append(Main.getInstance().algo(Integer.parseInt(tMaxPocetGeneracii.getText()),
						Double.parseDouble(tMutacia.getText()),0, Integer.parseInt(tPocetJedincov.getText()),
						cVyvoleny.isSelected()));
				}
				catch (NumberFormatException er){
					JOptionPane.showMessageDialog(oHlavne, "Nezadal si maxim·lny poËet gener·cii alebo öancu mut·cie.");
				}
			}
		});
		
		bSpustRulet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vypis.append(Main.getInstance().algo(Integer.parseInt(tMaxPocetGeneracii.getText()),
						Double.parseDouble(tMutacia.getText()),1, Integer.parseInt(tPocetJedincov.getText()),
						cVyvoleny.isSelected()));
			}
		});
	}
}