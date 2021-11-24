package edu.iu.c212.models;

import java.util.List;

public class User
{
    private String username;
    private double balance;
    private List<Item> inventory;

    public User(String username, double balance, List<Item> inventory)
    {
        this.username = username;
        this.balance = balance;
        this.inventory = inventory;
    }
}
