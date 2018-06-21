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

	public void exchange(Coordinate co2){
		char tmp_cate = co2.unit_cate;
		int tmp_id = co2.unit_id;
		co2.unit_cate = this.unit_cate;
		co2.unit_id = this.unit_id;
		this.unit_cate = tmp_cate;
		this.unit_id = tmp_id;
		return;
	}
	public Boolean valid(){
		if(this.sym=='Q') return false;
		if(this.unit_id >= 0) return false;
		return true;
	}

}

