package org.societegenerale.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.societegenerale.constants.ErrorMessage;
import org.societegenerale.exception.DataFileException;
import org.societegenerale.exception.InvalidInputException;
import org.societegenerale.model.DataFile;
import org.societegenerale.repository.WordsRepository;
import org.societegenerale.validator.WordTransformerValidator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class WordTransformerServiceImplTest {

    @InjectMocks
    private WordTransformerServiceImpl wordTransformerService;

    @Mock
    private WordsRepository wordsRepository;

    @Mock
    private WordTransformerValidator wordTransformerValidator;

    @Test
    public void transformWord_should_return_a_chain_of_transformed_words() throws DataFileException  {

        // given

        final String filename = "TestData.txt";
        final String startWord = "cat";
        final String endWord = "dog";
        final List<String> wordsList = new ArrayList<>(List.of("and", "but", "cat", "cot", "dot", "dog", "get", "his", "not", "you"));
        final DataFile dataFile = new DataFile(startWord, endWord, wordsList);

        final String expectedResult = "cat, cot, dot, dog";

        given(wordsRepository.getFileData(filename)).willReturn(dataFile);

        // when

        final String actualResult = wordTransformerService.getTransformedWords(filename);

        // then

        assertNotNull(actualResult);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void transformWord_should_return_an_error_when_start_and_end_words_are_the_same() throws DataFileException {

        // given

        final String filename = "TestData.txt";
        final String startWord = "cat";
        final String endWord = "cat";
        final List<String> wordsList = new ArrayList<>(List.of("and", "but", "cat", "cot", "dot", "dog", "get", "his", "not", "you"));
        final DataFile dataFile = new DataFile(startWord, endWord, wordsList);
        final String expectedResult = ErrorMessage.SAME_START_END_WORDS;

        given(wordsRepository.getFileData(filename)).willReturn(dataFile);

        // when

        doThrow(new InvalidInputException(ErrorMessage.SAME_START_END_WORDS)).when(wordTransformerValidator).validateData(dataFile);
        final String actualResult = wordTransformerService.getTransformedWords(filename);

        // then

        assertNotNull(actualResult);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void transformWord_should_return_cannot_transform_message_when_using_a_word_not_present_in_the_word_list() throws DataFileException {

        // given

        final String filename = "TestData.txt";
        final String startWord = "cat";
        final String endWord = "pig";
        final List<String> wordsList = new ArrayList<>(List.of("and", "but", "cat", "cot", "dot", "dog", "get", "his", "not", "you"));
        final DataFile dataFile = new DataFile(startWord, endWord, wordsList);
        final String expectedResult = String.format(ErrorMessage.CANNOT_TRANSFORM, startWord, endWord);

        given(wordsRepository.getFileData(filename)).willReturn(dataFile);

        // when

        doThrow(new InvalidInputException(String.format(ErrorMessage.CANNOT_TRANSFORM, startWord, endWord))).when(wordTransformerValidator).validateData(dataFile);
        final String actualResult = wordTransformerService.getTransformedWords(filename);

        // then

        assertNotNull(actualResult);
        assertEquals(expectedResult, actualResult);
    }
}
