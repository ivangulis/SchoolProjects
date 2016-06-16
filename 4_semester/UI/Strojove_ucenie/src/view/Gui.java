package view;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import controller.Main;

public class Gui {
	/** Všetko ku hlavnému oknu */
	private JFrame oHlavne = new JFrame("Hlavne Okno");
	private JButton bClear = new JButton("Vymaž");
	private JButton bVykonaj = new JButton("Vykonaj");
	private JButton bVykonajDoKonca = new JButton("Vykonaj všetky akcie v iterácii");
	
	private JScrollPane scroll = new JScrollPane();
	private JTextArea vypis = new JTextArea(35, 70);
	

	/** Celé gui */
	public Gui() {

		
		oHlavne.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		oHlavne.setVisible(true);
		oHlavne.setSize(525, 750);
		oHlavne.setLayout(new FlowLayout());
		
		oHlavne.add(bVykonaj);
		oHlavne.add(bVykonajDoKonca);
		oHlavne.add(bClear);
		oHlavne.add(vypis);
		oHlavne.getContentPane().add(scroll);		
		scroll.setViewportView(vypis);
		
		vypis.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		vypis.setLineWrap(true);
		vypis.setWrapStyleWord(true);


		bClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vypis.setText("");
			}
		});
		
		bVykonaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vypis.append(Main.getInstance().algo(false));
			}
		});
		bVykonajDoKonca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vypis.append(Main.getInstance().algo(true));
			}
		});
	}
}
