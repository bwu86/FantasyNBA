package abstractions;

import exceptions.InvalidTeamException;

import java.lang.reflect.Type;
import java.util.List;

public abstract class Operations {



    public void removeFromList(String itemToDelete) {
        this.removeFromList(itemToDelete);

    }

    public boolean checkIllegalParameter(List<?> list, String nameToCheck) {
        return list.contains(nameToCheck.toLowerCase());
    }

    public boolean addToList(List<?> list, String teamCity, String teamName) throws InvalidTeamException {
        if (this.checkIllegalParameter(list, teamName)) {
            throw new InvalidTeamException();
        }
        if (!teamName.contains(teamName)) {
            return (!teamCity.isEmpty() && !teamName.isEmpty());

        }
        return false;
    }
}