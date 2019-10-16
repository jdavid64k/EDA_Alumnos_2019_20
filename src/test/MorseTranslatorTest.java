package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.MorseTranslator;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MorseTranslatorTest {

    MorseTranslator translatorSample;

    @BeforeEach
    void setUp() {
        char[] charsetSample = {'a', 'h', 'r', 'u', 'l',
                'n', 'd', 'o', 'm', 'e',
                'w'};
        String[] codesSample = {".", "..", "...", "..-", ".-"
                , ".-.", ".--", "-", "-.", "--."
                , "---"};

        translatorSample = new MorseTranslator(charsetSample, codesSample);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void decode() {
        String input, expected, output;
        input = ". ";
        expected = "a";
        output = translatorSample.decode(input);
        assertEquals(expected, output);

        input = ".";
        expected = "a";
        output = translatorSample.decode(input);
        assertEquals(expected, output);

        input = ". .";
        expected = "aa";
        output = translatorSample.decode(input);
        assertEquals(expected, output);

        input = "..";
        expected = "h";
        output = translatorSample.decode(input);
        assertEquals(expected, output);


        input = ".. " + "- " + ".- " + ". " + " " + "-." + "..-" + ".-." + ".--" + "-";
        expected = "hola mundo";
        output = translatorSample.decode(input);
        assertEquals(expected, output);

        input = ".. --..- .- -  ---- ....- .--";
        expected = "hello world";
        output = translatorSample.decode(input);
        assertEquals(expected, output);

    }

    @Test
    void encode() {
        String input = "a";
        String expected = ". ";
        String output = translatorSample.encode(input);
        assertEquals(expected, output);

        input = "a ";
        expected = ".  ";
        output = translatorSample.encode(input);
        assertEquals(expected, output);

        input = "a a";
        expected = ".  . ";
        output = translatorSample.encode(input);
        assertEquals(expected, output);

        input = "h";
        expected = ".. ";
        output = translatorSample.encode(input);
        assertEquals(expected, output);

        input = "u";
        expected = "..-";
        output = translatorSample.encode(input);
        assertEquals(expected, output);

        input = "hola mundo";
        expected = ".. " + "- " + ".- " + ". " + " " + "-." + "..-" + ".-." + ".--" + "- ";
        output = translatorSample.encode(input);
        assertEquals(expected, output);

        input = "hello world";
        expected = ".. --..- .- -  ---- ....- .--";
        output = translatorSample.encode(input);
        assertEquals(expected, output);


    }
}