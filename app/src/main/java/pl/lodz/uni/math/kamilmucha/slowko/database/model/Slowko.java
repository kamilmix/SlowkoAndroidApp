package pl.lodz.uni.math.kamilmucha.slowko.database.model;

public class Slowko {
    private Integer _id;
    private String slowko;
    private String tlumaczenie;
    private boolean czyUmie;
    private int idZestawu;

    public Slowko() {
    }


    public Slowko(String slowko, String tlumaczenie) {
        this.slowko = slowko;
        this.tlumaczenie = tlumaczenie;
        czyUmie = false;
    }

    public Slowko(int id, String slowko, String tlumaczenie, boolean czyUmie, int idZestawu) {
        this._id = id;
        this.slowko = slowko;
        this.tlumaczenie = tlumaczenie;
        this.czyUmie = czyUmie;
        this.idZestawu = idZestawu;
    }


    public Integer get_id() {
        return _id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getSlowko() {
        return slowko;
    }

    public void setSlowko(String slowko) {
        this.slowko = slowko;
    }

    public String getTlumaczenie() {
        return tlumaczenie;
    }

    public void setTlumaczenie(String tlumaczenie) {
        this.tlumaczenie = tlumaczenie;
    }

    public boolean isCzyUmie() {
        return czyUmie;
    }

    public void setCzyUmie(boolean czyUmie) {
        this.czyUmie = czyUmie;
    }

    public void setCzyUmie(int anInt) {
        if (anInt == 1) {
            czyUmie = true;
        } else if (anInt == 0) {
            czyUmie = false;
        }
    }

    @Override
    public String toString() {
        return _id + " " + slowko + " " + tlumaczenie + " " + czyUmie;
    }
}
