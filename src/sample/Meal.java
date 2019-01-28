package sample;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Meal {

    @DatabaseField(id = true)
    private String id;

    @DatabaseField
    private int numberOfLunch;

    @DatabaseField
    private int numberOfDinner;

    @DatabaseField
    private double driverLunchAmmount;

    @DatabaseField
    private double driverDinnerAmmount;

    @DatabaseField
    private double helperLunchAmmount;

    @DatabaseField
    private double helperDinnerAmmount;

    public Meal (){}

    public Meal(String id) {
        this.id = id;
        numberOfLunch = 0;
        numberOfDinner = 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumberOfLunch() {
        return numberOfLunch;
    }

    public void setNumberOfLunch(int numberOfLunch) {
        this.numberOfLunch = numberOfLunch;
    }

    public int getNumberOfDinner() {
        return numberOfDinner;
    }

    public void setNumberOfDinner(int numberOfDinner) {
        this.numberOfDinner = numberOfDinner;
    }

    public double getDriverLunchAmmount() {
        return driverLunchAmmount;
    }

    public void setDriverLunchAmmount(double driverLunchAmmount) {
        this.driverLunchAmmount = driverLunchAmmount;
    }

    public double getDriverDinnerAmmount() {
        return driverDinnerAmmount;
    }

    public void setDriverDinnerAmmount(double driverDinnerAmmount) {
        this.driverDinnerAmmount = driverDinnerAmmount;
    }

    public double getHelperLunchAmmount() {
        return helperLunchAmmount;
    }

    public void setHelperLunchAmmount(double helperLunchAmmount) {
        this.helperLunchAmmount = helperLunchAmmount;
    }

    public double getHelperDinnerAmmount() {
        return helperDinnerAmmount;
    }

    public void setHelperDinnerAmmount(double helperDinnerAmmount) {
        this.helperDinnerAmmount = helperDinnerAmmount;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id='" + id + '\'' +
                ", numberOfLunch=" + numberOfLunch +
                ", numberOfDinner=" + numberOfDinner +
                ", driverLunchAmmount=" + driverLunchAmmount +
                ", driverDinnerAmmount=" + driverDinnerAmmount +
                ", helperLunchAmmount=" + helperLunchAmmount +
                ", helperDinnerAmmount=" + helperDinnerAmmount +
                '}';
    }
}
