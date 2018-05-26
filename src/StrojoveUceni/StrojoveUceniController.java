package StrojoveUceni;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import StrojoveUceni.Logika.Aplikace;
import StrojoveUceni.Logika.Slovo;
import StrojoveUceni.Logika.Uzivatel;
import StrojoveUceni.Logika.Veta;
import StrojoveUceni.Logika.ZaznamOdpovedi;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import java.sql.*;


public class StrojoveUceniController extends GridPane implements Observer {
	
	@FXML
	private JFXTextField vstupniSlovo;
	@FXML
	private JFXTextField vstupniVeta;
	@FXML
	private JFXTextField vyznamSlova;
	@FXML
	private JFXTextArea vystup;
	@FXML
	private JFXListView<String> seznamSlov;
	@FXML
	private ImageView uzivatel;
	@FXML
	private JFXHamburger hamburger;
	@FXML
	private JFXButton Napoveda;
	@FXML
	private JFXButton ZmenaVzhledu;
	@FXML
	private JFXButton KonecAplikace;
	@FXML
	JFXDrawer drawer;
	@FXML
	GridPane scene;
	@FXML
	StackPane dialog;
	
	private Slovo slovo = new Slovo();
	private Uzivatel uzivatelDatabaze = new Uzivatel();
	private Veta veta = new Veta();
	private ZaznamOdpovedi zaznamOdpovedi = new ZaznamOdpovedi();
	
	private int pocetSlovVety = 0;
	private int aktualniSlovo = 0;
	private int aktualniSlovoOffSet = 0;
	private int pocetVet = 0;
	private int aktualniVeta = 0;
	private int maxSlovo = 0;
	private int maxVeta = 0;
	private int maxZaznam = 0;
	private int maxUzivatel = 0;
	private int startIDSlovo = 0;
	private int endIDSlovo = 0;
	private List<String> vyznam = new ArrayList<String>();

	//private IAplikace hra;
	
	// JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    static final String DB_URL = "jdbc:mariadb://85.70.181.102:3306/StrojoveUceni";

    //  Database credentials
    static final String USER = "StrojoveUceni";
    static final String PASS = "StrojoveUceni";

    /**
     *  Provádí načetní prvotního slova, jeho významů a věty z databáze
     *  Tyto hodnoty pak načte do frontendu
     *  @author     Martin Veselý, Adam Novák
     *  @version    2.0
     *  @created    květen 2018
     *
     */
    public void pripojDatabzi() {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("org.mariadb.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(
                    "jdbc:mariadb://85.70.181.102:3306/StrojoveUceni", "StrojoveUceni", "StrojoveUceni");
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Selecting first word from table...");
            stmt = conn.createStatement();

            //dotazy pro nacteny slov, vet atd.
            String sql = "SELECT Slovo FROM `Slovo` where ID_Slovo = 0";
            String sql2 = "SELECT ID_Veta FROM `Slovo` where ID_Slovo = 0";
            String sql3 = "SELECT Veta FROM `Veta` where ID_Veta = " + slovo.getVeta_ID();
            String sql4 = "SELECT Vyznam FROM `ZaznamOdpovedi` WHERE ID_Slovo = 0";
            
            //dotazy pro nacteni MAX hodnot ID atd.
            String sql5 = "SELECT MAX(ID_Slovo) FROM `Slovo`";
            String sql6 = "SELECT MAX(ID_Uzivatel) FROM `Uzivatel`";
            String sql7 = "SELECT MAX(ID_Veta) FROM `Veta`";
            String sql8 = "SELECT MAX(ID_Zaznam) FROM `ZaznamOdpovedi`";
            String sql9 = "SELECT COUNT(ID_Slovo) FROM `Slovo` WHERE ID_Veta = " + slovo.getVeta_ID();
            String sql10 = "SELECT COUNT(ID_Veta) FROM `Veta`";
            slovo.setID_Slovo(0);

            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()) {
                slovo.setSlovo(rs.getString(1));
            }
            
