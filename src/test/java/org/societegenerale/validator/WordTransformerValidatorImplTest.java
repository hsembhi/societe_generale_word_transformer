package org.societegenerale.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.societegenerale.constants.ErrorMessage;
import org.societegenerale.exception.InvalidInputException;
import org.societegenerale.model.DataFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WordTransformerValidatorImplTest {

    private WordTransformerValidator wordTransformerValidator;
    @BeforeEach
    public void init() {
        wordTransformerValidator = new WordTransformerValidatorImpl();
    }

    @Test
    public void validateData_succeeds_without_throwing_an_exception(){

        // given

        final String startWord = "wordA";
        final String endWord = "wordE";
        final List<String> wordsList = new ArrayList<>(List.of("wordA","wordB","wordC","wordD","wordE"));

        final DataFile data = new DataFile(startWord, endWord, wordsList);

        // when and then

        assertDoesNotThrow(() -> wordTransformerValidator.validateData(data));

    }

    @Test
    public void validateData_throws_an_exception_when_start_and_end_words_are_the_same(){

        // given

        final String startWord = "wordA";
        final String endWord = "wordA";
        final List<String> wordsList = new ArrayList<>(List.of("wordA","wordB","wordC","wordD","wordE"));
        final DataFile data = new DataFile(startWord, endWord, wordsList);

        final String expectedResult = ErrorMessage.SAME_START_END_WORDS;

        // when

        final InvalidInputException actualResult = assertThrows(InvalidInputException.class, () -> wordTransformerValidator.validateData(data));

        // then

        assertNotNull(actualResult);
        assertEquals(expectedResult, actualResult.getMessage());
    }

    @Test
    public void validateData_throws_an_exception_when_start_is_not_present_in_word_list(){

        // given

        final String startWord = "wordX";
        final String endWord = "wordA";
        final List<String> wordsList = new ArrayList<>(List.of("wordA","wordB","wordC","wordD","wordE"));
        final DataFile data = new DataFile(startWord, endWord, wordsList);

        final String expectedResult = String.format(ErrorMessage.CANNOT_TRANSFORM, startWord, endWord);

        // when

        final InvalidInputException actualResult = assertThrows(InvalidInputException.class, () -> wordTransformerValidator.validateData(data));

        // then

        assertNotNull(actualResult);
        assertEquals(expectedResult, actualResult.getMessage());
    }

    @Test
    public void validateData_throws_an_exception_when_end_is_not_present_in_word_list(){

        // given

        final String startWord = "wordA";
        final String endWord = "wordX";
        final List<String> wordsList = new ArrayList<>(List.of("wordA","wordB","wordC","wordD","wordE"));
        final DataFile data = new DataFile(startWord, endWord, wordsList);

        final String expectedResult = String.format(ErrorMessage.CANNOT_TRANSFORM, startWord, endWord);

        // when

        final InvalidInputException actualResult = assertThrows(InvalidInputException.class, () -> wordTransformerValidator.validateData(data));

        // then

        assertNotNull(actualResult);
        assertEquals(expectedResult, actualResult.getMessage());
    }

}
