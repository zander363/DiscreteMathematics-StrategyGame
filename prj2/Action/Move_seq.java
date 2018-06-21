/*
 * Move_seq.java
 * Copyright (C) 2018  <@DESKTOP-TA60DPH>
 *
 * Distributed under terms of the MIT license.
 */

package Action;

import Action.Unit_state;

public class Move_seq
{
    int unit_no;
    String move_seq;
    Boolean attack_or_not;
    int attack_no;

    public Boolean valid(ArrayList<Unit_state> states,Coordinate[][] map,int height,int width){
        Unit_state state = states.get(this.unit_no);
        if(state.HP <= 0.0){
            if(move_seq.length()>0) return false;
            if(atack_or_not) return false;
        }
        int x = state.x;
        int y = state.y;
        int xn = 0;
        int yn = 0;
        int i = 0;
        for(i=0;i<this.move_seq.length();i++){
            char dir = move_seq.charAt(i);
            //x-axis
            switch(dir){
                case '1':
                case '2':
                case '8':
                xn = x+1;break;

                case '0':
                case '3':
                case '7':
                break;

                case '4':
                case '5':
                case '6':
                xn = x-1;break;

                default:
                return false;
            }
            //y-axis
            switch(dir){
                case '2':
                case '3':
                case '4':
                yn = y+1;break;

                case '0':
                case '1':
                case '5':
                break;

                case '6':
                case '7':
                case '8':
                yn = y-1;break;

                default:
                return false;
            }
            if(xn<0||xn>width||yn<0||yn>height) return false;
            if(!map[xn][yn].valid()) return false;

            //move it;
            map[x][y].exchange(map[xn][yn]);
        }
        
        return true;
    }

	public Unit_state(int unit_no, String move_seq,Boolean attack_or_not,int attack_no) {
        this.unit_no = unit_no;
        this.move_seq = move_seq;
        this.attack_or_not = attack_or_not;
        this.attack_no = attack_no;
	}
}