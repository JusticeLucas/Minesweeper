package cs1302.p1;

import cs1302.p1.Main;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Missle {

	boolean missleActive = true;
	boolean sameSpot;
	Rectangle missleShape = null;
	Pane pane;
	int reward;
	static Rectangle rect = Main.hitBox;

	/**
	 * Intializes Missle object
	 *
	 * @param pos launch position of a missile
	 * @param v the array of enemy ships the missile interacts with 
	 * @param p the pane on which the missile is shown
	 */
	public Missle( double pos, ImageView enemyShips[], Pane pane) {
		if (missleShape == null) {
			this.pane = pane;
			Timeline tlB;
			missleShape = new Rectangle(2, 10);
			pane.getChildren().add(missleShape);
			missleShape.setX(pos + 13);
			missleShape.setY(440);
			Duration dB = new Duration(4);
			KeyFrame fB = new KeyFrame(dB, e -> {
				if (missleShape != null) {
					missleShape.setY(missleShape.getY() - 5);
					missleCollison(enemyShips);
				}
			});
			tlB = new Timeline(fB);
			tlB.setCycleCount(Animation.INDEFINITE);
			tlB.play();
		}
	}// Missle Constructor

	/**
	 * Checks if a missile has collided with an enemy space ship
	 * 
	 * @param enemies
	 *            refers to the en
	 * @return
	 */
	private void missleCollison(ImageView enemyImageArray[]) {
		for (int i = 0; i < enemyImageArray.length; i++) {
			if (missleShape != null && enemyImageArray[i] != null) {
				
				if (spotCheck(enemyImageArray, i)) {
					enemyImageArray[i].setVisible(false);
					enemyImageArray[i] = null;
					removeMissle();
					missleActive = false;
					Main.score += 10;
					System.out.println(Main.score);
				} // if
			} // if
		} // for
		if (missleShape != null) {
			if (missleShape.getY() < 0 - missleShape.getHeight() - 1) {
				removeMissle();
			}
		} // remove missile if

	}// deathCheck
	
	/**
	 * Checks if the missile touches an enemy ship
	 * 
	 * @param enemyImageAray enemyShip array
	 * @param i specific enemy ship in the array
	 * @return
	 */
	private boolean spotCheck(ImageView enemyImageAray[], int i) {
		if (!(missleShape != null && missleShape.getX() < enemyImageAray[i].getX() + enemyImageAray[i].getFitWidth())) {
			return false;
		} else if (!(missleShape.getX() + missleShape.getWidth() > enemyImageAray[i].getX())) {
			return false;
		} else if (!(missleShape.getY() < enemyImageAray[i].getY() + enemyImageAray[i].getFitHeight())) {
			return false;
		} else if (missleShape.getHeight() + missleShape.getY() > enemyImageAray[i].getY()) {
			return false;
		} else {
			return true;
		}
	}// spotCheck

	public void getRectangle(Rectangle rect) {
		this.rect = rect;
	}// getRectangle

	public void removeMissle() {
		pane.getChildren().remove(missleShape);
		missleShape = null;
	}// removeBullet
}