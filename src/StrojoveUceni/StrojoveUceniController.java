package StrojoveUceni;


import java.io.IOException;
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
import javafx.application.Application;
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

	@FXML
	public void Vyber() {
		/* Zpracovává příkaz při kliknutí na kontextové menu vyber */
		String co = seznamSlov.getSelectionModel().getSelectedItem();
		/* Kontroluje zda, je označený item, který se má sebrat */
		if (co == null) {
			/*Není co vybrat*/
		} else {
			vyznamSlova.setText(co);
		}
	}
	
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

		
		
		/* Naplň listview a významy slov - do produkce odstranit */
		seznamSlov.getItems().addAll("dignissim","lacus","consectetur","euismod","dolor");
		vystup.appendText("\nSlovo " + "consectetur" + " ve větě: Lorem ipsum dolor sit amet, consectetur adipiscing elit." + "" +" znamená " + "význam slova consectetur" + "\n\n");
		vystup.appendText("\nSlovo " + "euismod" + " ve větě: Aenean risus erat, lobortis a odio vitae, eleifend euismod justo." + "" +" znamená " + "význam slova euismod" + "\n\n");
		vystup.appendText("\nSlovo " + "dignissim" + " ve větě: Nunc fermentum est turpis, in dignissim nunc elementum in." + "" +" znamená " + "význam slova dignissim" + "\n\n");
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
