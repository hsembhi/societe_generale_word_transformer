package org.societegenerale;

import org.societegenerale.repository.WordsRepository;
import org.societegenerale.repository.WordsRepositoryImpl;
import org.societegenerale.service.WordTransformerService;
import org.societegenerale.service.WordTransformerServiceImpl;
import org.societegenerale.validator.WordTransformerValidator;
import org.societegenerale.validator.WordTransformerValidatorImpl;

public class Application
{

    private static WordsRepository wordsRepository = new WordsRepositoryImpl();

    private static WordTransformerValidator wordTransformerValidator = new WordTransformerValidatorImpl();

    private static WordTransformerService wordTransformerService = new WordTransformerServiceImpl(wordsRepository, wordTransformerValidator);

    public static void main( String[] args )
    {
        System.out.println(wordTransformerService.getTransformedWords(args[0]));
    }
}
