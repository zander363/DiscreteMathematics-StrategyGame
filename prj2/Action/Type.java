/*
 * Type.java
 * Copyright (C) 2018  <@DESKTOP-TA60DPH>
 *
 * Distributed under terms of the MIT license.
 */
package Action;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class Type
{
	String name;
	public Type(String type) {
		this.name = type;
	}

	// should be ex_move.txt
	public static HashMap<String,Type> getFileContent(String path)throws IOException{
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		HashMap<String,Type> list = new HashMap<String,Type>();
		String string;
		while (br.ready()) {
			string = br.readLine().trim();
			if(new String("[resistance]").equals(string)){ 
				while(!(new String("[/resistance]").equals(string = br.readLine().trim()))){
					String[] type = string.split("=");
					Type typ = new Type(type[0]);
					list.put(type[0],typ);
				}
				break;
			}
		}
		fr.close();
		return list;
	}
	public static void main(String[] args)throws IOException{
		getFileContent("../../prj0/ex_move.txt");
	}
}

