package edu.iu.c212.places;

import edu.iu.c212.models.Item;
import edu.iu.c212.models.User;
import edu.iu.c212.utils.ConsoleUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class Inventory extends Place
{
    public Inventory()
    {
        super("Inventory", 0);
    }

    @Override
    public void onEnter(User user)
    {
        ConsoleUtils.printMenuToConsole("Inventory", user.getInventory(),false);
    }
}
