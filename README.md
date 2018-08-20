# wordchkr

Example usage of trie. Project was developed on java 10.0.1 and scala 2.12.6

##run
Application can be ran by sbt via following command
```sbt
sbt run
```

##packaging
To build package execute
```sbt
sbt assembly
```

##tests
In order to run all test execute
```sbt
sbt test
```

##usage
Application accepts three commands
* :search       - enters search mode, applies given phrase search and calculates matching scores
* :help         - prints very short description of commands, very similar to this one
* :quit         - ends program execution

#scores
Matching scores are calculated in very simple and limited way. Every phrase is split into set of words.
Each word has its value assigned - 1 / number of scores.
I am Groot phrase will generate following scores:
* I - 33.33%
* am - 33.33%
* Groot - 33.33%
Although is quite unfair matching score application can be easily extended by other calculation algorithms.
