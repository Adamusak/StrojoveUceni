/* Soubor je ulozen v kodovani UTF-8.
 * Kontrola kódování: Příliš žluťoučký kůň úpěl ďábelské ódy. */
package StrojoveUceni;

import javafx.scene.input.MouseEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import StrojoveUceni.Logika.Aplikace;
import javafx.event.EventHandler;

/**
 * Třída slouží ke spuštění adventury. Při spuštění bez parametru konstruuje
 * okno aplikace, s parametrem -text se spouští v textovém režimu
 * 
 * @author Filip Vencovsky, Adam Novak
 *
 */
public class Main extends javafx.application.Application {


	public static void main(String[] args) {
			launch(args);
	}

	/**
	 * Metoda, ve které se konstruuje okno, kontroler a hra, která se předává
	 * kontroleru
	 */
	private double xOffset = 0;
	private double yOffset = 0;

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		/*GridPane root = (GridPane)FXMLLoader.load(getClass().getResource("StrojoveUceni.fxml"));
			Scene scene = new Scene(root,400,400);
			
			primaryStage.setScene(scene);
			primaryStage.show();
			*/
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("StrojoveUceni.fxml"));
		Parent root = loader.load();
		StrojoveUceniController controller = loader.getController();
		controller.inicializuj(new Aplikace());
		primaryStage.setTitle("Adventura");
		//primaryStage.setMinWidth(300);
		//primaryStage.setMinHeight(300);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		root.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				primaryStage.setX(event.getScreenX() - xOffset);
				primaryStage.setY(event.getScreenY() - yOffset);
			}
		});
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		
		//controller.pripojDatabzi();
	}
}

