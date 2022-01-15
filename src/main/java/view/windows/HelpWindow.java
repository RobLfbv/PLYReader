package view.windows;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
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
 * Fenetre "help" de l'application
 *
 */
public class HelpWindow extends Stage {

	/**
	 * cree la fenetre "help"
	 */
	public HelpWindow() {
		VBox root = new VBox();

		Label workspaceTitle = new Label("Workspace");
		workspaceTitle.setFont(new Font(17));
		workspaceTitle.setStyle("-fx-font-weight: bold");

		Text workspaceText = new Text("The workspace is a folder containing some ply files that you want to open.\r\n"
				+ "In order to select a workspace, click on the \"Open Workspace\" button in the top left corner. You can also go to \"file > Open Workspace\" or use CTRL+O.\r\n"
				+ "Once you've opened your workspace, you can see all your files with their name and information in the table on the left. \r\n"
				+ "You can edit some of these information by double clicking and typing a new value. You can also search for a specific file by typing its name in the searchbar or sort every files by a certain criterion.\r\n"
				+ "To load any ply file, simply select it and click the \"Load Model\" button in the top left corner.\r\n");
		workspaceText.setFont(new Font(15));
		workspaceText.setTextAlignment(TextAlignment.JUSTIFY);
		workspaceText.wrappingWidthProperty().bind(root.widthProperty());

		root.getChildren().addAll(workspaceTitle, new Separator(), workspaceText);

		Label canvasGridTitle = new Label("The Canvas Grid");
		canvasGridTitle.setFont(new Font(17));
		canvasGridTitle.setStyle("-fx-font-weight: bold");

		Text canvasGridText = new Text(
				"The main component of this app is the canvas grid in the center. Here, you are able to see you 3D model.\r\n"
						+ "You can select different grid presets from the toolbar. But you can also customise the grid by adding or removing a canvas and by adjusting the grid's width and height.\r\n"
						+ "You can further modify how the model is rendered by right clicking on any canvas to open the parameters menu. In here, you can modify the rendering parameters as well as copy and paste parameters between the different canvas.\r\n");
		canvasGridText.setFont(new Font(15));
		canvasGridText.setTextAlignment(TextAlignment.JUSTIFY);
		canvasGridText.wrappingWidthProperty().bind(root.widthProperty());

		root.getChildren().addAll(canvasGridTitle, new Separator(), canvasGridText);

		Label controlsTitle = new Label("Controls");
		controlsTitle.setFont(new Font(17));
		controlsTitle.setStyle("-fx-font-weight: bold");

		Text controlsText = new Text(
				"You can use the mouse on any canvas to change the object's position and rotation. \r\n"
						+ "- Left click to move the object arround.\r\n"
						+ "- Right click to rotate the object arround its center.\r\n"
						+ "- Use the mouse wheel to zoom in and out.\r\n"
						+ "- Middle click to rotate the light source of the scene arround\r\n"
						+ "You also have further controls over some of those parameters by using the buttons in the \"Object Position\" section on the right side of the app.\r\n"
						+ "You can also use the timer section to automatically rotate the object arround its center at a controlled speed.\r\n");
		controlsText.setFont(new Font(15));
		controlsText.setTextAlignment(TextAlignment.JUSTIFY);
		controlsText.wrappingWidthProperty().bind(root.widthProperty());

		root.getChildren().addAll(controlsTitle, new Separator(), controlsText);

		HBox buttons = new HBox();

		Button closeButton = new Button("Close");
		closeButton.setOnAction(e -> {
			close();
		});
		closeButton.setFont(new Font(20));

		buttons.setAlignment(Pos.CENTER);
		buttons.getChildren().addAll(closeButton);

		root.getChildren().addAll(buttons);
		Scene scene = new Scene(root, 800, 600);
		this.setScene(scene);
		this.setTitle("Help");

		this.initModality(Modality.APPLICATION_MODAL);
		this.initStyle(StageStyle.UTILITY);
		this.setResizable(false);

		this.show();
	}
}
