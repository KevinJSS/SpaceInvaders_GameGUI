package movementThreads;

import javafx.scene.image.ImageView;

/**
 * This class handles the animation of the extra invading ship.
 * 
 * @author Alina
 * @author Kevin
 * @author Adriana
 */
public class ExtraInvaderMovement extends Thread {
    
    
    private final int STEP_VELOCITY = 30;
    private final int SLEEP_TIME = 214;
    private final int MAX = 5000;
    private final int MIN = 2000;
    private final double LEFT_LAYOUT_X = -45.0;
    private final double RIGHT_LAYOUT_X = 785.0;
    
    private final ImageView invaderBonus;
    private boolean stopInvaderBonus = false;
    private int currentBoundary;
    
    public ExtraInvaderMovement(ImageView invaderBonus) {
        this.invaderBonus = invaderBonus;
    }
    
    /**
     * change the value of the variable to indicate that the thread should stop
     */
    public void stopInvaderBonus() {
        stopInvaderBonus = true;
    }

    /**
     * show the extra ship within a random time
     */
    @Override
    public void run() {
        while (!stopInvaderBonus) {
            try {
                sleep(randomNumber(MIN, MAX));
                moveInvaderBonus();
                resetInvaderBonus();
            } catch (InterruptedException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
    
    /**
     * generates one or two, if it is one it will move to the right and if it is
     * two it will move to the left
     */
    private void moveInvaderBonus() {
        int dir = randomNumber(1, 2);
        if (dir == 1) {
            moveRight();
        } else {
            moveLeft();
        }
    }

    /**
     * is responsible for placing the invasor extra on the right side of the
     * screen to make a movement from right to left
     */
    private void moveLeft() {
        invaderBonus.setLayoutX(RIGHT_LAYOUT_X);
        invaderBonus.setVisible(true);
        int i = 40;
        while (i != 0) {
            try {
                invaderBonus.setX(invaderBonus.getX() - STEP_VELOCITY);
                sleep(SLEEP_TIME);
            } catch (InterruptedException ex) {
                System.err.println(ex.getMessage());
            }
            i--;
        }
    }

    /**
     * is responsible for placing the invasor extra on the left side of the
     * screen to make a movement from left to right
     */
    private void moveRight() {
        invaderBonus.setLayoutX(LEFT_LAYOUT_X);
        invaderBonus.setVisible(true);
        int i = 40;
        while (i != 0) {
            try {
                invaderBonus.setX(invaderBonus.getX() + STEP_VELOCITY);
                sleep(SLEEP_TIME);
            } catch (InterruptedException ex) {
                System.err.println(ex.getMessage());
            }
            i--;
        }
    }

    /**
     * places the extra invader in its original position and makes it invisible
     */
    private void resetInvaderBonus() {
        invaderBonus.setLayoutX(LEFT_LAYOUT_X);
        invaderBonus.setVisible(false);
        invaderBonus.setX(LEFT_LAYOUT_X);
    }
    
    /**
     * generates a random number between the received numbers
     * 
     * @param first
     * @param last
     * @return int
     */
    private int randomNumber(int first, int last) {
        int number = (int) (Math.random() * (last - first + 1) + first);
        return (number < 0) ? (number * -1) : number;
    }
}
