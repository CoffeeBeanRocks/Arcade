package edu.iu.c212.places;

import edu.iu.c212.Arcade;
import edu.iu.c212.models.Item;
import edu.iu.c212.models.User;
import edu.iu.c212.utils.ConsoleUtils;
import edu.iu.c212.utils.FileUtils;

import java.io.File;
import java.util.Arrays;

public class Store extends Place
{
    private Arcade arcade;

    public Store(Arcade arcade)
    {
        super("Store", 0);
        this.arcade = arcade;
    }

    @Override
    public void onEnter(User user)
    {
        StoreAction ans = ConsoleUtils.printMenuToConsole("Store", Arrays.asList(StoreAction.values()),true);
        while(ans != StoreAction.LEAVE)
        {
            if(ans == StoreAction.SELL)
            {
                if(user.getInventory().size()==0)
                    System.out.println("You have nothing to sell!");
                else
                {
                    System.out.println("Remember, you can only get 50% of the item value back!");
                    Item i = ConsoleUtils.printMenuToConsole("Sell", user.getInventory(), true);
                    if(Boolean.TRUE.equals(ConsoleUtils.printMenuToConsole("Are you sure?", Arrays.asList(true, false), true)))
                    {
                       user.getInventory().remove(user.getInventory().indexOf(i));
                       user.addValueToBalance(i.getValue()*.5);
                       System.out.println("You have sold " + i.getReadableName() + " for $" + i.getValue()*.5);
                    }
                }
            }
            else //Buy
            {
                Item i = ConsoleUtils.printMenuToConsole("Buy", Arrays.asList(Item.values()), true);
                if(user.getBalance() < i.getValue())
                    System.out.println("You can't buy this because either you don't have enough money or enough space!");
                else
                {
                    if(Boolean.TRUE.equals(ConsoleUtils.printMenuToConsole("Are you sure?", Arrays.asList(true, false), true)))
                    {
                        user.subtractValueFromBalance(i.getValue());
                        user.getInventory().add(i);
                        System.out.println("You have bought " + i.getReadableName() + " for $" + i.getValue());
                    }
                }
            }
            try {
                arcade.saveUsersToFile();
            }
            catch (Exception ignored){}
            ans = ConsoleUtils.printMenuToConsole("Store", Arrays.asList(StoreAction.values()),true);
        }
    }

    public enum StoreAction
    {
        BUY,SELL,LEAVE;

        @Override
        public String toString()
        {
            return name().toLowerCase();
        }
    }
}
