# Word Transformer

#### Authors: Harvinder Sembhi

---

### Project details

**Java version:** 17

**Project type:** Maven

**Build tool:** Maven Wrapper

---

### Building the Jar

This project is setup with Maven Wrapper and so the following maven command can be used to build the JAR file:

Navigate to the root directory of the project and run:

```
./mvnw clean install
```

A jar file will be created in the /target directory.

---

### Running the Jar

You can run the jar file by executing the following command. The jar file can be placed anywhere.
```
java -jar JAR_FILE "FILENAME"
```

_JAR_FILE_ = the jar file you want to run.

_FILENAME_ = name of the file you want the app to load. The file should be located in the same directory as the jar file.

For example, if you are executing the jar file form the project root directory then the command will be:

```
java -jar target/word-transformer-1.0.0-SNAPSHOT.jar "data.txt"
```

---

### Data File

The data file should be in the following format and located in the same directory as the jar:

```
Line 1: start_word, end_word
Line 2: Comma separated list of all the valid words
```

For example:

```
cat, dog
and, but, cat, cot, cup, dot, dog, get, his, not, you
```

---

### Output

The app will print out all the transformed words to console.

For example, if the data file contains the following:

```
cat, dog
and, but, cat, cot, cup, dot, dog, get, his, not, you
```

then the output will be:

```
cat, cot, dot, dog
```

it might not be possible always to transform one word to another in which case the output will be:

```
Cannot transform start_word to end_word
```

and if there is an error then the output will be:

```
ERROR: ERROR_MESSAGE
```

---

### Error Messages

Here is a list of error messages and descriptions:

| Errors                                              | Descriptions                                    |
|-----------------------------------------------------|-------------------------------------------------|
| ERROR: File not found                               | Unable to locate the data file                  |
| ERROR: Start word and End word must not be the same | Start word and end word cannot be the same word |
| ERROR: File contains invalid data                   | Invalid data file format                        |