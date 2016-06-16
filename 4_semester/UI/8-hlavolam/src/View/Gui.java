package View;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Controller.Hlavna;

public class Gui {
	/** Vöetko ku hlavnÈmu oknu */
	private JFrame oHlavne = new JFrame("Hlavne Okno");
	private JLabel lRiadky = new JLabel("PoËet riadkov:");
	private JTextField tRiadky = new JTextField(3);
	private JLabel lStlpce = new JLabel("PoËet stlpcov:");
	private JTextField tStlpce = new JTextField(3);
	private JLabel lStart = new JLabel("PoËiatoËn˝ stav (bez medzier, 0 pre medzeru)");
	private JTextField tStart = new JTextField(20);
	private JLabel lCiel = new JLabel("Cieæov˝ stav (bez medzier, 0 pre medzeru)");
	private JTextField tCiel = new JTextField(20);
	private JButton bNacitaj = new JButton("NaËÌtaj");
	private JButton bHeuristika1 = new JButton("Heuristika 1.");
	private JButton bHeuristika2 = new JButton("Heuristika 2.");
	private JButton bClear = new JButton("Vymaû");
	
	private JScrollPane scroll = new JScrollPane();
	private JTextArea vypis = new JTextArea(35, 70);
	

	/** CelÈ gui */
	public Gui() {

		tStart.setText("0");
		tCiel.setText("0");
		tRiadky.setText("0");
		tStlpce.setText("0");
		
		oHlavne.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		oHlavne.setVisible(true);
		oHlavne.setSize(525, 750);
		oHlavne.setLayout(new FlowLayout());
		
		oHlavne.add(lRiadky);
		oHlavne.add(tRiadky);
		oHlavne.add(lStlpce);
		oHlavne.add(tStlpce);
		oHlavne.add(lStart);
		oHlavne.add(tStart);
		oHlavne.add(lCiel);
		oHlavne.add(tCiel);
		oHlavne.add(bNacitaj);
		oHlavne.add(bHeuristika1);
		oHlavne.add(bHeuristika2);
		oHlavne.add(bClear);
		oHlavne.add(vypis);
		oHlavne.getContentPane().add(scroll);
		
		bHeuristika1.setVisible(false);
		bHeuristika2.setVisible(false);
		bClear.setVisible(false);
		
		scroll.setViewportView(vypis);
		
		vypis.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		vypis.setLineWrap(true);
		vypis.setWrapStyleWord(true);

		bClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vypis.setText("");
			}
		});
		bNacitaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/**Kontrolujem vstupy*/
				if (tStart.getText().length()  != (Integer.parseInt(tRiadky.getText())*
					Integer.parseInt(tStlpce.getText()))
					|| tCiel.getText().length()  != Integer.parseInt(tRiadky.getText())*
					Integer.parseInt(tStlpce.getText())
					|| tStart.getText().equals("0") || tCiel.getText().equals("0") ||
					tRiadky.getText().equals("0") || tStlpce.getText().equals("0"))
					vypis.append("Veækosù mapy sa nezhoduje so startom/cieæom \n");
				else {
					vypis.append(Hlavna.getInstance().nacitaj(tRiadky.getText(), 
					tStlpce.getText(), tStart.getText(), tCiel.getText()));
				}
				bHeuristika1.setVisible(true);
				bHeuristika2.setVisible(true);
				bClear.setVisible(true);
			}
		});
		bHeuristika1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vypis.append(Hlavna.getInstance().heuristika1());
				
			}
		});
		bHeuristika2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vypis.append(Hlavna.getInstance().heuristika2());
				
			}
		});
	}
}
