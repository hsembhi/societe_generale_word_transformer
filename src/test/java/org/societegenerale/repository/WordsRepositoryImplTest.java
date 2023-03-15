package org.societegenerale.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.societegenerale.constants.ErrorMessage;
import org.societegenerale.exception.DataFileException;
import org.societegenerale.model.DataFile;

import static org.junit.jupiter.api.Assertions.*;

public class WordsRepositoryImplTest {

    private WordsRepository wordsRepository;

    @BeforeEach
    public void init() {
        wordsRepository = new WordsRepositoryImpl();
    }

    @Test
    public void getFileData_should_succeed_when_loading_data_from_a_file() throws DataFileException {

        // given

        final String filePath = getClass().getClassLoader().getResource("TestData.txt").getPath();

        // when

        final DataFile actualResult = wordsRepository.getFileData(filePath);

        // then

        assertNotNull(actualResult);
        assertNotNull(actualResult.getStartWord());
        assertNotNull(actualResult.getEndWord());
        assertNotNull(actualResult.getWordsList());

        assertFalse(actualResult.getWordsList().isEmpty());
        assertEquals(11, actualResult.getWordsList().size());
    }

    @Test
    public void getFileData_should_succeed_when_loading_a_file_with_duplicates() throws DataFileException {

        // given

        final String filePath = getClass().getClassLoader().getResource("TestData_duplicateWordsInWordsList.txt").getPath();

        // when

        final DataFile actualResult = wordsRepository.getFileData(filePath);

        // then

        assertNotNull(actualResult);
        assertNotNull(actualResult.getStartWord());
        assertNotNull(actualResult.getEndWord());
        assertNotNull(actualResult.getWordsList());

        assertFalse(actualResult.getWordsList().isEmpty());
        assertEquals(11, actualResult.getWordsList().size());
    }

    @Test
    public void getFileData_should_succeed_when_reading_data_file_without_spaces() throws DataFileException {

        // given

        final String filePath = getClass().getClassLoader().getResource("TestData_withoutSpaces.txt").getPath();

        // when

        final DataFile actualResult = wordsRepository.getFileData(filePath);

        // then

        assertNotNull(actualResult);
        assertNotNull(actualResult.getStartWord());
        assertNotNull(actualResult.getEndWord());
        assertNotNull(actualResult.getWordsList());

        assertFalse(actualResult.getWordsList().isEmpty());
        assertEquals(11, actualResult.getWordsList().size());
    }

    @Test
    public void getFileData_should_fail_when_unable_to_find_the_data_file() {

        // given

        final String fileName = "TestData.txt";
        final String unknownFileName = "UnknownTestData.txt";
        final String filePath = getClass().getClassLoader().getResource(fileName).getPath().replace(fileName, unknownFileName);

        // when

        final DataFileException actualResult = assertThrows(DataFileException.class, () -> wordsRepository.getFileData(filePath));

        // then

        assertNotNull(actualResult);
        assertEquals(ErrorMessage.FILE_NOT_FOUND, actualResult.getMessage());
    }

    @Test
    public void getFileData_should_fail_when_unable_to_parse_the_file_data() {

        // given

        final String filePath = getClass().getClassLoader().getResource("TestData_NoStartAndEndWords.txt").getPath();

        // when

        final DataFileException actualResult = assertThrows(DataFileException.class, () -> wordsRepository.getFileData(filePath));

        // then

        assertNotNull(actualResult);
        assertEquals(ErrorMessage.INVALID_DATA, actualResult.getMessage());
    }

    @Test
    public void getFileData_should_fail_when_there_is_a_missing_list_of_words() {

        // given

        final String filePath = getClass().getClassLoader().getResource("TestData_missingListOfWords.txt").getPath();

        // when

        final DataFileException actualResult = assertThrows(DataFileException.class, () -> wordsRepository.getFileData(filePath));

        // then

        assertNotNull(actualResult);
        assertEquals(ErrorMessage.INVALID_DATA, actualResult.getMessage());
    }

    @Test
    public void getFileData_should_fail_when_loading_an_empty_file() {

        // given

        final String filePath = getClass().getClassLoader().getResource("TestData_empty.txt").getPath();

        // when

        final DataFileException actualResult = assertThrows(DataFileException.class, () -> wordsRepository.getFileData(filePath));

        // then

        assertNotNull(actualResult);
        assertEquals(ErrorMessage.INVALID_DATA, actualResult.getMessage());
    }
}
