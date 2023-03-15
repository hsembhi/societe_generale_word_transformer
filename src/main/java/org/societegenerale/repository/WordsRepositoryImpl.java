package org.societegenerale.repository;

import lombok.AllArgsConstructor;
import org.societegenerale.constants.ErrorMessage;
import org.societegenerale.exception.DataFileException;
import org.societegenerale.model.DataFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class WordsRepositoryImpl implements WordsRepository {

    @Override
    public DataFile getFileData(final String filename) throws DataFileException {
        try {
            final List<String> content = Files.readAllLines(new File(filename).toPath());

            final String comma = ",";
            final String space = "\\s+";
            final String blank = "";

            final DataFile dataFile = new DataFile();
            dataFile.setStartWord(content.get(0).replaceAll(space,blank).split(comma)[0]);
            dataFile.setEndWord(content.get(0).replaceAll(space,blank).split(comma)[1]);
            dataFile.setWordsList(removeDuplicates(content.get(1).replaceAll(space,blank).split(comma)));

            return dataFile;
        } catch (NoSuchFileException ex) {
            throw new DataFileException(ErrorMessage.FILE_NOT_FOUND);
        } catch (Exception ex) {
            throw new DataFileException(ErrorMessage.INVALID_DATA);
        }
    }

    private List<String> removeDuplicates(final String[] words){
        return Arrays.stream(words).distinct().collect(Collectors.toList());
    }

}
