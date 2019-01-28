package sample;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.DatabaseTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static sample.Main.CONNECTION_SOURCE;
import static sample.UI.isDouble;

@DatabaseTable
public class Expense {

    @DatabaseField(id = true)
    private String id;


    @DatabaseField
    private Date expenseDate;

    @DatabaseField
    private String purpose;

    @DatabaseField
    private String memo;

    @DatabaseField
    private String remark;

    @DatabaseField
    private double totalAmmount;

    private double expectedAmmount;

    @DatabaseField
    private boolean taka;

    @DatabaseField
    private String father;


    public double getExpectedAmmount() {
        return expectedAmmount;
    }

    public void setExpectedAmmount(double expectedAmmount) {
        this.expectedAmmount = expectedAmmount;
    }

    public static Expense toClass (ObservableList<String> data) throws ParseException {
        Expense expense = new Expense(data);
        return expense;
    }


    public Expense() {
    }

    public Expense (ObservableList<String> data) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-d");
        setId(data.get(0));
        if(data.get(1) != null)setExpenseDate(new Date( simpleDateFormat.parse(data.get(1)).getTime() ));
        setPurpose(data.get(2));
        setMemo(data.get(3));
        setRemark(data.get(4));
        if(isDouble(data.get(5)))setTotalAmmount(Double.parseDouble(data.get(5)));
        setTaka(data.get(6).compareTo("true")==0);
        setFather(data.get(7));
    }

    public static void tableInitByFather (ObservableList<ObservableList <String>> data, String vehicleType) throws SQLException {
        Dao<Expense, String> expenseDao = DaoManager.createDao(CONNECTION_SOURCE, Expense.class);
        QueryBuilder <Expense, String> queryBuilder = expenseDao.queryBuilder();
        queryBuilder.where().eq("father", vehicleType);
        PreparedQuery <Expense> preparedQuery = queryBuilder.prepare();
        List<Expense> results = expenseDao.query(preparedQuery);
        data.clear();
        if(results == null)return;
        for(int i=0; i<results.size(); i++)
            data.add(Expense.transform(results.get(i)));
    }

    public static ObservableList <String> transform (Expense expense){
        ObservableList <String> data = FXCollections.observableArrayList();
        data.add(expense.getId());
        if(expense.getExpenseDate() != null)data.add(expense.getExpenseDate().toString());
        else data.add(null);
        data.add(expense.getPurpose());
        data.add(expense.getMemo());
        data.add(expense.getRemark());
        data.add(expense.getTotalAmmount() + "");
        data.add(expense.isTaka() ? "true" : "false");
        data.add(expense.getFather());
        return data;
    }

    public boolean isTaka() {
        return taka;
    }

    public static ObservableList <ObservableList <String>> transformList (ObservableList <Expense> data){
        ObservableList <ObservableList <String>> myData = FXCollections.observableArrayList();
        for(int i=0; i<data.size(); i++)myData.add(transform(data.get(i)));
        return myData;
    }

    public Date getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(Date expenseDate) {
        this.expenseDate = expenseDate;
    }

    public double getTotalAmmount() {
        return totalAmmount;
    }

    public void setTotalAmmount(double totalAmmount) {
        this.totalAmmount = totalAmmount;
    }


    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setTaka(boolean taka) {
        this.taka = taka;
    }

    public static List<Expense> getById (String id) throws SQLException {
        Dao<Expense, String> ExpenseDao = DaoManager.createDao(CONNECTION_SOURCE, Expense.class);
        QueryBuilder<Expense, String> queryBuilder = ExpenseDao.queryBuilder();
        queryBuilder.where().eq("father", id);
        PreparedQuery<Expense> preparedQuery = queryBuilder.prepare();
        List<Expense> results = ExpenseDao.query(preparedQuery);
        return results == null ? null : results;
    }
}
