package ar.fiuba.tdd.template.tp0;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by eduu on 20/03/16.
 */
public class Pattern {
    private ArrayList<String> characters;
    private int quantificationMaxValue;
    private int quantificactionMinValue;
    private boolean literal;

    public Pattern(String regex, int maxNumber) {
        this.characters = new ArrayList<>();
        this.literal = false;

        if (regex.charAt(0) == '\\') {
            this.literal = true;
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
            characters.add(regex.substring(0,1));
            this.setQuantifiers(regex.charAt(regex.length() - 1),maxNumber);
        }
    }

    public String generateMatchingString() {
        String matchingString = "";

        for (int i = 0; i < this.getRandomNumberBetween(quantificactionMinValue,quantificationMaxValue); i++) {
            int numberOfCharacters = this.characters.size();
            if (numberOfCharacters > 1) {
                int index = this.getRandomNumberBetween(0,numberOfCharacters - 1);
                matchingString = matchingString.concat(this.characters.get(index));
            } else {
                if (!this.literal && this.characters.get(0) == ".") {
                    char character = (char) this.getRandomNumberBetween(0,255);
                    matchingString = matchingString.concat(String.valueOf(character));
                } else {
                    matchingString = matchingString.concat(this.characters.get(0));
                }
            }
        }

        return matchingString;
    }

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
        this.quantificactionMinValue = 1;
        if (character == '+') {
            this.quantificationMaxValue = maxNumber;
        } else if (character == '?') {
            this.quantificactionMinValue = 0;
        } else if (character == '*') {
            this.quantificactionMinValue = 0;
            this.quantificationMaxValue = maxNumber;
        }
    }

    private int getRandomNumberBetween(int numberA, int numberB) {
        Random generator = new Random();
        long range = (long) numberB - (long) numberA + 1;
        long fraction = (long) (range * generator.nextDouble());
        return (int) (fraction + numberA);
    }

}
