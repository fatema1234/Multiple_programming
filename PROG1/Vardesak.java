public abstract class Vardesak {
    private String namn;
    private double moms = 1.25;
    
    public Vardesak(String onskatNamn) {
        this.namn = onskatNamn;
    }
    public abstract double getVarde();
    public abstract void getName();
}
