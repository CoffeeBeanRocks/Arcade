package edu.iu.c212;

import edu.iu.c212.models.Item;
import edu.iu.c212.models.User;
import edu.iu.c212.places.Inventory;
import edu.iu.c212.places.Lobby;
import edu.iu.c212.places.Place;
import edu.iu.c212.places.Store;
import edu.iu.c212.places.games.blackjack.BlackjackGame;
import edu.iu.c212.places.games.hangman.HangmanGame;
import edu.iu.c212.utils.ConsoleUtils;
import edu.iu.c212.utils.FileUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Arcade implements IArcade
{
    User currentUser;
    List<User> allUsers;
    List<Place> allPlaces;

    public Arcade()
    {
        currentUser = getUserOnArcadeEntry();
        allPlaces = Arrays.asList(new BlackjackGame(), new HangmanGame(), new Store(), new Inventory(), new Lobby(this));
        transitionArcadeState("Lobby");
    }

    @Override
    public List<User> getUserSaveDataFromFile()
    {
        try
        {
            return FileUtils.getUserDataFromFile();
        }
        catch (Exception e)
        {
            System.exit(1);
            return null; //this will never be invoked but intelliJ was complaining
        }
    }

    @Override
    public void saveUsersToFile()
    {
        try
        {
            FileUtils.writeUserDataToFile(allUsers);
        }
        catch (Exception e) {
            System.exit(1);
        }
    }

    @Override
    public void transitionArcadeState(String newPlaceNameToGoTo) //TODO: Make sure this function doesn't recurse infinitely
    {
        for(Place p : allPlaces)
        {
            if(p.getPlaceName().equals(newPlaceNameToGoTo))
            {
                if(currentUser.getBalance() < p.getEntryFee())
                {
                    System.out.println("You do not have enough money, transitioning to lobby!");
                    transitionArcadeState("Lobby");
                }
                else
                    p.onEnter(currentUser);
            }
        }
        System.err.println("\""+newPlaceNameToGoTo+"\" does not exist, transitioning back to lobby");
        transitionArcadeState("Lobby");
    }

    @Override
    public User getUserOnArcadeEntry()
    {
        System.out.println("Enter username: ");
        String username = ConsoleUtils.readLineFromConsole();
        User user = null;
        for(User u : getUserSaveDataFromFile())
        {
            if(user.getUsername().equals(username))
            {
                user = u;
                System.out.println("Welcome back " + username);
                break;
            }
            else
            {
                allUsers.add(new User(username, 50, new ArrayList<Item>()));
                user = allUsers.get(allUsers.size()-1);
                saveUsersToFile(); //TODO: Rewrite entire file every invocation
                //TODO: User starting balance?
                System.out.println("Welcome " + username);
            }
        }
        return user;
    }

    @Override
    public List<Place> getAllPlaces()
    {
        return allPlaces;
    }
}
