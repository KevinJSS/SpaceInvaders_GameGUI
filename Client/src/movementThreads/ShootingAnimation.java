package movementThreads;

import javafx.scene.image.ImageView;

/**
 * This class is in charge of the animation of the bullet.
 *
 * @author Alina
 * @author Kevin
 * @author Adriana
 */
public class ShootingAnimation extends Thread {

    private final int DEFAULT_LAYOUT_X = 39;
    private final int DEFAULT_LAYOUT_Y = 527;
    private final int EXTRA_LAYOUT_X;
    private final int EXTRA_LAYOUT_Y;
    private final ImageView bullet;
    private final ImageView shooter;
    private final int BULLET_STEP;
    private final int SLEEP_TIME;
    private final int BOUNDARY;
    private boolean collision = false;

    public ShootingAnimation(ImageView bullet, ImageView shooter, int BOUNDARY, int BULLET_STEP, int SLEEP_TIME, int EXTRA_LAYOUT_X, int EXTRA_LAYOUT_Y) {
        this.bullet = bullet;
        this.shooter = shooter;
        this.BOUNDARY = BOUNDARY;
        this.BULLET_STEP = BULLET_STEP;
        this.SLEEP_TIME = SLEEP_TIME;
        this.EXTRA_LAYOUT_X = EXTRA_LAYOUT_X;
        this.EXTRA_LAYOUT_Y = EXTRA_LAYOUT_Y;
    }

    /**
     * indicates if the bullet has touched an invader
     */
    public void bulletCollision() {
        collision = true;
    }

    /**
     * if the ship is visible the bullet will position itself in the ship's
     * position to be fired
     */
    @Override
    public void run() {
        if (shooter.isVisible()) {
            setBulletLayout(getShooterLocation());
            bullet.setVisible(true);
            while (!touchedBound() && !collision) {
                try {
                    sleep(SLEEP_TIME);
                    bullet.setY(bullet.getY() + BULLET_STEP);
                } catch (InterruptedException ex) {
                    System.err.println(ex.getMessage());
                }
            }
            bullet.setVisible(false);
            resetBulletLocation();
        }
    }

    /**
     * receives a vector and configures the position of the bullet according to
     * position one and two of the received vector
     *
     * @param coord
     */
    private void setBulletLayout(int[] coord) {
        bullet.setLayoutX(coord[0]);
        bullet.setLayoutY(coord[1]);
    }

    /**
     * returns the position in which the ship is located
     *
     * @return int[]
     */
    private int[] getShooterLocation() {
        int x = (int) (shooter.getLayoutX() + shooter.getX() + EXTRA_LAYOUT_X);
        int y = (int) (shooter.getLayoutY() + shooter.getY() + EXTRA_LAYOUT_Y);
        return new int[]{x, y};
    }

    /**
     * set the bullet position to its initial values
     */
    private void resetBulletLocation() {
        bullet.setLayoutX(DEFAULT_LAYOUT_X);
        bullet.setLayoutY(DEFAULT_LAYOUT_Y);
        bullet.setY(shooter.getY());
    }

    /**
     * returns if the bullet touched the screen boundary
     *
     * @return boolean
     */
    private boolean touchedBound() {
        if (BULLET_STEP < 0 && bullet.getY() < BOUNDARY) {
            return true;
        } else if (BULLET_STEP < 0) {
            return false;
        }

        if (bullet.getY() >= (BOUNDARY - bullet.getLayoutY())) {
            return true;
        } else {
            return false;
        }
    }
}
