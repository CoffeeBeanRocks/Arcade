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
        try {
            allUsers = FileUtils.getUserDataFromFile();
        }
        catch (Exception e)
        {
            allUsers = new ArrayList<>();
        }
        allPlaces = Arrays.asList(new BlackjackGame(), new HangmanGame(), new GuessTheNumberGame(), new TriviaGame(), new Store(this), new Inventory(), new Lobby(this));
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
            case "Blackjack" -> {
                Place bj = allPlaces.get(0);
                if (validBalance(bj))
                    new BlackjackGame().onEnter(currentUser);
            }
            case "Hangman" -> {
                Place hm = allPlaces.get(1);
                if (validBalance(hm))
                    new HangmanGame().onEnter(currentUser);
            }
            case "Guess the Number" -> {
                Place gtn = allPlaces.get(2);
                if (validBalance(gtn))
                    new GuessTheNumberGame().onEnter(currentUser);
            }
            case "Trivia" -> {
                Place t = allPlaces.get(3);
                if (validBalance(t))
                    new TriviaGame().onEnter(currentUser);
            }
            case "Store" -> {
                Place s = allPlaces.get(4);
                if (validBalance(s))
                    new Store(this).onEnter(currentUser);
            }
            case "Inventory" -> {
                Place i = allPlaces.get(5);
                if (validBalance(i))
                    new Inventory().onEnter(currentUser);
            }
        }

        try {
            FileUtils.writeUserDataToFile(allUsers);
        }
        catch (Exception ignored){}
        allPlaces.get(6).onEnter(currentUser); //transition back to lobby
    }

    private boolean validBalance(Place place)
    {
        return currentUser.getBalance() >= place.getEntryFee();
    }

    @Override
    public User getUserOnArcadeEntry()
    {
        User user = null;
        try {
            System.out.println("Enter username: ");
            String username = ConsoleUtils.readLineFromConsole();

            allUsers = FileUtils.getUserDataFromFile();
            System.out.println(allUsers.size());

            for(User u : allUsers)
            {
                if(u.getUsername().equals(username))
                {
                    user = u;
                    System.out.println("Welcome back " + username);
                    break;
                }
            }
            if(user == null)
            {
                user = new User(username, 50, new ArrayList<Item>()); //Starting user balance never specified
                allUsers.add(user);
                FileUtils.writeUserDataToFile(allUsers);
                System.out.println("Welcome " + username);
            }
        }
        catch (Exception ignored){}
        return user;
    }

    @Override
    public List<Place> getAllPlaces()
    {
        return allPlaces;
    }
}
