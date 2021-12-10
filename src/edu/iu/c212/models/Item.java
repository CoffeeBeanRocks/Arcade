package edu.iu.c212.models;

public enum Item
{
    BEAR("Stuffed Bear", 30),
    COPPER_WIRE("Copper Wire", 5000),
    BANANA("Banana", 10),
    BASKETBALL("Basketball", 70),
    CAR("Toyota Prius", 1),
    FRISBEE("Frisbee", 30),
    IPHONE("IPhone", 200),
    HALFGUM("Half a piece of gum", 80),
    FULLGUM("Full piece of gum", 160),
    TSHIRT("T-Shirt", 50),
    CAPGUN("Capgun", 300);

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

    public String getReadableName() {
        return readableName;
    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString()
    {
        return "Name: "+readableName+" Value: "+value;
    }
}
