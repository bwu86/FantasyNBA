package observer;

import models.Team;
import models.TeamBasketballPlayerSet;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    private List<TeamObserver> observers = new ArrayList<>();

    public void addObserver(TeamObserver teamObserver){
        if (!observers.contains(teamObserver)){
            observers.add(teamObserver);
        }
    }

    public void notifyObservers(Team teamObserver){
        for (TeamObserver observer : observers){
            observer.update(teamObserver);
        }
    }
}
