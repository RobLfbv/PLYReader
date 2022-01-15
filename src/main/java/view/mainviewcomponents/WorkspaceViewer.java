package view.mainviewcomponents;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;

import exceptions.NoPLYFileInDirectoryException;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import model.Model;
import view.windows.MainWindow;

/**
 * 
 * Permet d'afficher la liste des fichier ply dans le workspace choisi
 *
 */
public class WorkspaceViewer extends VBox {

	/**
	 * La fenetre a laquelle le viewer appartient
	 */
	private MainWindow parent;
	/**
	 * Le fichier correspondant a l'emplacement du workspace
	 */
	private File workspace;

	/**
	 * Liste des fichiers ply dans le workspae
	 */
	private List<PLYFile> fileList;

	/**
	 * Boutton pour charger un nouveau workspace
	 */
	private Button openWorkspaceButton;
	/**
	 * Label pour afficher l'emplacement du workspace
	 */
	private TextField workspacePathLabel;
	/**
	 * le champ de recherche par nom de fichiers
	 */
	private TextField searchField;
	/**
	 * la table de tous les fichiers ply
	 */
	public TableView<PLYFile> tableView;
	/**
	 * boutton pour refresh le viewer
	 */
	private Button refreshButton;

	/**
	 * Reference vers le model pour les messages d'erreur
	 */
	private Model model;

