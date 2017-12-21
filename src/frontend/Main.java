package frontend;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import backend.Generator;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	BufferedImage wallpaper = null;
	Generator generator = new Generator();
	@Override
	public void start(Stage primaryStage) {
		//generator.test();
		BorderPane root = new BorderPane();
		FileChooser fChoosey = new FileChooser();
		fChoosey.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("JPG", "*.jpg"),
				new FileChooser.ExtensionFilter("PNG", "*.png")
				);

		Button fc = new Button();
		fc.setText("Save...");
		fc.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				//Create a file from a File Chooser
				File output = fChoosey.showSaveDialog(primaryStage);
				if(output!=null) {
					try {
						ImageIO.write(wallpaper,fChoosey.getSelectedExtensionFilter().getDescription(), output);
					} catch (IOException e) {
						System.out.println("Couldn't save your file!");
					}
				}
			}
		});


		Button g = new Button();
		g.setText("Generate a wallpaper");
		g.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				//Generate an image and show save button
				wallpaper = generator.generate(1);
				root.setRight(fc);
				WritableImage test = null;
				test = SwingFXUtils.toFXImage(wallpaper, test);
				ImageView viewy = new ImageView();
				viewy.setFitHeight(540);
				viewy.setFitWidth(960);
				viewy.setImage(test);
				root.setBottom(viewy);
			}

		});
		Button t = new Button();
		t.setText("Test");
		t.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				//Generate an image and show save button
				//range: 0 to 9809
				generator.test(0, 9809);
			}
		});

		root.setLeft(g);
		root.setCenter(t);
		Scene prefs = new Scene(root,400,400);
		prefs.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(prefs);
		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}
}
