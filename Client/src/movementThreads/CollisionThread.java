package movementThreads;

import controllers.Controller;
import javafx.scene.image.ImageView;

/**
 * This class is in charge of handling the collisions of the game.
 *
 * @author Alina
 * @author Kevin
 * @author Adriana
 */
public class CollisionThread extends Thread {

    private final ImageView[] invadersBullets;
    private final ImageView spaceShipBullet;
    private final ImageView spaceShip;
    private ImageView[] invaders;

    private boolean stopCollisionThread = false;

    public CollisionThread(ImageView[] invadersBullets, ImageView spaceShipBullet, ImageView[] invaders, ImageView spaceShip, ImageView invaderBonus) {
        this.invadersBullets = invadersBullets;
        this.spaceShipBullet = spaceShipBullet;
        this.invaders = invaders;
        this.spaceShip = spaceShip;
        addInvaderBonus(invaderBonus);
    }

    /**
     * change the value of the variable to indicate that the thread should stop
     */
    public void stopThread() {
        stopCollisionThread = true;
    }

    /**
     * add extra ship to invader vector
     *
     * @param invaderBonus
     */
    public void addInvaderBonus(ImageView invaderBonus) {
        ImageView[] temp = new ImageView[invaders.length + 1];
        for (int i = 0; i < invaders.length; i++) {
            temp[i] = invaders[i];
        }
        temp[temp.length - 1] = invaderBonus;
        invaders = temp;
    }

    /**
     * is in charge of constantly checking if the ship, the invaders or the
     * extra ship have been hit by the bullet as well as verifying if the
     * invaders have already reached the allowed limit
     */
    @Override
    public void run() {
        while (!stopCollisionThread) {
            //SpaceShip bullet vs Invaders
            if (spaceShipBullet.isVisible()) {
                for (int i = 0; i < invaders.length; i++) {
                    if (invaders[i].isVisible()) {
                        if (spaceShipBullet.getBoundsInParent().intersects(invaders[i].getBoundsInParent())) {
                            Controller.getInstance().collidedInvader(i);
                            invaders[i].setVisible(false);
                            spaceShipBullet.setVisible(false);
                            invaders[i].setVisible(false);
                        }
                    }
                }
            }
            //Invaders bullets vs spaceship
            for (ImageView invadersBullet : invadersBullets) {
                if (invadersBullet.isVisible() && invadersBullet.getBoundsInParent().intersects(spaceShip.getBoundsInParent())) {
                    Controller.getInstance().collidedSpaceship();
                    invadersBullet.setVisible(false);
                }
            }
            //Invaders vs spaceship
            for (ImageView invader : invaders) {
                if (invader.isVisible()) {
                    if (spaceShip.getBoundsInParent().intersects(invader.getBoundsInParent())) {
                        Controller.getInstance().gameOver();
                    }
                }
            }
        }
    }
}