	/**
	 * w cree un workspace viewer
	 * 
	 * @param parent le fenetre dans lequel le viewer se trouve
	 */
	@SuppressWarnings("unchecked")
	public WorkspaceViewer(MainWindow parent, Model model) {
		this.parent = parent;
		this.model = model;
		this.fileList = new ArrayList<>();

		// workspace = new File("." + File.separator + "Example");
		workspace = null;

		ToolBar toolBarMain = new ToolBar();

		openWorkspaceButton = new Button("Open Workspace");
		openWorkspaceButton.setOnAction(e -> {
			openWorkspace();
		});
		openWorkspaceButton.setFont(new Font(15));

		Button loadModelButton = new Button("Load Model");
		loadModelButton.setOnAction(e -> {
			parent.getModel().loadFromFile(tableView.getSelectionModel().getSelectedItem().getFile());
		});
		loadModelButton.setFont(new Font(15));
		loadModelButton.setAlignment(Pos.CENTER_RIGHT);
		loadModelButton.setVisible(false);
		loadModelButton.setManaged(false);

		toolBarMain.getItems().addAll(openWorkspaceButton, loadModelButton);

		ToolBar toolBarSecondary = new ToolBar();
		refreshButton = new Button("🔄");
		refreshButton.setOnAction(e -> {
			loadWorkspace(workspace);
		});
		workspacePathLabel = new TextField("");
		workspacePathLabel.setEditable(false);

		searchField = new TextField();
		searchField.setPromptText("Search");

		searchField.textProperty().addListener((observable, oldValue, newValue) -> {
			updateTableView();
		});
		toolBarSecondary.getItems().addAll(refreshButton, workspacePathLabel, searchField);

		tableView = new TableView<>();
		tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				loadModelButton.setVisible(true);
				loadModelButton.setManaged(true);
			} else {
				loadModelButton.setVisible(false);
				loadModelButton.setManaged(false);
			}
		});

		TableColumn<PLYFile, String> fileInfo = new TableColumn<>("File Information");
		TableColumn<PLYFile, String> filename = new TableColumn<>("Name");
		filename.setCellValueFactory(item -> item.getValue().fileNameProperty());
		filename.setCellFactory(TextFieldTableCell.forTableColumn());
		filename.setOnEditCommit(t -> {
			try {
				t.getTableView().getItems().get(t.getTablePosition().getRow()).renameFile(t.getNewValue());
				updateTableView();
			} catch (FileAlreadyExistsException e2) {
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setHeaderText("Could not rename the file");
				errorAlert.setContentText("File \"" + e2.getFile() + "\" already exists");
				errorAlert.showAndWait();
				updateTableView();
			} catch (IOException e1) {
				Alert errorAlert = new Alert(AlertType.ERROR);
				errorAlert.setHeaderText("Could not rename the file");
				errorAlert.setContentText(e1.getMessage());
				errorAlert.showAndWait();
			}
		});
		TableColumn<PLYFile, Integer> fileSize = new TableColumn<>("Size");

		fileSize.setCellValueFactory(item -> item.getValue().fileSizeProperty());
		fileSize.setCellFactory(item -> new TableCell<PLYFile, Integer>() {
			@Override
			protected void updateItem(Integer item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? null : convertFileSize(item));
			}
		});
		fileInfo.getColumns().addAll(filename, fileSize);

		TableColumn<PLYFile, String> modelInfo = new TableColumn<>("3D Model Information");

		TableColumn<PLYFile, Integer> nbPoints = new TableColumn<>("Points");
		nbPoints.setCellValueFactory(item -> item.getValue().nbPointsProperty());
		TableColumn<PLYFile, Integer> nbFaces = new TableColumn<>("Faces");
		nbFaces.setCellValueFactory(item -> item.getValue().nbFacesProperty());
		TableColumn<PLYFile, Boolean> colored = new TableColumn<>("Color");
		colored.setCellValueFactory(item -> item.getValue().coloredProperty());
		colored.setCellFactory(item -> new TableCell<PLYFile, Boolean>() {
			@Override
			protected void updateItem(Boolean item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? null : item ? "Colored" : "No Colors");
			}
		});
		modelInfo.getColumns().addAll(nbPoints, nbFaces, colored);

		TableColumn<PLYFile, String> plyInfo = new TableColumn<>("PLY Information");

		TableColumn<PLYFile, String> plyName = new TableColumn<>("Name");
		plyName.setCellValueFactory(item -> item.getValue().plyNameProperty());
		plyName.setCellFactory(TextFieldTableCell.forTableColumn());
		plyName.setOnEditCommit(t -> {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setNom(t.getNewValue());
		});
		TableColumn<PLYFile, String> plyAuteur = new TableColumn<>("Author");
		plyAuteur.setCellValueFactory(item -> item.getValue().plyAuteurProperty());
		plyAuteur.setCellFactory(TextFieldTableCell.forTableColumn());
		plyAuteur.setOnEditCommit(t -> {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setAuteur(t.getNewValue());
		});
		TableColumn<PLYFile, String> plyDateCreation = new TableColumn<>("Date");
		plyDateCreation.setCellValueFactory(item -> item.getValue().plyDateCreationProperty());
		plyDateCreation.setCellFactory(TextFieldTableCell.forTableColumn());
		plyDateCreation.setOnEditCommit(t -> {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setDateCreation(t.getNewValue());
		});
		TableColumn<PLYFile, String> plyDescription = new TableColumn<>("Description");
		plyDescription.setCellValueFactory(item -> item.getValue().plyDescriptionProperty());
		plyDescription.setCellFactory(TextFieldTableCell.forTableColumn());
		plyDescription.setOnEditCommit(t -> {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setDescription(t.getNewValue());
		});

		plyInfo.getColumns().addAll(plyName, plyAuteur, plyDateCreation, plyDescription);

		tableView.setEditable(true);
		tableView.getColumns().addAll(fileInfo, modelInfo, plyInfo);

		this.getChildren().addAll(toolBarMain, toolBarSecondary, tableView);
	}

	/**
	 * Converti la taille d'un fichier de bytes a l'unite la plus proche
	 * 
	 * @param bytes la taille en byte
	 * @return
	 */
	private static String convertFileSize(int bytes) {
		final int conversionGB = 1073741824, conversionMB = 1048576, conversionKB = 1024, convertionBytes = 1;

		if (bytes >= conversionGB) {
			return Math.round((bytes / 1073741824.0) * 100.0) / 100.0 + " GB";
		} else if (bytes >= conversionMB) {
			return Math.round((bytes / 1048576.0) * 100.0) / 100.0 + " MB";
		} else if (bytes >= conversionKB) {
			return Math.round((bytes / 1024.0) * 100.0) / 100.0 + " KB";
		} else if (bytes > convertionBytes) {
			return bytes + " bytes";
		} else if (bytes == convertionBytes) {
			return bytes + " byte";
		} else {
			return "0 bytes";
		}
	}

	/**
	 * Ouvre un nouveau workspace
	 */
	public void openWorkspace() {
		DirectoryChooser dc = new DirectoryChooser();
		dc.setInitialDirectory(workspace);
		File workspaceFolder = dc.showDialog(this.parent);
		loadWorkspace(workspaceFolder);
	}

	/**
	 * Charge le workspace choisi
	 * 
	 * @param workspaceFolder fichier du workspace
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
			model.notifyObservers(new NoPLYFileInDirectoryException());
			return;
		}

		this.workspace = workspaceFolder;
		this.workspacePathLabel.setText(workspace.getPath());

		fileList = new ArrayList<>();

		for (double i = 0; i < plyFiles.length; i++) {
			fileList.add(new PLYFile(plyFiles[(int) i], model));
		}

		updateTableView();
	}

	/**
	 * Mets a jour la table dans l'interface
	 */
	private void updateTableView() {
		String searchTerm = searchField.getText().toLowerCase();

		tableView.getItems().clear();
		for (PLYFile item : fileList) {
			if (item.getFileName().toLowerCase().contains(searchTerm)) {
				tableView.getItems().add(item);
			}
		}
	}
}
