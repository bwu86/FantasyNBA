package abstractions;

import models.Team;

import java.io.Serializable;

public abstract class Player implements Serializable {

    protected String name;

    public Player(String name) {
        this.name = name;
    }
    public String getName(){return this.name;}
}
