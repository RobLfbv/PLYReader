package view;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import exceptions.NoPLYFileInDirectoryException;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

/**
 * 
 * Classe qui affiche la liste des différents fichiers ply disponibles dans le
 * workspace ouvert
 *
 */
public class WorkspaceViewer extends VBox {

	/**
	 * La vue dans laquelle le viewer est situé
	 */
	private View parent;

	/**
	 * La barre d'outils
	 */
	@SuppressWarnings("unused")
	private ToolBar toolBar;
	/**
	 * Boutton pour charger un nouveau workspace
	 */
	private Button openWorkspaceButton;
	/**
	 * Hbox qui stocke les boutton a cacher si aucun workspace n'est chargé
	 */
	private HBox rootToggled;
	/**
	 * Le fichier correspondant à l'emplacement du workspace
	 */
	private File workspace;
	/**
	 * Label pour afficher l'emplacement du workspace
	 */
	private TextField workspacePathLabel;

	/**
	 * La liste des fichiers ply affichée dans la fenètre
	 */
	private List<ListViewItem> fileList;
	/**
	 * liste des fichier ply disponibles (il ne sont pas tous affichés tout le
	 * temps, par exemple si l'on fait une recherche par nom)
	 */
	private ListView<ListViewItem> fileListView;
	/**
	 * le champ de recherche par nom de fichiers
	 */
	private TextField searchField;

	/**
	 * Crée un workspace viewer
	 * 
	 * @param parent la vue dans laquelle il est placé
	 */
	public WorkspaceViewer(View parent) {
		this.parent = parent;
		workspace = new File(""+File.separator);
		//workspace = new File("." + File.separator + "Example");

		ToolBar toolBar = new ToolBar();

		openWorkspaceButton = new Button("Open Workspace");
		openWorkspaceButton.setOnAction(e -> {
			openWorkspace();
		});

		rootToggled = new HBox();
		Button refreshButton = new Button("R");

		refreshButton.setOnAction(e -> {
			loadWorkspace(workspace);
		});
		workspacePathLabel = new TextField("");
		workspacePathLabel.setEditable(false);
		Button loadModelButton = new Button("Load Model");
		loadModelButton.setOnAction(e -> {
			parent.getModel().loadFromFile(fileListView.getSelectionModel().getSelectedItem().getFile());
		});

		searchField = new TextField();
		searchField.setPromptText("Search");

		searchField.textProperty().addListener((observable, oldValue, newValue) -> {
			updateListView();
		});

		rootToggled.getChildren().addAll(refreshButton, workspacePathLabel, searchField, loadModelButton);
		rootToggled.setManaged(false);

		toolBar.getItems().addAll(rootToggled, openWorkspaceButton);

		this.fileListView = new ListView<>();

		fileListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent click) {
				if (click.getClickCount() == 2) {
					parent.getModel().loadFromFile(fileListView.getSelectionModel().getSelectedItem().getFile());
				}
			}
		});

		this.getChildren().addAll(toolBar, fileListView);
	}

	/**
	 * Met a jour la liste des fichier affichés en fonction de la barre de recherche
	 */
	public void updateListView() {
		String searchTerm = searchField.getText().toLowerCase();

		fileListView.getItems().clear();
		for (ListViewItem item : fileList) {
			if (item.getName().toLowerCase().contains(searchTerm)) {
				fileListView.getItems().add(item);
			}
		}
	}

	/**
	 * Ouvre un nouveau workspace
	 */
	public void openWorkspace() {
		DirectoryChooser dc = new DirectoryChooser();
		dc.setInitialDirectory(workspace);
		File workspaceFolder = dc.showDialog(parent);
		loadWorkspace(workspaceFolder);
	}

	/**
	 * charge le workspace dans la liste des fichiers
	 * 
	 * @param workspaceFolder le dossier qui contient tous les fichiers a charger
	 */
	public void loadWorkspace(File workspaceFolder) {
		if (workspaceFolder == null || !workspaceFolder.isDirectory())
			return;
		File[] plyFiles = workspaceFolder.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".ply");
			}
		});
		if (plyFiles.length == 0) {
			parent.update(parent.getModel(), new NoPLYFileInDirectoryException());
			return;
		}

		this.workspacePathLabel.setText(workspaceFolder.toString());
		this.workspace = workspaceFolder;

		fileList = new ArrayList<ListViewItem>();

		for (double i = 0; i < plyFiles.length; i++) {
			fileList.add(new ListViewItem(plyFiles[(int) i]));
		}
		updateListView();
		rootToggled.setVisible(true);
		rootToggled.setManaged(true);

		openWorkspaceButton.setManaged(false);
		openWorkspaceButton.setVisible(false);
	}
}
