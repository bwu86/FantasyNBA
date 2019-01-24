package ui;
import exceptions.InvalidTeamException;
import models.*;
import java.io.*;
import java.util.*;
import java.io.IOException;


public class FantasyDrafterConsole implements Loadable, Saveable, Serializable {

    private LeagueManager teamBasketballPlayerMap = new LeagueManager();
    private List<BasketballPlayer> emptyPool = new ArrayList<>();
    private BasketballPlayerPool entirePool = new BasketballPlayerPool(emptyPool);
    private List<BasketballPlayer> playerPool = entirePool.getPool();
    private boolean isSaved;

    FantasyDrafterConsole() throws IOException, InvalidTeamException {

        load();
        if (playerPool.isEmpty()){
            entirePool.loadPlayersFromWeb();
            playerPool = entirePool.getPool();
        }

        while (true) {
            System.out.println("What would you like to do?\n[1] Create a team\n[2] Delete a team\n[3] Draft a Player\n[4] Delete a Player\n[5] Quit");
            Scanner input = new Scanner(System.in);
            String operation = input.nextLine();
            if (operation.equals("1")) {
                System.out.println("What is the name of your city?");
                String teamCity = input.nextLine();
                System.out.println("What is the name of your team");
                String teamName = input.nextLine();
                teamBasketballPlayerMap.addTeam(teamCity, teamName);
            }

            else if (operation.equals("2")) {
                if (teamBasketballPlayerMap.isEmpty()){
                    System.out.println("There are no teams, please try another input.");
                }
                System.out.println("Which team would you like to delete? Please type the name EXACTLY as it appears, without the city name.\n");
                System.out.println(teamBasketballPlayerMap.printTeamList());
                String teamToDelete = input.nextLine();
                teamBasketballPlayerMap.removeTeam(teamToDelete, playerPool);
            }

            else if (operation.equals("3")) {
                if (teamBasketballPlayerMap.size()>0){
                    System.out.println("Which team is drafting? Please enter the NAME of the team, exactly as it appears.\n");
                    System.out.println(teamBasketballPlayerMap.printTeamList());
                    String teamIndex = input.nextLine();
                    Team teamDrafting = teamBasketballPlayerMap.selectTeam(teamIndex);
                    if (teamBasketballPlayerMap.containsTeam(teamDrafting)){
                        System.out.println("Which player would you like to draft?\n");
                        int counter = 0;
                        for(BasketballPlayer player: playerPool){
                            System.out.println("["+Integer.toString(counter)+"] "+player.getName());
                            counter+=1;
                        }
                        int playerIndex = input.nextInt();
                        if (playerIndex<=playerPool.size()-1){
                            if (playerPool.size()>1){
                                BasketballPlayer playerToAdd = playerPool.get(playerIndex);
                                teamBasketballPlayerMap.addPlayer(teamDrafting, playerToAdd, playerPool);
                                playerToAdd.loadStatsFromWeb();
                            }
                            else {
                                System.out.println("There aren't any players left...");
                            }
                        }
                        else{
                            System.out.println("That's an invalid selection, try again!");
                        }

                    }

                }
                else {
                    System.out.println("There aren't any teams yet! Try adding one first.");
                }
            }
            else if (operation.equals("4")){
                if (teamBasketballPlayerMap.size()>0){
                    System.out.println("Which team is removing a player? Please enter the NAME of the team, exactly as it appears.\n");
                    System.out.println(teamBasketballPlayerMap.printTeamList());
                    String teamIndex = input.nextLine();
                    Team teamDeleting = teamBasketballPlayerMap.selectTeam(teamIndex);
                    if (teamBasketballPlayerMap.containsTeam(teamDeleting)){
                        if (!teamBasketballPlayerMap.getPlayersOnTeam(teamDeleting).isEmpty()) {
                            System.out.println("Which player would you like to delete?\n");
                            int counter = 0;
                            for(BasketballPlayer player: teamBasketballPlayerMap.getPlayersOnTeam(teamDeleting)){
                                System.out.println("["+Integer.toString(counter)+"] "+player.getName());
                                counter+=1;
                            }
                            int playerIndex = input.nextInt();
                            if (playerIndex<playerPool.size()-1){
                                if (teamBasketballPlayerMap.getPlayersOnTeam(teamDeleting).size()>0){
                                    BasketballPlayer playerToRemove = teamDeleting.getPlayer(playerIndex);
                                    teamBasketballPlayerMap.removePlayer(teamDeleting, playerToRemove, playerPool);
                                }
                            }
                            else{
                                System.out.println("That's an invalid selection, try again!");
                            }
                        }
                        else {
                            System.out.println("There aren't any players on that team!");
                        }

                    }

                }
                else {
                    System.out.println("There aren't any teams yet! Try adding one first.");
                }
            }
            else if (operation.equals("5")) {
                break;
            }

            else {
                System.out.println("That input is not recognized... try again!");
            }
            save();
            System.out.println("");
        }
        if (!teamBasketballPlayerMap.isEmpty()){
            System.out.println(teamBasketballPlayerMap.returnScore());
        }
    }


    @Override
    //Referenced from https://www.mkyong.com/java/how-to-read-and-write-java-object-to-a-file/
    public void load() throws IOException {

        try {
            FileInputStream fi = new FileInputStream(new File("teamList.txt"));
            ObjectInputStream oi = new ObjectInputStream(fi);
            FileInputStream fi2 = new FileInputStream(new File("playerPool.txt"));
            ObjectInputStream oi2 = new ObjectInputStream(fi2);
            // Read objects
            this.teamBasketballPlayerMap = (LeagueManager) oi.readObject();
            this.playerPool = (List<BasketballPlayer>) oi2.readObject();
        }
        catch (FileNotFoundException e) {
            System.out.println("The list of teams was not found");
        }
        catch (IOException e) {
            System.out.println("Error initializing stream");
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally{
            System.out.println("Loading procedure completed.");
        }
    }

    @Override
    //Referenced from https://www.mkyong.com/java/how-to-read-and-write-java-object-to-a-file/
    public void save() throws IOException {
        FileOutputStream f = new FileOutputStream(new File ("teamList.txt"));
        ObjectOutput o = new ObjectOutputStream(f);
        FileOutputStream f2 = new FileOutputStream(new File ("playerPool.txt"));
        ObjectOutput o2 = new ObjectOutputStream(f2);
        try {
            o.writeObject(teamBasketballPlayerMap);
            o2.writeObject(playerPool);
            }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        catch (IOException e) {
            System.out.println("Error initializing stream");
        }
        finally {
            o.close();
            f.close();
        }
        }
}




