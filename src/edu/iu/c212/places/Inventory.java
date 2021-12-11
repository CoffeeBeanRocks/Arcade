package edu.iu.c212.places;

import edu.iu.c212.models.Item;
import edu.iu.c212.models.User;
import edu.iu.c212.utils.ConsoleUtils;

import java.util.*;

public class Inventory extends Place
{
    public Inventory()
    {
        super("Inventory", 0);
    }

    @Override
    public void onEnter(User user)
    {
        System.out.println("Hey " + user.getUsername() + " your username looks like this:");
        HashMap<Item,Integer> itemCount = new HashMap<>();
        int total = 0;
        for(Item i : user.getInventory())
        {
            if(itemCount.containsKey(i))
                itemCount.put(i,itemCount.get(i)+1);
            else
                itemCount.put(i,1);
            total+=i.getValue();
        }
        for(Map.Entry<Item,Integer> m : itemCount.entrySet())
            System.out.println(m.getKey().getReadableName()+": "+m.getValue()+" (Value: $"+m.getKey().getValue()+")");
        System.out.println("Total Net Worth: $"+total);
        System.out.println("REMEMBER! You can only have 3 items at a time. Sell one by going to the Store.");
        System.out.println();
    }
}
