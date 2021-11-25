package edu.iu.c212;

import java.util.List;

public interface IHangmanGame
{
    String getBlurredWord(List<Character> guesses, String word);

    List<Character> getValidLexicon();
}
