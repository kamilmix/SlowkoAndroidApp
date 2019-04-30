package pl.lodz.uni.math.kamilmucha.slowko.database;

public class Zestaw {
    private int id;
    private String nazwa;

    public Zestaw(){}

    public Zestaw(int id, String nazwa) {
        this.id = id;
        this.setNazwa(nazwa);
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getId() {
        return id;
    }
}
