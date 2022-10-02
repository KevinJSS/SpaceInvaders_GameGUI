package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * This class sends data and receives data from the server.
 *
 * @author Alina
 * @author Kevin
 * @author Adriana
 */
public class Client {

    private DataInputStream input;
    private DataOutputStream output;
    private Socket connection;
    private final String HOST = "localhost";
    private final int PORT = 12810;

    public Client() throws IOException {
        runClient();
    }

    /**
     * connects to the server and enables the outputs and inputs
     */
    private void runClient() {
        try {
            connectToServer();
            getStreams();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /**
     * sends a text to the server so that it defines what action to take
     *
     * @param request
     * @throws IOException
     */
    public void sendRequest(String request) throws IOException {
        output.writeUTF(request);
    }

    /**
     * send the username and password to the server to be evaluated
     *
     * @param username
     * @param password
     * @throws IOException
     */
    public void sendClient(String username, String password) throws IOException {
        output.writeUTF(username);
        output.writeUTF(password);
    }

    /**
     * send an integer to the Server through the output
     *
     * @param digit
     * @throws IOException
     */
    public void sendInt(int digit) throws IOException {
        output.writeInt(digit);
    }

    /**
     * send a String to the Server through the output
     *
     * @param msg
     * @throws IOException
     */
    public void sendString(String msg) throws IOException {
        output.writeUTF(msg);
    }

    /**
     * read the received int
     *
     * @return int
     */
    public int readInt() throws IOException {
        return input.readInt();
    }

    /**
     * read the received String
     *
     * @return String
     * @throws IOException
     */
    public String readString() throws IOException {
        return input.readUTF();
    }

    /**
     * read the received boolean
     *
     * @return boolean
     * @throws IOException
     */
    public boolean readBoolean() throws IOException {
        return input.readBoolean();
    }

    /**
     * try to connect with the server
     *
     * @throws IOException
     */
    private void connectToServer() throws IOException {
        System.out.println("Intentando conectar con el servidor");
        connection = new Socket(HOST, PORT);
        System.out.println("Conectado a: " + connection.getInetAddress().getHostName());
    }

    /**
     * initialize the outputs and inputs
     *
     * @throws IOException
     */
    private void getStreams() throws IOException {
        output = new DataOutputStream(connection.getOutputStream());
        output.flush();
        input = new DataInputStream(connection.getInputStream());
    }

    /**
     * end the connection with the server
     */
    public void closeConnection() {
        try {
            System.out.println("Conexi\u00F3n finalizada");
            output.close();
            input.close();
            connection.close();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
