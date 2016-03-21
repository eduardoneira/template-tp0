package ar.fiuba.tdd.template.tp0;

import java.util.ArrayList;
import java.util.List;

public class RegExGenerator {
    private int maxLength;

    public RegExGenerator(int maxLength) {
        this.maxLength = maxLength;
    }

    //First I parse the regEx to get all the tokens and then I proceed to generate strings for them
    public List<String> generate(String regEx, int numberOfResults) {
        RegexParser parser = new RegexParser();
        ArrayList<Token> regexAsTokens = parser.parse(regEx,maxLength);
        ArrayList<String> strings = new ArrayList<String>();

        for (int i = 0; i < numberOfResults; i++) {
            String currentString = "";
            for ( Token elem : regexAsTokens) {
                currentString = currentString.concat(elem.generateMatchingString());
            }
            strings.add(currentString);
        }

        return strings;
    }
}