/*
 * mapGenerator.java
 * Copyright (C) 2018  <@DESKTOP-TA60DPH>
 *
 * Distributed under terms of the MIT license.
 */
package Action;

import  java.io.FileInputStream;
import  java.io.InputStream;
import  java.util.Properties;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class Terrain{
	String id;
	char symbol;
	
	public Terrain(String id,char symbol){
		this.id = id;
		this.symbol = symbol;
	}

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
	public static void saveMap(String path)throws IOException {
		FileWriter fw = new FileWriter(path);
		BufferedWriter bw = new BufferedWriter(fw);
		try{
			for(int i=0; i< height;i++){
				for(int j=0;j<width;j++){
					bw.write(solution[height*i+j]);
					bw.write(' ');
				}
				bw.newLine();        
			}
			bw.flush();
		}catch(IOException ioe){
			throw ioe;
		}finally{
			bw.close();      
		}    
	}
	*/
}
