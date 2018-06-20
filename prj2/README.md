# Project 2 - Movement Checker

This subject is an interface for user to
command their units to move or attack
In the mean time, it would check is all movement correct.

## Class Dependence
In this subproject, there are a lot of class.
And the hierarchy of class is very important.
I design it like the image below.


<img src=".\Heirarchy.png" width="600">

## Algorithm
Basically, I use the BFS algorithm to search the range a unit can reach



## Usage

This program is statically dependent on the relative file like

`../prj0/ex_move.txt`,`ex_terrain_sym.txt`,`ex_unit.txt`

Beside you will need a map file,

you can generate one by [Project 1 - Map Generator](../prj1)



When the dependent file is ready, 

you can compile it under this folder,

`javac Action/Checker.java `

Then, Execute it. (Or Execute it directly)

`java Action.Checker`