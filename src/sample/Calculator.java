package sample;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static sample.Main.CONNECTION_SOURCE;


public class Calculator {


    private String postId;

    private Post post;

    private VehicleType vehicleType;

    private Vehicle vehicle;

    private List <Limit> limits;

    private List <Expense> expenses;

    private HashMap <String, String> utils;

    private DateTime dt1;

    private DateTime dt2;

    private Interval interval;

    private Map<String, Integer> limitMap;
    private ObservableList <ObservableList <String> > data;
    private Dao<Holiday, String> HolidayDao;

    private DecimalFormat df;// = new DecimalFormat("#0.00");



    public Calculator (String postId, ObservableList <ObservableList <String> > data) throws SQLException {
        df = new DecimalFormat("#0.00");
        this.postId =   postId;
        post        =   Post.getById(postId);
        dt1         =   new DateTime(post.getDepartTime());
        dt2         =   new DateTime(post.getArriveTime());
        interval    =   new Interval(dt1, dt2);
        dt1         =   dt1.hourOfDay().setCopy(0);
        dt1         =   dt1.minuteOfHour().setCopy(0);
        dt2         =   dt2.hourOfDay().setCopy(23);
        dt2         =   dt2.minuteOfHour().setCopy(59);
        vehicle     =   Vehicle.getById(post.getVehicle());
        vehicleType =   VehicleType.getById(vehicle.getVehicleType());
        utils       =   Util.getById();
        this.data   =   data;
        limits      =   Limit.getById(vehicleType.getId());
        limitMap    =   new HashMap<>();
        for(int i=0; limits!=null && i<limits.size(); i++)
            limitMap.put(limits.get(i).getMaterialName(), i);
        HolidayDao = DaoManager.createDao(CONNECTION_SOURCE, Holiday.class);
    }

    public Meal getMeal (){

        Meal meal = new Meal(postId);

        LocalTime lunch_starts = new LocalTime(utils.get("lunch_starts"));
        int lunch_duration = (int) Math.ceil(60.0 * Double.parseDouble(utils.get("lunch_duration")));
        LocalTime dinner_starts = new LocalTime(utils.get("dinner_starts"));
        int dinner_duration = (int) Math.ceil(60.0 *Double.parseDouble(utils.get("dinner_duration")));


        for(DateTime i = new DateTime(dt1); i.compareTo(dt2) <= 0; i = i.plusDays(1)){

            DateTime test = new DateTime(i);
            test     = test.hourOfDay().setCopy(lunch_starts.getHourOfDay());
            test     = test.minuteOfHour().setCopy(lunch_starts.getMinuteOfHour());
            Interval interval1 = new Interval(test, test.plusMinutes(lunch_duration));

            if(interval.overlaps(interval1)){
                meal.setNumberOfLunch(meal.getNumberOfLunch() + 1);
            }

            test = test.hourOfDay().setCopy(dinner_starts.getHourOfDay());
            test = test.minuteOfHour().setCopy(dinner_starts.getMinuteOfHour());
            interval1 = new Interval(test, test.plusMinutes(dinner_duration));

            if(interval.overlaps(interval1)){
                meal.setNumberOfDinner(meal.getNumberOfDinner() + 1);
            }
        }
        meal.setDriverLunchAmmount(meal.getNumberOfLunch() * vehicleType.getDriverLunchCost());
        meal.setDriverDinnerAmmount(meal.getNumberOfDinner() * vehicleType.getDriverDinnerCost());
        meal.setHelperLunchAmmount(meal.getNumberOfLunch() * vehicleType.getHelperLunchCost());
        meal.setHelperDinnerAmmount(meal.getNumberOfDinner() * vehicleType.getDriverDinnerCost());
        return meal;
    }


    public Allowance getKmAllowance () throws SQLException {

        Allowance allowance = new Allowance(postId);
        double km = post.getEndingKm() - post.getStartingKm();
        double tmp = 0.0;
        List <Expense> list = Expense.getById(postId);

        for(Expense x : list){

            if(limitMap.containsKey(x.getPurpose()))
                x.setExpectedAmmount(limits.get ( limitMap.get(x.getPurpose()) ).getMaxperKm()  * km);
            else x.setExpectedAmmount(x.getTotalAmmount());


            if(!x.isTaka()){
                double myprice = Price.getById(x.getPurpose()).getUnitPrice();
                x.setTotalAmmount(x.getTotalAmmount() * myprice);
                x.setExpectedAmmount(x.getExpectedAmmount() * myprice);
            }

            Double net = x.getExpectedAmmount() - x.getTotalAmmount();
            data.add(FXCollections.observableArrayList(new String [] {x.getPurpose(), x.getTotalAmmount() + "/" + x.getExpectedAmmount() }));
            tmp += net;
        }

        data.add(FXCollections.observableArrayList(new String [] {"driver km", km*vehicleType.getDriverAllowance() + ""}));
        data.add(FXCollections.observableArrayList(new String [] {"helper km", km*vehicleType.getHelperAllowance() + ""}));


        Double x = vehicleType.getDriverAllowance();
        Double y = vehicleType.getHelperAllowance();
        allowance.setDriverAllowance((tmp*x)/(x+y));
        allowance.setHelperAllowance((tmp*y)/(x+y));
        return allowance;
    }




