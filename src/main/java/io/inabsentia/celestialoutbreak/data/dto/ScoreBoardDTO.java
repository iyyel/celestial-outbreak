package io.inabsentia.celestialoutbreak.data.dto;

public class ScoreBoardDTO {

    private int sbId;
    private int sbLevelScore;
    private String sbLevelType;

    public ScoreBoardDTO(int sbId, int sbLevelScore, String sbLevelType) {
        this.sbId = sbId;
        this.sbLevelScore = sbLevelScore;
        this.sbLevelType = sbLevelType;
    }

    public int getSbId() {
        return sbId;
    }

    public int getSbLevelScore() {
        return sbLevelScore;
    }

    public String getSbLevelType() {
        return sbLevelType;
    }

    @Override
    public String toString() {
        return "ScoreBoardDTO{" + "sbId=" + sbId + ", sbLevelScore=" + sbLevelScore + ", sbLevelType='" + sbLevelType + '\'' + '}';
    }

}