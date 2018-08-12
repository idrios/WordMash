package com.idrios.wordfall.model.wordlist;

import com.idrios.wordfall.utils.Utils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by idrios on 3/20/18.
 */

public class WordMap {

    public final String MAINWORD;
    public final String[] WORDS;

    private Set<String> SIX_LETTER_WORDS;
    private Set<String> FIVE_LETTER_WORDS;
    private Set<String> FOUR_LETTER_WORDS;
    private Set<String> THREE_LETTER_WORDS;

    public Set<String> getSixLetterWords(){return SIX_LETTER_WORDS;}
    public Set<String> getFiveLetterWords(){return FIVE_LETTER_WORDS;}
    public Set<String> getFourLetterWords(){return FOUR_LETTER_WORDS;}
    public Set<String> getThreeLetterWords(){return THREE_LETTER_WORDS;}

    public WordMap(final String word){
        MAINWORD = word;
        SIX_LETTER_WORDS = new HashSet<String>();
        FIVE_LETTER_WORDS = new HashSet<String>();
        FOUR_LETTER_WORDS = new HashSet<String>();
        THREE_LETTER_WORDS = new HashSet<String>();
        loadWords(SIX_LETTER_WORDS, HashmapHelper.getSixLetterWordMap(), 6);
        loadWords(FIVE_LETTER_WORDS, HashmapHelper.getFiveLetterWordMap(), 5);
        loadWords(FOUR_LETTER_WORDS, HashmapHelper.getFourLetterWordMap(), 4);
        loadWords(THREE_LETTER_WORDS, HashmapHelper.getThreeLetterWordMap(), 3);
        WORDS = loadWords(THREE_LETTER_WORDS, FOUR_LETTER_WORDS, FIVE_LETTER_WORDS, SIX_LETTER_WORDS);
    }

    private String[] loadWords(Set<String>... allSizeWords){
        int wordsize = 0;
        for(Set iSizeWords : allSizeWords){
            wordsize += iSizeWords.size();
        }
        String[] WORDS = new String[wordsize];
        int ind = 0;
        for(Set<String> iSizeWords : allSizeWords){
            for(String word : iSizeWords){
                WORDS[ind] = word;
                ind += 1;
            }
        }
        return WORDS;
    }

    private void loadWords(Set wordBank, MapNode root, int len){
        char[] letters = MAINWORD.toCharArray();

        findNLengthCombinations(wordBank, root, letters, len, new char[6]);
        int i = 0;
    }

    private void findNLengthCombinations(Set wordBank, MapNode node, char[] cin, int nLength, char[] newWordIn){
        char nulChar = '\u0000';
        if(nLength == 0){ // Base case: got to the end of a chain in the hashmap and all characters were part of a word
            wordBank.add(Utils.mkStringFromCharArray(newWordIn).trim()); //Add the word to the set of words [trimming is necessary to remove null characters]
            return;
        }
        for(int i = 0; i<cin.length; i++){ //Repeat for every letter
            // Iterate to next character when it's not included in a word (or is null i.e. has been used already)
            while(cin[i] == nulChar || !node.getMap().containsKey(cin[i])){
                i += 1;
                if(i>=cin.length){  // If you've traversed all characters in cin, there are no more words to add from this iteration.
                    return;
                }
            }

            // If the character is part of a word, find the next null value in 'newWord' and fill it with the character
            char key = cin[i];
            char[] newWordOut = newWordIn.clone(); // Don't want to make changes to newWordIn since it gets reused
            for(int j = 0; j<newWordOut.length; j++){
                if(newWordOut[j] == nulChar){
                    newWordOut[j] = key;
                    break;
                }
            }
            char[] cout = cin.clone();
            cout[i] = nulChar; // change cout to the null value
            MapNode mapNodeOut = (MapNode)node.getMap().get(cin[i]);
            findNLengthCombinations(wordBank, mapNodeOut, cout, nLength-1, newWordOut);
        }
    }
}
