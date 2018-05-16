/*
 * Checker.java
 * Copyright (C) 2018  <@DESKTOP-TA60DPH>
 *
 * Distributed under terms of the MIT license.
 */
package Action;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;
import Action.*;

public class Checker
{
	public static void printMap(ArrayList<ArrayList<Character>> map){
		for(ArrayList<Character> row: map){
			for(char c: row){
				System.out.printf("%c ",c);
			}
			System.out.println("");
		}
		return;
	}

	public static ArrayList<ArrayList<Character>> readMap(String path)throws IOException {
		int width=0;
		int height=0;
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
		return list;
	}
	
	public BFS(Coordinate[][] map,int x,int y,int step){
		ArrayList<Coordinate> visited = new ArrayList<Coordinate>();
		queue = new queue();
		int range = 0;
		Coordinate last ;
		Boolean change_last = true;

		queue.enqueue(map[x][y]);
		while(!queue.empty()){
			current = queue.dequeue();
			if(last.equal(current)){
				range++;
				if(range>step) break;
				change_last = true;
			}
			int x_range = 0;
			int y_range = 0;
			if(current.x == 0) x_range = -1;
			if(current.x == width) x_range = 1;
			if(current.y == 0) y_range = -1;
			if(current.y == width) y_range = 1;
			if(y_range<1) 
				if(map[x][y+1].sym!='#'&& !visited.contain(map[x][y+1])){
					if(change_last){ 
						last = map[x][y+1];change_last=false;
					}
					queue.enqueue(map[x][y+1]);
				}
			if(x_range<1){ 
				if(map[x][y+1].sym!='#'&& !visited.contain(map[x][y+1])){
					if(change_last){ 
						last = map[x][y+1];change_last=false;
					}
					queue.enqueue(map[x+1][y]);
				}
			}
			if(y_range>-1)
				if(map[x][y+1].sym!='#'&& !visited.contain(map[x][y+1])){
					if(change_last){ 
						last = map[x][y+1];change_last=false;
					}
					queue.enqueue(map[x][y-1]);
				}
			if(y_range>-1)
				if(map[x][y+1].sym!='#'&& !visited.contain(map[x][y+1])){
					if(change_last){ 
						last = map[x][y+1];change_last=false;
					}
					 queue.enqueue(map[x][y-1]);
				}
			if(y_range==1)
				if(x_range==1)
					queue.enqueue(map[x-1][y-1]);
				else if(x_range==-1)
					queue.enqueue(map[x+1][y-1]);
				else
					queue.enqueue(map[x-1][y-1]);
					queue.enqueue(map[x+1][y-1]);
			else if(y_range==-1)
				if(x_range==1)
					queue.enqueue(map[x-1][y+1]);
				else if(x_range==-1)
					queue.enqueue(map[x+1][y+1]);
				else
					queue.enqueue(map[x-1][y+1]);
					queue.enqueue(map[x+1][y+1]);
			else 
				if(x_range==1)
					queue.enqueue(map[x-1][y-1]);
					queue.enqueue(map[x-1][y+1]);
				else if(x_range==-1)
					queue.enqueue(map[x+1][y-1]);
					queue.enqueue(map[x+1][y+1]);
				else
					queue.enqueue(map[x-1][y-1]);
					queue.enqueue(map[x+1][y-1]);
					queue.enqueue(map[x-1][y+1]);
					queue.enqueue(map[x+1][y+1]);
		}
	}

	public static Coordinate move(int x, int y, ArrayList<Character> sequence){
		for(char c: sequence){
			switch(c){
			case '0':
				break;
			case '1':
				x++;
				break;
			case '2':
				x++;
				y++;
				break;
			case '3':
				y++;
				break;
			case '4':
				x--;
				y++;
				break;
			case '5':
				x--;
				break;
			case '6':
				x--;
				y--;
				break;
			case '7':
				y--;
				break;
			case '8':
				x++;
				y--;
				break;
			}
		}
		return new Coordinate(x,y,' ',' ',-1);
	}

	public static void main(String[] args)throws IOException{
		// Initial for each dictionary
		HashMap<String,Type> type_dict = Type.getFileContent("../prj0/ex_move.txt");
		System.out.printf("type_dict size : %d\n",type_dict.size());
		ArrayList<Terrain> terr_list = Terrain.getFileContent("../prj0/ex_Terrain_sym.txt");
		System.out.printf("terr_list size : %d\n",terr_list.size());
		HashMap<String,Movement> move_dict = Movement.getFileContent("../prj0/ex_move.txt");
		System.out.printf("move_dict size : %d\n",move_dict.size());
		HashMap<String,Unit> unit_dict = Unit.getFileContent("../prj0/ex_unit.txt",type_dict,move_dict);
		System.out.printf("unit_dict size : %d\n",unit_dict.size());

		int amount = 0;
		int price = 500; // I assign it; TODO: should have some specific value
		Scanner scanner = new Scanner(System.in);
		
		ArrayList<ArrayList<Character>> map_tmp = readMap(scanner.next());
		printMap(map_tmp);
		int height = map_tmp.size();
		int width = map_tmp.get(0).size();
		Coordinate[][] map = new Coordinate[height][width];
		for(int i=0,i<height;i++){
			ArrayList<Character> list = map_tmp.get(height-i-1);
			for(int j=0;j<width;j++){
				map[i][j] = new Coordinate(i,j,list.get(j),' ',-1)
			}
		}

		System.out.print("How much initial amount it should be? ");
		while(amount <= 0){
			amount = scanner.nextInt();
		}
		int num = 0;
		ArrayList<String> unit_name = new ArrayList<String>();

		ArrayList<Unit_state> unit_state = new ArrayList<Unit_state>();
		for(Map.Entry<String,Unit> u: unit_dict.entrySet()){
			int unit_counter = 0;
			System.out.printf("Now, you have $%d. How many %s do you want? ",amount,u.getValue().id);
			do{
				num = scanner.nextInt();
			}while(amount-num*price<0);
			String name = u.getValue().id;
			char sym = u.getValue().sym;
			for(int i=0;i<num;i++){
				System.out.printf("please assign a coordinate for No% %s:\n",i+1,u.getValue().id);
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
				}while(map[x][y].valid());
				unit_state.add(new Unit_state(sym,unit_dict.get(name),x,y,100.0));
				map[x][y].unit_cate=sym;
				map[x][y].unit_id=unit_counter;
				unit_counter++;
			}
			amount -= num*price;
			if(amount <= 0){
				System.out.println("You can afford no more unit");
				break;
			}
		}

		for(Unit_state u : unit_state){
			ArrayList<Coordinate> avail = BFS(map,u.x,u.y,u.cate.movement);
			System.out.println("You can move this unit as plot illustrated");
			print_direct();
			while(true){
				String = scanner.next();

				ArrayList<char> move_sequence = new ArrayList();
				for(char c : string){
					if(c==' ')continue;
					if(!isDigit(c)) continue;
					if(c=='9')continue;
					move_sequence.add(c);
				}
				if(move_sequence.size()>u.cate.movement){
					System.out.printf("you can only move %d steps\n",u.cate.movement);
					continue;
				}
				Coordinate position = move(u.x,u.y,move_sequence);
				if(position.x<0 || position.x>=width){
					System.out.printf("out of bound at x\n");
					continue;
				}
				if(position.y<0 || position.y>=height){
					System.out.printf("out of bound at y\n");
					continue;
				}
				position = map[position.x][position.y];
				if(!position.valid()){
					System.out.printf("wrong destination\n");
					continue;
				}
				//move it
				
				break;
			}


		}


		System.out.println("compile OK");
	}
}
