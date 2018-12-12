import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.geometry.Pos;
import javafx.animation.FillTransition;
import javafx.util.Duration;
import java.util.List;
import java.util.ArrayList;

public class Homework4 extends Application
{
	private static String currentAxis = "";
	private static Shape3D selectedShape = null;

	public static void main(String[] args)
	{
		// Launch the application
		launch(args);
	}

	@Override
	public void start(Stage primaryStage)
	{
		FillTransition turnBlue = new FillTransition(new Duration(500));

		MenuBar menuBar = new MenuBar();
		Menu fileMenu = new Menu("File");
		MenuItem openFile = new MenuItem("Open");
		MenuItem saveFile = new MenuItem("Save");
		fileMenu.getItems().addAll(openFile, saveFile);
		menuBar.getMenus().add(fileMenu);

		Group shapeGroup = new Group();
		SubScene shapeScene = new SubScene(shapeGroup, 700, 650);

		Label axis = new Label("Choose Axis of Animation:");
		Button xAxis = new Button("X-Axis");
		Button yAxis = new Button("Y-Axis");
		Button zAxis = new Button("Z-Axis");
		xAxis.setOnAction(event -> { currentAxis = "X" });
		yAxis.setOnAction(event -> { currentAxis = "Y" });
		zAxis.setOnAction(event -> { currentAxis = "Z" });
		HBox axisBox = new HBox(5, xAxis, yAxis, zAxis)

		Scene scene = new Scene(menuBar, 1000, 700);

		// Set the stage title
		primaryStage.setTitle("Homework 4");

		// Set the scene
		primaryStage.setScene(scene);

		// Show the window
		primaryStage.show();
	}
}
