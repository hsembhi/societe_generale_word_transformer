package org.societegenerale.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataFile {

    private String startWord;

    private String endWord;

    private List<String> wordsList;

}