            rs = stmt.executeQuery(sql2);
            if(rs.next()) {
                slovo.setVeta_ID(rs.getInt(1));
            }
            
            rs = stmt.executeQuery(sql3);
            if(rs.next()) {
                veta.setVeta(rs.getString(1));
            }
            
            rs = stmt.executeQuery(sql4);
            while(rs.next()) {
                vyznam.add(rs.getString(1));
            }
            
            rs = stmt.executeQuery(sql5);
            if(rs.next()) {
                maxSlovo = rs.getInt(1);
            }
            
            rs = stmt.executeQuery(sql6);
            if(rs.next()) {
                maxUzivatel = rs.getInt(1);
            }
            
            rs = stmt.executeQuery(sql7);
            if(rs.next()) {
                maxVeta = rs.getInt(1);
            }
            
            rs = stmt.executeQuery(sql8);
            if(rs.next()) {
                maxZaznam = rs.getInt(1);
            }
            
            rs = stmt.executeQuery(sql9);
            if(rs.next()) {
                pocetSlovVety = rs.getInt(1);
            }
            
            rs = stmt.executeQuery(sql10);
            if(rs.next()) {
                pocetVet = rs.getInt(1);
            }
            
            veta.setID_Veta(slovo.getVeta_ID());
            endIDSlovo = pocetSlovVety - 1;
            
