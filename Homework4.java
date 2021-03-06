import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.scene.Group;
import javafx.geometry.Pos;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;
import java.lang.StringBuilder;

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
		HBox axisButtons = new HBox(5, xAxis, yAxis, zAxis);
		VBox axisBox = new VBox(5, axis, axisButtons);

		Label translateLabel = new Label("Translation:");
		Button plus = new Button("+");
		Button minus = new Button("-");
		HBox transButtons = new HBox(5, plus, minus);
		VBox transBox = new VBox(5, translateLabel, transButtons);

		plus.setOnAction(event -> {
			if (selectedShape != null) {
				if (currentAxis.equals("X"))
					selectedShape.getTransforms().add(new Translate(10, 0, 0));
				else if (currentAxis.equals("Y"))
					selectedShape.getTransforms().add(new Translate(0, 10, 0));
				else if (currentAxis.equals("Z"))
					selectedShape.getTransforms().add(new Translate(0, 0, 10));
			}
		});
		minus.setOnAction(event -> {
			if (selectedShape != null) {
				if (currentAxis.equals("X"))
					selectedShape.getTransforms().add(new Translate(-10, 0, 0));
				else if (currentAxis.equals("Y"))
					selectedShape.getTransforms().add(new Translate(0, -10, 0));
				else if (currentAxis.equals("Z"))
					selectedShape.getTransforms().add(new Translate(0, 0, -10));
			}
		});

		Label rotateLabel = new Label("Rotation (degrees):");
		Slider degreeSlider = new Slider(0, 360, 1);
		VBox rotateBox = new VBox(5, rotateLabel, degreeSlider);

		degreeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
			if (selectedShape != null) {
				if (currentAxis.equals("X"))
					selectedShape.getTransforms().add(new Rotate(newVal.doubleValue(), Rotate.X_AXIS));
				else if (currentAxis.equals("Y"))
					selectedShape.getTransforms().add(new Rotate(newVal.doubleValue(), Rotate.Y_AXIS));
				else if (currentAxis.equals("Z"))
					selectedShape.getTransforms().add(new Rotate(newVal.doubleValue(), Rotate.Z_AXIS));
				else
					return;
			}
		});

		Label scaleLabel = new Label("Scale active shape");
		Slider scaleSlider = new Slider(0.9, 1.1, 0.01);
		VBox scaleBox = new VBox(5, scaleLabel, scaleSlider);

		scaleSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
			if (selectedShape != null) {
				if (currentAxis.equals("X")) {
					Scale scale = new Scale();
					scale.setX(newVal.doubleValue());
					selectedShape.getTransforms().add(scale);
				}
				else if (currentAxis.equals("Y")) {
					Scale scale = new Scale();
					scale.setY(newVal.doubleValue());
					selectedShape.getTransforms().add(scale);
				}
				else if (currentAxis.equals("Z")) {
					Scale scale = new Scale();
					scale.setZ(newVal.doubleValue());
					selectedShape.getTransforms().add(scale);
				}
				else
					return;
			}
		});

		Label switchColor = new Label("Change active shape color:");
		Button redColor = new Button("Red");
		Button greenColor = new Button("Green");
		Button blueColor = new Button("Blue");
		VBox switchBox = new VBox(5, switchColor, redColor, greenColor, blueColor);

		redColor.setOnAction(event -> {
			if (selectedShape != null) {
				((PhongMaterial)selectedShape.getMaterial()).setDiffuseColor(Color.RED);
			}
		});
		greenColor.setOnAction(event -> {
			if (selectedShape != null) {
				((PhongMaterial)selectedShape.getMaterial()).setDiffuseColor(Color.GREEN);
			}
		});
		blueColor.setOnAction(event -> {
			if (selectedShape != null) {
				((PhongMaterial)selectedShape.getMaterial()).setDiffuseColor(Color.BLUE);
			}
		});

		Label subSceneColor = new Label("Change SubScene background color:");
		Button redSceneColor = new Button("Red");
		Button greenSceneColor = new Button("Green");
		Button blueSceneColor = new Button("Blue");
		VBox subSceneBox = new VBox(5, subSceneColor, redSceneColor, greenSceneColor, blueSceneColor);

		redSceneColor.setOnAction(event -> { shapeScene.setFill(Color.RED); });
		greenSceneColor.setOnAction(event -> { shapeScene.setFill(Color.GREEN); });
		blueSceneColor.setOnAction(event -> { shapeScene.setFill(Color.BLUE); });

		TextField shapeField = new TextField();
		Button addShape = new Button("Add Shape");
		VBox shapeBox = new VBox(5, shapeField, addShape);

		addShape.setOnAction(event -> {
			String str = shapeField.getText();
			String[] data = str.split(" ");

			if (data.length == 1) {
				double r = Double.parseDouble(data[0]);
				Sphere shape = new Sphere(r);
				shape.setMaterial(new PhongMaterial());
				shape.setOnMouseClicked(evt -> { selectedShape = shape; });
				shapeGroup.getChildren().add(shape);
				selectedShape = shape;
			}
			else if (data.length == 2) {
				double r = Double.parseDouble(data[0]);
				double h = Double.parseDouble(data[1]);
				Cylinder shape = new Cylinder(r, h);
				shape.setMaterial(new PhongMaterial());
				shape.setOnMouseClicked(evt -> { selectedShape = shape; });
				shapeGroup.getChildren().add(shape);
				selectedShape = shape;
			}
			else if (data.length == 3) {
				double w = Double.parseDouble(data[0]);
				double h = Double.parseDouble(data[1]);
				double d = Double.parseDouble(data[2]);
				Box shape = new Box(w, h, d);
				shape.setMaterial(new PhongMaterial());
				shape.setOnMouseClicked(evt -> { selectedShape = shape; });
				shapeGroup.getChildren().add(shape);
				selectedShape = shape;
			}
		});

		saveFile.setOnAction(event -> {
			try {
				StringBuilder sb = new StringBuilder();
				TextInputDialog dialog = new TextInputDialog();

				dialog.setTitle("Save a file");
				dialog.setHeaderText("Enter filename (no extension):");
				dialog.setContentText("Filename:");

				Optional<String> result = dialog.showAndWait();
				result.ifPresent(name -> { sb.append(name); });

				ObjectOutputStream oos = new ObjectOutputStream(
	                    new FileOutputStream(System.getProperty("user.dir") + "/" + sb.toString() + ".dat")
	            );

				if (shapeScene.getFill() == Color.RED)		oos.writeInt(1);
				else if (shapeScene.getFill() == Color.GREEN)		oos.writeInt(2);
				else if (shapeScene.getFill() == Color.BLUE)		oos.writeInt(3);

				for (Node shape: shapeGroup.getChildren()) {
					if (shape instanceof Sphere) {
						oos.writeInt(1);
						Sphere sphere = (Sphere)shape;
						oos.writeDouble(sphere.getRadius());

						oos.writeDouble(sphere.getTranslateX());
						oos.writeDouble(sphere.getTranslateY());
						oos.writeDouble(sphere.getTranslateZ());

						if (sphere.getRotationAxis() == Rotate.X_AXIS)	oos.writeInt(1);
						else if (sphere.getRotationAxis() == Rotate.Y_AXIS)	oos.writeInt(2);
						else if (sphere.getRotationAxis() == Rotate.Z_AXIS)	oos.writeInt(3);

						oos.writeDouble(sphere.getRotate());
						oos.writeDouble(sphere.getScaleX());
						oos.writeDouble(sphere.getScaleY());
						oos.writeDouble(sphere.getScaleZ());

						if (sphere.getMaterial() == null)		oos.writeInt(0);
						else {
							PhongMaterial pm = (PhongMaterial)sphere.getMaterial();
							if (pm.getDiffuseColor() == Color.RED)	oos.writeInt(1);
							else if (pm.getDiffuseColor() == Color.GREEN)		oos.writeInt(2);
							else if (pm.getDiffuseColor() == Color.BLUE)		oos.writeInt(3);
						}
					}
					else if (shape instanceof Cylinder) {
						oos.writeInt(2);
						Cylinder cylinder = (Cylinder)shape;
						oos.writeDouble(cylinder.getRadius());
						oos.writeDouble(cylinder.getHeight());

						oos.writeDouble(cylinder.getTranslateX());
						oos.writeDouble(cylinder.getTranslateY());
						oos.writeDouble(cylinder.getTranslateZ());

						if (cylinder.getRotationAxis() == Rotate.X_AXIS)	oos.writeInt(1);
						else if (cylinder.getRotationAxis() == Rotate.Y_AXIS)	oos.writeInt(2);
						else if (cylinder.getRotationAxis() == Rotate.Z_AXIS)	oos.writeInt(3);

						oos.writeDouble(cylinder.getRotate());
						oos.writeDouble(cylinder.getScaleX());
						oos.writeDouble(cylinder.getScaleY());
						oos.writeDouble(cylinder.getScaleZ());

						if (cylinder.getMaterial() == null)		oos.writeInt(0);
						else {
							PhongMaterial pm = (PhongMaterial)cylinder.getMaterial();
							if (pm.getDiffuseColor() == Color.RED)	oos.writeInt(1);
							else if (pm.getDiffuseColor() == Color.GREEN)		oos.writeInt(2);
							else if (pm.getDiffuseColor() == Color.BLUE)		oos.writeInt(3);
						}
					}
					else if (shape instanceof Box) {
						oos.writeInt(3);
						Box box = (Box)shape;
						oos.writeDouble(box.getWidth());
						oos.writeDouble(box.getHeight());
						oos.writeDouble(box.getDepth());

						oos.writeDouble(box.getTranslateX());
						oos.writeDouble(box.getTranslateY());
						oos.writeDouble(box.getTranslateZ());

						if (box.getRotationAxis() == Rotate.X_AXIS)	oos.writeInt(1);
						else if (box.getRotationAxis() == Rotate.Y_AXIS)	oos.writeInt(2);
						else if (box.getRotationAxis() == Rotate.Z_AXIS)	oos.writeInt(3);

						oos.writeDouble(box.getRotate());
						oos.writeDouble(box.getScaleX());
						oos.writeDouble(box.getScaleY());
						oos.writeDouble(box.getScaleZ());

						if (box.getMaterial() == null)		oos.writeInt(0);
						else {
							PhongMaterial pm = (PhongMaterial)box.getMaterial();
							if (pm.getDiffuseColor() == Color.RED)	oos.writeInt(1);
							else if (pm.getDiffuseColor() == Color.GREEN)		oos.writeInt(2);
							else if (pm.getDiffuseColor() == Color.BLUE)		oos.writeInt(3);
						}
					}
				}

				oos.writeInt(10000); // "close" flag
				oos.close();
				System.out.println("Data saved");
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		});

		openFile.setOnAction(event -> {
			try {
				StringBuilder sb = new StringBuilder();
				TextInputDialog dialog = new TextInputDialog();

				dialog.setTitle("Open a file");
				dialog.setHeaderText("Enter filename (no extension):");
				dialog.setContentText("Filename:");

				Optional<String> result = dialog.showAndWait();
				result.ifPresent(name -> { sb.append(name); });

				ObjectInputStream ois = new ObjectInputStream(
								new FileInputStream(System.getProperty("user.dir") + "/" + sb.toString() + ".dat")
				);

				boolean eofReached = false;
				int currentInt = ois.readInt();

				if (currentInt == 1)	shapeScene.setFill(Color.RED);
				else if (currentInt == 2)	shapeScene.setFill(Color.GREEN);
				else if (currentInt == 3)	shapeScene.setFill(Color.BLUE);

				shapeGroup.getChildren().clear();

				while (eofReached == false) {
					currentInt = ois.readInt();

					if (currentInt == 10000)		eofReached = true;
					else {
						if (currentInt == 1) {
							Sphere sphere = new Sphere(ois.readDouble());
							Translate t = new Translate(ois.readDouble(), ois.readDouble(), ois.readDouble());
							sphere.getTransforms().add(t);

							currentInt = ois.readInt();
							Rotate r = new Rotate();
							if (currentInt == 1)			r.setAxis(Rotate.X_AXIS);
							else if (currentInt == 2)	r.setAxis(Rotate.Y_AXIS);
							else if (currentInt == 3)	r.setAxis(Rotate.Z_AXIS);
							r.setAngle(ois.readDouble());
							sphere.getTransforms().add(r);

							Scale s = new Scale(ois.readDouble(), ois.readDouble(), ois.readDouble());
							sphere.getTransforms().add(s);

							currentInt = ois.readInt();
							if (currentInt > 0) {
								PhongMaterial pm = new PhongMaterial();
								if (currentInt == 1)			pm.setDiffuseColor(Color.RED);
								else if (currentInt == 2)	pm.setDiffuseColor(Color.GREEN);
								else if (currentInt == 3)	pm.setDiffuseColor(Color.BLUE);
								sphere.setMaterial(pm);
							}

							sphere.setOnMouseClicked(evt -> { selectedShape = sphere; });
							shapeGroup.getChildren().add(sphere);
						}
						else if (currentInt == 2) {
							Cylinder cylinder = new Cylinder(ois.readDouble(), ois.readDouble());
							Translate t = new Translate(ois.readDouble(), ois.readDouble(), ois.readDouble());
							cylinder.getTransforms().add(t);

							currentInt = ois.readInt();
							Rotate r = new Rotate();
							if (currentInt == 1)			r.setAxis(Rotate.X_AXIS);
							else if (currentInt == 2)	r.setAxis(Rotate.Y_AXIS);
							else if (currentInt == 3)	r.setAxis(Rotate.Z_AXIS);
							r.setAngle(ois.readDouble());
							cylinder.getTransforms().add(r);

							Scale s = new Scale(ois.readDouble(), ois.readDouble(), ois.readDouble());
							cylinder.getTransforms().add(s);

							currentInt = ois.readInt();
							if (currentInt > 0) {
								PhongMaterial pm = new PhongMaterial();
								if (currentInt == 1)			pm.setDiffuseColor(Color.RED);
								else if (currentInt == 2)	pm.setDiffuseColor(Color.GREEN);
								else if (currentInt == 3)	pm.setDiffuseColor(Color.BLUE);
								cylinder.setMaterial(pm);
							}

							cylinder.setOnMouseClicked(evt -> { selectedShape = cylinder; });
							shapeGroup.getChildren().add(cylinder);
						}
						else if (currentInt == 3) {
							Box box = new Box(ois.readDouble(), ois.readDouble(), ois.readDouble());
							Translate t = new Translate(ois.readDouble(), ois.readDouble(), ois.readDouble());
							box.getTransforms().add(t);

							currentInt = ois.readInt();
							Rotate r = new Rotate();
							if (currentInt == 1)			r.setAxis(Rotate.X_AXIS);
							else if (currentInt == 2)	r.setAxis(Rotate.Y_AXIS);
							else if (currentInt == 3)	r.setAxis(Rotate.Z_AXIS);
							r.setAngle(ois.readDouble());
							box.getTransforms().add(r);

							Scale s = new Scale(ois.readDouble(), ois.readDouble(), ois.readDouble());
							box.getTransforms().add(s);

							currentInt = ois.readInt();
							if (currentInt > 0) {
								PhongMaterial pm = new PhongMaterial();
								if (currentInt == 1)			pm.setDiffuseColor(Color.RED);
								else if (currentInt == 2)	pm.setDiffuseColor(Color.GREEN);
								else if (currentInt == 3)	pm.setDiffuseColor(Color.BLUE);
								box.setMaterial(pm);
							}

							box.setOnMouseClicked(evt -> { selectedShape = box; });
							shapeGroup.getChildren().add(box);
						}
					}
				}

				ois.close();
				System.out.println("Data loaded");
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		});

		VBox controlSet = new VBox(30, axisBox, transBox, rotateBox, scaleBox, switchBox, subSceneBox, shapeBox);
		controlSet.setAlignment(Pos.CENTER);
		HBox sceneBox = new HBox(1, shapeScene, controlSet);
		VBox mainBox = new VBox(1, menuBar, sceneBox);

		Scene scene = new Scene(mainBox, 1000, 700);

		// Set the stage title
		primaryStage.setTitle("Homework 4");

		// Set the scene
		primaryStage.setScene(scene);

		// Show the window
		primaryStage.show();
	}
}
