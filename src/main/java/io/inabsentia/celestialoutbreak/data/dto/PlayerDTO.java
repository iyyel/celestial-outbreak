package io.inabsentia.celestialoutbreak.data.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class PlayerDTO implements Serializable {

    private List<String> playerList;
    private String selectedPlayer;

    public PlayerDTO() {
        playerList = new ArrayList<>();
        selectedPlayer = "NONE";
    }

    public void setSelectedPlayer(String playerName) {
        this.selectedPlayer = playerName;
    }

    public void addPlayer(String playerName) {
        playerList.add(playerName);
    }

    public void removePlayer(String playerName) {
        playerList.remove(playerName);
    }

    public boolean containsPlayer(String playerName) {
        return playerList.stream().anyMatch(playerName::equalsIgnoreCase);
    }

    public boolean isPlayerSelected() {
        return selectedPlayer.equals("NONE");
    }

    public List<String> getPlayers() {
        return playerList;
    }

}