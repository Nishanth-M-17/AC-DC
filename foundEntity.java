package ACDC.hack_backend.entity;

public class foundEntity extends entity {
    private String finderName;      // who found the item
    private boolean claimed;// whether someone claimed it

    public String getFinderName() {
        return finderName;
    }

    public void setFinderName(String finderName) {
        this.finderName = finderName;
    }

    public boolean isClaimed() {
        return claimed;
    }

    public void setClaimed(boolean claimed) {
        this.claimed = claimed;
    }

}
