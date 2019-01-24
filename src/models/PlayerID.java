package models;

public class PlayerID {
    String firstName;
    String lastName;
    Integer playerId;
    String position;

    public PlayerID(String firstName, String lastName, int playerID, String position){
        this.firstName = firstName;
        this.lastName = lastName;
        this.playerId = playerID;
        this.position = position;
    }

}