            System.out.println("Word selected...");
            System.out.println(slovo.getSlovo());
            System.out.println(slovo.getVeta_ID());
            System.out.println(veta.getVeta());
            System.out.println(veta.getID_Veta());
            for (int i = 0; i <= vyznam.size() - 1; i++) {
            	System.out.println(vyznam.get(i));
            }
            System.out.println("pocetSlovVety: " + pocetSlovVety);
            System.out.println("aktualniSlovo: " + aktualniSlovo);
            System.out.println("pocetVet: " + pocetVet);
            System.out.println("aktualniVeta: " + aktualniVeta);
            System.out.println("maxSlovo: " + maxSlovo);
            System.out.println("maxVeta: " + maxVeta);
            System.out.println("maxZaznam: " + maxZaznam);
            System.out.println("maxUzivatel: " + maxUzivatel);
            
            
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }//end pripojDatabzi
	
    /**
     *  Metoda načítá z databáze další slovo, případně vetu a načte jí do frontendu
     *  @author     Martin Veselý, Adam Novák
     *  @version    2.0
     *  @created    květen 2018
     *
     */
    public void dalsi() {
		if (vyznamSlova.getText() == null | vyznamSlova.getText().length() == 0) {
			vystup.appendText("\nSlovo " + "'" + vstupniSlovo.getText() +"'" + " ve větě: '" + vstupniVeta.getText() +" bylo přeskočeno.\n\n");
		}
		else {
		//zadani textu zadaneho slova do historie prikazu
		//vystup.appendText("\nSlovo " + vstupniSlovo.getText() + " ve větě: " + vstupniVeta.getText() +" znamená " + vyznamSlova.getText() + "\n\n");
		vystup.appendText("\nSlovo " + "'" + vstupniSlovo.getText() +"'" + " ve větě: '" + vstupniVeta.getText() +"' znamená '" + vyznamSlova.getText() + "'\n\n");
		}// end podminky
		//pripojeni k databazi
		Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("org.mariadb.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(
                    "jdbc:mariadb://85.70.181.102:3306/StrojoveUceni", "StrojoveUceni", "StrojoveUceni");
            System.out.println("Connected database successfully...");
		
        //bloky rozhodujici o tom, co se bude nacitat do pameti    
		if (aktualniSlovoOffSet <= (pocetSlovVety -2) && aktualniSlovo <= (maxSlovo -1)) {
			//Execute a query
            stmt = conn.createStatement();
            String sql = "SELECT Slovo FROM `Slovo` where ID_Slovo = " + (slovo.getID_Slovo() + 1);
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()) {
                slovo.setSlovo(rs.getString(1));
            }
            
            sql = "SELECT Vyznam FROM `ZaznamOdpovedi` WHERE ID_Slovo = " + (slovo.getID_Slovo() + 1);
            rs = stmt.executeQuery(sql);
            vyznam = new ArrayList<String>();
            while(rs.next()) {
                vyznam.add(rs.getString(1));
            }
            //vyznam = new ArrayList<String>();
            seznamSlov.getItems().clear();
            seznamSlov.getItems().addAll(vyznam);
            
            aktualniSlovo ++;
            aktualniSlovoOffSet ++;
            slovo.setID_Slovo(aktualniSlovo);
            //zobrazeni slova ve frontendu
            vstupniSlovo.setText(slovo.getSlovo());
            vyznamSlova.setText("");
            
            System.out.println("pocetSlovVety: " + pocetSlovVety);
            System.out.println("aktualniSlovo: " + aktualniSlovo);
            System.out.println("aktualniSlovoOffSet: " + aktualniSlovoOffSet);
            System.out.println("aktualniVeta: " + aktualniVeta);            
		}
		else {
			if (aktualniVeta <= maxVeta -1) {
				stmt = conn.createStatement();
	            String sql = "SELECT Veta FROM `Veta` where ID_Veta = " + (veta.getID_Veta() + 1);
	            ResultSet rs = stmt.executeQuery(sql);
	            if(rs.next()) {
	                veta.setVeta(rs.getString(1));
	            }
	            veta.setID_Veta(veta.getID_Veta() + 1);
	            vstupniVeta.setText(veta.getVeta());
	            aktualniVeta ++;
	            
	            aktualniSlovo++;
	            aktualniSlovoOffSet = 0;
	            slovo.setID_Slovo(aktualniSlovo);
	            sql = "SELECT Slovo FROM `Slovo` where ID_Slovo = " + (slovo.getID_Slovo());
	            rs = stmt.executeQuery(sql);
	            if(rs.next()) {
	                slovo.setSlovo(rs.getString(1));
	            }
	            sql = "SELECT ID_Veta FROM `Slovo` where ID_Slovo = " + (slovo.getID_Slovo());
	            rs = stmt.executeQuery(sql);
	            if(rs.next()) {
	                slovo.setVeta_ID(rs.getInt(1));
	            }
	            vstupniSlovo.setText(slovo.getSlovo());
	            
	            sql = "SELECT COUNT(ID_Slovo) FROM `Slovo` WHERE ID_Veta = " + slovo.getVeta_ID();
	            rs = stmt.executeQuery(sql);
	            if(rs.next()) {
	                pocetSlovVety = rs.getInt(1);
	            }
	            
	            sql = "SELECT Vyznam FROM `ZaznamOdpovedi` WHERE ID_Slovo = " + (slovo.getID_Slovo());
	            rs = stmt.executeQuery(sql);
	            vyznam = new ArrayList<String>();
	            while(rs.next()) {
	                vyznam.add(rs.getString(1));
	            }
	            //vyznam = new ArrayList<String>();
	            seznamSlov.getItems().clear();
	            seznamSlov.getItems().addAll(vyznam);
	            	            
	            startIDSlovo = slovo.getID_Slovo();
	            endIDSlovo = pocetSlovVety - 1;	
	            vyznamSlova.setText("");
	            
	            System.out.println("pocetSlovVety: " + pocetSlovVety);
	            System.out.println("aktualniSlovo: " + aktualniSlovo);	
	            System.out.println("aktualniSlovoOffSet: " + aktualniSlovoOffSet);
	            System.out.println("aktualniVeta: " + aktualniVeta);	            
			}
			else {
				stmt = conn.createStatement();

	            //dotazy pro nacteny slov, vet atd.
	            String sql = "SELECT Slovo FROM `Slovo` where ID_Slovo = 0";
	            String sql2 = "SELECT ID_Veta FROM `Slovo` where ID_Slovo = 0";
	            String sql3 = "SELECT Veta FROM `Veta` where ID_Veta = 0";
	            String sql4 = "SELECT Vyznam FROM `ZaznamOdpovedi` WHERE ID_Slovo = 0";
	            
	            //dotazy pro nacteni MAX hodnot ID atd.
	            String sql5 = "SELECT MAX(ID_Slovo) FROM `Slovo`";
	            String sql6 = "SELECT MAX(ID_Uzivatel) FROM `Uzivatel`";
	            String sql7 = "SELECT MAX(ID_Veta) FROM `Veta`";
	            String sql8 = "SELECT MAX(ID_Zaznam) FROM `ZaznamOdpovedi`";
	            String sql9 = "SELECT COUNT(ID_Slovo) FROM `Slovo` WHERE ID_Veta = " + slovo.getVeta_ID();
	            String sql10 = "SELECT COUNT(ID_Veta) FROM `Veta`";
	            slovo.setID_Slovo(0);
	            aktualniSlovo = 0;
	            aktualniVeta = 0;
	            startIDSlovo = 0;
	            aktualniSlovoOffSet = 0;

	            ResultSet rs = stmt.executeQuery(sql);
	            if(rs.next()) {
	                slovo.setSlovo(rs.getString(1));
	            }
	            
	            rs = stmt.executeQuery(sql2);
	            if(rs.next()) {
	                slovo.setVeta_ID(rs.getInt(1));
	            }
	            
	            rs = stmt.executeQuery(sql3);
	            if(rs.next()) {
	                veta.setVeta(rs.getString(1));
	            }
	            
	            rs = stmt.executeQuery(sql4);
	            vyznam = new ArrayList<String>();
	            while(rs.next()) {
	                vyznam.add(rs.getString(1));
	            }
	            //vyznam = new ArrayList<String>();
	            seznamSlov.getItems().clear();
	            seznamSlov.getItems().addAll(vyznam);
	            
	            rs = stmt.executeQuery(sql5);
	            if(rs.next()) {
	                maxSlovo = rs.getInt(1);
	            }
	            
	            rs = stmt.executeQuery(sql6);
	            if(rs.next()) {
	                maxUzivatel = rs.getInt(1);
	            }
	            
	            rs = stmt.executeQuery(sql7);
	            if(rs.next()) {
	                maxVeta = rs.getInt(1);
	            }
	            
	            rs = stmt.executeQuery(sql8);
	            if(rs.next()) {
	                maxZaznam = rs.getInt(1);
	            }
	            
	            rs = stmt.executeQuery(sql9);
	            if(rs.next()) {
	                pocetSlovVety = rs.getInt(1);
	            }
	            
	            rs = stmt.executeQuery(sql10);
	            if(rs.next()) {
	                pocetVet = rs.getInt(1);
	            }
	            
	            veta.setID_Veta(slovo.getVeta_ID());
	            endIDSlovo = pocetSlovVety - 1;
	            
	            vstupniSlovo.setText(slovo.getSlovo());
	            vstupniVeta.setText(veta.getVeta());
	            vyznamSlova.setText("");
	            
	            System.out.println("pocetSlovVety: " + pocetSlovVety);
	            System.out.println("aktualniSlovo: " + aktualniSlovo);	 
	            System.out.println("aktualniSlovoOffSet: " + aktualniSlovoOffSet);
	            System.out.println("aktualniVeta: " + aktualniVeta);	            
			}
		}
		
		//blok pro ukonceni pripojeni
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }//end pripojDatabazi
	
    /**
     *  Porovnává význam slova zadaný uživatelem s již existujícími význami tohoto slova
     *  v databázi. 
     *  @author     Martin Veselý
     *  @version    2.0
     *  @created    květen 2018
     *
     *@param parametry - jako  parametr obsahuje text uživatelem zadaného významu
     *@return true / false podle toho, zdali uživatelem zadaný váznam již existuje v dazabízi
     */
	public boolean vyznamExistuje(String vstupniText) {
		String testovaciVypis = "";
		for (int i = 0; i <= vyznam.size() - 1; i++) {
			testovaciVypis = vyznam.get(i);
			System.out.println("vyznam.get(i): " + vyznam.get(i));
			System.out.println("vstupniText: " + vstupniText);
			
        	if (vyznam.get(i).equals(vstupniText)) {
        		System.out.println("vyznam existuje");
        		System.out.println("vyznam.get(i): " + vyznam.get(i));
    			System.out.println("vstupniText: " + vstupniText);
        		return true;
        	}
        }
		System.out.println("vyznam neexistuje");
		System.out.println("vyznam.get(i): " + testovaciVypis);
		System.out.println("vstupniText: " + vstupniText);
		return false;
	}
	
	/**
     *  Ukládá nový význam daného slova do databáze a poté volá metodu dalsi()
     *  @author     Martin Veselý
     *  @version    2.0
     *  @created    květen 2018
     */
	public void ulozVyznam() {
		Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("org.mariadb.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(
                    "jdbc:mariadb://85.70.181.102:3306/StrojoveUceni", "StrojoveUceni", "StrojoveUceni");
            System.out.println("Connected database successfully...");
            
            //STEP 4: Execute a query
            stmt = conn.createStatement();
            if (vyznamSlova.getText() == null | vyznamSlova.getText().length() == 0) {
    			//nic nenahrávat
    		}
            else {
            String sql = "INSERT INTO ZaznamOdpovedi (ID_Zaznam, ID_Uzivatel, ID_Slovo, ID_Veta, Vyznam) VALUES (" + (maxZaznam + 1) + ", " + maxUzivatel + ", " + (aktualniSlovo) + ", " + (aktualniVeta) + ", " + "'" + (vyznamSlova.getText() + "'") + ")";
            System.out.println(sql);
            stmt.executeQuery(sql);
            
            //inkrementace max hodnot
            maxZaznam++;
            
            System.out.println("vyznam ulozen");
            
            //nacteni dalsiho slova/vety
            //this.dalsi();
            }
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    conn.close();
                }
            } catch (SQLException se) {
            }// do nothing
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
        
        //nacteni dalsiho slova/vety
        this.dalsi();
    }
	
	
	
	/**
	 * metoda čte příkaz ze vstupního textového. Pokud už daný význam existuje, volá metodu dalsi(),
	 * pokud ne, vola metodu ulozVyznam()
	 *  @author     Martin Veselý
     *  @version    2.0
     *  @created    květen 2018
	 */
	@FXML
	public void odesliVyznam() {
		if (this.vyznamExistuje(vyznamSlova.getText())) {
			this.dalsi();
		}
		else {
			this.ulozVyznam();
		}
	}

	/**
	 * metoda pro výběr již existujícího významu
	 * pokud není výběr nulový, zavolá metodu dalsi()
	 *  @author     Martin Veselý, Adam Novák
     *  @version    2.0
     *  @created    květen 2018
	 */
	@FXML
	public void Vyber() {
		/* Zpracovává příkaz při kliknutí na kontextové menu vyber */
		String co = seznamSlov.getSelectionModel().getSelectedItem();
		/* Kontroluje zda, je označený item, který se má sebrat */
		if (co == null) {
			/*Není co vybrat*/
		} else {
			vyznamSlova.setText(co);
			this.dalsi();
		}
	}
	
	
	/**
	 * metoda pro zmenu barevného schématu
	 *  @author     Adam Novák
     *  @version    2.0
     *  @created    květen 2018
	 */
	@FXML
	public void ZmenaSchema() {
		/* Stisknutí tlačítka Změna vzhledu */
		/* Převeď StackPane do popředí */
		dialog.toFront();
		/* Nastav nový dialog */
		JFXDialogLayout obsah = new JFXDialogLayout();
		obsah.setHeading(new Text("Výběr vzhledu"));
		obsah.setBody(new Text("K výběru je z následujících následujících barevných schémat"));
		JFXDialog vyber = new JFXDialog(dialog, obsah, JFXDialog.DialogTransition.CENTER);
		/* Nastav tlačítko 1 */
		JFXButton Schéma1 = new JFXButton("Červené");
		Schéma1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				vyber.close();
				String css = Main.class.getResource("css/scene.css").toExternalForm();
				try {
					ZmenaCSS(css);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		/* Nastav tlačítko 2 */
		JFXButton Schéma2 = new JFXButton("Šedé");
		Schéma2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				vyber.close();
				String css = Main.class.getResource("css/scene2.css").toExternalForm();
				try {
					ZmenaCSS(css);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		/* Nastav tlačítko 3 */
		JFXButton Zrušit = new JFXButton("Zrušit");
		Zrušit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				vyber.close();
			}
		});
		/* Nastav akce při otevření a zavření dialogu */
		vyber.setOnDialogOpened(event -> {
			/* Při otevření převeď StackPane do popředí */
			dialog.toFront();
		});
		vyber.setOnDialogClosed(event -> {
			/* Při zavření převeď StackPane do pozadí */
			dialog.toBack();
		});
		/* Přidej tlačítka do dialogu */
		obsah.setActions(Schéma1, Schéma2, Zrušit);
		/* Zobraz dialog */
		vyber.show();
	}

	
	private void ZmenaCSS(String css) throws IOException {
		scene.getStylesheets().clear();
		scene.getStylesheets().add(css);
		scene.applyCss();
	}


	/**
	 * Metoda souží pro předání objektu se spuštěnou hrou kontroleru a zobrazí
	 * první slovo, jeho váznam a větu z databáze
	 *  @author     Martin Veselý, Adam Novák
     *  @version    2.0
     *  @created    květen 2018
	 * 
	 * @param objekt
	 *            spuštěné hry
	 */
	public void inicializuj(Aplikace aplikace) {
		//nacteni prvniho slova, jeho vety a jeho vyznamu do objedktu
		this.pripojDatabzi();
		
		/* Zavři menu */
		drawer.open();
		/* Nastav log */
		vystup.setEditable(false);
		/* Nastav editovatelnost vstupních polí */
		//vstupniSlovo.setEditable(false);
		//vstupniVeta.setEditable(false);

		
		
		/* Naplň listview s významy slov*/
		vstupniSlovo.setText(slovo.getSlovo());
		vstupniVeta.setText(veta.getVeta());
		seznamSlov.getItems().addAll(vyznam);
		//vystup.appendText("\nSlovo " + "'" + slovo.getSlovo() +"'" + " ve větě: '" + veta.getVeta() +"' znamená '" + vyznam.get(0) + "'\n\n");
		/* Nastavení animace pro menu ikony */
		HamburgerBasicCloseTransition transition = new HamburgerBasicCloseTransition(hamburger);
		transition.setRate(-1);
		hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
			transition.setRate(transition.getRate() * -1);
			transition.play();
			drawer.toggle();
		});
		/* Přidání funkcí tlačítkům */
		ZmenaVzhledu.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
			transition.setRate(transition.getRate() * -1);
			transition.play();
			drawer.toggle();
			});
		Napoveda.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
			/* Skrytí menu */
			transition.setRate(transition.getRate() * -1);
			transition.play();
			drawer.toggle();
			/* Zobrazení v textarea */
			//vystup.appendText("\n\n-------Nápověda-------\n Tato funkce zatím není podporována\n");
			/* Zobrazení v html */
			Stage stage = new Stage();
			stage.setTitle("Nápověda");
			WebView webview = new WebView();
			webview.getEngine().load(Main.class.getResource("html/napoveda.html").toExternalForm());
			stage.setScene(new Scene(webview, 720, 300));
			stage.setMinWidth(720);
			stage.setMinHeight(300);
			stage.setMaxWidth(720);
			stage.setMaxHeight(300);
			stage.show();
			
		});

		
		KonecAplikace.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
			Stage stage = (Stage) KonecAplikace.getScene().getWindow();
			stage.close();
		});
	}



	@Override
	public void update(Observable arg0, Object arg1) {
		/*Co se má updatovat*/
	}
}
