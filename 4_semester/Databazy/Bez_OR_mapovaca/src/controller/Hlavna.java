package controller;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import view.Gui;

public class Hlavna {
	
	/** Singleton */
	private static Hlavna instance = null;
	private Hlavna() {}
	public static Hlavna getInstance() {
		if (instance == null)
			instance = new Hlavna();
		return instance;
	}
	
	static Connection c; //pripojenie k datab�ze, inicializovan� vo funkcii zavolanej v maine
	
	/**Vr�ti pole stringov pre combobox s postavi�kami*/
	public String[] vsetkyPostavicky() throws SQLException{
		ArrayList<String> celyVypis = new ArrayList<String>();
		try {
			Statement vstup = c.createStatement(); //nov� statement
	         ResultSet postavicky = vstup.executeQuery( "SELECT meno FROM postavy order by id_postavy asc;" );
	         while ( postavicky.next() ) {
	            celyVypis.add(postavicky.getString("meno")); //tvor�m list
	         }
	         postavicky.close();
	         vstup.close();
	         c.commit(); //commit
			} catch (Exception e) {
		         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
		         System.exit(0);
		         c.rollback(); //inak rollback
		    }
		String[] pole = new String[celyVypis.size()];
		for (int i = 0;i<celyVypis.size();i++)//tvor�m pole
			pole[i]=celyVypis.get(i);
		
		return pole;
	}
	
	/**Vr�ti pole stringov pre combobox s autormi*/
	public String[] vsetciAutori() throws SQLException{
		ArrayList<String> celyVypis = new ArrayList<String>();
		try {
			Statement vstup = c.createStatement();
	         ResultSet autori = vstup.executeQuery( "SELECT * FROM umelci order by id_zamestnanca asc;" );
	         while ( autori.next() ) {
	            celyVypis.add(autori.getString("meno")); //tvor�m list
	         }
	         autori.close();
	         vstup.close();
	         c.commit();
			} catch (Exception e) {
		         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
		         System.exit(0);
		         c.rollback();
		    }
		String[] pole = new String[celyVypis.size()];
		for (int i = 0;i<celyVypis.size();i++) //tvor�m pole
			pole[i]=celyVypis.get(i);
		
		return pole;
	}
	
	/**Vr�ti pole stringov pre combobox s komixami*/
	public String[] vsetkyKomixy() throws SQLException{
		ArrayList<String> celyVypis = new ArrayList<String>();
		try {
			Statement vstup = c.createStatement();
	         ResultSet autori = vstup.executeQuery( "SELECT * FROM komixy order by cislo_komixu asc;" );
	         while ( autori.next() ) {
	            celyVypis.add(autori.getString("nazov"));
	         }
	         autori.close();
	         vstup.close();
	         c.commit();
			} catch (Exception e) {
		         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
		         System.exit(0);
		         c.rollback();
		    }
		String[] pole = new String[celyVypis.size()];
		for (int i = 0;i<celyVypis.size();i++)
			pole[i]=celyVypis.get(i);
		
		return pole;
	}
	
	/**Vr�ti pole stringov pre combobox so stranami ku konkretnemu komixu*/
	public String[] vsetkyStrany(String nazovKomixu) throws SQLException{
		ArrayList<String> celyVypis = new ArrayList<String>();
		try {
			Statement vstup = c.createStatement();
	         ResultSet autori = vstup.executeQuery( "SELECT id_strany FROM strany JOIN komixy ON komixy.cislo_komixu = "
	         		+ " strany.cislo_komixu where komixy.nazov = '" + nazovKomixu + "' order by id_strany asc;" );
	         while ( autori.next() ) {
	            celyVypis.add(autori.getString("id_strany"));
	         }
	         autori.close();
	         vstup.close();
	         c.commit();
			} catch (Exception e) {
		         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
		         System.exit(0);
		         c.rollback();
		    }
		String[] pole = new String[celyVypis.size()];
		for (int i = 0;i<celyVypis.size();i++)
			pole[i]=celyVypis.get(i);
		
		return pole;
	}
	
