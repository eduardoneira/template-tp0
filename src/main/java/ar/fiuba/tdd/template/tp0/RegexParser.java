package ar.fiuba.tdd.template.tp0;

import java.util.ArrayList;

/**
 * Created by eduu on 20/03/16.
 */
public class RegexParser {
    private int maxNumber;

    public RegexParser(int number) {
        super();
        this.maxNumber = number;
    }

    public ArrayList<Pattern> parse(String regex) {
        ArrayList<Pattern> patterns = new ArrayList<Pattern>();
        int offset = 0;
        int newOffset = 0;
        int regexLength = regex.length();

        while (offset < regexLength) {
            char character = regex.charAt(offset);
            if (character == '\\') {
                newOffset = readReservedCharacter(regex,offset);
            } else if (character == '[') {
                newOffset = readSet(regex,offset);
            } else {
                newOffset = read(regex,offset);
            }

            patterns.add(new Pattern(regex.substring(offset,newOffset),this.maxNumber));
            offset = newOffset;
        }

        return patterns;
    }

    private int read(String regex, int offset) {
        return offset + 1 + this.checkForQuantifier(regex,offset + 1);
    }

    private int readSet(String regex, int offset) {
        int newOffset = regex.indexOf("]",offset);

        while (regex.charAt(newOffset - 1) == '\\' || newOffset == -1 ) {
            newOffset = regex.indexOf("]",newOffset + 1);
        }

        return newOffset + 1 + this.checkForQuantifier(regex,newOffset + 1);
    }

    private int readReservedCharacter(String regex, int offset) {
        return offset + 2 + this.checkForQuantifier(regex,offset + 2);
    }

    private int checkForQuantifier(String regex, int index) {
        try {
            char character = regex.charAt(index);
            if (character == '+' || character == '?' || character == '*') {
                return 1;
            } else {
                return 0;
            }
        } catch (StringIndexOutOfBoundsException outOfBoundException) {
            return 0;
        }
    }
}
