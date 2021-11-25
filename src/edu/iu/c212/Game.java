package edu.iu.c212;

import edu.iu.c212.models.User;

public abstract class Game extends Place
{
    public Game(String placeName, double entryFee)
    {
        super(placeName, entryFee);
    }

    public abstract void onEnter(User user);

    @Override
    public String toString()
    {
        return super.toString()+" Game: True";
    }
}