	/**Insertuje do tabulky pre nakreslen� postavy*/
	public String insertNakreslenie(String menoPostavy, String idStrany, String cinnost) throws SQLException{
		Date datum = new Date(); //s��asn� d�tum
		SimpleDateFormat formatD = new SimpleDateFormat("dd/MM/yyyy"); //form�t d�tumu
		try {
		Statement vstup = c.createStatement();
		String prikaz;
		
		int id_postavy;
		
		ResultSet postavy = vstup.executeQuery( "SELECT id_postavy FROM postavy"
				+ " where meno = '" + menoPostavy + "';" ); //najprv si n�jdem ID postavy
		postavy.next();
        id_postavy = postavy.getInt("id_postavy");
		
		prikaz = "INSERT INTO postavicka_nakreslena (id_postavy, id_strany, datum_nakreslenia, cinnost) VALUES ('"
				+ id_postavy +"','" + Integer.parseInt(idStrany) + "','"  + formatD.format(datum) + "','" + cinnost + "');";
        vstup.executeUpdate(prikaz);

        vstup.close();
        c.commit();
		} catch (Exception e) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         System.exit(0);
	         c.rollback();
	    }
		return "Postavi�ka " + menoPostavy + " bola �spe�ne nakreslen� a jej �innos� je: '" + cinnost + "'\n";
	}
	
	
	/**Insertujem do tabu�ky komixov*/
	public String insertKomix(String nazov) throws SQLException{
		Date datum = new Date();
		SimpleDateFormat formatD = new SimpleDateFormat("dd/MM/yyyy");
		try {
		Statement vstup = c.createStatement();
		String prikaz;
		
		
		prikaz = "INSERT INTO komixy (nazov,datum_vytvorenia) VALUES ('"
				+ nazov +"','" + formatD.format(datum) + "');";
        vstup.executeUpdate(prikaz);

        vstup.close();
        c.commit();
		} catch (Exception e) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         System.exit(0);
	         c.rollback();
	    }
		return "Komix " + nazov + " bol �spe�ne vytvoren� \n";
	}
	
	/**Insertujem do tabu�ky umelcov*/
	public String insertUmelec(String meno) throws SQLException{
		Date datum = new Date();
		SimpleDateFormat formatD = new SimpleDateFormat("dd/MM/yyyy");
		try {
		Statement vstup = c.createStatement();
		String prikaz;
		
		prikaz = "INSERT INTO umelci (meno,zamestnany_od) VALUES ('"
				+ meno +"','" + formatD.format(datum) + "');";
        vstup.executeUpdate(prikaz);

        vstup.close();
        c.commit();
		} catch (Exception e) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         System.exit(0);
	         c.rollback();
	    }
		return "Autor " + meno + " bol �spe�ne pridan�\n";
	}
	
	/**Insertujem do tabu�ky postavi�iek*/
	public String insertPostavu(String meno, String typ, boolean muz, String menoAutora) throws SQLException{
		try {
		Statement vstup = c.createStatement();
		String prikaz;
		int ID_autora = -1;
		ResultSet autori = vstup.executeQuery( "SELECT * FROM umelci"
				+ " where meno='" + menoAutora + "';" ); //n�jdem si ID autora
		autori.next();
        ID_autora = autori.getInt("id_zamestnanca");
		
		prikaz = "INSERT INTO postavy (meno,typ,muz,id_autora) VALUES ('"
				+ meno +"','" + typ + "','"	+ muz + "'," + ID_autora + ");"; //pou�ijem najdene ID ako FK
        vstup.executeUpdate(prikaz);

        vstup.close();
        c.commit();
		} catch (Exception e) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         System.exit(0);
	         c.rollback();
	    }
		return "Postava " + meno + " bola �spe�ne vytvoren� \n";
	}
	
	/**Insertujem do tabu�ky komixov*/
	public String insertStrana(String nazov, String situacia) throws SQLException{
		try {
		Statement vstup = c.createStatement();
		String prikaz;
		int cislo_komixu = -1;
		ResultSet autori = vstup.executeQuery( "SELECT * FROM komixy"
				+ " where nazov='" + nazov + "';" );
		autori.next();
        cislo_komixu = autori.getInt("cislo_komixu"); //najdem si ��slo komixu
		
		prikaz = "INSERT INTO strany (cislo_komixu,situacia) VALUES ("
				+ cislo_komixu +" ,'" + situacia + "');"; //�islo pou�ijem ako FK
        vstup.executeUpdate(prikaz);

        vstup.close();
        c.commit();
		} catch (Exception e) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         System.exit(0);
	         c.rollback();
	    }
		return "Strana bola pridan� do komixu s n�zvom " + nazov + "\n";
	}
	
	
	/**Zmen�m/updatnem n�zov komixu*/
	public String updateNazov(String nazov, String povodnyNazov) throws SQLException{
		try {
		Statement vstup = c.createStatement();
		String prikaz;
		int cisloKomixu = -1;
		ResultSet komixy = vstup.executeQuery( "SELECT nazov,cislo_komixu FROM komixy where "
				+ "nazov ='" + povodnyNazov + "';" );
        komixy.next();
        cisloKomixu = komixy.getInt("cislo_komixu"); //n�jdem si ��slo
		
		prikaz = "UPDATE komixy SET nazov='" + nazov + "' where cislo_komixu =" + cisloKomixu + ";";
        vstup.executeUpdate(prikaz);

        vstup.close();
        c.commit();
		} catch (Exception e) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         System.exit(0);
	         c.rollback();
	    }
		return "Nov� n�zov vybran�ho komixu(" + povodnyNazov + ") je: " + nazov + "\n";
	}
	
	/**Deletnem komix*/
	public String deleteKomix(String nazov) throws SQLException{
		try {
		Statement vstup = c.createStatement();
		String prikaz;
        
		prikaz = "DELETE from komixy where nazov ='"+ nazov + "';";
        vstup.executeUpdate(prikaz);

        vstup.close();
        c.commit();
		} catch (Exception e) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         System.exit(0);
	         c.rollback();
	    }
		return "Komix s n�zvom " + nazov + " bol vymazan�\n";
	}
	
	/**Deletnem autora a jeho postavy presuniem druhemu*/
	public String deleteAutor(String meno, String nahradnik) throws SQLException{
		try {
		Statement vstup = c.createStatement();
		String prikaz;
		String prikaz2;
		int id_autora = -1;
		int id_nahradnika = -1;
		ResultSet autori = vstup.executeQuery( "SELECT * FROM umelci"
				+ " where meno='" + meno + "';" );
        autori.next(); 
        id_autora = autori.getInt("id_zamestnanca");
        
        ResultSet nahradnici = vstup.executeQuery( "SELECT * FROM umelci"
				+ " where meno='" + nahradnik + "';" );
        nahradnici.next(); 
        id_nahradnika = nahradnici.getInt("id_zamestnanca"); //zistim ID nahradnika pre FK postavy
		
        prikaz2 = "UPDATE postavy SET id_autora= " + id_nahradnika + " where id_autora = " + id_autora + ";";
        vstup.executeUpdate(prikaz2); //presuniem postavy nahradnikovy
        
		prikaz = "DELETE from umelci where id_zamestnanca ="+ id_autora + ";";
        vstup.executeUpdate(prikaz);

        vstup.close();
        c.commit();
		} catch (Exception e) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         System.exit(0);
	         c.rollback();
	    }
		return "Vyhoden� bol autor s menom " + meno + "\n";
	}
	
	/**Najdem autora postavi�ky*/
	public String searchAutor(String menoPostavicky) throws SQLException{
		
		StringBuilder menoAutora = new StringBuilder();
		try {
		Statement vstup = c.createStatement();
		ResultSet hladam = vstup.executeQuery( "SELECT umelci.meno AS menoUmelca from umelci JOIN postavy ON "
				+ "umelci.id_zamestnanca = postavy.id_autora where postavy.meno='" + menoPostavicky + "';" );
		hladam.next(); 
		menoAutora.append(hladam.getString("menoUmelca"));
        vstup.close();
        c.commit();
		} catch (Exception e) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         System.exit(0);
	         c.rollback();
	    }
		
		return "Postavi�ku menom " + menoPostavicky +  " kresl� " + menoAutora.toString() + "\n";
	}
	
	/**Tvorba tabulky nakreslen�*/
	public DefaultTableModel spravTabulkuNakreslenia(String komix) throws SQLException{
		final Vector<String> stlpce = new Vector<String>();
		Vector<Vector<Object>> vypln = new Vector<Vector<Object>>();
		stlpce.add("Postavi�ka");
		stlpce.add("ID strany");
		stlpce.add("D�tum nakreslenia");
		stlpce.add("�o rob�?");
		try {
			Statement vstup = c.createStatement();
			Statement vstup2 = c.createStatement();
	         ResultSet postavicky = vstup.executeQuery( "SELECT * FROM postavicka_nakreslena where id_strany IN "
	         		+ "(SELECT id_strany FROM strany JOIN komixy ON komixy.cislo_komixu = strany.cislo_komixu where komixy.nazov = '" + komix + 
	         		"') order by id_strany;");
	         while ( postavicky.next() ) {
	        	 Vector<Object> riadok = new Vector<Object>();
	        	 
	        	 ResultSet hladamMeno = vstup2.executeQuery( "SELECT meno FROM postavy where id_postavy = " 
	        	 + postavicky.getInt("id_postavy") + ";" );
	        	 hladamMeno.next();
	        	 riadok.add(hladamMeno.getString("meno"));
	        	 vypln.add(riadok);
	        	 hladamMeno.close();
	        	 
	        	 riadok.add(postavicky.getString("id_strany"));
	        	 riadok.add(postavicky.getString("datum_nakreslenia"));
	        	 riadok.add(postavicky.getString("cinnost"));	        	 
	         }
	         postavicky.close();
	         vstup.close();
	         vstup2.close();
	         c.commit();
			} catch (Exception e) {
		         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
		         System.exit(0);
		         c.rollback();
		    }
		return new DefaultTableModel(vypln, stlpce){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int colum){
				return false;
			}
		};
	}
	
	/**Tvorba tabulky komixov*/
	public DefaultTableModel spravTabulkuKomixy() throws SQLException{
		final Vector<String> stlpce = new Vector<String>();
		Vector<Vector<Object>> vypln = new Vector<Vector<Object>>();
		stlpce.add("Nazov");
		stlpce.add("��slo");
		stlpce.add("D�tum vytvorenia");
		try {
			Statement vstup = c.createStatement();
	         ResultSet komixy = vstup.executeQuery( "SELECT * FROM komixy order by cislo_komixu asc;" );
	         while ( komixy.next() ) {
	        	 Vector<Object> riadok = new Vector<Object>();
	        	 riadok.add(komixy.getString("nazov"));
	        	 riadok.add(komixy.getString("cislo_komixu"));
	        	 riadok.add(komixy.getString("datum_vytvorenia"));
	        	 vypln.add(riadok);
	         }
	         komixy.close();
	         vstup.close();
	         c.commit();
			} catch (Exception e) {
		         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
		         System.exit(0);
		         c.rollback();
		    }
		return new DefaultTableModel(vypln, stlpce){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int colum){
				return false;
			}
		};
	}
	
	/**Tvorba tabulky autorov, bez post�v*/
	public DefaultTableModel spravTabulkuAutoriSimple() throws SQLException{
		Vector<String> stlpce = new Vector<String>();
		Vector<Vector<Object>> vypln = new Vector<Vector<Object>>();
		stlpce.add("Meno");
		stlpce.add("ID");
		stlpce.add("Zamestnan� od");
		try {
			Statement vstup = c.createStatement();
			ResultSet autori = vstup.executeQuery( "SELECT meno,id_zamestnanca, zamestnany_od FROM umelci order by umelci.id_zamestnanca asc;" );
	         while ( autori.next() ) {
	        	 Vector<Object> riadok = new Vector<Object>();
	        	 riadok.add(autori.getString("meno"));
	        	 riadok.add(autori.getString("id_zamestnanca"));
	        	 riadok.add(autori.getString("zamestnany_od"));
	        	 vypln.add(riadok);
	         }
	         autori.close();
	         vstup.close();
	         c.commit();
			} catch (Exception e) {
		         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
		         System.exit(0);
		         c.rollback();
		    }
		return new DefaultTableModel(vypln, stlpce){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int colum){
				return false;
			}
		};
	}
	
	/**Tvorba tabulky autorov, s po�tom post�v*/
	public DefaultTableModel spravTabulkuAutori() throws SQLException{
		Vector<String> stlpce = new Vector<String>();
		Vector<Vector<Object>> vypln = new Vector<Vector<Object>>();
		stlpce.add("Meno");
		stlpce.add("ID");
		stlpce.add("Zamestnan� od");
		stlpce.add("Kresl� post�v");
		try {
			Statement vstup = c.createStatement();
			ResultSet autori = vstup.executeQuery( "SELECT umelci.meno AS menoUmelca, "
					+ "umelci.id_zamestnanca AS idUmelca, umelci.zamestnany_od AS date"
					+ ",COUNT(postavy) FROM umelci JOIN postavy ON "
	         		+ "umelci.id_zamestnanca = postavy.id_autora GROUP BY idUmelca order by umelci.id_zamestnanca asc;" );
	         while ( autori.next() ) {
	        	 Vector<Object> riadok = new Vector<Object>();
	        	 riadok.add(autori.getString("menoUmelca"));
	        	 riadok.add(autori.getString("idUmelca"));
	        	 riadok.add(autori.getString("date"));
	        	 riadok.add(autori.getString(4));
	        	 vypln.add(riadok);
	         }
	         autori.close();
	         vstup.close();
	         c.commit();
			} catch (Exception e) {
		         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
		         System.exit(0);
		         c.rollback();
		    }
		return new DefaultTableModel(vypln, stlpce){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int colum){
				return false;
			}
		};
	}
	
	/**Tvorba tabulky autorov s po�tom post�v mu�ov*/
	public DefaultTableModel autorPostavyCountMuzi() throws SQLException{
		Vector<String> stlpce = new Vector<String>();
		Vector<Vector<Object>> vypln = new Vector<Vector<Object>>();
		stlpce.add("Meno");
		stlpce.add("Po�et post�v");
		try {
			Statement vstup = c.createStatement();
	         ResultSet postavicky = vstup.executeQuery( "SELECT umelci.meno,COUNT(postavy) FROM umelci JOIN postavy ON "
	         		+ "umelci.id_zamestnanca = postavy.id_autora AND postavy.muz = TRUE GROUP BY umelci.meno order by COUNT(postavy) desc;" );
	         while ( postavicky.next() ) {
	        	 Vector<Object> riadok = new Vector<Object>();
	        	 riadok.add(postavicky.getString("meno"));
	        	 riadok.add(postavicky.getString(2));
	        	 vypln.add(riadok);
	         }
	         postavicky.close();
	         vstup.close();
	         c.commit();
			} catch (Exception e) {
		         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
		         System.exit(0);
		         c.rollback();
		    }
		return new DefaultTableModel(vypln, stlpce){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int colum){
				return false;
			}
		};
	}
	
	/**Tvorba tabulky autorov s po�tom post�v �ien*/
	public DefaultTableModel autorPostavyCountZeny() throws SQLException{
		Vector<String> stlpce = new Vector<String>();
		Vector<Vector<Object>> vypln = new Vector<Vector<Object>>();
		stlpce.add("Meno");
		stlpce.add("Po�et post�v");
		try {
			Statement vstup = c.createStatement();
	         ResultSet postavicky = vstup.executeQuery( "SELECT umelci.meno,COUNT(postavy) FROM umelci JOIN postavy ON "
	         		+ "umelci.id_zamestnanca = postavy.id_autora AND postavy.muz = FALSE GROUP BY umelci.meno order by COUNT(postavy) desc;" );
	         while ( postavicky.next() ) {
	        	 Vector<Object> riadok = new Vector<Object>();
	        	 riadok.add(postavicky.getString("meno"));
	        	 riadok.add(postavicky.getString(2));
	        	 vypln.add(riadok);
	         }
	         postavicky.close();
	         vstup.close();
	         c.commit();
			} catch (Exception e) {
		         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
		         System.exit(0);
		         c.rollback();
		    }
		return new DefaultTableModel(vypln, stlpce){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int colum){
				return false;
			}
		};
	}
	
	/**Tvorba tabulky post�v*/
	public DefaultTableModel spravTabulkuPostav() throws SQLException{
		Vector<String> stlpce = new Vector<String>();
		Vector<Vector<Object>> vypln = new Vector<Vector<Object>>();
		stlpce.add("Meno postavy");
		stlpce.add("ID postavy");
		stlpce.add("Typ");
		stlpce.add("Mu�?");
		stlpce.add("Meno autora");
		try {
			Statement vstup = c.createStatement();
			Statement vstup2 = c.createStatement();
	         ResultSet postavicky = vstup.executeQuery( "SELECT * FROM postavy order by id_postavy asc;" );
	         while ( postavicky.next() ) {
	        	 Vector<Object> riadok = new Vector<Object>();
	        	 riadok.add(postavicky.getString("meno"));
	        	 riadok.add(postavicky.getString("id_postavy"));
	        	 riadok.add(postavicky.getString("typ"));
	        	 riadok.add(postavicky.getBoolean("muz"));
	        	 ResultSet hladamMeno = vstup2.executeQuery( "SELECT meno FROM umelci where id_zamestnanca = " 
	        	 + postavicky.getInt("id_autora") + ";" );
	        	 hladamMeno.next();
	        	 riadok.add(hladamMeno.getString("meno"));
	        	 vypln.add(riadok);
	        	 hladamMeno.close();
	         }
	         postavicky.close();
	         vstup.close();
	         vstup2.close();
	         c.commit();
			} catch (Exception e) {
		         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
		         System.exit(0);
		         c.rollback();
		    }
		return new DefaultTableModel(vypln, stlpce){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int colum){
				return false;
			}
		};
		
	}
	
	/**Tvorba tabulky pre search autora postavi�ky*/
	public DefaultTableModel spravTabulkuSearchAutor(String menoPostavicky) throws SQLException{
		Vector<String> stlpce = new Vector<String>();
		Vector<Vector<Object>> vypln = new Vector<Vector<Object>>();
		stlpce.add("Meno autora");
		stlpce.add("ID autora");
		stlpce.add("Typ postavi�ky");		
		try {
			Statement vstup = c.createStatement();
	         ResultSet search = vstup.executeQuery( "SELECT umelci.id_zamestnanca AS idAutora, "
	         		+ "postavy.typ AS typPostavy, umelci.meno AS menoAutora from umelci JOIN postavy ON "
	 				+ "umelci.id_zamestnanca = postavy.id_autora where postavy.meno='" + menoPostavicky + "';" );
	         search.next();
	        	 Vector<Object> riadok = new Vector<Object>();
	        	 riadok.add(search.getString("menoAutora"));
	        	 riadok.add(search.getString("idAutora"));
	        	 riadok.add(search.getString("typPostavy"));
	        	 vypln.add(riadok);
	         search.close();
	         vstup.close();
	         c.commit();
			} catch (Exception e) {
		         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
		         System.exit(0);
		         c.rollback();
		    }
		return new DefaultTableModel(vypln, stlpce){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int colum){
				return false;
			}
		};
	}
	
	/**Tvorba v�pisu s��tu post�v*/
	public String postavySpolu() throws SQLException{
		
		String spolu = "0";
		try {
		Statement vstup = c.createStatement();
		ResultSet postavicky = vstup.executeQuery( "SELECT COUNT(postavy) FROM postavy; ");
		postavicky.next(); 
		spolu = postavicky.getString(1);
        vstup.close();
        c.commit();
		} catch (Exception e) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         System.exit(0);
	         c.rollback();
	    }
		
		return spolu;
	}
	
	/**Pripojenie k datab�ze*/
	public static void pripojSa(){
	      try {
	         Class.forName("org.postgresql.Driver");
	         c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Projekt1",
	            "Ivan", "25051993");
	         c.setAutoCommit(false);
	      } catch (Exception e) {
	         e.printStackTrace();
	         System.err.println(e.getClass().getName()+": "+e.getMessage());
	         System.exit(0);
	      }
	}

	public static void main(String[] args) {
		/** Na��ta sa Gui */
		new Gui();
		pripojSa(); //zavolanie pripojenia
	}
}
