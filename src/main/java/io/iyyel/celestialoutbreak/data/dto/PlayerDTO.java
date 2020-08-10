package io.iyyel.celestialoutbreak.data.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public final class PlayerDTO implements Serializable {

    private List<String> playerList;
    private String selectedPlayer;

    public PlayerDTO() {
        playerList = new ArrayList<>();
        selectedPlayer = null;
    }

    public void addPlayer(String player) {
        playerList.add(player);
    }

    public void removePlayer(String player) {
        playerList.remove(player);
    }

    public void removePlayer(int index) {
        playerList.remove(index);
    }

    public String getPlayer(int index) {
        return playerList.get(index);
    }

    public List<String> getPlayers() {
        return playerList;
    }

    public int getPlayerCount() {
        return playerList.size();
    }

    public String getSelectedPlayer() {
        return selectedPlayer;
    }

    public void setSelectedPlayer(String selectedPlayer) {
        this.selectedPlayer = selectedPlayer;
    }

}