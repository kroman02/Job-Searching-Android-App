package bbk.finalyearproject.constructionjobs;

public class Rating {
    private int rater;
    private int rated;
    private float rate;

    public Rating(int rater, int rated, float rate) {
        this.rater = rater;
        this.rated = rated;
        this.rate = rate;
    }

    public int getRater() {
        return rater;
    }

    public void setRater(int rater) {
        this.rater = rater;
    }

    public int getRated() {
        return rated;
    }

    public void setRated(int rated) {
        this.rated = rated;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
