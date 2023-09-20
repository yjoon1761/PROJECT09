package BlueMarbleDTO;

public class Player {
    private int playerNUm;
    private String name1;
    private int location = 1;
    private int budget = 2000;
    private int jailCnt = 0;

    public int getPlayerNUm() {
        return playerNUm;
    }

    public void setPlayerNUm(int playerNUm) {
        this.playerNUm = playerNUm;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public int getJailCnt() {
        return jailCnt;
    }

    public void setJailCnt(int jailCnt) {
        this.jailCnt = jailCnt;
    }
}
