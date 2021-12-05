package edu.iu.c212.places;

import edu.iu.c212.Arcade;
import edu.iu.c212.models.User;
import edu.iu.c212.utils.ConsoleUtils;

public class Lobby extends Place
{
    Arcade arcade;
    public Lobby(Arcade arcade)
    {
        super("Lobby",0);
        this.arcade = arcade;
    }
    @Override
    public void onEnter(User user)
    {
        System.out.println("Welcome to the lobby!");
        System.out.println("Your balance is: $" + user.getBalance());
        Place p = ConsoleUtils.printMenuToConsole("Places", arcade.getAllPlaces(),true);
        arcade.transitionArcadeState(p.getPlaceName());
    }
}
