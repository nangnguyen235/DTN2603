public class Position {
    public int positionId;
    public PosEnum positionName;

    public enum PosEnum {
        DEV,
        TEST,
        SCRUM_MASTER,
        PM
    }
}