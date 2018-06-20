/*
 * UserIO.java
 * Copyright (C) 2018  <@DESKTOP-TA60DPH>
 *
 * Distributed under terms of the MIT license.
 *
 */

//package
package Action;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;


public class UserIO{
	public static void printFullMap(Coordinate[][] map){
		for(int i=map[0].length-1;i>-1;i--){
			for(int j=0;j<map.length;j++){
				System.out.printf("%c%c ",map[j][i].sym,map[j][i].unit_cate);
			}
			System.out.println("");
		}
		return;
    }
    
    public static ArrayList<ArrayList<Character>> readMap(String path) throws IOException {
		FileReader fr = new FileReader(path);
		BufferedReader br = new BufferedReader(fr);
		ArrayList<ArrayList<Character>> list = new ArrayList<ArrayList<Character>>();
		while (br.ready()) {
			ArrayList<Character> row = new ArrayList<Character>();
			String string = br.readLine().trim();
			String[] lis = string.split(" ");
			for(int i=0; i<lis.length;i++){
				row.add(lis[i].charAt(0));
			}
			list.add(row);
        }
        br.close();
		return list;
	}

    /*
    public void read_map();
    public static void read_state();
    public static void write_state();
    public static void read_move();
    public static void write_move();
    */

    public static void main(String args[]){
        try{
            String workspace = null;
            Action.Coordinate[][] map = null;
            int height = 0;
            int width = 0;
            
            for (int i = 0; i < args.length; i++) {
                if (new String("--workspace").equals(args[i]))
                    if(i+1<args.length){
                        workspace = args[i + 1];
                    }
            }
            if (workspace == null)
                workspace = "C:\\GDATA";

            HashMap<String,Type> type_dict = Type.getFileContent(workspace+"/ex_move.txt");
            System.out.printf("type_dict size : %d\n",type_dict.size());
            ArrayList<Terrain> terr_list = Terrain.getFileContent(workspace+"/ex_Terrain.txt");
            System.out.printf("terr_list size : %d\n",terr_list.size());
            HashMap<String,Movement> move_dict = Movement.getFileContent(workspace+"/ex_move.txt");
            System.out.printf("move_dict size : %d\n",move_dict.size());
            HashMap<String,Unit> unit_dict = Unit.getFileContent(workspace+"/ex_unit.txt",type_dict,move_dict);
            System.out.printf("unit_dict size : %d\n",unit_dict.size());

            Scanner scanner = new Scanner(System.in);
            Boolean initiative = true;
            char init_char = '\t';
            while (init_char == '\t') {
                System.out.printf("Would you like act first? (Y/N)");
                init_char = scanner.next().charAt(0);
                switch (init_char) {
                case 'Y':
                case 'y':
                    initiative = true; break;
                case 'N':
                case 'n':
                    initiative = false; break;
                default:
                    init_char = '\t';
                }
            }
            if(initiative){
                //create the map
                Boolean create = false;
                char  create_char= '\t';
                while (create_char == '\t') {
                    System.out.printf("Would you like generate a map manully? (Y/N)");
                    create_char = scanner.next().charAt(0);
                    switch (create_char) {
                    case 'Y':
                    case 'y':
                        initiative = true; break;
                    case 'N':
                    case 'n':
                        initiative = false; break;
                    default:
                        create_char = '\t';
                    }
                }
                String file_string = "D W D G F F G G F W F D Q W D W F W F F \nC D D F F F G F F G F G W D F G F D F D \nD D F D D F F W G F D F W W W F G G G F \nD W W D W G W D G W W G G W W F D D W D \nF F F D F F G F D G W F F D F W Q G G F \nF G G Q W F D F F W G D F G F F D F F F \nD W D D F W W G G W W G D W G W D W W D \nF G G G F W W W F D F G W F F D D F D D \nD F D F G F D W G F G F F G F F F D D C \nF F W F W D W Q D F W F G G F F G D W D";

                FileWriter fw = new FileWriter(workspace+"\\map.txt");
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(file_string);
                bw.newLine();        
                bw.flush();
                bw.close();      
                ArrayList<ArrayList<Character>> map_tmp = readMap(workspace+"\\map.txt");
                height = map_tmp.size();
                width = map_tmp.get(0).size();
                map = new Coordinate[width][height];
                for(int i=0;i<height;i++){
                    ArrayList<Character> row = map_tmp.get(height-i-1);
                    for(int j=0;j<width;j++){
                        map[j][i] = new Coordinate(j,i,row.get(j),' ',-1);
                    }
                }
 
                if(create){
                    //MapGeneraor();
                    //double_map();
                }
                //map_write(map,workspace);
            }
            else{
                ArrayList<ArrayList<Character>> map_tmp = readMap(workspace+"\\map.txt");
                height = map_tmp.size();
                width = map_tmp.get(0).size();
                map = new Coordinate[width][height];
                for(int i=0;i<height;i++){
                    ArrayList<Character> row = map_tmp.get(height-i-1);
                    for(int j=0;j<width;j++){
                        map[j][i] = new Coordinate(j,i,row.get(j),' ',-1);
                    }
                }
            }

            
		    int amount = 0;
            while(amount <= 0){
                System.out.printf("Initialize the initial money : ");
                amount = scanner.nextInt();
            }
            int num = 0;

            /*
            */
            int cost_Mage = 0;
            int cost_Poacher = 0;
            for(Map.Entry<String,Unit> u: unit_dict.entrySet()){
                Unit unit = u.getValue();
                if(new String("Mage").equals(unit.name)){
                    cost_Mage = unit.cost;
                }
                if(new String("Poacher").equals(unit.name)){
                    cost_Poacher = unit.cost;
                }
                /*
                System.out.printf("id: %s (%c), hp = %d, move_type: %s, cost:%d\n"
                        ,unit.id,unit.sym,unit.hit,unit.move_type.name,unit.cost);
                for(Attack a:unit.attacks){
                    System.out.printf(" %s : %d *%d @%s\n",a.name,a.damage,a.number,a.range);
                }
                System.out.println("");
                */
            }


            //esitmate the number of my unit
            int budget_Mage = (amount*2/5)/cost_Mage;
            int esti_Poacher = (amount*3/5)/cost_Poacher;


            ArrayList<Unit_state> unit_state = new ArrayList<Unit_state>();
            for(Map.Entry<String,Unit> u: unit_dict.entrySet()){
                int unit_counter = 0;
                int price = u.getValue().cost;
                System.out.printf("Now, you have $%d. How many %s do you want?\n(every %s need $%d)",
                    amount,u.getValue().id,u.getValue().id,price);
                do{
                    num = scanner.nextInt();
                }while(amount-num*price<0);
                String name = u.getValue().id;
                char sym = u.getValue().sym;
                for(int i=0;i<num;i++){
                    System.out.printf("please assign a coordinate for %s-%d:\n",u.getValue().id,i+1);
                    int x = -1;
                    int y = -1;
                    do{
                        System.out.printf("For x coordinate : ");
                        do{
                            x = scanner.nextInt();
                        }while(x<0||x>=width);
                        System.out.printf("For y coordinate : ");
                        do{
                            y = scanner.nextInt();
                        }while(y<0||y>=height);
                    }while(!map[x][y].valid());
                    unit_state.add(new Unit_state(sym,unit_dict.get(name),x,y,100.0));
                    map[x][y].unit_cate=sym;
                    map[x][y].unit_id=unit_counter;
                    unit_counter++;
                }
                amount = amount- num*price;
            }
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
        
    }
}