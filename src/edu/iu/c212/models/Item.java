package edu.iu.c212.models;

public enum Item
{
    BEAR("Stuffed Bear", 30);
    //add more variables here, these are all constants
    //initialize as shown above, need at least 10
    //all enum names should be fully uppercase, as per java naming conventions

    private final String readableName;
    private final double value;

    Item(String readableName, double value)
    {
        this.readableName = readableName;
        this.value = value;
    }

    @Override
    public String toString()
    {
        return "Name: "+readableName+" Value: "+value;
    }
}
