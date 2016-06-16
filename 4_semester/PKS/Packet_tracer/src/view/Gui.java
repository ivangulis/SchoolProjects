package view;
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

import controller.Analyzator;

public class Gui {
	/** Všetko ku hlavnému oknu */
	private JFrame oHlavne = new JFrame("Hlavne Okno");
	private JLabel lEdit = new JLabel("Zadaj názov súboru (mnou vytvorené majú tvar: trace-XX.pcap kde XX je èíslo 1-25)");
	private JButton bAnalyzuj = new JButton("Analyzuj");
	private JButton bVypisRamce = new JButton("Vypíš rámce");
	private JButton bSubor = new JButton("Naèítaj súbor");
	private JButton bClear = new JButton("Vymaž");
	
	private JButton bHttp = new JButton("http");
	private JButton bHttps = new JButton("https");
	private JButton bTelnet = new JButton("telnet");
	private JButton bSsh = new JButton("ssh");
	private JButton bFtpControl = new JButton("ftp-control");
	private JButton bFtpData = new JButton("ftp-data");
	private JButton bTftp = new JButton("tftp");
	private JButton bIcmp = new JButton("icmp");
	private JButton bArp = new JButton("arp");
	
	private JScrollPane scroll = new JScrollPane();
	private JTextArea vypisAnalyzy = new JTextArea(35, 70);
	private JTextField subor = new JTextField(20);
	

	/** Celé gui */
	public Gui() {

		oHlavne.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		oHlavne.setVisible(true);
		oHlavne.setSize(1000, 750);
		oHlavne.setLayout(new FlowLayout());
		
		oHlavne.add(lEdit);
		oHlavne.add(subor);
		oHlavne.add(bSubor);
		oHlavne.add(bAnalyzuj);
		oHlavne.add(bClear);
		oHlavne.add(bVypisRamce);
		
		oHlavne.add(bHttp);
		oHlavne.add(bHttps);
		oHlavne.add(bTelnet);
		oHlavne.add(bSsh);
		oHlavne.add(bFtpControl);
		oHlavne.add(bFtpData);
		oHlavne.add(bTftp);
		oHlavne.add(bIcmp);
		oHlavne.add(bArp);
		oHlavne.add(vypisAnalyzy);
		oHlavne.getContentPane().add(scroll);
		
		bAnalyzuj.setVisible(false);
		bClear.setVisible(false);
		bVypisRamce.setVisible(false);
		bHttp.setVisible(false);
		bHttps.setVisible(false);
		bTelnet.setVisible(false);
		bSsh.setVisible(false);
		bFtpControl.setVisible(false);
		bFtpData.setVisible(false);
		bTftp.setVisible(false);
		bIcmp.setVisible(false);
		bArp.setVisible(false);
		
		scroll.setViewportView(vypisAnalyzy);
		
		vypisAnalyzy.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		vypisAnalyzy.setLineWrap(true);
		vypisAnalyzy.setWrapStyleWord(true);

		bClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vypisAnalyzy.setText("");
			}
		});
		
		bHttp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vypisAnalyzy.append(Analyzator.getInstance().vypisHttp());
			}
		});
		
		bHttps.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vypisAnalyzy.append(Analyzator.getInstance().vypisHttps());
			}
		});
		
		bTelnet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vypisAnalyzy.append(Analyzator.getInstance().vypisTelnet());
			}
		});
		
		bSsh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vypisAnalyzy.append(Analyzator.getInstance().vypisSsh());
			}
		});
		
		bFtpControl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vypisAnalyzy.append(Analyzator.getInstance().vypisFtpControl());
			}
		});
		
		bFtpData.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vypisAnalyzy.append(Analyzator.getInstance().vypisFtpData());
			}
		});
		
		bTftp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vypisAnalyzy.append(Analyzator.getInstance().vypisTftp());
			}
		});
		
		bIcmp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vypisAnalyzy.append(Analyzator.getInstance().vypisIcmp());
			}
		});
		
		bArp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vypisAnalyzy.append(Analyzator.getInstance().vypisArp());
			}
		});
		
		bVypisRamce.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i=0;i<Analyzator.pocet;i++)
					vypisAnalyzy.append(Analyzator.getInstance().vypisAnalyzy());
					vypisAnalyzy.append(Analyzator.getInstance().vypisIP());
			}
		});
		
		bAnalyzuj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Analyzator.pocet = 0;
				Analyzator.pocetIP = 0;
				Analyzator.getInstance().analyzuj();
				bSubor.setVisible(true);
				subor.setVisible(true);
				lEdit.setVisible(true);
				bAnalyzuj.setVisible(false);
				bVypisRamce.setVisible(true);
				bClear.setVisible(true);

				bHttp.setVisible(true);
				bHttps.setVisible(true);
				bTelnet.setVisible(true);
				bSsh.setVisible(true);
				bFtpControl.setVisible(true);
				bFtpData.setVisible(true);
				bTftp.setVisible(true);
				bIcmp.setVisible(true);
				bArp.setVisible(true);
			}
		});
		bSubor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Analyzator.getInstance().nazovSuboru(subor.getText());
				Analyzator.getInstance().nacitajExterny();
				if (Analyzator.celySubor == null) {  
					vypisAnalyzy.append(Analyzator.getInstance().error()); 
		        }
				else {
				vypisAnalyzy.setText("");
				bAnalyzuj.setVisible(true);
				bSubor.setVisible(false);
				subor.setVisible(false);
				lEdit.setVisible(false);
				
				bClear.setVisible(false);
				bVypisRamce.setVisible(false);
				bHttp.setVisible(false);
				bHttps.setVisible(false);
				bTelnet.setVisible(false);
				bSsh.setVisible(false);
				bFtpControl.setVisible(false);
				bFtpData.setVisible(false);
				bTftp.setVisible(false);
				bIcmp.setVisible(false);
				bArp.setVisible(false);
				}
			}
		});
	}
}