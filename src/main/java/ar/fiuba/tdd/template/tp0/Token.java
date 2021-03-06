package ar.fiuba.tdd.template.tp0;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by eduu on 20/03/16.
 */
public class Token {
    private ArrayList<String> characters;
    private int quantificationMaxValue;
    private int quantificationMinValue;
    //Apparently not all ascii are suitable for a regex '.', I found these three
    //but there may be more
    private static final List INVALIDASCIICHARACTERS = new ArrayList<Integer>(){
        {
            add(10);
            add(13);
            add(133);
        }
    };

    // This constructor sets the available characters of the string and its quantification
    // the list of characters remains empty in case the token is a '.'
    public Token(String regex, int maxNumber) {
        this.characters = new ArrayList<>();

        if (regex.charAt(0) == '\\') {
            characters.add(regex.substring(1,2));
            if (regex.length() == 3) {
                this.setQuantifiers(regex.charAt(regex.length() - 1),maxNumber);
            } else {
                this.setQuantifiers(regex.charAt(0),maxNumber);
            }
        } else if (regex.charAt(0) == '[') {
            this.addSet(regex);
            this.setQuantifiers(regex.charAt(regex.length() - 1),maxNumber);
        } else {
            if (regex.charAt(0) != '.') {
                characters.add(regex.substring(0, 1));
            }
            this.setQuantifiers(regex.charAt(regex.length() - 1),maxNumber);
        }
    }

    public String generateMatchingString() {
        String matchingString = "";

        for (int i = 0; i < this.getRandomNumberBetween(quantificationMinValue,quantificationMaxValue); i++) {
            int numberOfCharacters = this.characters.size();
            if (numberOfCharacters > 1) {
                int index = this.getRandomNumberBetween(0,numberOfCharacters - 1);
                matchingString = matchingString.concat(this.characters.get(index));
            } else {
                if (numberOfCharacters == 0) {
                    char character = this.getRandomAscii();
                    matchingString = matchingString.concat(String.valueOf(character));
                } else {
                    matchingString = matchingString.concat(this.characters.get(0));
                }
            }
        }

        return matchingString;
    }

    // Set considers literals inside it
    private void addSet(String set) {
        int offset = 1;
        int setLength = set.length() - 1;

        if (set.charAt(setLength) != ']') {
            setLength--;
        }

        while (offset < setLength) {
            if (set.charAt(offset) != '\\') {
                characters.add(set.substring(offset,offset + 1));
                offset++;
            } else {
                characters.add(set.substring(offset + 1,offset + 2));
                offset = offset + 2;
            }
        }
    }

    private void setQuantifiers(char character, int maxNumber) {
        this.quantificationMaxValue = 1;
        this.quantificationMinValue = 1;
        if (character == '+') {
            this.quantificationMaxValue = maxNumber;
        } else if (character == '?') {
            this.quantificationMinValue = 0;
        } else if (character == '*') {
            this.quantificationMinValue = 0;
            this.quantificationMaxValue = maxNumber;
        }
    }

    //Function from internet
    private int getRandomNumberBetween(int numberA, int numberB) {
        Random generator = new Random();
        long range = (long) numberB - (long) numberA + 1;
        long fraction = (long) (range * generator.nextDouble());
        int  returnNumber = (int) (fraction + numberA);

        return returnNumber;
    }

    private char getRandomAscii() {
        int  asciiNumber = this.getRandomNumberBetween(0,255);

        while (INVALIDASCIICHARACTERS.contains(asciiNumber)) {
            asciiNumber = this.getRandomNumberBetween(0,255);
        }
        return (char) asciiNumber;
    }

}
