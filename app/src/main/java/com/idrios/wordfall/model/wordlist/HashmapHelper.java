package com.idrios.wordfall.model.wordlist;

import android.content.Context;

import com.idrios.wordfall.R;
import com.idrios.wordfall.common.Shared;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * Created by idrios on 3/20/18.
 * ** Because this is in the Utils package, all methods should be static so there should be another
 * class that keeps the correct maps (changed to "Model" package but I still want
 */

public class HashmapHelper {
    private static HashMap LETTER_VALUES_PAIR;

    private static MapNode SIX_LETTER_WORD_MAP;
    private static MapNode FIVE_LETTER_WORD_MAP;
    private static MapNode FOUR_LETTER_WORD_MAP;
    private static MapNode THREE_LETTER_WORD_MAP;

    public static String[] sixS;
    public static String[] fiveS;
    public static String[] fourS;
    public static String[] threeS;

    public static HashMap getLetterValuesPair(){
        return LETTER_VALUES_PAIR;
    }
    public static MapNode getSixLetterWordMap(){
        return SIX_LETTER_WORD_MAP;
    }
    public static MapNode getFiveLetterWordMap(){
        return FIVE_LETTER_WORD_MAP;
    }
    public static MapNode getFourLetterWordMap(){
        return FOUR_LETTER_WORD_MAP;
    }
    public static MapNode getThreeLetterWordMap(){
        return THREE_LETTER_WORD_MAP;
    }

    public static void init(){
        Context context = Shared.context;

        // Load points associated for each letter
        initLetterValuesPair(context); //Assign points for each letter,

        // Load strings from resource file to string array
        loadWordsFromFile(context);

        // Load words from string array into map-format
        initLetterWordMap();

        System.out.println("INITIALIZING");
    }

    ///////////// Initiate Word-Maps (onCreate methods) ///////////

    public static void initLetterValuesPair(Context context){
        LETTER_VALUES_PAIR = new HashMap();
        int fIDLetterVals = R.raw.letter_value;
        String[] letterValsS = readRawTextFile(context, fIDLetterVals);
        System.out.println("DONE");

        for(int i = 0; i < letterValsS.length; i++){
            String[] lin = letterValsS[i].split("-");
            char key = lin[0].charAt(0);
            int val = Integer.parseInt(lin[1]);
            LETTER_VALUES_PAIR.put(key, val);
        }
    }

    public static void loadWordsFromFile(Context context){
        int fIDSix = R.raw.six_letter_words;
        int fIDFive = R.raw.five_letter_words;
        int fIDFour = R.raw.four_letter_words;
        int fIDThree = R.raw.three_letter_words;
        sixS = readRawTextFile(context, fIDSix);
        fiveS = readRawTextFile(context, fIDFive);
        fourS = readRawTextFile(context, fIDFour);
        threeS = readRawTextFile(context, fIDThree);
    }

    public static void initLetterWordMap(){
        SIX_LETTER_WORD_MAP = new MapNode('\u0000');  // Init root nodes
        FIVE_LETTER_WORD_MAP = new MapNode('\u0000'); // Value for Letter is null character
        FOUR_LETTER_WORD_MAP = new MapNode('\u0000');
        THREE_LETTER_WORD_MAP = new MapNode('\u0000');
        buildLetterWordMap(SIX_LETTER_WORD_MAP, sixS);
        buildLetterWordMap(FIVE_LETTER_WORD_MAP, fiveS);
        buildLetterWordMap(FOUR_LETTER_WORD_MAP, fourS);
        buildLetterWordMap(THREE_LETTER_WORD_MAP, threeS);
    }

    private static void buildLetterWordMap(MapNode root, String[] dictionary){
        for(int i = 0; i < dictionary.length; i++){
            String word = dictionary[i];               // take one word from list
            char[] letters = word.toCharArray();       // break word into letters
            buildSubLetterWordMap(root, letters, 0);        // begin at index 0
        }
    }

    private static void buildSubLetterWordMap(MapNode node, char[] c, int i){
        if(i >= c.length) {      // Base Case.. iterated through all letters
            return;
        }
        char key = c[i];
        HashMap map = node.getMap();
        MapNode childNode;
        if(map.containsKey(key)) {
            childNode = (MapNode) map.get(key);
        } else{
            childNode = new MapNode(c[i], node, new HashMap());
            map.put(key, childNode);
        }
        i+=1;
        buildSubLetterWordMap(childNode, c, i); // Fill values for new, empty MapNode
    }


    /////////////// Miscellaneous Functions ////////////////////////////

    public static boolean mapContainsString(String string){
        MapNode node; // Consider changing to switch/case
        switch (string.length()) {
            case 6:
                node = SIX_LETTER_WORD_MAP;
                break;
            case 5:
                node = FIVE_LETTER_WORD_MAP;
                break;
            case 4:
                node = FOUR_LETTER_WORD_MAP;
                break;
            case 3:
                node = THREE_LETTER_WORD_MAP;
                break;
            default:
                return false;
        }
        return mapHasCharArr(node, string.toCharArray(), 0);
    }

    private static boolean mapHasCharArr(MapNode node, char[] c, int i){
        if(i == c.length) { // Base Case.. iterated through all letters without finding a difference
            return true;
        }
        char key = c[i];
        HashMap map = node.getMap();
        if(map.containsKey(key)){   // If map contains a letter, reiterate for the next letter
            MapNode child = (MapNode)map.get(key);
            return mapHasCharArr(child, c, i+1);
        }
        else{   // If map does not contain the letter, the word does not exist in our dictionary
            return false;
        }
    }

    public static String[] readRawTextFile(Context context, int fileId){
        InputStream inputStream = context.getResources().openRawResource(fileId);

        InputStreamReader inputReader = new InputStreamReader(inputStream);
        BufferedReader buffReader = new BufferedReader(inputReader);
        String line;
        StringBuilder text = new StringBuilder();

        try {
            while (( line = buffReader.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
        } catch (IOException e) {
            return null;
        }
        //Modified to parse the string into an array
        //return text.toString();
        return text.toString().replaceAll(" ","").toLowerCase().split("\\n");
    }




}
