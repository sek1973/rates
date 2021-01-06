package sk.servlets;

/** class to contain single rate request response data */
public class RateData {
    public String date;
    public Double rate;

    public RateData(String _date, Double _rate) {
        this.date = _date;
        this.rate = _rate;
    }

    @Override
    public String toString() {
        return "date: " + this.date + ", rate: " + this.rate;
    }
}
