package movementThreads;

import javafx.scene.image.ImageView;

/**
 * This class handles the animation of the invaders.
 *
 * @author Alina
 * @author Kevin
 * @author Adriana
 */
public class InvadersMovement extends Thread {

    private final ImageView[] invaders;
    private final int DESCENT_VELOCITY = 20;
    private boolean stopMovement = false;
    private int currentBoundary = 122;
    private int stepVelocity = 4;
    private int sleepTime = 500;

    public InvadersMovement(ImageView[] invaders) {
        this.invaders = invaders;
    }

    /**
     * change the value of the variable to indicate that the thread should stop
     */
    public void stopMovement() {
        stopMovement = true;
    }

    /**
     * start the movement of the invaders
     */
    @Override
    public void run() {
        while (!stopMovement) { //While !stop && !collition
            try {
                for (int i = 0; i < invaders.length; i++) {
                    //Invaders movement X
                    invaders[i].setX(invaders[i].getX() + stepVelocity);
                    //Bound validation
                    if (touchedBound()) {
                        descentInvaders();
                        reverseMotionData();
                        break;
                    }
                }
                sleep(sleepTime);
            } catch (InterruptedException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    /**
     * if the invaders touch the edge of the screen they descend
     */
    private void descentInvaders() {
        for (ImageView invader : invaders) {
            invader.setX(invader.getX() + (stepVelocity * -1));
            invader.setY(invader.getY() + DESCENT_VELOCITY); //Movement Y
        }
    }

    /**
     * change the direction and speed of the invaders when colliding with the
     * edge of the screen
     */
    private void reverseMotionData() {
        stepVelocity *= -1;
        currentBoundary *= -1;
        stepVelocity += (currentBoundary > 0) ? 2 : -2;
        sleepTime -= 3;
    }

    /**
     * indicates if invaders are on the left or right screen boundary
     *
     * @return boolean
     */
    private boolean touchedBound() {
        if (currentBoundary > 0 && invaders[invaders.length - 1].getX() >= currentBoundary) {
            return true;
        }
        if (currentBoundary < 0 && invaders[invaders.length - 1].getX() <= currentBoundary) {
            return true;
        }
        return false;
    }
}
