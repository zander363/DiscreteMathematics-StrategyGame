/*
 * Coordinate.java
 * Copyright (C) 2018  <@DESKTOP-TA60DPH>
 *
 * Distributed under terms of the MIT license.
 */
package Action;

public class Coordinate
{
	int x;
	int y;
	char sym;
	char unit_cate;
	int unit_id;

	public Coordinate(int x,int y,char sym,char unit_cate,int unit_id) {
		this.x = x;
		this.y = y;
		this.sym = sym;
		this.unit_cate = unit_cate;
		this.unit_id = unit_id;
	}

	public Boolean valid(){
		if(this.sym=='#') return false;
		if(this.unit_id >= 0) return false;
		return true;
	}

}

