/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dodgeballmovement;

import java.io.Serializable;



public class Person implements Serializable {
    private String name;
    private int wins;
    
    public Person(String name){
        this.name = name;
        wins = 0;
    }
    
    public Person(String name, int wins){
        this(name);
        this.wins = wins;
    }
    
    public void addWin(){
        wins++;
    }
    
    public int getWin(){
        return wins;
    }
    
    public String getName(){
        return name;
    }
}
