package controllers;

import client.Client;
import javafx.scene.input.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import movementThreads.CollisionThread;
import movementThreads.ExtraInvaderMovement;
import movementThreads.InvadersMovement;
import movementThreads.InvadersShootingSelector;
import movementThreads.ShootingAnimation;

/**
 * This class is the controller of all views.
 *
 * @author Alina
 * @author Kevin
 * @author Adriana
 */
public class Controller implements Initializable {

    private static Client connection;

    //START STARTVIEW
    @FXML
    private Label lbTitle;
    @FXML
    private Button btSignUp;
    @FXML
    private Button btLogIn;
    @FXML
    private Button btExit;
    //END STARTVIEW

    //START USERVIEW
    @FXML
    private Label lbDinamicTitle;
    @FXML
    private Label lbSubtitle;
    @FXML
    private Label lbUserName;
    @FXML
    private TextField tfUserName;
    @FXML
    private Label lbPassword;
    @FXML
    private TextField tfPassword;
    @FXML
    private Button btGoBack;
    @FXML
    private Button btAccpt;
    //END USERVIEW

    //START MENUVIEW
    @FXML
    private AnchorPane menuView;
    @FXML
    private Button btPlay;
    @FXML
    private Button btScores;
    @FXML
    private Button btInstructions;
    @FXML
    private Button btBackMenu;
    //END MENUVIEW

    //START INFOVIEW
    @FXML
    private TextArea txtArea;
    @FXML
    private Button btBackInfo;
    @FXML
    private Label lbDinamicInfo;
    //END INFOVIEW

    //START GAMEVIEW
    @FXML
    private AnchorPane gameView;
    @FXML
    private Button btBackGame;
    @FXML
    private Label lbTxtPoints;
    @FXML
    private static Label lbNumberPoints;
    @FXML
    private Label lbTxtLive;
    @FXML
    private Label lbNumerLives;
    @FXML
    private ImageView spaceShip;
    @FXML
    private ImageView inv40pts1;
    @FXML
    private ImageView inv40pts2;
    @FXML
    private ImageView inv40pts3;
    @FXML
    private ImageView inv40pts4;
    @FXML
    private ImageView inv40pts5;
    @FXML
    private ImageView inv40pts6;
    @FXML
    private ImageView inv40pts7;
    @FXML
    private ImageView inv40pts8;
    @FXML
    private ImageView inv40pts10;
    @FXML
    private ImageView inv40pts9;
    @FXML
    private ImageView inv20pts1;
    @FXML
    private ImageView inv20pts2;
    @FXML
    private ImageView inv20pts3;
    @FXML
    private ImageView inv20pts4;
    @FXML
    private ImageView inv20pts5;
    @FXML
    private ImageView inv20pts6;
    @FXML
    private ImageView inv20pts7;
    @FXML
    private ImageView inv20pts8;
    @FXML
    private ImageView inv20pts9;
    @FXML
    private ImageView inv20pts10;
    @FXML
    private ImageView inv10pts1;
    @FXML
    private ImageView inv10pts2;
    @FXML
    private ImageView inv10pts3;
    @FXML
    private ImageView inv10pts4;
    @FXML
    private ImageView inv10pts5;
    @FXML
    private ImageView inv10pts6;
    @FXML
    private ImageView inv10pts7;
    @FXML
    private ImageView inv10pts8;
    @FXML
    private ImageView inv10pts9;
    @FXML
    private ImageView inv10pts10;
    @FXML
    private ImageView extraInvader;
    @FXML
    private ImageView spaceShipBullet;
    @FXML
    private ImageView invaderBullet1;
    @FXML
    private ImageView invaderBullet2;
    @FXML
    private ImageView invaderBullet3;
    @FXML
    private ImageView invaderBullet4;
    @FXML
    private ImageView invaderBullet5;
    @FXML
    private Label lbPlayerName;

    private static ImageView[] invadersShips;
    private static int spaceShipX;

    private static final int GAME_VIEW_LEFT_BOUND = -360;
    private static final int GAME_VIEW_RIGHT_BOUND = 360;
    private static final int GAME_VIEW_TOP_BOUND = -427;

    private static String playerName = "";
    private static int playerScore = 0;
    private static int playerLifes = 3;
    private static InvadersMovement invadersMovementThread;
    private static InvadersShootingSelector invadersShootingSelector;
    private static ExtraInvaderMovement extraInvaderMovement;
    private static CollisionThread collisionThread;
    private static ShootingAnimation shootingAnimation;
    //END GAMEVIEW

    private static final Controller INSTANCE = new Controller();
    
    public void setClient(Client connection) {
        this.connection = connection;
    }
    
    public static synchronized Controller getInstance() {
        return INSTANCE; //Sabemos que el controlador debe ser privado, pero FXML no lo permite
    }

