/*
 * movementChecker.java
 * Copyright (C) 2018  <@DESKTOP-TA60DPH>
 *
 * Distributed under terms of the MIT license.
 */
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.NetworkInterface;
import java.net.InetAddress;
import java.util.*;
import java.util.Scanner;

public class MovementChecker
{
	/*
	private Socket clientSocket = null;
	private BufferedReader inFromServer = null;
	*/
	public static boolean check(Socket socket)throws Exception{
		DataOutputStream out =
				new DataOutputStream(socket.getOutputStream());
		BufferedReader in = 
				new BufferedReader(new InputStreamReader(socket.getInputStream()));
		return true;
	}

	public static void list_ip() throws Exception{
		Enumeration e = NetworkInterface.getNetworkInterfaces();
		System.out.println("----------------------------------");
		System.out.println("Here is your ip list");
		System.out.println("----------------------------------");
		while(e.hasMoreElements())
		{
			NetworkInterface n = (NetworkInterface) e.nextElement();
			Enumeration ee = n.getInetAddresses();
			while (ee.hasMoreElements())
			{
				InetAddress i = (InetAddress) ee.nextElement();
				System.out.println(i.getHostAddress());
			}
		}
		System.out.println("----------------------------------");
	}
	public static Socket connect(boolean ServerMode, String serverAddress)throws Exception{
		if(ServerMode){
			ServerSocket serverSocket = new ServerSocket(9090);
			list_ip();
			System.out.println("The Game is start and wait for someone join...");
			Socket socket = serverSocket.accept();
			System.out.println("a player join to this game");
			return socket;
		}else{
			int port = 9090;
			Socket socket = new Socket(serverAddress, port);
			System.out.println("you just join the game succesfully");
			return socket;
		}
	}
	public MovementChecker() {
		
	}
	public static void main(String[] args) throws Exception {
		try{
			Scanner scanner = new Scanner(System.in);
			boolean ServerMode = true;
			System.out.print("Do you want to create a new game?(Y/N) ");
			switch(scanner.next().charAt(0)){
				case 'n':
				case 'N':
					ServerMode = false;
				default: 
			}
			String serverAddress = "127.0.0.1";
			if(!ServerMode){
				System.out.print("Please input the destnation IP address : ");
				serverAddress = scanner.next();
			}
			Socket socket = connect(ServerMode,serverAddress);
			//TODO : game checker
			//TODO : negociate()
			//TODO : init()
			//TODO : while(condition){
			//		if(myturn){
			//
			//		}else{
			//
			//		}
			//	}
			scanner.next();
			socket.close();
		}
		catch(Exception e){
			System.out.println("---------------------------");
			System.out.println("Some Error Occurred. Please Try agin");
			System.out.println("Here is the error message :");
			System.out.println(e.getMessage());
			System.out.println("---------------------------");
		}
	}
}

