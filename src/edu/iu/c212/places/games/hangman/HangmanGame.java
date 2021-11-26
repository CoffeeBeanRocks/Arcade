package edu.iu.c212.places.games.hangman;

import edu.iu.c212.models.User;
import edu.iu.c212.places.games.Game;

import java.util.ArrayList;
import java.util.List;

public class HangmanGame extends Game implements IHangmanGame
{
    private String word;
    private List<Character> guessedLetters;
    private List<Character> validLetters;

    public HangmanGame()
    {
        super("Hangman",5);

        String letters = "abcdefghijklmnopqrstuvwxyz";
        for(int i=0; i<letters.length(); i++)
            validLetters.add(letters.charAt(i));
        List<Character> guessedLetters = new ArrayList<>();
    }

    @Override
    public void onEnter(User user)
    {
        //main programming loop
        //add GUI
    }

    private boolean gameOver()
    {
        for(int i=0; i<word.length(); i++)
        {
            if(!guessedLetters.contains(word.charAt(i)))
                return false;
        }
        return true;
    }

    public List<Character> getValidLexicon()
    {
        return validLetters;
    }

    public String getBlurredWord(List<Character> guesses, String word)
    {
        String blurredWord = "";
        for(int i=0; i<word.length(); i++)
        {
            if(guesses.contains(word.charAt(i)))
                blurredWord += word.charAt(i);
            else
                blurredWord += "*";
        }

        return  blurredWord;
    }

}