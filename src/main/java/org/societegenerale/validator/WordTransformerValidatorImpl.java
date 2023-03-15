package org.societegenerale.validator;

import org.societegenerale.constants.ErrorMessage;
import org.societegenerale.exception.InvalidInputException;
import org.societegenerale.model.DataFile;

import java.util.List;

public class WordTransformerValidatorImpl implements WordTransformerValidator {

    @Override
    public void validateData(final DataFile data) {
        isStartAndEndWordTheSame(data);
        doesWordListContainStartAndEndWords(data.getStartWord(), data.getEndWord(), data.getWordsList());
    }

    private void isStartAndEndWordTheSame(final DataFile data) {
        if(data.getStartWord().equals(data.getEndWord())){
            throw new InvalidInputException(ErrorMessage.SAME_START_END_WORDS);
        }
    }

    private void doesWordListContainStartAndEndWords(final String startWord, final String endWord, final List<String> wordsList) {
        if(!wordsList.contains(startWord) || !wordsList.contains(endWord)){
            throw new InvalidInputException(String.format(ErrorMessage.CANNOT_TRANSFORM, startWord, endWord));
        }
    }

}
