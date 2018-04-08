/*
 * mapGenerator.java
 * Copyright (C) 2018  <@DESKTOP-TA60DPH>
 *
 * Distributed under terms of the MIT license.
 */

import  java.io.FileInputStream;
import  java.io.InputStream;
import  java.util.Properties;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class MapGenerator{
	public static class Terrain{
		String id;
		char symbol;
		
		public Terrain(String id,char symbol){
			this.id = id;
			this.symbol = symbol;
		}
	};
	public static ArrayList<Terrain> getFileContent(String path)throws IOException {
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		ArrayList<Terrain> list = new ArrayList<Terrain>();
		while (br.ready()) {
			String string = br.readLine().trim();
			if(new String("[terrain_type]").equals(string)){
				String id = null;
				char symbol = ' ';
				while(!(new String("[/terrain_type]").equals(string = br.readLine().trim()))){
					String[] attribute = string.split("=");
					if(new String("id").equals(attribute[0])) id = attribute[1]; 
					if(new String("string").equals(attribute[0])) symbol = attribute[1].charAt(0); 
				}
				list.add(new Terrain(id,symbol));
			}
		}
		fr.close();
		return list;
	}
	/*
	private int[] unwalkable(int w,int h){
		int n = w*h;
		int[] perm = new int[n+1]; 

		// perm is the free permutation of walkable and unwalkable
		int term=1;
		int i = 0;
		perm[0] = 1;
		for(i = 1; i<=n;i++){
			term*=(n-i+1)/i;
			perm[i] = term;
		}

		int[] sub = new int[n+1];
		
		// sub the num block in corner
		// sub the num block aside side w
		// sub the num block aside side h
		// sub the num block inside
		// sub the num the line horizontal
		// sub the num the line vertical


		return perm;

	}
	*/
	public static void main(String args[]){
		try{
			ArrayList<Terrain> list = new ArrayList<Terrain>();
			Scanner scanner = new Scanner(System.in);
			list = MapGenerator.getFileContent("../prj0/ex_terrain.txt");
			int width = 0;
			int height = 0;
			while(width == 0){
				System.out.printf("Input the width(1~20):");
				width = scanner.nextInt();
			}
			while(height == 0){
				System.out.printf("Input the height(1~20):");
				height = scanner.nextInt();
			}
			int distriction = 0;
			while(distriction == 0){
				System.out.printf("which way do you want to use(1.By Number/2.By Ratio)?");
				int way = scanner.nextInt();
				switch(way){
					case 1:
					case 2:
						distriction = way;
						break;
					default:
						distriction = 0;
						System.out.println("invalid format!!");
				}
			}
			ArrayList<Integer> dist_num = new ArrayList<Integer>();
			String question1 = null;
			String question2 = new String("(if do want to set this option, input -1) :");
			if(distriction == 1) question1 = new String("please set the number of");
			if(distriction == 2) question1 = new String("please set the ratio of");
			for(Terrain t:list){
				System.out.printf("%s %s, %s",question1,t.id,question2);
				dist_num.add(scanner.nextInt());
			}
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
}

