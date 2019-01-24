package GUI;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ChoiceBox;

import models.BasketballPlayer;
import models.BasketballPlayerPool;
import models.LeagueManager;
import models.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FantasyDrafterGUI extends Application implements Loadable,Saveable{

    Button addPlayerB;
    Button removePlayerB;
    Button addTeamB;
    Button removeTeamB;
    Button exitB;
    Button submitAddTeam;
    Button submitRemoveTeam;
    Button rtbackB;
    Button atbackB;
    Button apbackB;
    Button rpbackB;
    Button apsubmitPlayer;
    Button aPlayerSubmitB;
    Button apBackBB;
    Button rpsubmitPlayer;

    Label addTeamL;
    Label removeTeamL;
    Label astPlayerL;
    Label rstPlayerL;

    TextField inputAddTeamCity;
    TextField inputAddTeamName;
    TextField inputRemoveTeamName;

    ChoiceBox<String> teamListCB;
    ChoiceBox<String> apteamListCB;
    ChoiceBox<String> rpteamListCB;
    ComboBox<String> aPlayerListCB;
    ComboBox<String> rsteamListCB;
    ComboBox<String> rPlayerListCB;

    private LeagueManager teamBasketballPlayerMap = new LeagueManager();
    private List<BasketballPlayer> emptyPool = new ArrayList<>(500);
    private BasketballPlayerPool entirePool = new BasketballPlayerPool(emptyPool);
    private List<BasketballPlayer> playerPool = entirePool.getPool();
    private boolean isSaved;
    private Team nullTeam = new Team ("", "");

    Stage window;
    Scene mainScene, aTeamScene, rTeamScene, astPlayerScene, rstPlayerScene;

    public static void main(String[] args) {
        launch(args);
    }

    //Updates a choicebox with the list of teams in the draft
    public void updateTeam(ChoiceBox updateList){
        for (Team team : teamBasketballPlayerMap.keySet()){
            updateList.getItems().add(team.getName());
        }
    }

    //Updates a ComboBox with the list of all available players in the pool
    public void updatePlayers (ComboBox updateList){
        updateList.getItems().clear();
        updateList.getItems().setAll(playerPool);
    }

    //Updates a ComboBox with a team's current roster
    public void updateRemovePlayers (ComboBox updateList, String team){
        for (Team playerList:teamBasketballPlayerMap.getTeams()){
            if (playerList.getPlayers().isEmpty()){
                updateList.getItems().clear();
            }
            else{
                updateList.getItems().clear();
                Team selectedTeam = teamBasketballPlayerMap.selectTeam(team);
                updateList.getItems().addAll(teamBasketballPlayerMap.getPlayersOnTeam(selectedTeam));
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        load();
        if (playerPool.isEmpty()){
            entirePool.loadPlayersFromWeb();
            playerPool = entirePool.getPool();
        }

        window = primaryStage;
        window.setTitle("Ben's Fantasy NBA Draft Application");

        //BUTTONS
        addPlayerB = new Button("Add Player");
        removePlayerB = new Button("Remove Player");

        addTeamB = new Button("Add Team");
        removeTeamB = new Button("Remove Team");

        aPlayerSubmitB = new Button ("Submit");

        submitAddTeam = new Button("Submit");
        submitRemoveTeam = new Button ("Submit");
        apsubmitPlayer = new Button ("Submit");
        rpsubmitPlayer = new Button ("Submit");

        atbackB = new Button ("Back");
        rtbackB = new Button ("Back");
        apbackB = new Button ("Back");
        apBackBB = new Button ("Back");
        rpbackB = new Button ("Back");

        exitB = new Button("Exit and calculate scores");

        //LABELS
        addTeamL = new Label ("Please enter the CITY, then NAME of the team to add.");
        removeTeamL = new Label ("Please select the team to remove.");
        astPlayerL = new Label ("Select the team that and player that is drafting");
        rstPlayerL = new Label ("Select the player and his team to delete the player from that team");

        //TEXTFIELDS
        inputAddTeamCity = new TextField();
        inputAddTeamName = new TextField();
        inputRemoveTeamName = new TextField();

        //CHOICEBOXES
        teamListCB = new ChoiceBox<>();
        apteamListCB = new ChoiceBox<>();
        rsteamListCB = new ComboBox<>();
        rpteamListCB = new ChoiceBox<>();
        aPlayerListCB = new ComboBox<>();
        rPlayerListCB = new ComboBox<>();

        //Main Menu Layout
        VBox mainLayout = new VBox(20);
        mainLayout.setAlignment(Pos.CENTER);
        Scene mainScene = new Scene(mainLayout, 500, 500);
        mainLayout.getChildren().addAll(addTeamB, removeTeamB, addPlayerB, removePlayerB, exitB);

        //Add Team Layout
        VBox aTeamLayout = new VBox(20);
        aTeamLayout.setAlignment(Pos.CENTER);
        aTeamLayout.getChildren().addAll(addTeamL, inputAddTeamCity, inputAddTeamName, submitAddTeam, atbackB);
        aTeamScene = new Scene (aTeamLayout, 500, 500);

        //Remove Team Layout
        VBox rTeamLayout = new VBox(20);
        rTeamLayout.setAlignment(Pos.CENTER);
        rTeamLayout.getChildren().addAll(removeTeamL, teamListCB, submitRemoveTeam, rtbackB);
        rTeamScene = new Scene (rTeamLayout, 500, 500);

        //Add Player Layout
        VBox astPlayerLayout = new VBox(20);
        astPlayerLayout.setAlignment(Pos.CENTER);
        astPlayerLayout.getChildren().addAll(astPlayerL, apteamListCB, aPlayerListCB, apsubmitPlayer, apbackB);
        astPlayerScene = new Scene (astPlayerLayout, 500, 500);

        //Remove Player Layout
        VBox rstPlayerLayout = new VBox(20);
        rstPlayerLayout.setAlignment(Pos.CENTER);
        rstPlayerLayout.getChildren().addAll(rstPlayerL, rpteamListCB, rPlayerListCB, rpsubmitPlayer, rpbackB);
        rstPlayerScene = new Scene (rstPlayerLayout, 500, 500);

        //Back/Exit Button Functionality, redirects the user to the main menu
        rtbackB.setOnAction(e -> {
            window.setScene(mainScene);
        });
        atbackB.setOnAction(e -> {
            window.setScene(mainScene);
        });
        apbackB.setOnAction(e -> {
            window.setScene(mainScene);
        });
        apBackBB.setOnAction(e -> {
            window.setScene(mainScene);
        });
        rpbackB.setOnAction(e -> {
            window.setScene(mainScene);
        });
        exitB.setOnAction(e -> {
            AlertBox.display("Final Scores", teamBasketballPlayerMap.returnScore());
            try {
                save();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            window.close();
        });


        //Add Team Button Functionality
        addTeamB.setOnAction(e -> {
            inputAddTeamCity.clear();
            inputAddTeamName.clear();
            window.setScene(aTeamScene);
            });
        submitAddTeam.setOnAction(e -> {
            int initSize = teamBasketballPlayerMap.size();
            String teamCity = String.valueOf(inputAddTeamCity.getText());
            String teamName = String.valueOf(inputAddTeamName.getText());
            teamBasketballPlayerMap.addTeam(teamCity, teamName);
            if (teamBasketballPlayerMap.size()>initSize){
                AlertBox.display("A team has been added!", "The " + teamCity +" " + teamName +" has been added to the fantasy draft!");
            }
            else{
                AlertBox.display("Error!", "This team could not be added. Try a different name!");
            }
            try {
                save();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            window.setScene(mainScene);
        });

        //Remove Team Button Press Functionality
        removeTeamB.setOnAction(e -> {
            teamListCB.getItems().clear();
            updateTeam(teamListCB);
            teamListCB.getSelectionModel().selectFirst();
            if (teamBasketballPlayerMap.size() == 0) {
                AlertBox.display("Error!","There are no teams yet. Try adding a team first!");
            }
            else {
                window.setScene(rTeamScene);
            }
        });
        submitRemoveTeam.setOnAction(e -> {
            String teamToDelete = teamListCB.getValue().toLowerCase();
            teamBasketballPlayerMap.removeTeam(teamToDelete, playerPool);
            AlertBox.display("A team has been removed!", "The " + teamListCB.getValue() + " has been removed from the fantasy draft.");
            try {
                save();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            window.setScene(mainScene);
        });

        //Add Player Button Press Functionality
        addPlayerB.setOnAction(e -> {
            if (teamBasketballPlayerMap.keySet().isEmpty()) {
                AlertBox.display("Error!", "An error has occurred, try adding a team first!");
                window.setScene(mainScene);
            }
            else{
                apteamListCB.getItems().clear();
                aPlayerListCB.getItems().clear();
                updateTeam(apteamListCB);
                updatePlayers(aPlayerListCB);
                apteamListCB.getSelectionModel().selectFirst();
                aPlayerListCB.getSelectionModel().selectFirst();
                window.setScene(astPlayerScene);
            }
        });
        apsubmitPlayer.setOnAction(e -> {
            if (!(apteamListCB.getValue() == null)){
                String teamToDraftString = apteamListCB.getValue().toLowerCase();
                Team teamDrafting = teamBasketballPlayerMap.selectTeam(teamToDraftString);
                int index = aPlayerListCB.getSelectionModel().getSelectedIndex();
                if (index == -1) return;
                BasketballPlayer playerToAdd = playerPool.get(index);
                if (teamDrafting.validRoster(playerToAdd)){
                    teamBasketballPlayerMap.addPlayer(teamDrafting, playerToAdd, playerPool);
                    playerToAdd.loadStatsFromWeb();
                    AlertBox.display("A player has been added!", playerToAdd.getName() + " has been drafted to the " + teamToDraftString + "!");
                }
                else{
                    AlertBox.display( "A player could not be added...", playerToAdd.getName() + " could not be added to that team, try another player.");
                }
            }
            try {
                save();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            window.setScene(mainScene);
        });

        //Remove Player Button Functionality
        removePlayerB.setOnAction(e -> {
            if (teamBasketballPlayerMap.keySet().isEmpty()) {
                AlertBox.display("Error!", "An error has occurred, try adding a team first!");
                window.setScene(mainScene);
            }
            else{
                rpteamListCB.getItems().clear();
                updateTeam(rpteamListCB);
                rpteamListCB.getSelectionModel().selectFirst();
                window.setScene(rstPlayerScene);
            }
        });
        //Updates player list upon selection
        rpteamListCB.setOnAction(e -> {
            rPlayerListCB.getItems().clear();
            updateRemovePlayers(rPlayerListCB, rpteamListCB.getValue());
            rPlayerListCB.getSelectionModel().selectFirst();
        });
        rpsubmitPlayer.setOnAction(e -> {
            if (!(rPlayerListCB.getValue() == null || rPlayerListCB.getValue() == null)){
                int index = rPlayerListCB.getSelectionModel().getSelectedIndex();
                Team selectedTeam = teamBasketballPlayerMap.selectTeam(rpteamListCB.getValue());
                BasketballPlayer playerToDrop = teamBasketballPlayerMap.getPlayersOnTeam(selectedTeam).get(index);
                teamBasketballPlayerMap.removePlayer(selectedTeam, playerToDrop, playerPool);
                AlertBox.display("A player has been kicked!", playerToDrop.getName() + " has been removed from the " + selectedTeam.getName() + "!");
                try {
                    save();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                window.setScene(mainScene);
            }
            else {
                AlertBox.display("Error!", "Something went wrong, make sure there aren't any blank fields.");
                window.setScene(mainScene);
            }

        });
        window.setScene(mainScene);
        window.show();

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
