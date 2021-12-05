package edu.iu.c212.places;

import edu.iu.c212.Arcade;
import edu.iu.c212.models.User;
import edu.iu.c212.places.games.hangman.HangmanGame;
import edu.iu.c212.utils.ConsoleUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lobby extends Place
{
    public Lobby()
    {
        super("Lobby",0);
    }
    @Override
    public void onEnter(User user)
    {
        System.out.println("Welcome to the lobby!");
        System.out.println("Your balance is: $" + user.getBalance());
        Place p = ConsoleUtils.printMenuToConsole("Places", Arcade.getAllPlaces(),true);
        //TODO: Add transition to place
        //TODO: Make sure each location is saving appropriately
        //TODO: Leaving the arcade
    }
}
