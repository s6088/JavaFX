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
public class FExpense {

    @DatabaseField(id = true)
    private String id;

    @DatabaseField
    private Date fexpenseDate;

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

    public FExpense (Expense expense){
        id = expense.getId();
        fexpenseDate = expense.getExpenseDate();
        purpose = expense.getPurpose();
        memo = expense.getMemo();
        remark = expense.getRemark();
        totalAmmount = expense.getTotalAmmount();
        expectedAmmount = expense.getExpectedAmmount();
        taka = expense.isTaka();
        father = expense.getFather();
    }


    public double getExpectedAmmount() {
        return expectedAmmount;
    }

    public void setExpectedAmmount(double expectedAmmount) {
        this.expectedAmmount = expectedAmmount;
    }

    public static FExpense toClass (ObservableList<String> data) throws ParseException {
        FExpense fexpense = new FExpense(data);
        return fexpense;
    }


    public FExpense() {
    }

    public FExpense (ObservableList<String> data) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-d");
        setId(data.get(0));
        if(data.get(1) != null)setfexpenseDate(new Date( simpleDateFormat.parse(data.get(1)).getTime() ));
        setPurpose(data.get(2));
        setMemo(data.get(3));
        setRemark(data.get(4));
        if(isDouble(data.get(5)))setTotalAmmount(Double.parseDouble(data.get(5)));
        setTaka(data.get(6).compareTo("true")==0);
        setFather(data.get(7));
    }

    public static void tableInitByFather (ObservableList<ObservableList <String>> data, String vehicleType) throws SQLException {
        Dao<FExpense, String> fexpenseDao = DaoManager.createDao(CONNECTION_SOURCE, FExpense.class);
        QueryBuilder <FExpense, String> queryBuilder = fexpenseDao.queryBuilder();
        queryBuilder.where().eq("father", vehicleType);
        PreparedQuery <FExpense> preparedQuery = queryBuilder.prepare();
        List<FExpense> results = fexpenseDao.query(preparedQuery);
        data.clear();
        if(results == null)return;
        for(int i=0; i<results.size(); i++)
            data.add(FExpense.transform(results.get(i)));
    }

    public static ObservableList <String> transform (FExpense fexpense){
        ObservableList <String> data = FXCollections.observableArrayList();
        data.add(fexpense.getId());
        if(fexpense.getfexpenseDate() != null)data.add(fexpense.getfexpenseDate().toString());
        else data.add(null);
        data.add(fexpense.getPurpose());
        data.add(fexpense.getMemo());
        data.add(fexpense.getRemark());
        data.add(fexpense.getTotalAmmount() + "");
        data.add(fexpense.isTaka() ? "true" : "false");
        data.add(fexpense.getFather());
        return data;
    }

    public boolean isTaka() {
        return taka;
    }



    public static ObservableList <ObservableList <String>> transformList (ObservableList <FExpense> data){
        ObservableList <ObservableList <String>> myData = FXCollections.observableArrayList();
        for(int i=0; i<data.size(); i++)myData.add(transform(data.get(i)));
        return myData;
    }

    public Date getfexpenseDate() {
        return fexpenseDate;
    }

    public void setfexpenseDate(Date fexpenseDate) {
        this.fexpenseDate = fexpenseDate;
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

    public static List<FExpense> getById (String id) throws SQLException {
        Dao<FExpense, String> fexpenseDao = DaoManager.createDao(CONNECTION_SOURCE, FExpense.class);
        QueryBuilder<FExpense, String> queryBuilder = fexpenseDao.queryBuilder();
        queryBuilder.where().eq("father", id);
        PreparedQuery<FExpense> preparedQuery = queryBuilder.prepare();
        List<FExpense> results = fexpenseDao.query(preparedQuery);
        return results == null ? null : results;
    }
}
