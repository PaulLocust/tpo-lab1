package lab.pavel.domain;

public class President {
    private boolean onBoard = true;

    public boolean isOnBoard() {
        return onBoard;
    }

    public void disembark() {
        onBoard = false;
    }
}

