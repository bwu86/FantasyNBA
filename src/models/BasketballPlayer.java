package models;



import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import observer.TeamObserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.util.List;
import java.util.Objects;

public class BasketballPlayer implements Serializable, TeamObserver {

    private String firstName;
    private String lastName;
    private String fullName;
    protected Team playerTeam;
    private String jersey;
    private String personId;
    private String pos;
    private PlayerStats stats;
    final public Team nullTeam = new Team("","");

    public BasketballPlayer(String firstName, String lastName, Team playerTeam, String pos, String jersey, String personId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = firstName + " " + lastName;
        this.pos = pos;
        this.playerTeam = playerTeam;
        this.jersey = jersey;
        this.personId = personId;
    }

    public String getName(){return this.fullName;}
    public String toString(){return this.fullName;}
    public String getPersonId(){return this.personId;}
    public String getJersey(){
        return this.jersey;
    }
    public String getPos(){
        return this.pos;
    }
    public double getStats() {return Math.round(this.stats.getScore());}
    public void changeTeam(Team newTeam){
        String originalTeam = this.playerTeam.getName();
        this.playerTeam = newTeam;
        if (newTeam.getName().equals("")){
            System.out.println(fullName + " has been removed from the " + originalTeam + "s have has been added to the available player pool.");
        }
        else{
            update(newTeam);
        }
    }

    public void loadStatsFromWeb(){
        BufferedReader br;
        try{
            String prefix = "http://data.nba.net//data/10s/prod/v1/2018/players/";
            String suffix = "_profile.json";
            URL statURL = new URL (prefix + this.getPersonId() + suffix);
            br = new BufferedReader(new InputStreamReader(statURL.openStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
            }
            String json = sb.toString();

            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(json);
            JsonObject jo = je.getAsJsonObject();
            JsonObject stats = jo.get("league").getAsJsonObject().get("standard").getAsJsonObject().get("stats").getAsJsonObject().get("latest").getAsJsonObject();
            this.stats = new PlayerStats(stats.get("ppg").getAsDouble(), stats.get("rpg").getAsDouble(), stats.get("apg").getAsDouble(), stats.get("spg").getAsDouble(), stats.get("bpg").getAsDouble(), stats.get("topg").getAsDouble());
            } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Update should also grab positions of players and reflect in negative stat changes (i.e. need 2 G, 2F, 1C)
    public void update(Team teamChange){
        System.out.println("The current roster is;\n" + this.playerTeam.printPlayers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullName);
    }
}