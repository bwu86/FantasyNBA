package models;

import observer.Subject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Team extends Subject implements Serializable{

    private String name;
    private String city;
    private List<BasketballPlayer> playerList = new ArrayList<>(5);
    private double totalScore;

    public Team(String cityName, String teamName){
        name = teamName;
        city = cityName;
        this.playerList = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }
    public String getCity(){
        return this.city;
    }
    public String toString() {return this.city + " " + this.name;}

    public BasketballPlayer getPlayer(int index){
        return playerList.get(index);
    }

    public List<BasketballPlayer> getPlayers(){
        return playerList;
    }

    public boolean validRoster(BasketballPlayer potentialPlayer){
        String potentialPosition = potentialPlayer.getPos();
        int numG = 0;
        int numF = 0;
        int numC = 0;
        if (potentialPosition.contains("G")){
            numG ++;
        }
        if (potentialPosition.contains("F")){
            numF ++;
        }
        if (potentialPosition.contains("C")){
            numC ++;
        }
        for (BasketballPlayer player:this.playerList){
            String position = player.getPos();
            if (position.contains("G")){
                numG ++;
            }
            if (position.contains("F")){
                numF ++;
            }
            if (position.contains("C")){
                numC ++;
            }
        }
        return (numG<=2 && numF<=2 && numC<=1 && this.playerList.size() <= 5);
    }

    public void addPlayerToList(BasketballPlayer player){
        if (validRoster(player)){
            this.playerList.add(player);
            addObserver(player);
        }
        else {
            System.out.println(player.getName() + " cannot be added to the team due to position violations, you may only have 2 guards, 2 forwards, and 1 center");

        }
    }
    public void removePlayerFromList(BasketballPlayer player){
        if (playerList.contains(player)){
            this.playerList.remove(player);
        }
    }

    public double getScore(){
        double score = 0;
        for (BasketballPlayer player:this.playerList){
            score += player.getStats();
        }
        totalScore = Math.floor(score * 100)/100;
        return totalScore;
    }

    public String printPlayers() {
        List<String> playerNames = new ArrayList<>();
        for (BasketballPlayer player : this.playerList) {
            playerNames.add(player.getName());
        }
        return playerNames.toString();
    }

    public void update(Team teamChange){
        System.out.println("The current team roster is;\n" + playerList.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(name.toLowerCase(), team.name.toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


}


