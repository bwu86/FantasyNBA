package models;

import observer.Subject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LeagueManager extends TeamBasketballPlayerSet implements Serializable{

    public LeagueManager(){
        super();
    }

    public void addTeam(String teamCity, String teamName) {
        Team newTeam = new Team(teamCity, teamName);
        if (!checkIllegalTeam(newTeam) && !newTeam.getName().isEmpty()) {
            List<BasketballPlayer> playerList = new ArrayList<>();
            teamBasketballPlayerMap.put(newTeam, playerList);
            System.out.println("\nThe new team '" + newTeam.getName() + "' has joined the fantasy draft!");
        } else {
            System.out.println("This team cannot be added!");
        }
    }

    public String returnScore(){
        List<Double> scoreList = new ArrayList<>();
        List<String>teamList = new ArrayList<>();
        String output = "";
        for (Team team:teamBasketballPlayerMap.keySet()){
            scoreList.add(team.getScore());
            teamList.add(team.toString());
        }
        for (int i=0; i<scoreList.size();i++){
            output = output + teamList.get(i)+"\t"+scoreList.get(i)+"\n";
        }
        return output;
    }

    public void removeTeam(String teamToRemove, List<BasketballPlayer> pool) {
        if (!teamToRemove.isEmpty()) {
            Team selectedTeam = selectTeam(teamToRemove);
            if (!selectedTeam.equals(nullTeam)){
                List<BasketballPlayer> removeTeam = teamBasketballPlayerMap.get(selectedTeam);
                for (BasketballPlayer player : removeTeam) {
                    player.changeTeam(nullTeam);
                }
                pool.addAll(removeTeam);
                teamBasketballPlayerMap.get(selectedTeam).clear();
                teamBasketballPlayerMap.remove(selectedTeam);
                System.out.println("The "+ selectedTeam.getName()+ " has been deleted from the draft.");
            }
        }
    }

        public void addPlayer (Team team, BasketballPlayer playerToAdd, List <BasketballPlayer> pool){
            if (!containsPlayer(playerToAdd) && team.validRoster(playerToAdd)) {
                team.addPlayerToList(playerToAdd);
                teamBasketballPlayerMap.get(team).add(playerToAdd);
                playerToAdd.changeTeam(team); //move to the class where it lives
                pool.remove(playerToAdd);
                notifyObservers(team);
            }
        }

        public void removePlayer (Team team, BasketballPlayer playerToRemove, List<BasketballPlayer> pool){
            if (teamBasketballPlayerMap.get(team).contains(playerToRemove)) {
                team.removePlayerFromList(playerToRemove);
                playerToRemove.changeTeam(nullTeam);
                //teamBasketballPlayerMap.remove(team, playerToRemove);
                teamBasketballPlayerMap.get(team).remove(playerToRemove);
                pool.add(playerToRemove);
            }
        }

}
