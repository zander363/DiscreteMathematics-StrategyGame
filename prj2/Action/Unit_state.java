/*
 * Unit_state.java
 * Copyright (C) 2018  <@DESKTOP-TA60DPH>
 *
 * Distributed under terms of the MIT license.
 */
package Action;

public class Unit_state
{
	char unit_cate;
	Unit cate;
	int x;
	int y;
	double HP;

	public Unit_state(char unit_cate, Unit cate,int x,int y,double HP) {
		this.unit_cate = unit_cate;
		this.cate = cate;
		this.x = x;
		this.y = y;
		this.HP = HP;
	}
}

