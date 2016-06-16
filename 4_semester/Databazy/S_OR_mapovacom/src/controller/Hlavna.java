package controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import model.Komix;
import model.Nakreslenie;
import model.Postava;
import model.Strana;
import model.Umelec;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

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
	

	static SessionFactory sessionFactory;
	
	/**Vr·ti pole stringov pre combobox s postaviËkami*/
	@SuppressWarnings("unchecked")
	public String[] vsetkyPostavicky() throws SQLException{
		Session session = sessionFactory.openSession();
		Transaction ts = null;
		ArrayList<String> celyVypis = new ArrayList<String>();
		List<Postava> postavy = null;
		try {
			ts = session.beginTransaction();
			
			Criteria crit = session.createCriteria(Postava.class);
			crit.addOrder(Order.asc("id_postavy"));
			postavy = crit.list();
			
			ts.commit();
		} catch (Exception e) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         ts.rollback();
	    } finally {
	    	session.close();
	    }
		for (Postava p : postavy){
			celyVypis.add(p.getMeno());
		}
		String[] pole = new String[celyVypis.size()];
		for (int i = 0;i<celyVypis.size();i++)//tvorÌm pole
			pole[i]=celyVypis.get(i);
		
		return pole;
	}
	
	/**Vr·ti pole stringov pre combobox s autormi*/
	@SuppressWarnings("unchecked")
	public String[] vsetciAutori() throws SQLException{
		Session session = sessionFactory.openSession();
		Transaction ts = null;
		ArrayList<String> celyVypis = new ArrayList<String>();
		List<Umelec> umelci = null;
		try {
			ts = session.beginTransaction();
			
			Criteria crit = session.createCriteria(Umelec.class);
			crit.addOrder(Order.asc("id_zamestnanca"));
			umelci = crit.list();
			
			ts.commit();
		} catch (Exception e) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         ts.rollback();
	    } finally {
	    	session.close();
	    }
		for (Umelec u : umelci){
			celyVypis.add(u.getMeno());
		}
		String[] pole = new String[celyVypis.size()];
		for (int i = 0;i<celyVypis.size();i++) //tvorÌm pole
			pole[i]=celyVypis.get(i);
		
		return pole;
	}
	
	/**Vr·ti pole stringov pre combobox s komixami*/
	@SuppressWarnings("unchecked")
	public String[] vsetkyKomixy() throws SQLException{
		Session session = sessionFactory.openSession();
		Transaction ts = null;
		ArrayList<String> celyVypis = new ArrayList<String>();
		List<Komix> komixy = null;
		try {
			ts = session.beginTransaction();
			
			Criteria crit = session.createCriteria(Komix.class);
			crit.addOrder(Order.asc("cislo_komixu"));
			komixy = crit.list();
			
			
			ts.commit();
		} catch (Exception e) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         ts.rollback();
	    } finally {
	    	session.close();
	    }
		for (Komix k : komixy){
			celyVypis.add(k.getNazov());
		}
		String[] pole = new String[celyVypis.size()];
		for (int i = 0;i<celyVypis.size();i++)
			pole[i]=celyVypis.get(i);
		
		return pole;
	}
	
	/**Vr·ti pole stringov pre combobox so stranami ku konkretnemu komixu*/
	@SuppressWarnings("unchecked")
	public String[] vsetkyStrany(String nazovKomixu) throws SQLException{
		ArrayList<String> celyVypis = new ArrayList<String>();
		Session session = sessionFactory.openSession();
		Transaction ts = null;
		List<Strana> strany = null;
		try {
			ts = session.beginTransaction();
			
			Criteria crit = session.createCriteria(Strana.class);
			crit.addOrder(Order.asc("id_strany"));
			Criteria crit2 = crit.createCriteria("komix"); //premenn· komix, nie stÂpec
			crit2.add(Restrictions.eq("nazov", nazovKomixu));
			strany = crit2.list();
			
			ts.commit();
		} catch (Exception e) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         ts.rollback();
	    } finally {
	    	session.close();
	    }
		for (Strana s : strany){
			celyVypis.add(Integer.toString(s.getId_strany()));
		}
		String[] pole = new String[celyVypis.size()];
		for (int i = 0;i<celyVypis.size();i++)
			pole[i]=celyVypis.get(i);
		
		return pole;
	}
	
	/**Insertuje do tabulky pre nakreslenÈ postavy*/
	@SuppressWarnings("unchecked")
	public String insertNakreslenie(String menoPostavy, String idStrany, String cinnost) throws SQLException{
		Session session = sessionFactory.openSession();
		Transaction ts = null;
		Date datum = new Date();
		List<Postava> postavy;
		List<Strana> strany;
		try {
			ts = session.beginTransaction();
			
			Criteria crit = session.createCriteria(Postava.class);
			crit.add(Restrictions.eq("meno", menoPostavy));
			postavy = crit.list();
			
			Criteria crit2 = session.createCriteria(Strana.class);
			crit2.add(Restrictions.eq("id_strany", Integer.parseInt(idStrany)));
			strany = crit2.list();
			
			Nakreslenie nakreslenie = new Nakreslenie();
			nakreslenie.setCinnost(cinnost);
			nakreslenie.setDatum_nakreslenia(datum);
			nakreslenie.setPostava(postavy.get(0));
			nakreslenie.setStrana(strany.get(0));
			
			strany.get(0).getNakreslenia().add(nakreslenie);
			postavy.get(0).getNakreslenia().add(nakreslenie);
			
			
			session.update(postavy.get(0));
			session.update(strany.get(0));
			
			session.save(nakreslenie);
			
			
			
			
			ts.commit();
		} catch (Exception e) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         ts.rollback();
	    } finally {
	    	session.close();
	    }
		return "PostaviËka " + menoPostavy + " bola ˙speöne nakreslen· a jej Ëinnosù je: '" + cinnost + "'\n";
	}
	
	
	/**Insertujem do tabuæky komixov*/
	public String insertKomix(String nazov) throws SQLException{
		Session session = sessionFactory.openSession();
		Transaction ts = null;
		Date datum = new Date();
		try {
			ts = session.beginTransaction();
			
			Komix komix = new Komix();
			komix.setNazov(nazov);
			komix.setDatum_vytvorenia(datum);
			
			session.save(komix);
			
			ts.commit();
		} catch (Exception e) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         ts.rollback();
	    } finally {
	    	session.close();
	    }
		return "Komix " + nazov + " bol ˙speöne vytvoren˝ \n";
	}
	
	/**Insertujem do tabuæky umelcov*/
	public String insertUmelec(String meno) throws SQLException{
		Session session = sessionFactory.openSession();
		Transaction ts = null;
		Date datum = new Date();
		try {
			ts = session.beginTransaction();
			
			Umelec umelec = new Umelec();
			umelec.setMeno(meno);
			umelec.setZamestnany_od(datum);
			
			session.save(umelec);
			
			ts.commit();
		} catch (Exception e) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         ts.rollback();
	    } finally {
	    	session.close();
	    }
		return "Autor " + meno + " bol ˙speöne pridan˝\n";
	}
	
	/**Insertujem do tabuæky postaviËiek*/
	@SuppressWarnings("unchecked")
	public String insertPostavu(String meno, String typ, boolean muz, String menoAutora) throws SQLException{
		Session session = sessionFactory.openSession();
		Transaction ts = null;
		List<Umelec> umelci;
		try {
			ts = session.beginTransaction();
			
			Criteria crit = session.createCriteria(Umelec.class);
			crit.add(Restrictions.eq("meno", menoAutora));
			umelci = crit.list();
			
			Postava postava = new Postava();
			postava.setMeno(meno);
			postava.setMuz(muz);
			postava.setTyp(typ);
			postava.setUmelec(umelci.get(0));
			
			umelci.get(0).getPostavy().add(postava);
			
			session.save(postava);
			
			ts.commit();
		} catch (Exception e) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         ts.rollback();
	    } finally {
	    	session.close();
	    }

		return "Postava " + meno + " bola ˙speöne vytvoren· \n";
	}
	
	/**Insertujem do tabuæky komixov*/
	@SuppressWarnings("unchecked")
	public String insertStrana(String nazov, String situacia) throws SQLException{
		Session session = sessionFactory.openSession();
		Transaction ts = null;
		List<Komix> komixy;
		try {
			ts = session.beginTransaction();
			
			Criteria crit = session.createCriteria(Komix.class);
			crit.add(Restrictions.eq("nazov", nazov));
			komixy = crit.list();
			
			Strana strana = new Strana();
			strana.setKomix(komixy.get(0));
			strana.setSituacia(situacia);
			
			komixy.get(0).getStrany().add(strana);
			
			session.save(strana);
			
			ts.commit();
		} catch (Exception e) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         ts.rollback();
	    } finally {
	    	session.close();
	    }
		return "Strana bola pridan· do komixu s n·zvom " + nazov + "\n";
	}
	
	
	/**ZmenÌm/updatnem n·zov komixu*/
	@SuppressWarnings("unchecked")
	public String updateNazov(String nazov, String povodnyNazov) throws SQLException{
		Session session = sessionFactory.openSession();
		Transaction ts = null;
		List<Komix> komixy;
		try {
			ts = session.beginTransaction();
			
			Criteria crit = session.createCriteria(Komix.class);
			crit.add(Restrictions.eq("nazov", povodnyNazov));
			komixy = crit.list();
			
			komixy.get(0).setNazov(nazov); //update netreba
			
			ts.commit();
		} catch (Exception e) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         ts.rollback();
	    } finally {
	    	session.close();
	    }
		return "Nov˝ n·zov vybranÈho komixu(" + povodnyNazov + ") je: " + nazov + "\n";
	}
	
	/**Deletnem komix*/
	@SuppressWarnings("unchecked")
	public String deleteKomix(String nazov) throws SQLException{
		Session session = sessionFactory.openSession();
		Transaction ts = null;
		List<Komix> komixy;
		try {
			ts = session.beginTransaction();
			
			Criteria crit = session.createCriteria(Komix.class);
			crit.add(Restrictions.eq("nazov", nazov));
			komixy = crit.list();
			
			session.delete(komixy.get(0));
			ts.commit();
		} catch (Exception e) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         ts.rollback();
	    } finally {
	    	session.close();
	    }
		return "Komix s n·zvom " + nazov + " bol vymazan˝\n";
	}
	
	/**Deletnem autora a jeho postavy presuniem druhemu*/
	@SuppressWarnings("unchecked")
	public String deleteAutor(String meno, String nahradnik) throws SQLException{
		Session session = sessionFactory.openSession();
		Transaction ts = null;
		List<Umelec> umelci = null;
		List<Umelec> umelciNahradnici = null;
		try {
			ts = session.beginTransaction();
			
			Criteria crit = session.createCriteria(Umelec.class);
			crit.add(Restrictions.eq("meno", meno));
			umelci = crit.list();
			
			Criteria crit2 = session.createCriteria(Umelec.class);
			crit2.add(Restrictions.eq("meno", nahradnik));
			umelciNahradnici = crit2.list();
			
			
			for (Postava p : umelci.get(0).getPostavy()){
				Postava postava = p;
				Umelec umelecN = umelciNahradnici.get(0);
				p.setUmelec(umelecN);
				umelciNahradnici.get(0).getPostavy().add(postava);
			}
			
			session.delete(umelci.get(0));
			
	         ts.commit();
		} catch (Exception e) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         ts.rollback();
	    } finally {
	    	session.close();
	    }
		return "Vyhoden˝ bol autor s menom " + meno + "\n";
	}
	
	/**Najdem autora postaviËky*/
	@SuppressWarnings("unchecked")
	public String searchAutor(String menoPostavicky) throws SQLException{
		Session session = sessionFactory.openSession();
		Transaction ts = null;
		StringBuilder menoAutora = new StringBuilder();
		List<Umelec> umelci = null;
		try {
			ts = session.beginTransaction();
			
			Criteria crit = session.createCriteria(Umelec.class);
			Criteria crit2 = crit.createCriteria("postavy"); //premenn· komix, nie stÂpec
			crit2.add(Restrictions.eq("meno", menoPostavicky));
			umelci = crit2.list();
			
			menoAutora.append(umelci.get(0).getMeno());
			
			ts.commit();
		} catch (Exception e) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         ts.rollback();
	    } finally {
	    	session.close();
	    }
		return "PostaviËku menom " + menoPostavicky +  " kreslÌ " + menoAutora.toString() + "\n";
	}
	
	/**Tvorba tabulky nakreslenÌ*/
	@SuppressWarnings("unchecked")
	public DefaultTableModel spravTabulkuNakreslenia(String komix) throws SQLException{
		final Vector<String> stlpce = new Vector<String>();
		Vector<Vector<Object>> vypln = new Vector<Vector<Object>>();
		stlpce.add("PostaviËka");
		stlpce.add("ID strany");
		stlpce.add("D·tum nakreslenia");
		stlpce.add("»o robÌ?");
		Session session = sessionFactory.openSession();
		Transaction ts = null;
		List<Strana> strany = null;
		
		try {
			ts = session.beginTransaction();
			
			Criteria crit = session.createCriteria(Strana.class);
			crit.addOrder(Order.asc("id_strany"));
			Criteria crit2 = crit.createCriteria("komix"); //premenn· komix, nie stÂpec
			crit2.add(Restrictions.eq("nazov", komix));
			strany = crit2.list();
			
			for (Strana s : strany){
				for (Nakreslenie n : s.getNakreslenia()){
						Vector<Object> riadok = new Vector<Object>();
						riadok.add(n.getPostava().getMeno());
						riadok.add(n.getStrana().getId_strany());
						riadok.add(n.getDatum_nakreslenia());
						riadok.add(n.getCinnost());
						vypln.add(riadok);
				}

				
			}
	         ts.commit();
			} catch (Exception e) {
		         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
		         ts.rollback();
		    } finally {
		    	session.close();
		    }
		return new DefaultTableModel(vypln, stlpce){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int colum){
				return false;
			}
		};
	}
	
	/**Tvorba tabulky komixov*/
	@SuppressWarnings("unchecked")
	public DefaultTableModel spravTabulkuKomixy() throws SQLException{
		final Vector<String> stlpce = new Vector<String>();
		Vector<Vector<Object>> vypln = new Vector<Vector<Object>>();
		stlpce.add("Nazov");
		stlpce.add("»Ìslo");
		stlpce.add("D·tum vytvorenia");
		Session session = sessionFactory.openSession();
		Transaction ts = null;
		List<Komix> komixy;
		try {
			ts = session.beginTransaction();
			
			Criteria crit = session.createCriteria(Komix.class);
			crit.addOrder(Order.asc("cislo_komixu"));
			komixy = crit.list();
			
			for (Komix p : komixy){
				Vector<Object> riadok = new Vector<Object>();
				riadok.add(p.getNazov());
	        	riadok.add(p.getCislo_komixu());
	        	riadok.add(p.getDatum_vytvorenia());
	        	vypln.add(riadok);
			}
	         ts.commit();
			} catch (Exception e) {
		         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
		         ts.rollback();
		    } finally {
		    	session.close();
		    }
		return new DefaultTableModel(vypln, stlpce){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int colum){
				return false;
			}
		};
	}
	
	/**Tvorba tabulky autorov, bez post·v*/
	@SuppressWarnings("unchecked")
	public DefaultTableModel spravTabulkuAutoriSimple() throws SQLException{
		Vector<String> stlpce = new Vector<String>();
		Vector<Vector<Object>> vypln = new Vector<Vector<Object>>();
		stlpce.add("Meno");
		stlpce.add("ID");
		stlpce.add("Zamestnan˝ od");
		Session session = sessionFactory.openSession();
		Transaction ts = null;
		List<Umelec> umelci;
		try {
			ts = session.beginTransaction();
			
			Criteria crit = session.createCriteria(Umelec.class);
			crit.addOrder(Order.asc("id_zamestnanca"));
			umelci = crit.list();
			
			for (Umelec p : umelci){
				Vector<Object> riadok = new Vector<Object>();
				riadok.add(p.getMeno());
	        	riadok.add(p.getId_zamestnanca());
	        	riadok.add(p.getZamestnany_od());
	        	vypln.add(riadok);
			}
	         ts.commit();
			} catch (Exception e) {
		         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
		         ts.rollback();
		    } finally {
		    	session.close();
		    }
		return new DefaultTableModel(vypln, stlpce){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int colum){
				return false;
			}
		};
	}
	
	/**Tvorba tabulky autorov, s poËtom post·v*/
	@SuppressWarnings("unchecked")
	public DefaultTableModel spravTabulkuAutori() throws SQLException{
		Vector<String> stlpce = new Vector<String>();
		Vector<Vector<Object>> vypln = new Vector<Vector<Object>>();
		stlpce.add("Meno");
		stlpce.add("ID");
		stlpce.add("Zamestnan˝ od");
		stlpce.add("KreslÌ post·v");
		Session session = sessionFactory.openSession();
		Transaction ts = null;
		List<Umelec> umelci;
		try {
			ts = session.beginTransaction();
			
			Criteria crit = session.createCriteria(Umelec.class);
			crit.addOrder(Order.asc("id_zamestnanca"));
			umelci = crit.list();
			
			for (Umelec u : umelci) {
	        	 Vector<Object> riadok = new Vector<Object>();
	        	 riadok.add(u.getMeno());
	        	 riadok.add(u.getId_zamestnanca());
	        	 riadok.add(u.getZamestnany_od());
	        	 riadok.add(u.getPostavy().size());
	        	 vypln.add(riadok);
	         }
			ts.commit();
			} catch (Exception e) {
		         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
		         ts.rollback();
		    } finally {
		    	session.close();
		    }
		return new DefaultTableModel(vypln, stlpce){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int colum){
				return false;
			}
		};
	}
	
	/**Tvorba tabulky autorov s poËtom post·v muûov/ûien*/
	@SuppressWarnings("unchecked")
	public DefaultTableModel autorPostavyCountMZ(boolean a) throws SQLException{
		Vector<String> stlpce = new Vector<String>();
		Vector<Vector<Object>> vypln = new Vector<Vector<Object>>();
		stlpce.add("Meno");
		stlpce.add("PoËet post·v");
		Session session = sessionFactory.openSession();
		Transaction ts = null;
		List<Umelec> umelci;
		int pocet = 0;
		try {
			ts = session.beginTransaction();
			
			Criteria crit = session.createCriteria(Umelec.class);
			crit.addOrder(Order.asc("id_zamestnanca"));
			umelci = crit.list();
			
			for (Umelec u : umelci) {
				pocet = 0;
				for (Postava p : u.getPostavy()) {
					if (p.isMuz() == a) pocet++;
				}
	        	 Vector<Object> riadok = new Vector<Object>();
	        	 riadok.add(u.getMeno());
	        	 riadok.add(pocet);
	        	 vypln.add(riadok);
	         }
			ts.commit();
			} catch (Exception e) {
		         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
		         ts.rollback();
		    } finally {
		    	session.close();
		    }
		return new DefaultTableModel(vypln, stlpce){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int colum){
				return false;
			}
		};
	}
	
	/**Tvorba tabulky post·v*/
	@SuppressWarnings("unchecked")
	public DefaultTableModel spravTabulkuPostav() throws SQLException{
		Vector<String> stlpce = new Vector<String>();
		Vector<Vector<Object>> vypln = new Vector<Vector<Object>>();
		stlpce.add("Meno postavy");
		stlpce.add("ID postavy");
		stlpce.add("Typ");
		stlpce.add("Muû?");
		stlpce.add("Meno autora");
		Session session = sessionFactory.openSession();
		Transaction ts = null;
		List<Postava> postavy;
		try {
			ts = session.beginTransaction();
			
			Criteria crit = session.createCriteria(Postava.class);
			crit.addOrder(Order.asc("id_postavy"));
			postavy = crit.list();
			
			for (Postava p : postavy){
				Vector<Object> riadok = new Vector<Object>();
				riadok.add(p.getMeno());
	        	riadok.add(p.getId_postavy());
	        	riadok.add(p.getTyp());
	        	riadok.add(p.isMuz());
	        	riadok.add(p.getUmelec().getMeno());	        	
	        	vypln.add(riadok);
			}
	         ts.commit();
			} catch (Exception e) {
		         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
		         ts.rollback();
		    } finally {
		    	session.close();
		    }
		return new DefaultTableModel(vypln, stlpce){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int colum){
				return false;
			}
		};
		
	}
	
	/**Tvorba tabulky pre search autora postaviËky*/
	@SuppressWarnings("unchecked")
	public DefaultTableModel spravTabulkuSearchAutor(String menoPostavicky) throws SQLException{
		Vector<String> stlpce = new Vector<String>();
		Vector<Vector<Object>> vypln = new Vector<Vector<Object>>();
		stlpce.add("Meno autora");
		stlpce.add("ID autora");
		stlpce.add("Typ postaviËky");	
		Session session = sessionFactory.openSession();
		Transaction ts = null;
		List<Postava> postavy = null;
		
		try {
			ts = session.beginTransaction();
			
			Criteria crit = session.createCriteria(Postava.class);
			crit.add(Restrictions.eq("meno", menoPostavicky));
			postavy = crit.list();
			
			Vector<Object> riadok = new Vector<Object>();
			riadok.add(postavy.get(0).getUmelec().getMeno());
			riadok.add(postavy.get(0).getUmelec().getId_zamestnanca());
			riadok.add(postavy.get(0).getTyp());
			vypln.add(riadok);
			
	         ts.commit();
			} catch (Exception e) {
		         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
		         ts.rollback();
		    } finally {
		    	session.close();
		    }
		return new DefaultTableModel(vypln, stlpce){
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int colum){
				return false;
			}
		};
	}
	
	/**Tvorba v˝pisu s˙Ëtu post·v*/
	@SuppressWarnings("unchecked")
	public String postavySpolu() throws SQLException{
		int spolu = 0;
		Session session = sessionFactory.openSession();
		Transaction ts = null;
		try {
			ts = session.beginTransaction();
			Criteria crit = session.createCriteria(Postava.class);
			List<Postava> postavy = crit.list();
		    spolu = postavy.size();
        ts.commit();
		} catch (Exception e) {
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         ts.rollback();
	    } finally {
	    	session.close();
	    }
		
		return Integer.toString(spolu);
	}
	
	/**Pripojenie k datab·ze*/
	public static void pripojSa(){
		try {
        	Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                    configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        catch (Throwable ex) {
            System.err.println("Zlyhalo vytvorenie sessionFactory." + ex);
            throw new ExceptionInInitializerError(ex);
        }
		
	}

	public static void main(String[] args) {
		/** NaËÌta sa Gui */
		new Gui();
		pripojSa(); //zavolanie pripojenia
	}
}
