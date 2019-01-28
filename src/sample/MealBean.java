package sample;


public class MealBean {

    private String id;
    private String name;
    private int noOfLunch;
    private int noOfDinner;
    private double lunchAmmount;
    private double dinnerAmmount;
    private double total;



    public MealBean (String id){
        this.id = id;
        noOfLunch  = 0;
        noOfDinner = 0;
        lunchAmmount = 0;
        dinnerAmmount = 0;
    }

    public double getTotal() {
        return lunchAmmount+dinnerAmmount;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "MealBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", noOfLunch=" + noOfLunch +
                ", noOfDinner=" + noOfDinner +
                ", lunchAmmount=" + lunchAmmount +
                ", dinnerAmmount=" + dinnerAmmount +
                ", total=" + getTotal() +
                '}';
    }

    public void addNoOfLunch (int x){
        noOfLunch += x;
    }

    public void addNoOfDinner (int x){
        noOfDinner += x;
    }

    public void addLunchAmmount (double x){
        lunchAmmount += x;
    }

    public void addDinnerAmmount (double x){
        dinnerAmmount += x;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNoOfLunch() {
        return noOfLunch;
    }

    public void setNoOfLunch(int noOfLunch) {
        this.noOfLunch = noOfLunch;
    }

    public int getNoOfDinner() {
        return noOfDinner;
    }

    public void setNoOfDinner(int noOfDinner) {
        this.noOfDinner = noOfDinner;
    }

    public double getLunchAmmount() {
        return lunchAmmount;
    }

    public void setLunchAmmount(double lunchAmmount) {
        this.lunchAmmount = lunchAmmount;
    }

    public double getDinnerAmmount() {
        return dinnerAmmount;
    }

    public void setDinnerAmmount(double dinnerAmmount) {
        this.dinnerAmmount = dinnerAmmount;
    }
}
