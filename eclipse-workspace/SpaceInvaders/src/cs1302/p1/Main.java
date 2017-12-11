package cs1302.p1;

import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
	
	int scoreToBeatLevel = 150;
	int enemySize = 25;
	int numEnemyRows = 3;
	int numEnemyCols = 5;
	int speed = 1;
	int level = 1;

	int screenWidth = 500;
	int screenHeight = 500;

	public int record = 0;
	Missle missle;
	Pane pane = new Pane();
	Text levelWarning = new Text("");
	Button easy,hard;
	Image backgroundImg = new Image("Images/Background.png");
	ImageView back = new ImageView(backgroundImg);
	Image shipImg = new Image("Images/PlayerShip.png");
	ImageView ship = new ImageView(shipImg);
	Image enemyShipImg = new Image("Images/EnemyShip.png");
	
	public static Rectangle hitBox = new Rectangle();
	ImageView[] enemyImageArray = new ImageView[numEnemyCols * numEnemyRows];

	boolean rightEnemy = true;
	boolean nextWave = true;
	boolean isRunning = false;

	public static int score = 0;
	
	Text scoreText = new Text("Score: " + score);

	@Override
	public void start(Stage primaryStage) {

		ship.setFitHeight(25);
		ship.setFitWidth(25);
		ship.setX(100);
		ship.setY(440);

		back.setX(0);
		back.setY(0);

		scoreText.setX(10);
		scoreText.setY(20);
		scoreText.setStyle("-fx-font-family: Impact; -fx-fill: white; -fx-font-size: 20px;");

		
		easy = new Button("Easy");
		easy.setOnAction(e -> {
			difficultyModifier(0);
			wave();
		});
		easy.setLayoutX(250.0);
		easy.setLayoutY(250.0);
		
		hard = new Button("Hard");
		hard.setOnAction(e -> {
			difficultyModifier(2);
			wave();
		});
		hard.setLayoutX(250.0);
		hard.setLayoutY(280.0);
		
		pane.getChildren().addAll(back, ship, scoreText, easy, hard);
		
		Scene scene = new Scene(pane, screenWidth, screenHeight);
		scene.setOnKeyPressed(e -> userInput(e));

		primaryStage.setTitle("SpaceInvaders");
		primaryStage.setScene(scene);
		primaryStage.show();

	}// start
	
	public void difficultyModifier(int difficulty) {
		speed += difficulty * 2;
		scoreToBeatLevel = 10*numEnemyRows*numEnemyCols;
	}// difficultyModifier

	public void wave() {
		pane.getChildren().removeAll(easy,hard);
		for (int x = 0; x < numEnemyRows; x++) {
			for (int y = 0; y < numEnemyCols; y++) {
				enemyImageArray[x * numEnemyCols + y] = new ImageView(enemyShipImg);
				enemyImageArray[x * numEnemyCols + y].setX(y * 40);
				enemyImageArray[x * numEnemyCols + y].setY(x * 40);
				enemyImageArray[x * numEnemyCols + y].setFitWidth(enemySize);
				pane.getChildren().add(enemyImageArray[x * numEnemyCols + y]);
				if (y == (numEnemyCols - 1) && x == 0) {
					hitBox.setWidth(enemySize);
					hitBox.setHeight(enemySize);
					hitBox.setX(enemyImageArray[y].getX() + enemySize);
				} // sets hitBox
			} // inner for loop
		} // outer for loop
		
		Duration duration = new Duration(30);
		KeyFrame frame = new KeyFrame(duration, e -> enemyMovement());
		Timeline tl = new Timeline(frame);
		if (isRunning) {
			tl.stop();
			isRunning = false;
		}
		tl.setCycleCount(Animation.INDEFINITE);
		tl.play();
		isRunning = true;
	}// wave
	public void userInput(KeyEvent key) {
		
		
		if (key.getCode() == KeyCode.D) {
			double x = ship.getX();
			x += 10;
			ship.setX(x);
			scoreText.setText("Score: " + score);
		} else if (key.getCode() == KeyCode.A) {
			double x = ship.getX();
			x -= 10;
			ship.setX(x);
			scoreText.setText("Score: " + score);
		}//if

		if (key.getCode() == KeyCode.SPACE) {
			
			missle = new Missle(ship.getX(), enemyImageArray, pane);
			scoreText.setText("Score: " + score);

			if (score == (scoreToBeatLevel * level)) {
				
				levelWarning.setText("Press Backspace to go to level " + (level+1));
				levelWarning.setStyle("-fx-font-family: Impact; -fx-fill: white; -fx-font-size: 20px;");
				levelWarning.setY(300);levelWarning.setX(100);
				pane.getChildren().add(levelWarning);
				
				
			}//if
		}//if controls when a missile is released
		
		if ((key.getCode() == KeyCode.BACK_SPACE) && (score == (scoreToBeatLevel * level))) {
			levelWarning.setText("   ");
			level++;
			speed++;
			wave();
			
		}//if
		
	}// userInput

	public void enemyMovement() {

		if (rightEnemy) {
			if (hitBox.getX() + enemySize >= screenWidth) {
				rightEnemy = false;
				for (int i = 0; i < enemyImageArray.length; i++) {
					if (enemyImageArray[i] != null) {
						enemyImageArray[i].setY(enemyImageArray[i].getY() + 50);
					} // if
				} // for
			} // checks enemy direction and if the array of enemies has collided with the
				// screen's edge

			for (int x = 0; x < enemyImageArray.length; x++) {
				if (enemyImageArray[x] != null) {
					enemyImageArray[x].setX(enemyImageArray[x].getX() + speed);
				} // if
			} // moves the enemy
			hitBox.setX(hitBox.getX() + speed); // moves the hitBox 
		} else {
			if (hitBox.getX() - ((enemySize * (numEnemyCols + 3))) <= 0) {
				rightEnemy = true;
				for (int i = 0; i < enemyImageArray.length; i++) {
					if (enemyImageArray[i] != null) {
						enemyImageArray[i].setY(enemyImageArray[i].getY() + 50);
						if(enemyImageArray[i].getY() > 440) {
							gameOver();
						}//If enemy array reaches player ship game is over
					}//moves enemy array down when right boundry is met
				}
			}
			for (int x = 0; x < enemyImageArray.length; x++) {
				if (enemyImageArray[x] != null) {
					enemyImageArray[x].setX(enemyImageArray[x].getX() - speed);
				}
			}
			hitBox.setX(hitBox.getX() - speed);
		} // else
		
		
	}// enemyMovement
	
	public void gameOver() {
		Text gameOver = new Text("GAME OVER");
		gameOver.setStyle("-fx-font-family: Impact; -fx-fill: white; -fx-font-size: 100px;");
		gameOver.setX(50);
		gameOver.setY(150);
		
		Button exit = new Button("EXIT");
		exit.setLayoutX(250);
		exit.setLayoutY(250);
		
		pane.getChildren().addAll(gameOver, exit);
	}
	public static void main(String[] args) {
		launch(args);
	}
}