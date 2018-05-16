/*
 * Movement.java
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

public class Movement
{
	String name;
	HashMap<String,Integer> cost;
	HashMap<String,Integer> defense;
	HashMap<String,Integer> resistance;

	public Movement(String name, HashMap<String,Integer> cost, HashMap<String,Integer> defense, HashMap<String,Integer> resistance) {
		this.name = name;
		this.cost = cost;
		this.defense = defense;
		this.resistance = resistance;
	}

	// should be ex_move.txt
	public static HashMap<String,Movement> getFileContent(String path)throws IOException {
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		HashMap<String,Movement> movements = new HashMap<String,Movement>();
		while (br.ready()) {
			String string = br.readLine().trim();
			String name = null;
			HashMap<String,Integer> cost = new HashMap<String,Integer>();
			HashMap<String,Integer> defense = new HashMap<String,Integer>();
			HashMap<String,Integer> resistance = new HashMap<String,Integer>();
			if(new String("[movetype]").equals(string)){
				while(!(new String("[/movetype]").equals(string = br.readLine().trim()))){
					if(new String("[movement_costs]").equals(string)){
						while(!(new String("[/movement_costs]").equals(string = br.readLine().trim()))){
							String[] attribute = string.split("=");
							cost.put(attribute[0],Integer.parseInt(attribute[1]));
						}
					}
					if(new String("[defense]").equals(string)){
						while(!(new String("[/defense]").equals(string = br.readLine().trim()))){
							String[] attribute = string.split("=");
							defense.put(attribute[0],Integer.parseInt(attribute[1]));
						}
					}
					if(new String("[resistance]").equals(string)){
						while(!(new String("[/resistance]").equals(string = br.readLine().trim()))){
							String[] attribute = string.split("=");
							resistance.put(attribute[0],Integer.parseInt(attribute[1]));
						}
					}
					String[] attribute = string.split("=");
					if(new String("name").equals(attribute[0])) name = attribute[1]; 
				}
				movements.put(name,new Movement(name,cost,defense,resistance));
			}
		}
		fr.close();
		return movements;
	}
}

