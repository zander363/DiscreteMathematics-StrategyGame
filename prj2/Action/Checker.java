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
import java.util.concurrent.ConcurrentLinkedDeque;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;
import Action.*;

public class Checker
{
	public static void printMap(Coordinate[][] map){
		for(int i=map[0].length-1;i>-1;i--){
			for(int j=0;j<map.length;j++){
				System.out.printf("%c ",map[j][i].sym);
			}
			System.out.println("");
		}
		return;
	}
	public static void printFullMap(Coordinate[][] map){
		for(int i=map[0].length-1;i>-1;i--){
			for(int j=0;j<map.length;j++){
				System.out.printf("%c%c ",map[j][i].sym,map[j][i].unit_cate);
			}
			System.out.println("");
		}
		return;
	}

	public static ArrayList<ArrayList<Character>> readMap(String path)throws IOException {
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

	public static void print_direct(){
		System.out.println("4 3 2");
		System.out.println("5 0 1");
		System.out.println("6 7 8");
		return ;
	}

	public static Coordinate enqueue(Coordinate[][] map,int x,int y,boolean change_last,
			ArrayList<Coordinate> visited,ConcurrentLinkedDeque<Coordinate> queue){
		Coordinate last = null;
		if(map[x][y].sym!='#'&& !visited.contains(map[x][y])){
			if(change_last){ 
				last = map[x][y];change_last=false;
			}
			queue.offer(map[x][y]);
			visited.add(map[x][y]);
		}
		//return change_last;
		return last;
	}
	
	public static ArrayList<Coordinate> BFS(Coordinate[][] map,int x,int y,int step){
		ArrayList<Coordinate> visited = new ArrayList<Coordinate>();
		//ArrayList<Integer> visited = new ArrayList<Integer>();
		ConcurrentLinkedDeque<Coordinate> queue = new ConcurrentLinkedDeque<Coordinate>();
		int height = map[0].length;
		int width = map.length;
		int range = 0;
		Coordinate last = map[x][y];
		boolean change_last = true;
		int[][] distance = new int[width][height];
		for(int i=0;i<height;i++){
			for(int j=0;j<width;j++){
				distance[j][i] = -1;
			}
		}

		System.out.printf("map.height = %d\n",map[0].length);
		System.out.printf("map.width = %d\n",map.length);
		System.out.printf("height = %d\n",height);
		System.out.printf("width = %d\n",width);

		queue.offer(map[x][y]);
		visited.add(map[x][y]);
		while(!queue.isEmpty()){
			System.out.println(queue.size());
			Coordinate tmp = null;
			Coordinate current = queue.poll();
			//visited.add(x*height+y);
			distance[current.x][current.y] = range;
			System.out.printf("x:%d,y:%d\n",last.x,last.y);
			if(last!=null&&current.x == last.x && current.y == last.y){
				range++;
				System.out.println(range);
				if(range>step) break;
				change_last = true;
			}
			int x_range = 0;
			int y_range = 0;
			if(current.x == 0) x_range = -1;
			if(current.x == width-1) x_range = 1;
			if(current.y == 0) y_range = -1;
			if(current.y == height-1) y_range = 1;
			//if((tmp = enqueue(map,x,y,change_last,visited,queue))!=null){
			//	last=tmp;queue.offer(tmp);
			if(y_range<1){
				if(map[x][y+1].sym!='#'&& !visited.contains(map[x][y+1])){
					if(change_last){ 
						last = map[x][y+1];change_last=false;
					}
					queue.offer(map[x][y+1]);
					visited.add(map[x][y+1]);
				}
			}
			if(x_range<1){
				if(map[x+1][y].sym!='#'&& !visited.contains(map[x+1][y])){
					if(change_last){ 
						last = map[x+1][y];change_last=false;
					}
					queue.offer(map[x+1][y]);
					visited.add(map[x+1][y]);
				}
			}
			if(y_range>-1) {
				if(map[x][y-1].sym!='#'&& !visited.contains(map[x][y-1])){
					if(change_last){ 
						last = map[x][y-1];change_last=false;
					}
					queue.offer(map[x][y-1]);
					visited.add(map[x][y-1]);
				}
			}
			if(x_range>-1){
				if(map[x-1][y].sym!='#'&& !visited.contains(map[x-1][y])){
					if(change_last){ 
						last = map[x-1][y];change_last=false;
					}
					queue.offer(map[x-1][y]);
					visited.add(map[x-1][y]);
				}
			}
			if(y_range==1){
				if(x_range==1) {
					if(map[x-1][y-1].sym!='#'&& !visited.contains(map[x-1][y-1])){
						if(change_last){ 
							last = map[x-1][y-1];change_last=false;
						}
						queue.offer(map[x-1][y-1]);
						visited.add(map[x-1][y-1]);
					}
				}
				else if(x_range==-1){
					if(map[x+1][y-1].sym!='#'&& !visited.contains(map[x+1][y-1])){
						if(change_last){ 
							last = map[x+1][y-1];change_last=false;
						}
						queue.offer(map[x+1][y-1]);
						visited.add(map[x+1][y-1]);
					}
				}
				else{
					if(map[x-1][y-1].sym!='#'&& !visited.contains(map[x-1][y-1])){
						if(change_last){ 
							last = map[x-1][y-1];change_last=false;
						}
						queue.offer(map[x-1][y-1]);
						visited.add(map[x-1][y-1]);
					}
					if(map[x+1][y-1].sym!='#'&& !visited.contains(map[x+1][y-1])){
						if(change_last){ 
							last = map[x+1][y-1];change_last=false;
						}
						queue.offer(map[x+1][y-1]);
						visited.add(map[x+1][y-1]);
					}
				}
			}
			else if(y_range==-1){
				if(x_range==1){
					if(map[x-1][y+1].sym!='#'&& !visited.contains(map[x-1][y+1])){
						if(change_last){ 
							last = map[x-1][y+1];change_last=false;
						}
						queue.offer(map[x-1][y+1]);
						visited.add(map[x-1][y+1]);
					}
				}
				else if(x_range==-1){
					if(map[x+1][y+1].sym!='#'&& !visited.contains(map[x+1][y+1])){
						if(change_last){ 
							last = map[x+1][y+1];change_last=false;
						}
						queue.offer(map[x+1][y+1]);
						visited.add(map[x+1][y+1]);
					}
				}
				else{
					if(map[x-1][y+1].sym!='#'&& !visited.contains(map[x-1][y+1])){
						if(change_last){ 
							last = map[x-1][y+1];change_last=false;
						}
						queue.offer(map[x-1][y+1]);
						visited.add(map[x-1][y+1]);
					}
					if(map[x+1][y+1].sym!='#'&& !visited.contains(map[x+1][y+1])){
						if(change_last){ 
							last = map[x+1][y+1];change_last=false;
						}
						queue.offer(map[x+1][y+1]);
						visited.add(map[x+1][y+1]);
					}
				}
			}
			else{ 
				if(x_range==1){
					if(map[x-1][y-1].sym!='#'&& !visited.contains(map[x-1][y-1])){
						if(change_last){ 
							last = map[x-1][y-1];change_last=false;
						}
						queue.offer(map[x-1][y-1]);
						visited.add(map[x-1][y-1]);
					}
					if(map[x-1][y+1].sym!='#'&& !visited.contains(map[x-1][y+1])){
						if(change_last){ 
							last = map[x-1][y+1];change_last=false;
						}
						queue.offer(map[x-1][y+1]);
						visited.add(map[x-1][y+1]);
					}
				}
				else if(x_range==-1){ 
					if(map[x+1][y-1].sym!='#'&& !visited.contains(map[x+1][y-1])){
						if(change_last){ 
							last = map[x+1][y-1];change_last=false;
						}
						queue.offer(map[x+1][y-1]);
						visited.add(map[x+1][y-1]);
					}
					if(map[x+1][y+1].sym!='#'&& !visited.contains(map[x+1][y+1])){
						if(change_last){ 
							last = map[x+1][y+1];change_last=false;
						}
						queue.offer(map[x+1][y+1]);
						visited.add(map[x+1][y+1]);
					}
				}
				else{
					if(map[x-1][y-1].sym!='#'&& !visited.contains(map[x-1][y-1])){
						if(change_last){ 
							last = map[x-1][y-1];change_last=false;
						}
						queue.offer(map[x-1][y-1]);
						visited.add(map[x-1][y-1]);
					}
					if(map[x+1][y-1].sym!='#'&& !visited.contains(map[x+1][y-1])){
						if(change_last){ 
							last = map[x+1][y-1];change_last=false;
						}
						queue.offer(map[x+1][y-1]);
						visited.add(map[x+1][y-1]);
					}
					if(map[x-1][y+1].sym!='#'&& !visited.contains(map[x-1][y+1])){
						if(change_last){ 
							last = map[x-1][y+1];change_last=false;
						}
						queue.offer(map[x-1][y+1]);
						visited.add(map[x-1][y+1]);
					}
					if(map[x+1][y+1].sym!='#'&& !visited.contains(map[x+1][y+1])){
						if(change_last){ 
							last = map[x+1][y+1];change_last=false;
						}
						queue.offer(map[x+1][y+1]);
						visited.add(map[x+1][y+1]);
					}
				}
			}
		}


		for(int i=height-1;i>-1;i--){
			for(int j=0;j<width;j++){
				System.out.printf("%c",map[j][i].sym);
				System.out.printf("%c",map[j][i].unit_cate);
				if(distance[j][i]!=-1){
					System.out.printf("%d",distance[j][i]);
				}else{
					System.out.printf(" ");
				}
				System.out.printf(" ");
			}
			System.out.println("");
		}
		return visited;
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
		//HashMap<String,Unit> unit_dict = Unit.getFileContent("../prj0/ex_unit.txt",type_dict,move_dict);
		HashMap<Character, Unit> unit_dict = Unit.getFileContent(workspace + "/ex_unit.txt", type_dict, move_dict);
		System.out.printf("unit_dict size : %d\n",unit_dict.size());

		int amount = 0;
		System.out.printf("please assign the path of map file : ");
		Scanner scanner = new Scanner(System.in);
		
		ArrayList<ArrayList<Character>> map_tmp = readMap(scanner.next());
		int height = map_tmp.size();
		int width = map_tmp.get(0).size();
		Coordinate[][] map = new Coordinate[width][height];
            map = new Coordinate[width][height];
		for(int i=0;i<height;i++){
			ArrayList<Character> row = map_tmp.get(height-i-1);
			for(int j=0;j<width;j++){
				map[j][i] = new Coordinate(j,i,row.get(j),' ',-1);
			}
		}
		printMap(map);

		System.out.print("How much initial amount it should be? ");
		while(amount <= 0){
			amount = scanner.nextInt();
		}
		int num = 0;
		ArrayList<String> unit_name = new ArrayList<String>();

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
			amount -= num*price;
			if(amount <= 0){
				System.out.println("You can afford no more unit");
				break;
			}
		}

		for(Unit_state u : unit_state){
			ArrayList<Coordinate> avail = BFS(map,u.x,u.y,u.cate.movement);
			System.out.printf("You can move this unit %d steps as plot illustrated\n",u.cate.movement);
			print_direct();
			System.out.println(avail.size());
			while(true){
				String string = scanner.next();

				ArrayList<Character> move_sequence = new ArrayList<Character>();
				for(int i=0;i<string.length();i++){
					char c = string.charAt(i);
					if(c==' ')continue;
					if(!Character.isDigit(c)) continue;
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
				if(position.x==u.x&&position.y==u.y)break;
				position = map[position.x][position.y];
				if(!position.valid()){
					System.out.printf("wrong destination\n");
					continue;
				}
				if(!avail.contains(position)){
					System.out.printf("Unreachable destination\n");
					continue;
				}
				position.exchange(map[u.x][u.y]);

				printFullMap(map);
				
				break;
			}
		}
		//System.out.println("compile OK");
	}
}
