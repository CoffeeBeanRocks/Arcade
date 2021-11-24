package edu.iu.c212;

import edu.iu.c212.models.User;

public abstract class Place
{
    private String placeName;
    //private Arcade arcade; //reference to the Arcade
    private double entryFee;

    public abstract void onEnter(User user);

    @Override
    public String toString()
    {
        return "Name: "+placeName+" Entry Fee: "+entryFee;
    }
    //This should return the place name, the entry fee, and whether the place is a game or not
}
