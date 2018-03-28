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
	private JFXTextField vstupniText;
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
	public void odesliPrikaz() {
		/* Zpracovává příkaz zadaný do příkazové řádky */
		
		vystup.appendText("\n\n-------" + vstupniText.getText() + "-------\n");
		vstupniText.setText("");
		/* Při vstupu se ptá jestli neskončila hra */
		if (Aplikace.konecAplikace()) {
			vystup.appendText("\n\n-------Konec hry-------\n");
			vstupniText.setDisable(true);
			/* zobrazit nabídku na novou hru */
		}
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
		/* Naplň listview */
		//this.hra = hra;
		seznamSlov.getItems().addAll("Slovo1","Slovo2","Slovo3","Slovo4","Slovo5");
		/* Nastav mapu */
		//hra.getHerniPlan().addObserver(this);
		// hra.getBatoh().addObserver(this);
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
			vystup.appendText("\n\n-------Nápověda-------\n Tato funkce zatím není podporována\n");
			/* Zobrazení v html */
			Stage stage = new Stage();
			stage.setTitle("Nápověda");
			WebView webview = new WebView();
			webview.getEngine().load(Main.class.getResource("napoveda.html").toExternalForm());
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
		/*String css = Application.class.getResource("css/scene2.css").toExternalForm();
		scene.getStylesheets().clear();
		scene.getStylesheets().add(css);
		scene.applyCss();*/
		vystup.appendText("\n\n-------Změna CSS-------\n Tato funkce zatím není podporována\n");
	}



	@Override
	public void update(Observable arg0, Object arg1) {
		/*Co se má updatovat*/
	}
}
