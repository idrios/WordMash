package com.idrios.wordmash.model.wordlist;

import java.util.HashMap;

/**
 * Created by idrios on 10/23/17.
 * Data Structure to ease traversal and discovery of new words
 * A better implementation would just turn the different words into json files and read from those
 */

public class MapNode {

    private char letter;        // The letter in the mapnode
    private int points;         // The amount of points associated with the letter
    private MapNode parent;     // The MapNode before this node
    private HashMap map;        // A map containing references to all children
    private boolean isWord;     // A boolean that states whether this node completes a word

    public char getLetter() {return letter;}
    public int getPoints() {return points;}
    public MapNode getParent() {return parent;}
    public HashMap getMap() {return map;}

    public void setLetter(char letter) {this.letter = letter;}
    public void setPoints(int points) {this.points = points;}
    public void setParent(MapNode parent) {this.parent = parent;}
    public void setMap(HashMap map) {this.map = map;}

    // Constructor arguments.. unless declared otherwise, points = default values from hashmap, isTerminator = false.
    public MapNode(char letter){
        this.letter = letter;
        try { // read the point value from the hash
            points = (int) HashmapHelper.getLetterValuesPair().get(letter);
        } catch (Exception e){
            e.printStackTrace();
            points = 1;
        }
        this.parent = null;
        this.map = new HashMap();
    }
    public MapNode(char letter, MapNode parent){
        this.letter = letter;
        this.parent = parent;
        try { // read the point value from the hash
            points = (int) HashmapHelper.getLetterValuesPair().get(letter);
        } catch (Exception e){
            e.printStackTrace();
            points = 1;
        }
        this.map = new HashMap();

    }
    public MapNode(char letter, int points, MapNode parent){
        this.letter = letter;
        this.points = points;
        this.parent = parent;
        this.map = new HashMap();
    }
    public MapNode(char letter, MapNode parent, HashMap map) {
        this.letter = letter;
        this.parent = parent;
        this.map = map;
        try { // read the point value from the hash
            points = (int) HashmapHelper.getLetterValuesPair().get(letter);
        } catch (Exception e){
            points = 1;
            e.printStackTrace();
        }
    }
    public MapNode(char letter, int points, MapNode parent, HashMap map) {
        this.letter = letter;
        this.points = points;
        this.parent = parent;
        this.map = map;
    }

    // Return a shallow copy of MapNode: entries are not changed
    public MapNode clone(){
        return new MapNode(letter, points, parent, map);
    }

}
