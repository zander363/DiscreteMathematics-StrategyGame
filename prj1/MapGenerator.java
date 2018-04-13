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
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Scanner;

public class MapGenerator{
	// global variable declaration
	static int width = 0;
	static int height = 0;
	static long permutationID = 0;
	static char[] set = null;
	static int[] dist_num = null; // record how many times do each charactor show
	static char[] solution = null; // record a valid permutation(each int mean the number of the terrain)
	static boolean[] used = null; // record the used state of each element
	static long permutationNo;

	// inner class Terrain contain information about each terrain
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

	public static char[] generateSet(int grid,int[] dist,ArrayList<Terrain> list){
		int pos = 0;
		int counter = 0;
		int sum = 0;
		char[] arr = new char[grid];
		while(true){
			while(pos<dist.length&&counter == dist[pos]){ 
				pos++;
				counter = 0;
			}
			if(pos==dist.length)break;
			arr[sum] = list.get(pos).symbol;
			sum++;
			counter++;

		}
		return arr;
	}
	// n : the position this level should deal
	// N : how long is a permutation 
	public static int backtrack(int n, int N){
		// terminate condition
		if(n == N){
			permutationID++;
			if(permutationID == permutationNo){
				return 1;
			}
			else return 0;
		}
		char last_letter = '\u0000';
		for(int i = 0; i < N; i++){
			if(used[i]) continue;
			if(set[i] == last_letter) continue;

			last_letter = set[i];
			used[i] = true;
			solution[n] = set[i];
			if(backtrack(n+1,N)==1) return 1;

			used[i] = false;
		}
		return 0;
	}
	public static void print_map(char[] map){
		for(int i = 0; i < height;i++){
			for(int j = 0; j < width;j++){
				System.out.printf("%c ",map[height*i+j]);
			}
			System.out.println("");
		}

	}
	public static void print_num(ArrayList<Terrain> list,int[] list_num){
		long sum = 0;
		System.out.println("");
		System.out.println("-----------Here is the number of each terrain-------------");
		for(int i=0;i<list.size();i++){
			sum += dist_num[i];
			System.out.printf("%s : %d\n",list.get(i).id,dist_num[i]);
		}
		System.out.println("----------------------------------------------------------");
		System.out.printf("Total : %d\n",sum);
		System.out.println("==========================================================");
		System.out.println("");
	}
	public static long Combination(int sum,int K){
		long c = 1;
		for(int k=1,n=sum;k<=K;k++,n--){
			c= c*n/k;
		}
		return c;

	}
	public static long estimate(int sum,int[] dist_num){
		sum -= dist_num[0];
		long comb = 1;
		for(int i = 1;i<dist_num.length-1;i++){
			comb *= Combination(sum,dist_num[i]);
			sum -= dist_num[i];
		}
		return comb;
	}
	public static void main(String args[]){
		try{
			String inputpath = null;
			String outputpath = null;
			for(int i = 0;i < args.length; i++){
				if(new String("--inputpath").equals(args[i])) inputpath = args[i+1];
				if(new String("--outputpath").equals(args[i])) outputpath = args[i+1];
			}
			if(inputpath==null) inputpath = "../prj0/ex_terrain.txt";
			if(outputpath==null) outputpath = "map.txt";
			ArrayList<Terrain> list = new ArrayList<Terrain>();
			Scanner scanner = new Scanner(System.in);
			list = MapGenerator.getFileContent(inputpath);
			int n = list.size();
			while(width == 0){
				System.out.printf("Input the width(1~20) : ");
				width = scanner.nextInt();
				if(width > 20 || width < 1) height=0;
			}
			while(height == 0){
				System.out.printf("Input the height(1~20) : ");
				height = scanner.nextInt();
				if(height > 20 || height < 1) height=0;
			}
			int distriction = 0;
			while(distriction == 0){
				System.out.printf("which way do you want to use(1.By Number/2.By Ratio)? ");
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
			int grid = width*height;
			dist_num = new int[grid];
			int sum = 0;
			if(distriction == 1){
				int i = 0;
				for(i=0;i < n-1;i++){
					int j = -1;
					while(j==-1){
						System.out.printf("please set the number of %s : ",list.get(i).id);
						j = scanner.nextInt();
						if(j<0||(sum+j)>grid){ 
							if(j<0) System.out.println("Input should not be negative");
							else System.out.println("Out of map size");
							j = -1;
						}
						else{
							dist_num[i]=j;
							sum+=j;
						}
					}
				}
				System.out.printf("therefore the number of %s is %d\n",list.get(i).id,grid-sum);
				dist_num[i] = grid - sum;
			}
			double total = 0.0;
			sum = 0;
			if(distriction == 2){
				int i = 0;
				for(i=0;i < n-1;i++){
					double j = -1.0;
					int k = 0;
					while(j ==-1.0){
						System.out.printf("please set the ratio of %s : ",list.get(i).id);
						j = scanner.nextDouble();
						if(j < 0.0 || (total+j) > 1.0) {
							if(j<0.0) System.out.println("Input should not be negative");
							else System.out.println("Out of map size");
							j = -1.0;
						}
						else{
							Double t = j*grid;
							k = t.intValue();
							dist_num[i]=k;
							total += j;
							sum += k;
						}
					}
				}
				System.out.printf("therefore the ratio of %s is %f\n",list.get(i).id,1-total);
				dist_num[i] = grid - sum;
			}
			print_num(list,dist_num);
			// cauculate the number of permutation;
			long esti_permutation = estimate(grid,dist_num);

			set = generateSet(grid,dist_num,list);

			// Generate the set by dist_num 
			while(true){
				permutationNo = 0;
				permutationID = 0;
				solution = new char[grid];
				used = new boolean[grid];
				System.out.printf("By the rule you gave, we estimate there are %d possible map could be generated\n",esti_permutation);
				while(permutationNo == 0){
					System.out.printf("Choose a map(1~%d) : ",esti_permutation);
					permutationNo = scanner.nextLong();
					if(permutationNo>esti_permutation){
						System.out.println("Out of range");
						esti_permutation = 0;
					}
					if(permutationNo<0){
						System.out.println("can't be negative");
					}
				}
				backtrack(0,grid);
				print_map(solution);
				System.out.printf("Would you want to save this map(Y/N)? ");
				switch(scanner.next().charAt(0)){
					case 'n':
					case 'N':
						continue;
					default: 
						saveMap(outputpath);
				}
				break;
			}
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
}

