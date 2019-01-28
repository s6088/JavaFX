package sample;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GenerateReport {

    List<FPost> posts;
    boolean isDriver;

    public GenerateReport(List<FPost> posts, boolean isDriver){
        this.posts = posts;
        this.isDriver = isDriver;
    }


    public ArrayList<MealBean> getMealBeanList () throws SQLException {

        HashMap <String, MealBean> map = new HashMap<>();

        for(FPost x : posts){
            String name;
            if(isDriver && x.getDriver()!=null){
                MealBean mealBean;
                if(!map.containsKey(x.getDriver())){
                    mealBean = new MealBean(x.getDriver());
                    Driver driver = (Driver) GaziDao.getById(Driver.class, "id",x.getDriver());
                    mealBean.setName(driver.getName());
                    map.put(x.getDriver(), mealBean);
                }
                mealBean = map.get(x.getDriver());
                Meal meal = (Meal) GaziDao.getById(Meal.class,"id" , x.getId());
                mealBean.addNoOfLunch(meal.getNumberOfLunch());
                mealBean.addNoOfDinner(meal.getNumberOfDinner());
                mealBean.addLunchAmmount(meal.getDriverLunchAmmount());
                mealBean.addDinnerAmmount(meal.getDriverDinnerAmmount());
            }
            else if(!isDriver && x.getHelper()!=null){
                MealBean mealBean;
                if(!map.containsKey(x.getDriver())){
                    mealBean = new MealBean(x.getDriver());
                    Helper helper = (Helper)GaziDao.getById(Helper.class, "id",x.getHelper());
                    mealBean.setName(helper.getName());
                    map.put(x.getHelper(), mealBean);
                }
                mealBean = map.get(x.getHelper());
                Meal meal = (Meal) GaziDao.getById(Meal.class, "id", x.getId());
                mealBean.addNoOfLunch(meal.getNumberOfLunch());
                mealBean.addNoOfDinner(meal.getNumberOfDinner());
                mealBean.addLunchAmmount(meal.getDriverLunchAmmount());
                mealBean.addDinnerAmmount(meal.getDriverDinnerAmmount());
            }
        }

        ArrayList <MealBean> mealBeans = new ArrayList<>();
        for(MealBean x : map.values()){
            mealBeans.add(x);
        }
        return mealBeans;
    }


/*    public void printReport (){
        *//*C:\Users\s6088\Desktop\GaziTyres
        DataBeanList DataBeanList = new DataBeanList();
        ArrayList<DataBean> dataList = DataBeanList.getDataBeanList();
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
        Map parameters = new HashMap();
        parameters.put("ReportTitle", "Gazi Auto Tyres LTD");
        parameters.put("Author", "posta, Dhaka");*//*
        String sourceFileName = "C:\\Users\\s6088\\JaspersoftWorkspace\\MyReports\\meal.jrxml";
        try {
            HashMap parameters = new HashMap();
            parameters.put("one", "one");
            parameters.put("two", "two");
            parameters.put("three", "three");
            JasperReport jasperReport = JasperCompileManager.compileReport(sourceFileName);
            JasperPrint jasperprint = JasperFillManager.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(getMealBeanList()));
            JasperViewer.viewReport(jasperprint);
        } catch (JRException | SQLException e) {
            e.printStackTrace();
        }
    }*/




}
