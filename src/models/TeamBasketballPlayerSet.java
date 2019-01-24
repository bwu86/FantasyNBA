package models;

import observer.Subject;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TeamBasketballPlayerSet extends Subject implements Serializable {
    protected Map<Team, List<BasketballPlayer>> teamBasketballPlayerMap;
    List<String> illegalTeams;
    List<BasketballPlayer> pool;
    final public Team nullTeam = new Team("","");

    public TeamBasketballPlayerSet(){
        teamBasketballPlayerMap = new ConcurrentHashMap<>();
        this.illegalTeams = new ArrayList<>();
        this.pool = new ArrayList<>();
        illegalTeams.add("warriors");
        illegalTeams.add("rockets");
        illegalTeams.add("pelicans");
        illegalTeams.add("raptors");
        illegalTeams.add("cavaliers");

        illegalTeams.add("nuggets");
        illegalTeams.add("76ers");
        illegalTeams.add("timberwolves");
        illegalTeams.add("clippers");
        illegalTeams.add("hornets");
        illegalTeams.add("lakers");
        illegalTeams.add("thunder");
        illegalTeams.add("wizards");
        illegalTeams.add("nets");
        illegalTeams.add("bucks");
        illegalTeams.add("blazers");
        illegalTeams.add("pacers");
        illegalTeams.add("knicks");
        illegalTeams.add("jazz");
        illegalTeams.add("celtics");
        illegalTeams.add("suns");
        illegalTeams.add("pistons");
        illegalTeams.add("heat");
        illegalTeams.add("magic");
        illegalTeams.add("hawks");
        illegalTeams.add("bulls");
        illegalTeams.add("spurs");
        illegalTeams.add("mavericks");
        illegalTeams.add("grizzlies");
        illegalTeams.add("kings");
    }

    public int size() {
        return teamBasketballPlayerMap.size();}

    public boolean containsTeam(Team teamToCheck) {
        return teamBasketballPlayerMap.containsKey(teamToCheck);
    }

    public boolean containsPlayer(BasketballPlayer playerToCheck) {
        for (Team teamList: teamBasketballPlayerMap.keySet()) {
            for (BasketballPlayer playerList:teamBasketballPlayerMap.get(teamList)){
                if (playerList.getName().equals(playerToCheck.getName())){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkIllegalTeam(Team teamToCheck) {
        for (String team:illegalTeams){
                if (team.equals(teamToCheck.getName().toLowerCase())){
                    return true;
                }
            }
        return (checkTeamExists(teamToCheck));
    }

    public boolean isEmpty(){
        return this.teamBasketballPlayerMap.isEmpty();
    }

    public Team selectTeam (String teamName) {
        for (Team teamList : teamBasketballPlayerMap.keySet())
            if (teamList.getName().toLowerCase().equals(teamName.toLowerCase())) {
                return teamList;
            }
        return nullTeam;
    }

    public List<BasketballPlayer> getPlayersOnTeam(Team team){
        return teamBasketballPlayerMap.get(team);
    }

    public Set<Team> getTeams(){
        return teamBasketballPlayerMap.keySet();
    }

    public String printTeamList(){
        return teamBasketballPlayerMap.keySet().toString();
    }

    public Set<Team> keySet(){
        return teamBasketballPlayerMap.keySet();
    }

    public boolean checkTeamExists(Team team){
        return (teamBasketballPlayerMap.containsKey(team));
    }

    public void printLeague(){
        for (Team teamList : teamBasketballPlayerMap.keySet()){
            String key = teamList.toString();
            List<String> playerNames = new ArrayList<>();
            for (BasketballPlayer playerName:teamBasketballPlayerMap.get(teamList)){
                playerNames.add(playerName.getName());
            }
            System.out.println(key+" "+ playerNames);
        }
    }
}
