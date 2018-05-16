/*
 * Unit.java
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

public class Unit
{
	String id;
	char sym;
	int hit;
	Movement move_type;
	int movement;
	ArrayList<Attack> attacks;

	public Unit(String id, char sym, int hit,Movement move_type,int movement,ArrayList<Attack> attacks) {
		this.id = id;
		this.sym = sym;
		this.hit = hit;
		this.move_type = move_type;
		this.movement = movement;
		this.attacks = attacks;
	}

	public static HashMap<String,Unit> getFileContent(String path, HashMap<String,Type> type_dict, HashMap<String,Movement> move_dict)throws IOException {
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		HashMap<String,Unit> units = new HashMap<String,Unit>();
		char sym = 'A';
		while (br.ready()) {
			String string = br.readLine().trim();
			if(new String("[unit_type]").equals(string)){
				String id = null;
				int hit = 0;
				Movement move_type = null;
				int movement = 0;
				ArrayList<Attack> attacks = new ArrayList<Attack>();
				while(!(new String("[/unit_type]").equals(string = br.readLine().trim()))){
					String[] attribute = string.split("=");
					if(new String("id").equals(attribute[0])) id = attribute[1]; 
					if(new String("hitpoints").equals(attribute[0])) hit = Integer.parseInt(attribute[1]);
					if(new String("movement_type").equals(attribute[0])) move_type = move_dict.get(attribute[1]);  
					if(new String("movement").equals(attribute[0])) movement = Integer.parseInt(attribute[1]);
					if(new String("[attack]").equals(string)){
						String a_name = null;
						Type a_type = null;
						Boolean a_range = false;
						int a_damage = 0;
						int a_number = 0;
						while(!(new String("[/attack]").equals(string = br.readLine().trim()))){
							if(new String("name").equals(attribute[0])) a_name = attribute[1]; 
							if(new String("type").equals(attribute[0])) a_type = type_dict.get(attribute[1]);
							if(new String("range").equals(attribute[0])){
								if(new String("ranged").equals(attribute[1])) a_range = true;
								else a_range = false;
							}
							if(new String("damage").equals(attribute[0])) a_damage = Integer.parseInt(attribute[1]); 
							if(new String("number").equals(attribute[0])) a_number = Integer.parseInt(attribute[1]); 
						}
						attacks.add(new Attack(a_name, a_type, a_range, a_damage, a_number));
					}
				}
				units.put(id,new Unit(id,sym,hit,move_type,movement,attacks)); 
				sym++;
			}
		}
		fr.close();
		return units;
	}
}

