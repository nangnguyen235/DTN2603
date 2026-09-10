package entity;

import entity.enums.PositionName;

public class Position {
    private int positionId;
    private PositionName positionName;

    public Position() {
    }

    public Position(int positionId, PositionName positionName) {
        this.positionId = positionId;
        this.positionName = positionName;
    }

    public int getPositionId() {
        return positionId;
    }

    public PositionName getPositionName() {
        return positionName;
    }

    public void setPositionId(int positionId) {
        this.positionId = positionId;
    }

    public void setPositionName(PositionName positionName) {
        this.positionName = positionName;
    }

    @Override
    public String toString() {
        return String.format("|%19s |%19s |", positionId, positionName);
    }
}