    public List<Goodwork> getGoodwork () throws SQLException {

        List <Goodwork> goodworks = new ArrayList<>();
        LocalTime work_starts = new LocalTime(utils.get("work_starts"));
        int work_duration = (int) Math.ceil(60 * Double.parseDouble(utils.get("work_duration")));
        int month_days = Integer.parseInt(utils.get("month_days"));

        double minTimeToCount = Double.parseDouble(utils.get("min_time_to_count"));
        for(DateTime i = new DateTime(dt1); i.compareTo(dt2) <= 0; i = i.plusDays(1)){
            DateTime test = new DateTime(i);
            test     = test.hourOfDay().setCopy(work_starts.getHourOfDay());
            test     = test.minuteOfHour().setCopy(work_starts.getMinuteOfHour());
            LocalDate localDate = test.toLocalDate();
            String string = localDate.toString()+ " " + sadman[localDate.getDayOfWeek()-1];
            if(HolidayDao.queryBuilder().where().eq("id", string).countOf() < 1)continue;
            Interval interval1 = new Interval(test, test.plusMinutes(work_duration));

            if(interval.overlap(interval1)!=null && interval.overlap(interval1).toDurationMillis() >= minTimeToCount*3600000){
                Goodwork goodwork = new Goodwork(postId);
                goodwork.setHoliday(new Date(test.getMillis()));
                goodwork.setHrs((int)Math.ceil(interval.overlap(interval1).toDurationMillis()/3600000));
                goodwork.setDriverAmmount(vehicleType.getDriverSalary()/month_days);
                goodwork.setHelperAmmount(vehicleType.getHelperSalary()/month_days);
                data.add(FXCollections.observableArrayList(new String [] {goodwork.getHoliday() + " / " + goodwork.getHrs() + " Hrs",  df.format(goodwork.getDriverAmmount()) + "/" + df.format(goodwork.getHelperAmmount())}));
            }
        }
        return goodworks;
    }




    private String [] sadman = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};


    public Overtime getOvertime () throws SQLException {

        Dao <HourRate, String> dao = DaoManager.createDao(CONNECTION_SOURCE, HourRate.class);
        List <HourRate> hourRates = dao.queryForAll();

        Collections.sort(hourRates, new Comparator<HourRate>(){
            public int compare(HourRate o1, HourRate o2)
            {
                return o2.getHours().compareTo(o1.getHours());
            }
        });


        Overtime overtime = new Overtime(postId);
        LocalTime work_starts = new LocalTime(utils.get("work_starts"));
        int work_duration = (int) Math.ceil(60 * Double.parseDouble(utils.get("work_duration")));
        LocalTime work_ends = work_starts.plusMinutes(work_duration);

        double tk = 0;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");


        for(DateTime i = new DateTime(dt1); i.compareTo(dt2) <= 0; i = i.plusDays(1)){

            DateTime test = new DateTime(i);
            test     = test.hourOfDay().setCopy(work_ends.getHourOfDay());
            test     = test.minuteOfHour().setCopy(work_ends.getMinuteOfHour());

            for(int j=0; j<hourRates.size(); j++){
                Interval interval1 = new Interval(test, test.plusMinutes((int)Math.ceil(60*hourRates.get(j).getHours())));
                if( interval.overlap(interval1)!=null && interval.overlap(interval1).toDurationMillis() >= hourRates.get(j).getHours()*1000*60*60 ){
                    Interval me = interval.overlap(interval1);
                    data.add(FXCollections.observableArrayList(new String [] {new Date(test.getMillis()) + " " + me.getStart().getHourOfDay() + ":" + me.getStart().getMinuteOfHour() + " " + interval1.toDurationMillis()/3600000 + " Hrs",  hourRates.get(j).getDriverRate()+"/"+ hourRates.get(j).getHelperRate()}));
                    break;
                }

            }

        }

        Double x = vehicleType.getDriverAllowance();
        Double y = vehicleType.getHelperAllowance();

        return overtime;
    }


}
