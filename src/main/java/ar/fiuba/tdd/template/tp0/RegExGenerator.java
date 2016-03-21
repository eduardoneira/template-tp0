package ar.fiuba.tdd.template.tp0;

import java.util.ArrayList;
import java.util.List;

public class RegExGenerator {
    private int maxLength;

    public RegExGenerator(int maxLength) {
        this.maxLength = maxLength;
    }

    public List<String> generate(String regEx, int numberOfResuls) {
        RegexParser parser = new RegexParser(maxLength);
        ArrayList<Pattern> regexAsPatterns = parser.parse(regEx);
        ArrayList<String> strings = new ArrayList<String>();

        for (int i = 0; i < numberOfResuls; i++) {
            String currentString = "";
            for ( Pattern elem : regexAsPatterns) {
                currentString = currentString.concat(elem.generateMatchingString());
            }
            strings.add(currentString);
        }

        return strings;
    }
}