package pl.lodz.uni.math.kamilmucha.slowko.database;

public class Slowko {
    private Integer _id;
    private String slowko;
    private String tlumaczenie;
    private boolean czyUmie;

    public Slowko(){};

    public Slowko(String slowko, String tlumaczenie){
        this.slowko = slowko;
        this.tlumaczenie = tlumaczenie;
        czyUmie=false;

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
        if(anInt==1){
            czyUmie= true;
        }
        else if (anInt==0){
            czyUmie=false;
        }

    }

    @Override
    public String toString() {
        return _id + " " + slowko + " " + tlumaczenie + " " + czyUmie;
    }
}
