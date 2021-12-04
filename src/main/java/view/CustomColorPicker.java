package view;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Skin;
import javafx.scene.control.skin.ColorPickerSkin;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Classe qui représente un colorpicker qui ne possède pas les options de
 * couleurs personalisées (qui faisent crasher notre application à cause d'un
 * bug dans javafx) Bug : https://bugs.openjdk.java.net/browse/JDK-8095838
 * 
 * modifiée à partir de :
 * https://stackoverflow.com/questions/42812471/javafx-colorpicker-nullpointerexception
 *
 */

public class CustomColorPicker extends ColorPicker {

	/**
	 * Créé un color picker custom
	 * 
	 * @param color
	 */
	public CustomColorPicker(Color color) {
		super(color);
	}

	/**
	 * Change le skin par default de ce color picker pour enlever le couler des
	 * couleurs perso
	 */
	@Override
	protected Skin<?> createDefaultSkin() {
		return new CustomColorPickerSkin(this);
	}

	/**
	 * 
	 * popup custom pour le color picker
	 *
	 */
	private class CustomColorPickerSkin extends ColorPickerSkin {

		/**
		 * 
		 * @param control le colorpicker
		 */
		public CustomColorPickerSkin(ColorPicker control) {
			super(control);
		}

		/**
		 * Popup custom
		 */
		@Override
		protected Node getPopupContent() {
			Node colorPalette = super.getPopupContent(); // This is an instance of private API ColorPalette which
			// extends Region
			Region r = (Region) colorPalette;
			List<Node> vboxes = r.getChildrenUnmodifiable().stream().filter(e -> {
				return e instanceof VBox;
			}).collect(Collectors.toList()); // This ColorPalette contains a VBox which contains the Hyperlink we want
			// to remove.
			for (Node n : vboxes) {
				VBox vbox = (VBox) n;
				List<Node> hyperlinks = vbox.getChildren().stream().filter(e -> {
					return e instanceof Hyperlink;
				}).collect(Collectors.toList());
				vbox.getChildren().removeAll(hyperlinks); // Remove the hyperlink
			}
			return colorPalette;
		}

	}

}
