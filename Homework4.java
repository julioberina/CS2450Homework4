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
import javafx.scene.control.TextField;
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
		xAxis.setOnAction(event -> { currentAxis = "X"; });
		yAxis.setOnAction(event -> { currentAxis = "Y"; });
		zAxis.setOnAction(event -> { currentAxis = "Z"; });
		HBox axisBox = new HBox(5, xAxis, yAxis, zAxis);

		Label translateLabel = new Label("Translation:");
		Button plus = new Button("+");
		Button minus = new Button("-");
		HBox transButtons = new HBox(5, plus, minus);
		VBox transBox = new VBox(5, translateLabel, transButtons);

		Label rotateLabel = new Label("Rotation (degrees):");
		Slider degreeSlider = new Slider(0, 360, 1);
		VBox rotateBox = new VBox(5, rotateLabel, degreeSlider);

		Label scaleLabel = new Label("Scale active shape");
		Slider scaleSlider = new Slider(0, 3, 0.5);

		Label switchColor = new Label("Change active shape color:");
		Button redColor = new Button("Red");
		Button greenColor = new Button("Green");
		Button blueColor = new Button("Blue");
		VBox switchBox = new VBox(5, switchColor, redColor, greenColor, blueColor);

		Label subSceneColor = new Label("Change SubScene background color:");
		Button redSceneColor = new Button("Red");
		Button greenSceneColor = new Button("Green");
		Button blueSceneColor = new Button("Blue");
		VBox subSceneBox = new VBox(5, redSceneColor, greenSceneColor, blueSceneColor);

		TextField shapeField = new TextField();
		Button addShape = new Button("Add Shape");
		VBox shapeBox = new VBox(5, shapeField, addShape);

		Scene scene = new Scene(menuBar, 1000, 700);

		// Set the stage title
		primaryStage.setTitle("Homework 4");

		// Set the scene
		primaryStage.setScene(scene);

		// Show the window
		primaryStage.show();
	}
}
