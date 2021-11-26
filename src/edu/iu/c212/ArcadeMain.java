package edu.iu.c212;

import edu.iu.c212.models.Item;
import edu.iu.c212.models.User;
import edu.iu.c212.places.Place;
import edu.iu.c212.places.games.hangman.HangmanGame;

import java.util.ArrayList;

public class ArcadeMain
{
    public static void main(String[] args)
    {
        //new Arcade();
        User me = new User("ewm",10000,new ArrayList<Item>());
        Place hangmanTest = new HangmanGame();
        hangmanTest.onEnter(me);
    }
}
