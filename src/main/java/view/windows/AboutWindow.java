package view.windows;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 
 * Fenetre "about" de l'application
 *
 */
public class AboutWindow extends Stage {

	/**
	 * cree la fenetre "about"
	 */
	public AboutWindow() {
		VBox root = new VBox();

		Text txt = new Text(
				"This software was made by Robin LEFEBVRE, Noe DELCROIX, Quentin BERNARD & Constant Vennin");
		txt.setFont(new Font(25));
		txt.setStyle("-fx-font-weight: bold");
		txt.setTextAlignment(TextAlignment.CENTER);
		txt.wrappingWidthProperty().bind(root.widthProperty());

		HBox buttons = new HBox();

		Button sourceCodeButton = new Button("Source Code");
		sourceCodeButton.setFont(new Font(20));
		sourceCodeButton.setOnAction(e -> {
			try {
				java.awt.Desktop.getDesktop()
						.browse(new URI("https://gitlab.univ-lille.fr/2021-projet-modelisation/projetmodeh2"));
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (URISyntaxException e1) {
				e1.printStackTrace();
			}
		});

		Button closeButton = new Button("Close");
		closeButton.setOnAction(e -> {
			close();
		});
		closeButton.setFont(new Font(20));

		buttons.setAlignment(Pos.CENTER);
		buttons.getChildren().addAll(sourceCodeButton, closeButton);

		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(txt, buttons);
		Scene scene = new Scene(root, 400, 250);
		this.setScene(scene);
		this.setTitle("About...");

		this.initModality(Modality.APPLICATION_MODAL);
		this.initStyle(StageStyle.UTILITY);
		this.setResizable(false);

		this.show();
	}
}
