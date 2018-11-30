import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Polygon;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.geometry.Pos;
import javafx.animation.TranslateTransition;
import javafx.animation.FillTransition;
import javafx.animation.RotateTransition;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

public class Homework4 extends Application
{
	private String activeShape = "";
	private boolean[][] areasAnimated;

	public static void main(String[] args)
	{
		// Launch the application
		launch(args);
	}

	@Override
	public void start(Stage primaryStage)
	{
		areasAnimated = new boolean[3][4];
		for (int i = 0; i < 12; ++i)	areasAnimated[i/4][i%4] = false;

		Circle circle = new Circle(150.0, 150.0, 50.0, Color.RED);
		Rectangle rectangle = new Rectangle(300.0, 100.0, 100.0, 100.0);
		rectangle.setFill(Color.RED);
		Polygon triangle = new Polygon(500.0, 200.0, 550.0, 100.0, 600.0, 200.0);
		triangle.setFill(Color.RED);

		circle.setOnMouseClicked((event) -> { activeShape = "circle"; });
		rectangle.setOnMouseClicked((event) -> { activeShape = "rectangle"; });
		triangle.setOnMouseClicked((event) -> { activeShape = "triangle"; });

		Button translate = new Button("Translate");
		Button fill = new Button("Fill");
		Button rotate = new Button("Rotate");
		Button fade = new Button("Fade");

		TranslateTransition moveDown = new TranslateTransition(new Duration(500));
		FillTransition turnBlue = new FillTransition(new Duration(500));
		RotateTransition turn180 = new RotateTransition(new Duration(500));
		FadeTransition fadeOut = new FadeTransition(new Duration(500));

		translate.setOnAction((event) -> {
			if (!activeShape.equals("")) {
				int row = 0;
				int col = 0;

				if (activeShape.equals("rectangle")) {
					row = 1;
					moveDown.setNode(rectangle);
				}
				else if (activeShape.equals("triangle")) {
					row = 2;
					moveDown.setNode(triangle);
				}
				else
					moveDown.setNode(circle);

				if (areasAnimated[row][col] == false) {
					moveDown.setToY(100.0);
					moveDown.play();
					areasAnimated[row][col] = true;
				}
			}
		});

		fill.setOnAction((event) -> {
			if (!activeShape.equals("")) {
				int row = 0;
				int col = 1;

				if (activeShape.equals("rectangle")) {
					row = 1;
					turnBlue.setShape(rectangle);
				}
				else if (activeShape.equals("triangle")) {
					row = 2;
					turnBlue.setShape(triangle);
				}
				else
					turnBlue.setShape(circle);

				if (areasAnimated[row][col] == false) {
					turnBlue.setToValue(Color.BLUE);
					turnBlue.play();
					areasAnimated[row][col] = true;
				}
			}
		});

		rotate.setOnAction((event) -> {
			if (!activeShape.equals("")) {
				int row = 0;
				int col = 2;

				if (activeShape.equals("rectangle")) {
					turn180.setNode(rectangle);
					row = 1;
				}
				else if (activeShape.equals("triangle")) {
					turn180.setNode(triangle);
					row = 2;
				}
				else
					turn180.setNode(circle);


				if (areasAnimated[row][col] == false) {
					turn180.setToAngle(180.0);
					turn180.play();
					areasAnimated[row][col] = true;
				}
			}
		});

		fade.setOnAction((event) -> {
			if (!activeShape.equals("")) {
				int row = 0;
				int col = 3;

				if (activeShape.equals("rectangle")) {
					fadeOut.setNode(rectangle);
					row = 1;
				}
				else if (activeShape.equals("triangle")) {
					fadeOut.setNode(triangle);
					row = 2;
				}
				else
					fadeOut.setNode(circle);

				if (areasAnimated[row][col] == false) {
					fadeOut.setFromValue(0.0);
					fadeOut.setToValue(1.0);
					fadeOut.play();
					areasAnimated[row][col] = true;
				}
			}
		});

		HBox box = new HBox(80, translate, fill, rotate, fade);
		box.setTranslateX(110.0);
		box.setTranslateY(450.0);
		box.setAlignment(Pos.CENTER);

		Group group = new Group(circle, rectangle, triangle, box);

		Scene scene = new Scene(group, 700, 500);

		// Set the stage title
		primaryStage.setTitle("Homework 4");

		// Set the scene
		primaryStage.setScene(scene);

		// Show the window
		primaryStage.show();
	}
}
