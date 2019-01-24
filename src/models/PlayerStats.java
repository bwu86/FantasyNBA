package models;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

public class PlayerStats implements Serializable{

    private double pointPG, rebPG, astPG, stlPG, blkPG, toPG;
    final String baseURL = "https://stats.nba.com/stats/playerprofilev2?LeagueID=&PerMode=PerGame&PlayerID=2544";

    public PlayerStats(double pointPG, double rebPG, double astPG, double stlPG, double blkPG, double toPG) {
        this.pointPG = pointPG;
        this.rebPG = rebPG;
        this.astPG = astPG;
        this.stlPG = stlPG;
        this.blkPG = blkPG;
        this.toPG = toPG;
    }

    public double getPoints (){
        return pointPG;
    }
    public double getRebs () {
        return rebPG;
    }
    public double getAst () {
        return astPG;
    }
    public double getStl () {
        return stlPG;
    }
    public double getBlk () {
        return blkPG;
    }
    public double getTO () {
        return toPG;
    }
    public double getScore () {
        return  Math.floor(((pointPG) + (rebPG * 1.2) + (astPG * 1.5) + (stlPG * 3) + (blkPG * 3) + (toPG * -1f)) * 100) / 100;
        }

}