    /**
     * it has the events of the StartView window to know which button has been
     * pressed
     *
     * @param event
     * @throws IOException
     */
    @FXML
    private void startView(ActionEvent event) throws IOException {
        if (event.getSource() == btSignUp) {
            openUserViewSignUp();
            closeView(btSignUp);
            return;
        }
        if (event.getSource() == btLogIn) {
            openUserViewLogIn();
            closeView(btLogIn);
            return;
        }
        if (event.getSource() == btExit) {
            connection.sendRequest("exit");
            JOptionPane.showMessageDialog(null, "\u269D Gracias por jugar \u269D");
            connection.closeConnection();
            System.exit(0);
        }
    }

    /**
     * it has the events of the UserView window to know which button has been
     * pressed
     *
     * @param event
     */
    @FXML
    private void userView(ActionEvent event) {
        if (event.getSource() == btAccpt) {
            if (lbDinamicTitle.getText().equals("REGISTRARSE")) {
                try {
                    connection.sendRequest("signup");
                    connection.sendClient(tfUserName.getText(), tfPassword.getText());
                    if (connection.readBoolean()) {
                        playerName = connection.readString();
                        openMenuView();
                        closeView(btAccpt);
                        return;
                    }
                    JOptionPane.showMessageDialog(null, connection.readString());
                    openStartView();
                    closeView(btAccpt);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            if (lbDinamicTitle.getText().equals("INGRESAR")) {
                try {
                    connection.sendRequest("login");
                    connection.sendClient(tfUserName.getText(), tfPassword.getText());
                    if (connection.readBoolean()) {
                        playerName = connection.readString();
                        openMenuView();
                        closeView(btAccpt);
                        return;
                    }
                    JOptionPane.showMessageDialog(null, connection.readString());
                    openStartView();
                    closeView(btAccpt);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            return;
        }
        if (event.getSource() == btGoBack) {
            openStartView();
            closeView(btGoBack);
        }
    }

    /**
     * it has the events of the MenuView window to know which button has been
     * pressed
     *
     * @param event
     */
    @FXML
    private void menuView(ActionEvent event) {
        if (event.getSource() == btPlay) {
            openGameView();
            closeView(btPlay);
            return;
        }
        if (event.getSource() == btScores) {
            openInfoViewScores();
            closeView(btScores);
            return;
        }
        if (event.getSource() == btInstructions) {
            openInfoViewInstructions();
            closeView(btInstructions);
            return;
        }
        if (event.getSource() == btBackMenu) {
            openStartView();
            closeView(btBackMenu);
        }
    }

    /**
     * it has the events of the InfoView window to know which button has been
     * pressed
     *
     * @param event
     */
    @FXML
    private void infoView(ActionEvent event) {
        if (event.getSource() == btBackInfo) {
            openMenuView();
            closeView(btBackInfo);
        }
    }

    /**
     * it has the events of the GameView window to know which button has been
     * pressed
     *
     * @param event
     */
    @FXML
    private void gameView(ActionEvent event) {
        if (event.getSource() == btBackGame) {
            openMenuView();
            stopThreads();
            playerScore = 0;
            playerLifes = 3;
            closeView(btBackGame);
        }
    }

    /**
     * open the StartView window
     */
    private void openStartView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/StartView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Ventana de inicio");
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * open the UserView window first time person registration
     */
    private void openUserViewSignUp() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/UserView.fxml"));
            Parent root = loader.load();
            ((Controller) loader.getController()).setLabelUserView("REGISTRARSE");
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Ventana de registro");
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * open the UserView window autenthicate the person
     */
    private void openUserViewLogIn() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/UserView.fxml"));
            Parent root = loader.load();
            ((Controller) loader.getController()).setLabelUserView("INGRESAR");
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Ventana de ingreso");
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * open MenuView window
     */
    private void openMenuView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MenuView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Menu de juego");
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * open the InfoView for see the scores
     */
    private void openInfoViewScores() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/InfoView.fxml"));
            Parent root = loader.load();
            ((Controller) loader.getController()).setLabelInfo("TOP PUNTAJE");
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Ventana de puntuaciones");
            stage.setResizable(false);
            connection.sendRequest("scores");
            ((Controller) loader.getController()).setPlayersScore(connection.readString());
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * open the InfoView for see the instructions
     */
    private void openInfoViewInstructions() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/InfoView.fxml"));
            Parent root = loader.load();
            ((Controller) loader.getController()).setLabelInfo("INSTRUCCIONES");
            ((Controller) loader.getController()).setIntructions();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Ventana de instrucciones");
            stage.setResizable(false);
            stage.show();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * open the GameView
     */
    private void openGameView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/GameView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Ventana de juego");
            ((Controller) loader.getController()).lbPlayerName.setText(playerName);
            stage.setResizable(false);
            stage.show();

            setKeyListener(stage, loader);
            startThreads(loader);
            
            System.out.println("INICIO DEL JUEGO");
            System.out.println("\u269D Vidas del jugador: " + playerLifes);
            System.out.println("\u269D Puntaje: " + playerScore + "\n");
            
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * stops the game threads and sends a message notifying the player that he
     * has lost
     */
    public void gameOver() {
        stopThreads();
        JOptionPane.showMessageDialog(null, "GAME OVER!");
    }
    
    /**
     * if the ship hits an invader it will send the index of the hit ship to the
     * server and receive the points obtained from the player
     *
     * @param invaderIndex
     */
    public void collidedInvader(int invaderIndex) {
        try {
            shootingAnimation.bulletCollision();

            connection.sendRequest("invaderpoints");
            connection.sendInt(invaderIndex);
            playerScore += connection.readInt();

            System.out.println("\u269D Puntaje: " + playerScore);

            checkGameStatus();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * subtracts a life from the player each time he is hit, prints the number
     * of available lives of the player
     */
    public void collidedSpaceship() {
        if ((--playerLifes) == 0) {
            gameOver();
        }
        System.out.println("\u269D Vidas del jugador: " + playerLifes);
    }

    /**
     * check if the player has already killed all the invaders, if so, end the
     * game and notify the player of his victory
     */
    private void checkGameStatus() {
        if (oneLeft()) {
            stopThreads();
            String username = playerName;
            String score = String.valueOf(playerScore);
            JOptionPane.showMessageDialog(null, username + ", ganaste!!!\n\u269D Puntuaci\u00F3n obtenida: " + score);
            try {
                connection.sendRequest("newscore");
                connection.sendString(username);
                connection.sendString(score);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }
    
    /**
     * returns if all the invaders have already been defeated
     * 
     * @return boolean
     */
    private boolean oneLeft() {
        int counter = 0;
        for (ImageView invadersShip : invadersShips) {
            if (!invadersShip.isVisible()) {
                counter++;
            }
        }
        return (30 - counter == 1);
    }

    /**
     * modify the label of the UserView with the received text
     */
    private void setLabelUserView(String txt) {
        lbDinamicTitle.setText(txt);
    }

    /**
     * modify the label of the UserView with the received text
     */
    private void setLabelInfo(String txt) {
        lbDinamicInfo.setText(txt);
    }

    /**
     * enter the game instructions in the text area
     */
    private void setIntructions() {
        txtArea.setText("\u2022 El jugador controla una nave y la utiliza para combatir a los invasores \n  que se aproximan."
                + "\n\u2022 Si el jugador toca a un invasor o uno de sus disparos, o si los invasores \n  llegan a la parte"
                + " inferior de la pantalla, el jugador pierde."
                + "\n\u2022 Si el jugador pierde todas sus vidas, acabará la partida."
                + "\n\u2022 Se puede disparar nuevamente solo si la bala no se encuentra en la pantalla." 
                + "\n\u2022 Para disparar se utiliza el espacio y para desplazarse las teclas de izquierda  \n  y derecha." 
                + "\n\u2022 Los invasores fucsia equivalen a 40 puntos." 
                + "\n\u2022 Los invasores naranjas equivalen a 20 puntos." 
                + "\n\u2022 Los invasores verdes equivalen a 10 puntos." 
                + "\n\u2022 La nave extra aparecerá arriba de la pantalla, y equivale entre 80 y 150 puntos.");
        txtArea.setFont(Font.font("Verdana", 12));
    }

    /**
     * enter the ten best scores in the text area
     *
     * @param playersScore
     * @throws IOException
     */
    private void setPlayersScore(String playersScore) throws IOException {
        txtArea.setText(playersScore);
        txtArea.setFont(Font.font("Verdana", 14));
    }

    /**
     * enter the points to the game points label
     *
     * @param score
     */
    public static void setScoreLabel(String score) {
        lbNumberPoints.setText(score);
    }

    /**
     * stores all invaders into one vector so that they can be used on threads
     * 
     * @param loader
     * @return ImageView[]
     */
    private ImageView[] getInvaderShips(FXMLLoader loader) {
        invadersShips = new ImageView[]{
            ((Controller) loader.getController()).inv40pts1,
            ((Controller) loader.getController()).inv40pts2,
            ((Controller) loader.getController()).inv40pts3,
            ((Controller) loader.getController()).inv40pts4,
            ((Controller) loader.getController()).inv40pts5,
            ((Controller) loader.getController()).inv40pts6,
            ((Controller) loader.getController()).inv40pts7,
            ((Controller) loader.getController()).inv40pts8,
            ((Controller) loader.getController()).inv40pts9,
            ((Controller) loader.getController()).inv40pts10,
            ((Controller) loader.getController()).inv20pts1,
            ((Controller) loader.getController()).inv20pts2,
            ((Controller) loader.getController()).inv20pts3,
            ((Controller) loader.getController()).inv20pts4,
            ((Controller) loader.getController()).inv20pts5,
            ((Controller) loader.getController()).inv20pts6,
            ((Controller) loader.getController()).inv20pts7,
            ((Controller) loader.getController()).inv20pts8,
            ((Controller) loader.getController()).inv20pts9,
            ((Controller) loader.getController()).inv20pts10,
            ((Controller) loader.getController()).inv10pts1,
            ((Controller) loader.getController()).inv10pts2,
            ((Controller) loader.getController()).inv10pts3,
            ((Controller) loader.getController()).inv10pts4,
            ((Controller) loader.getController()).inv10pts5,
            ((Controller) loader.getController()).inv10pts6,
            ((Controller) loader.getController()).inv10pts7,
            ((Controller) loader.getController()).inv10pts8,
            ((Controller) loader.getController()).inv10pts9,
            ((Controller) loader.getController()).inv10pts10
        };
        return invadersShips;
    }

    /**
     * stores the bullets in a vector to be used in the threads
     * 
     * @param loader
     * @return ImageView[]
     */
    private ImageView[] getInvadersBullets(FXMLLoader loader) {
        return new ImageView[]{
            ((Controller) loader.getController()).invaderBullet1,
            ((Controller) loader.getController()).invaderBullet2,
            ((Controller) loader.getController()).invaderBullet3,
            ((Controller) loader.getController()).invaderBullet4,
            ((Controller) loader.getController()).invaderBullet5
        };
    }

    /**
     * initialize all threads in charge of executing the game
     * 
     * @param loader
     */
    private void startThreads(FXMLLoader loader) {
        invadersMovementThread = new InvadersMovement(((Controller) loader.getController()).getInvaderShips(loader));
        invadersMovementThread.start();

        invadersShootingSelector = new InvadersShootingSelector(((Controller) loader.getController()).getInvaderShips(loader),
                ((Controller) loader.getController()).getInvadersBullets(loader));
        invadersShootingSelector.start();

        extraInvaderMovement = new ExtraInvaderMovement(((Controller) loader.getController()).extraInvader);
        extraInvaderMovement.start();

        collisionThread = new CollisionThread(((Controller) loader.getController()).getInvadersBullets(loader),
                ((Controller) loader.getController()).spaceShipBullet,
                ((Controller) loader.getController()).getInvaderShips(loader),
                ((Controller) loader.getController()).spaceShip,
                ((Controller) loader.getController()).extraInvader);
        collisionThread.start();
    }

    /**
     * stops all threads in charge of running the game
     */
    private void stopThreads() {
        invadersMovementThread.stopMovement();
        invadersShootingSelector.stopThread();
        extraInvaderMovement.stopInvaderBonus();
        collisionThread.stopThread();
    }

    /**
     * receive a button and close the window
     *
     * @param button
     */
    private void closeView(Button button) {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

    /**
     * performs the movement of the ship depending on the button pressed
     *
     * @param scene
     * @param loader
     */
    private void setKeyListener(Stage stage, FXMLLoader loader) {
        stage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                spaceShipX = (int) ((Controller) loader.getController()).spaceShip.getX();

                if ("RIGHT".equals(e.getCode().toString())) {
                    moveRight(loader);
                    return;
                }
                if ("LEFT".equals(e.getCode().toString())) {
                    moveLeft(loader);
                    return;
                }
                if ("SPACE".equals(e.getCode().toString())) {
                    if (!((Controller) loader.getController()).spaceShipBullet.isVisible()) {
                        shootingAnimation = new ShootingAnimation(((Controller) loader.getController()).spaceShipBullet,
                                ((Controller) loader.getController()).spaceShip, GAME_VIEW_TOP_BOUND, -50, 200, 25, -25);
                        shootingAnimation.start();
                    }
                }
            }
        });
    }

    /**
     * moves the ship to the left
     *
     * @param loader
     */
    private void moveLeft(FXMLLoader loader) {
        if (spaceShipX != GAME_VIEW_LEFT_BOUND) {
            ((Controller) loader.getController()).spaceShip.setX(spaceShipX - 10);
        }
    }

    /**
     * moves the ship to the right
     *
     * @param loader
     */
    private void moveRight(FXMLLoader loader) {
        if (spaceShipX != GAME_VIEW_RIGHT_BOUND) {
            ((Controller) loader.getController()).spaceShip.setX(spaceShipX + 10);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
