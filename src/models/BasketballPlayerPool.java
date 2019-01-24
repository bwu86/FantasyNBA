package models;


import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;


public class BasketballPlayerPool implements Serializable {

    protected Team nullTeam = new Team("", "");
    protected List<BasketballPlayer> playerPool = new ArrayList<>(500);

    public BasketballPlayerPool(List<BasketballPlayer> playerPool) {
        this.playerPool = loadPlayersFromWeb();
    }

    public List<BasketballPlayer> loadPlayersFromWeb() {
        BufferedReader br = null;
        try {
            URL url = new URL("http://data.nba.net/data/10s/prod/v1/2018/players.json");
            br = new BufferedReader(new InputStreamReader(url.openStream()));
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
            JsonObject league = jo.get("league").getAsJsonObject();
            JsonArray standard = league.get("standard").getAsJsonArray();

            for (JsonElement o : standard) {
                JsonObject player = o.getAsJsonObject();
                if (player.get("isActive").getAsBoolean()){
                    String fName = player.get("firstName").getAsString();
                    String lName = player.get("lastName").getAsString();
                    String jersey = player.get("jersey").getAsString();
                    String pos = player.get("pos").getAsString();
                    String personId = player.get("personId").getAsString();
                    BasketballPlayer newPlayer = new BasketballPlayer(fName, lName, nullTeam, pos, jersey, personId);
                    playerPool.add(newPlayer);
                }
            }
            return playerPool;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<BasketballPlayer> getPool(){
        return this.playerPool;
    }
}
