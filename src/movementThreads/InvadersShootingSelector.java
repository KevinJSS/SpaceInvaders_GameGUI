package movementThreads;

import javafx.scene.image.ImageView;

/**
 * This class is in charge of selecting the invader who throws a bullet.
 *
 * @author Alina
 * @author Kevin
 * @author Adriana
 */
public class InvadersShootingSelector extends Thread {

    private final ImageView[] invaders;
    private final ImageView[] bullets;
    private boolean stopThread = false;
    private static final int MIN = 500;
    private static final int MAX = 2000;

    public InvadersShootingSelector(ImageView[] invaders, ImageView[] bullets) {
        this.invaders = invaders;
        this.bullets = bullets;
    }

    /**
     * change the value of the variable to indicate that the thread should stop
     */
    public void stopThread() {
        stopThread = true;
    }

    /**
     * pick a random invader from those visible to fire a bullet
     */
    @Override
    public void run() {
        ImageView selectedBullet;
        while (!stopThread) {
            try {
                selectedBullet = bullets[randomNumber(0, bullets.length - 1)];

                if (!selectedBullet.isVisible()) {
                    new ShootingAnimation(selectedBullet, invaders[randomNumber(0, invaders.length - 1)], 468, 18, 180, 19, 30).start();
                }

                sleep(randomNumber(MIN, MAX));
            } catch (InterruptedException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    /**
     * generates a random number between the two received numbers
     *
     * @param first
     * @param last
     * @return
     */
    private static int randomNumber(int first, int last) {
        int number = (int) (Math.random() * (last - first + 1) + first);
        return (number < 0) ? (number * -1) : number;
    }
}
