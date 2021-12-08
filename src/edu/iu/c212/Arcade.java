package edu.iu.c212;

import edu.iu.c212.models.Item;
import edu.iu.c212.models.User;
import edu.iu.c212.places.Inventory;
import edu.iu.c212.places.Lobby;
import edu.iu.c212.places.Place;
import edu.iu.c212.places.Store;
import edu.iu.c212.places.games.GuessTheNumberGame;
import edu.iu.c212.places.games.TriviaGame;
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
        allUsers = new ArrayList<>();
        allPlaces = Arrays.asList(new BlackjackGame(), new HangmanGame(), new GuessTheNumberGame(), new TriviaGame(), new Store(), new Inventory(), new Lobby(this));
        //TODO: Add an exit!
        currentUser = getUserOnArcadeEntry();
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
    public void transitionArcadeState(String newPlaceNameToGoTo)
    {
        switch (newPlaceNameToGoTo)
        {
            case "Blackjack" -> allPlaces.get(0).onEnter(currentUser);
            case "Hangman" -> allPlaces.get(1).onEnter(currentUser);
            case "Guess the Number" -> allPlaces.get(2).onEnter(currentUser);
            case "Trivia" -> allPlaces.get(3).onEnter(currentUser);
            case "Store" -> allPlaces.get(4).onEnter(currentUser);
            case "Inventory" -> allPlaces.get(5).onEnter(currentUser);

        }
        allPlaces.get(6).onEnter(currentUser); //transition back to lobby
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
        }
        if(user == null)
        {
            allUsers.add(new User(username, 50, new ArrayList<Item>()));
            user = allUsers.get(allUsers.size()-1);
            saveUsersToFile();
            //TODO: User starting balance?
            System.out.println("Welcome " + username);
        }
        currentUser = user;
        return user;
    }

    @Override
    public List<Place> getAllPlaces()
    {
        return allPlaces;
    }
}
