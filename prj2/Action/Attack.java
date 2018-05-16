/*
 * Attack.java
 * Copyright (C) 2018  <@DESKTOP-TA60DPH>
 *
 * Distributed under terms of the MIT license.
 */
package Action;

public class Attack
{
	String name;
	Type type;
	Boolean range;
	int damage;
	int number;
	
	public Attack(String name, Type type,Boolean range,int damage,int number) {
		this.name = name;
		this.type = type;
		this.range = range;
		this.damage = damage;
		this.number = number;
	}
}

