package org.societegenerale.service;

import lombok.AllArgsConstructor;
import org.societegenerale.constants.ErrorMessage;
import org.societegenerale.exception.DataFileException;
import org.societegenerale.exception.InvalidInputException;
import org.societegenerale.model.DataFile;
import org.societegenerale.repository.WordsRepository;
import org.societegenerale.validator.WordTransformerValidator;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringJoiner;

@AllArgsConstructor
public class WordTransformerServiceImpl implements WordTransformerService {

    private WordsRepository wordsRepository;

    private WordTransformerValidator wordTransformerValidator;

    @Override
    public String getTransformedWords(final String filename) {
        try {

            // get data from the file
            final DataFile dataFile = wordsRepository.getFileData(filename);

            // validate the data before processing
            wordTransformerValidator.validateData(dataFile);

            final String startWord = dataFile.getStartWord();
            final String endWord = dataFile.getEndWord();
            final List<String> wordsList = dataFile.getWordsList();

            // push the start word into the queue
            final Queue<String> wordsToProcess = new LinkedList<>();
            wordsToProcess.add(startWord);

            // create a sublist of the wordlist as were only concerned about the words in-between
            final List<String> words = wordsList.subList(wordsList.indexOf(startWord), wordsList.indexOf(endWord));

            // keep a list of transformed words
            final StringJoiner transformedWords = new StringJoiner(", ");

            // keep looping until the queue is empty
            while (!wordsToProcess.isEmpty()) {

                // process each word in the queue
                for (int i = 0; i < wordsToProcess.size(); ++i) {

                    // remove the first word from the queue and convert to a char array
                    final char[] word = wordsToProcess.remove().toCharArray();

                    // now lets start transforming each character in the word to find matching words in the list of words
                    for (int j = 0; j < startWord.length(); ++j) {

                        // keep the original character at the current position
                        char originalCharacter = word[j];

                        // replace the current character with every possible lowercase alphabet
                        for (char k = 'a'; k <= 'z'; ++k) {

                            // replace the current character with the alphabet character
                            word[j] = k;

                            // convert it back to String to make it easier to work with
                            final String newWord = String.valueOf(word);

                            // if the new word is equal to the end word then were done so return transformed words
                            if (newWord.equals(endWord)) {
                                transformedWords.add(endWord);
                                return transformedWords.toString();
                            }

                            // otherwise remove the word from the list if it exists
                            if (words.contains(newWord)) {
                                words.remove(newWord);

                                // then push the newly created word onto the queue
                                wordsToProcess.add(newWord);

                                // and add the newly created word to the transformed words list
                                transformedWords.add(newWord);
                            }
                        }

                        // finally, restore the original character at the current position
                        word[j] = originalCharacter;
                    }
                }
            }

            return String.format(ErrorMessage.CANNOT_TRANSFORM, startWord, endWord);

        } catch (InvalidInputException | DataFileException ex) {
            return ex.getMessage();
        }
    }

}
