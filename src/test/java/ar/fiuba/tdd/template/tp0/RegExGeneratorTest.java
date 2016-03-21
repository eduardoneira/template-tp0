package ar.fiuba.tdd.template.tp0;

import org.junit.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertTrue;

public class RegExGeneratorTest {

    private final int maxOcurrences = 10;

    private boolean validate(String regEx, int numberOfResults) {
        RegExGenerator generator = new RegExGenerator(maxOcurrences);
        List<String> results = generator.generate(regEx, numberOfResults);
        Pattern pattern = Pattern.compile("^" + regEx + "$");
        return results
                .stream()
                .reduce(true,
                    (acc, item) -> {
                        Matcher matcher = pattern.matcher(item);
                        return acc && matcher.find();
                    },
                    (item1, item2) -> item1 && item2);
    }

    @Test
    public void testAnyCharacter() {
        assertTrue(validate(".", 1));
    }
    
    @Test
    public void testParserOnlyLiterals() {
        RegexParser parser = new RegexParser(100);
        assertTrue(parser.parse("abc").size() == 3);
    }
    
    @Test
    public void testParserLiteralsAndQuantifications() {
        RegexParser parser = new RegexParser(100);
        assertTrue(parser.parse("\\++hola?").size() == 5);
    }

    @Test
    public void testParserSets() {
        RegexParser parser = new RegexParser(100);
        assertTrue(parser.parse("[abC]\\.od").size() == 4);
    }

    @Test
    public void testParserSetsComplex() {
        RegexParser parser = new RegexParser(100);
        assertTrue(parser.parse("[a\\]C].od").size() == 4);
    }

    @Test
    public void testParserComplexRegex() {
        RegexParser parser = new RegexParser(100);
        assertTrue(parser.parse("\\[h*[abC423]?\\.+\\@?").size() == 5);
    }

    @Test
    public void testMultipleCharacters() {
        assertTrue(validate("...", 1));
    }

    @Test
    public void testLiteral() {
        assertTrue(validate("\\@", 1));
    }

    @Test
    public void testLiteralDotCharacter() {
        assertTrue(validate("\\@..", 1));
    }

    @Test
    public void testZeroOrOneCharacter() {
        assertTrue(validate("\\@.h?", 1));
    }

    @Test
    public void testCharacterSet() {
        assertTrue(validate("[abc]", 1));
    }

    @Test
    public void testCharacterSetWithQuantifiers() {
        assertTrue(validate("[abc]+", 1));
    }
    
    @Test
    public void testComplexRegex1() {
        assertTrue(validate("\\++hola?", 1));
    }
    
    @Test
    public void testComplexRegex2() {
        assertTrue(validate("\\[h*[abC423]?\\.+\\@?", 1));
    }
    
    @Test
    public void testComplexRegex3() {
        assertTrue(validate("[abC]\\.od", 1));
    }
    
    @Test
    public void testComplexRegex4() {
        assertTrue(validate("[a\\]C].od", 1));
    }
}
