package edu.iu.c212.places;

import edu.iu.c212.models.User;
import edu.iu.c212.places.games.hangman.HangmanGame;
import edu.iu.c212.utils.ConsoleUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Lobby extends Place
{
    private final List<Place> places;

    public Lobby()
    {
        super("Lobby",0);
        places = Arrays.asList(new Inventory(), new Store(), new HangmanGame(), new Place("Exit", 0) {
            @Override
            public void onEnter(User user) {}
        });
        //TODO: Add locations: TriviaGame, BlackjackGame, GuessTheNumberGame
    }
    @Override
    public void onEnter(User user)
    {
        System.out.println("Welcome to the lobby!");
        System.out.println("Your balance is: $" + user.getBalance());
        Place p = ConsoleUtils.printMenuToConsole("Places",places,true);
        while(p != this)
        {
            if(p.getEntryFee() > user.getBalance())
                System.out.println("You don't have enough money to enter!");
            else
                p.onEnter(user);
            p = ConsoleUtils.printMenuToConsole("Places",places,true);
        }
        //TODO: Make sure each location is saving appropriately
        //TODO: Leaving the arcade
    }
}
