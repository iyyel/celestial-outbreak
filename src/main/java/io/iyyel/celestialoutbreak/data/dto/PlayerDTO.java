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

    public List<String> getPlayerList() {
        return playerList;
    }

    public String getSelectedPlayer() {
        return selectedPlayer;
    }

    public void setSelectedPlayer(String selectedPlayer) {
        this.selectedPlayer = selectedPlayer;
    }

}