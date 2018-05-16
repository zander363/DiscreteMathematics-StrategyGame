// P1Client.java
import java.io.*;
import java.net.Socket;

public class P1Client {
	private Socket clientSocket = null;
	private BufferedReader inFromServer = null;

	public void connectToServer() throws IOException {
		//String serverAddress = "127.0.0.1";
		String serverAddress = "140.112.18.178";
		int port = 9090;
		clientSocket = new Socket(serverAddress, port);
		// TODO: clientSocket = ?
		// TODO: inFromServer = ?
		inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		System.out.println("Connect to server at " + serverAddress + "..");
	}

	public void start() throws Exception {
		// TODO: handle messages recv from and send to server
		for (int i = 1; i < 5; i++) {
			String modifiedSentence = inFromServer.readLine();
			System.out.println(modifiedSentence);
			BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
			String sentence = inFromUser.readLine();
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			outToServer.writeBytes(sentence + '\n');
		}
		String modifiedSentence = inFromServer.readLine();
		System.out.println(modifiedSentence);
	}

	/**
	 * Runs the client application.
	 */
	public static void main(String[] args) throws Exception {
		P1Client client = new P1Client();
		client.connectToServer();
		client.start();
		client.clientSocket.close();
	}
}
