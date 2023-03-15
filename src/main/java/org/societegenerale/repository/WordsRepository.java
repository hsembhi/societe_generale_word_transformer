package org.societegenerale.repository;

import org.societegenerale.exception.DataFileException;
import org.societegenerale.model.DataFile;

public interface WordsRepository {

    DataFile getFileData(final String filePath) throws DataFileException;

}
