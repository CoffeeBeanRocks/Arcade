package edu.iu.c212.utils;

import edu.iu.c212.models.Item;
import edu.iu.c212.models.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtils {
    private static File file = new File("src/users.txt");

    // line format:
    // user_name|balance|item1,item2,item3
    // user name not allowed to contain pipe

    /**
     * Write user data to the file you provided above.
     *
     * @param users The total list of all users
     */
    public static void writeUserDataToFile(List<User> users) throws IOException
    {
        FileWriter myWriter = new FileWriter(file);
        String contents = "";
        for(User u : users)
        {
            contents += u.getUsername()+"|";
            contents += u.getBalance()+"|";
            for(Item i : u.getInventory())
                contents+=i.getReadableName()+",";
            contents = contents.substring(0,contents.length()-1)+"\n"; //remove final comma
        }
        contents = contents.substring(0,contents.length()-1);
        myWriter.write(contents);
        myWriter.close();
    }

    /**
     * Read user data from the file you provided above. Return a list of Users
     */
    public static List<User> getUserDataFromFile() throws IOException
    {
        Scanner scan = new Scanner(file);
        List<User> users = new ArrayList<>();
        while(scan.hasNextLine())
        {
            String[] userData = scan.nextLine().split("\\|"); //there needs to be an escape sequence before the line because "|" is a metacharacter
            if(userData.length > 2)
            {
                String[] itemNames = userData[2].split(",");
                List<Item> myItems = new ArrayList<>();
                for(String s : itemNames)
                    myItems.add(getItemFromName(s));
                users.add(new User(userData[0],Double.parseDouble(userData[1]),myItems));
            }
            else
                users.add(new User(userData[0],Double.parseDouble(userData[1]),new ArrayList<>()));
        }
        return users;
    }

    public static Item getItemFromName(String name)
    {
        for(Item i : Item.values())
        {
            if(i.getReadableName().equals(name))
                return i;
        }
        throw new IllegalArgumentException("Item not found in inventory!");
    }
}
