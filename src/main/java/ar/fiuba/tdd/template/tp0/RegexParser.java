package ar.fiuba.tdd.template.tp0;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eduu on 20/03/16.
 */
public class RegexParser {
    private static final List QUANTIFIERS = new ArrayList<Character>(){
        {
            add('+');
            add('?');
            add('*');
        }
    };

    //Main method, returns a list of tokens
    public ArrayList<Token> parse(String regex, int maxNumber) {
        ArrayList<Token> tokens = new ArrayList<Token>();
        int offset = 0;
        int newOffset;
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

            tokens.add(new Token(regex.substring(offset,newOffset),maxNumber));
            offset = newOffset;
        }

        return tokens;
    }

    private int read(String regex, int offset) {
        return offset + 1 + this.checkForQuantifier(regex,offset + 1);
    }

    //Considers the possibility of a literal \\]
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

    //if the function catches the exception, it's because it reached the end of the string
    private int checkForQuantifier(String regex, int index) {
        try {
            char character = regex.charAt(index);
            if (QUANTIFIERS.contains(character)) {
                return 1;
            } else {
                return 0;
            }
        } catch (StringIndexOutOfBoundsException outOfBoundException) {
            return 0;
        }
    }
}
