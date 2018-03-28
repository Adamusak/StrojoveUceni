package StrojoveUceni;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;



import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;

import StrojoveUceni.Logika.Aplikace;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


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

	//private IAplikace hra;

	/**
	 * metoda čte příkaz ze vstupního textového pole a zpracuje ho
	 */
	@FXML
	public void odesliVyznam() {
		/* Zpracovává slovo, které bylo zadané */
		vystup.appendText("\nSlovo " + vstupniSlovo.getText() + " ve větě: " + vstupniVeta.getText() +" znamená " + vyznamSlova.getText() + "\n\n");
		seznamSlov.getItems().addAll(vstupniSlovo.getText());
		/* Nahraj význam do databáze */

		
		/* Vyčisti formulář */
		vyznamSlova.setText("");
		/* Možná časem přidat možnost vybrat z listview již odeslané slovo a upravit ho */
		
		/*Nahraj vstupní větu a slovo databáze a doplň jí do pole*/
		vstupniVeta.setText("Další věta");
		vstupniSlovo.setText("Další slovo");


	}

	

	



	/**
	 * Metoda bude soužit pro předání objektu se spuštěnou hrou kontroleru a zobrazí
	 * stav hry v grafice.
	 * 
	 * @param objekt
	 *            spuštěné hry
	 */
	public void inicializuj(Aplikace aplikace) {
		/* Zavři menu */
		drawer.open();
		/* Nastav log */
		vystup.setEditable(false);
		/* Nastav editovatelnost vstupních polí */
		//vstupniSlovo.setEditable(false);
		//vstupniVeta.setEditable(false);

		
		
		/* Naplň listview */
		seznamSlov.getItems().addAll("Slovo1","Slovo2","Slovo3","Slovo4","Slovo5");
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
			try {
				ZmenaCSS();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

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
			stage.setScene(new Scene(webview, 720, 480));
			stage.setMinWidth(720);
			stage.setMinHeight(480);
			stage.setMaxWidth(720);
			stage.setMaxHeight(480);
			stage.show();
			
		});

		
		KonecAplikace.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
			Stage stage = (Stage) KonecAplikace.getScene().getWindow();
			stage.close();
		});
	}

	private void ZmenaCSS() throws IOException {
		String css = Main.class.getResource("css/scene2.css").toExternalForm();
		scene.getStylesheets().clear();
		scene.getStylesheets().add(css);
		scene.applyCss();
		//vystup.appendText("\n\n-------Změna CSS-------\n Tato funkce zatím není podporována\n");
	}



	@Override
	public void update(Observable arg0, Object arg1) {
		/*Co se má updatovat*/
	}
}
