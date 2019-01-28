package sample;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.jfoenix.controls.*;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static sample.GaziDao.deleteByColumn;
import static sample.Main.CONNECTION_SOURCE;
import static sample.UI.*;

public class Gazi {


    private TextField[] textFields;
    private JFXDatePicker [] jfxDatePickers;
    private JFXTimePicker [] jfxTimePickers;
    private JFXComboBox [] jfxComboBoxes;
    private JFXCheckBox jfxCheckBox;
    private TextArea textArea;
    private JFXButton [] jfxButtons;
    private TableView tableView;


    private String [] columnNames;
    private String components;
    private String isnull;
    private String datatype;
    private boolean auto;
    private ObservableList <ObservableList<String>> data;

    private Gazi child;
    private TextField father;

    private Dao dao;
    private Class name;


    public Gazi(TextField[] textFields, JFXDatePicker[] jfxDatePickers, JFXTimePicker[] jfxTimePickers, JFXComboBox[] jfxComboBoxes, JFXCheckBox jfxCheckBox, TextArea textArea,
                JFXButton[] jfxButtons, TableView tableView, String[] columnNames,
                String components, String isnull, String datatype,
                boolean auto, Gazi child, TextField father, Class name) throws SQLException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        this.textFields = textFields;
        this.jfxDatePickers = jfxDatePickers;
        this.jfxTimePickers = jfxTimePickers;
        this.jfxComboBoxes = jfxComboBoxes;
        this.jfxCheckBox = jfxCheckBox;
        this.textArea = textArea;
        this.jfxButtons = jfxButtons;
        this.tableView = tableView;
        this.columnNames = columnNames;
        this.components = components;
        this.isnull = isnull;
        this.datatype = datatype;
        this.auto = auto;
        this.child = child;
        this.father = father;
        this.name = name;
        dao = DaoManager.createDao(CONNECTION_SOURCE, name);
        enterKey();
        if(auto){
            textFields[0].setDisable(true);
            textFields[0].setText(getTimestamp());
        }
        Method method = name.getMethod("transformList", ObservableList.class);
        if(!auto || name==Post.class || name== HourRate.class)data = (ObservableList<ObservableList<String>>)
                method.invoke(null, FXCollections.observableArrayList(dao.queryForAll()));
        else data = FXCollections.observableArrayList();
        tableInit();
        jfxButtons[0].setOnAction(new Add ());
        jfxButtons[1].setOnAction(new Delete ());
        jfxButtons[2].setOnAction(new Update());
        jfxButtons[3].setOnAction(new Clear ());
    }

    public Dao getDao(){
        return dao;
    }

    public Class getName (){
        return name;
    }

    public ObservableList<ObservableList<String>> getData (){
        return data;
    }

    public void setData (ObservableList<ObservableList<String>> data){
        this.data = data;
    }

    public void setTable() throws SQLException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        child.getData().clear();
        Method method = child.getName().getMethod("tableInitByFather", ObservableList.class, String.class);
        method.invoke(null, child.getData(), textFields[0].getText().trim());
    }

    public void tableInit (){
        for (int i = 0; i < columnNames.length; i++) {
            if(auto && i==0)continue;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnNames[i]);
            column.setCellFactory(TextFieldTableCell.forTableColumn());
            final int index = i;
            column.setCellValueFactory(cd -> Bindings.valueAt(cd.getValue(), index));
            column.setOnEditCommit(evt -> {
                ObservableList<String> item = evt.getRowValue();
                String newValue = evt.getNewValue();
                if (item.size() <= index) {
                    for (int j = item.size(); j < index; j++) {
                        item.add(null);
                    }
                    item.add(newValue);
                } else {
                    item.set(index, newValue);
                }
            });
            column.setStyle("-fx-alignment: CENTER;");
            tableView.getColumns().add(column);
        }
        tableView.setItems(data);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getSelectionModel().selectedIndexProperty().addListener(new RowSelectChangeListener());
    }

    private class RowSelectChangeListener implements ChangeListener<Number> {
        @Override
        public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
            int ix = newVal.intValue();
            if ((ix < 0) || (ix >= data.size()))return;
            setPanel(ix);
            if(child != null) {
                try {
                    setTable();
                } catch (SQLException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            textFields[0].setDisable(true);
        }
    }

    public boolean isValid (boolean is){

        if(father!=null && father.getText()==null){
            makeAlert("no entity");
            return false;
        }

        ObservableList <String> tmp = getPanel();

        for(int i=0; i<tmp.size(); i++)if(isnull.charAt(i)=='0' && tmp.get(i)==null){
            makeAlert("required field cant be empty");
            return false;
        }
        for(int i=0; i<tmp.size(); i++){
            if(tmp.get(i)==null)continue;
            switch (datatype.charAt(i)){
                case 'd' :
                    if(!isDouble(tmp.get(i))){
                        makeAlert("type mis match");
                        return false;
                    }
                    break;
                case 'i':
                    if(!isInteger(tmp.get(i))){
                        makeAlert("type mis match");
                        return false;
                    }
                    break;
            }
        }
        try {
            boolean exist = dao.queryBuilder().where().eq("id", textFields[0].getText().trim()).countOf() > 0;
            if(((is ^ exist))){
                makeAlert("problem with id");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private class Add implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            try{
                if(!isValid(false))return;
                ObservableList tmp = getPanel();
                Method method = name.getMethod("toClass", ObservableList.class);
                Object object = method.invoke(null, tmp);
                dao.create(object);
                data.add(tmp);
                int row = data.size() - 1;
                tableView.requestFocus();
                tableView.getSelectionModel().select(row);
                tableView.getFocusModel().focus(row);
                if(name!=Post.class)clearPanel();
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | SQLException e1) {
                makeAlert("problem while adding !!");
                return;
            }

        }
    }

    private class Delete implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            int ix = tableView.getSelectionModel().getSelectedIndex();
            if(ix<0 || ix>=data.size()) {
                makeAlert("nothing to delete");
                return;
            }
            try {
                deleteByColumn(name, "id", textFields[0].getText().trim());
                if(child != null)deleteByColumn(child.getName(), "father", textFields[0].getText().trim());
                data.remove(ix);
                clearPanel();
                if (ix != 0) ix--;
                tableView.requestFocus();
                tableView.getSelectionModel().select(ix);
                tableView.getFocusModel().focus(ix);
            } catch (SQLException e1) {
                makeAlert("problem while deleting !!");
                return;
            }
        }
    }

    private class Update implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {int ix = tableView.getSelectionModel().getSelectedIndex();
            if(ix<0 || ix>=data.size()){
                makeAlert("nothing to update");
                return;
            }
            try {
                if(!isValid(true))return;
                ObservableList tmp = getPanel();
                Method method = name.getMethod("toClass", ObservableList.class);
                Object object = method.invoke(null, tmp);
                deleteByColumn(name, "id", textFields[0].getText().trim());
                dao.create(object);
                data.set(ix, tmp);
                clearPanel();
                tableView.requestFocus();
                tableView.getSelectionModel().select(ix);
                tableView.getFocusModel().focus(ix);
            } catch (SQLException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e1) {
                makeAlert("problem while updateing");
                return;
            }
        }
    }

    private class Clear implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            clearPanel();
        }
    }

    public void setPanel (int ix){
        int [] x = new int [] {0, 0, 0, 0};
        DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("yyyy-MM-d");
        DateTimeFormatter dateTimeFormatter2 = DateTimeFormatter.ofPattern("H:mm");
        for(int i=0; i<components.length(); i++){
            String tmp = data.get(ix).get(i);
            switch (components.charAt(i)){
                case 't' :
                    if(tmp!=null)textFields[x[0]].setText(tmp);
                    else textFields[x[0]].setText(null);
                    x[0]++;
                    break;
                case 'D':
                    if(tmp!=null)jfxDatePickers[x[1]].setValue(LocalDate.parse(tmp, dateTimeFormatter1));
                    else jfxDatePickers[x[1]].setValue(null);
                    x[1]++;
                    break;
                case 'T' :
                    if(tmp!=null)jfxTimePickers[x[2]].setValue(LocalTime.parse(tmp, dateTimeFormatter2));
                    else jfxTimePickers[x[2]].setValue(null);
                    x[2]++;
                    break;
                case 'c' :
                    if(tmp!=null)jfxCheckBox.setSelected(tmp.compareTo("true")==0);
                    else jfxCheckBox.setSelected(false);
                    break;
                case 'C' :
                    if(tmp!=null)jfxComboBoxes[x[3]].getSelectionModel().select(tmp);
                    else jfxComboBoxes[x[3]].getSelectionModel().select(null);
                    x[3]++;
                    break;
                case 'x':
                    if(tmp!=null)textArea.setText(tmp);
                    else textArea.setText(null);
                    break;
            }
        }
    }

    public void clearPanel (){
        int [] x = new int [] {0, 0, 0, 0};
        for(int i=0; i<components.length(); i++){
            switch (components.charAt(i)){
                case 't' :
                    textFields[x[0]].setText(null);
                    x[0]++;
                    break;
                case 'D':
                    jfxDatePickers[x[1]].setValue(null);
                    x[1]++;
                    break;
                case 'T' :
                    jfxTimePickers[x[2]].setValue(null);
                    x[2]++;
                    break;
                case 'c' :
                    jfxCheckBox.setSelected(false);
                    break;
                case 'C' :
                    jfxComboBoxes[x[3]].getSelectionModel().select(null);
                    x[3]++;
                    break;
                case 'x':
                    textArea.setText(null);
                    break;
            }
        }
        textFields[0].setDisable(auto);
        if(auto)textFields[0].setText(getTimestamp());
        if(name== Post.class)jfxDatePickers[0].setValue(LocalDate.now());
        if(child != null){
            child.getData().clear();
            child.clearPanel();
        }
        tableView.getSelectionModel().clearSelection();
    }

    public void enterKey(){
        int [] x = new int [] {0, 0, 0, 0};
        for(int i=0; i<components.length(); i++){
            final int j = i;
            switch (components.charAt(i)){
                case 't' :
                    textFields[x[0]].setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent event) {
                            if(event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.DOWN)) {
                                focus(j+1);
                            }
                            else if(event.getCode().equals(KeyCode.UP))focus(j-1);
                        }
                    });
                    x[0]++;
                    break;
                case 'D' :
                    jfxDatePickers[x[1]].setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent event) {
                            if(event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.DOWN))focus(j+1);
                            else if(event.getCode().equals(KeyCode.UP))focus(j-1);

                        }
                    });
                    x[1]++;
                    break;
                case 'T' :
                    jfxTimePickers[x[2]].setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent event) {
                            if(event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.DOWN)) {
                                focus(j+1);
                            }
                            else if(event.getCode().equals(KeyCode.UP))focus(j-1);
                        }
                    });
                    x[2]++;
                    break;
                case 'C' :
                    jfxComboBoxes[x[3]].setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent event) {
                            if(event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.DOWN)) {
                                focus(j+1);
                            }
                            else if(event.getCode().equals(KeyCode.UP))focus(j-1);
                        }
                    });
                    x[3]++;
                    break;
                case 'c':
                    jfxCheckBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent event) {
                            if(event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.DOWN)) {
                                focus(j+1);
                            }
                            else if(event.getCode().equals(KeyCode.UP))focus(j-1);
                        }
                    });
                    break;
                case 'x':
                    textArea.setOnKeyPressed(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent event) {
                            if(event.getCode().equals(KeyCode.ENTER) || event.getCode().equals(KeyCode.DOWN)) {
                                focus(j+1);
                            }
                            else if(event.getCode().equals(KeyCode.UP))focus(j-1);
                        }
                    });
                    break;
            }
        }

    }

    public void focus (int index){
        int cnt = 0;
        index = (index + components.length()) % components.length();
        for(int j=0; j<index; j++)if(components.charAt(index)==components.charAt(j))cnt++;
        switch (components.charAt(index)){
            case 't' :
                textFields[cnt].requestFocus();
                break;
            case 'D' :
                jfxDatePickers[cnt].requestFocus();
                break;
            case 'T' :
                jfxTimePickers[cnt].requestFocus();
                break;
            case 'C' :
                jfxComboBoxes[cnt].show();
                break;
            case 'c':
                jfxCheckBox.setSelected(!jfxCheckBox.isSelected());
                break;
            case 'x':
                textArea.requestFocus();
                break;
        }
    }

    public ObservableList <String> getPanel (){
        ObservableList <String> tmp = FXCollections.observableArrayList();
        int [] x = new int [] {0, 0, 0, 0};
        for(int i=0; i<components.length(); i++) {

            switch (components.charAt(i)) {
                case 't':
                    if(textFields[x[0]].getText()==null || textFields[x[0]].getText().trim().isEmpty())tmp.add(null);
                    else tmp.add(textFields[x[0]].getText().trim());
                    x[0]++;
                    break;
                case 'D':
                    if(jfxDatePickers[x[1]].getValue() != null)
                        tmp.add(jfxDatePickers[x[1]].getValue().toString());
                    else tmp.add(null);
                    x[1]++;
                    break;
                case 'T':
                    if(jfxTimePickers[x[2]].getValue() != null)
                        tmp.add(jfxTimePickers[x[2]].getValue().toString());
                    else tmp.add(null);
                    x[2]++;
                    break;
                case 'c':
                    tmp.add(jfxCheckBox.isSelected()  ? "true" : "false");
                    break;
                case 'C':
                    if(jfxComboBoxes[x[3]].getValue() != null)tmp.add(jfxComboBoxes[x[3]].getValue().toString());
                    else tmp.add(null);
                    x[3]++;
                    break;
                case 'x':
                    tmp.add(textArea.getText().trim());
                    break;
            }
        }
        if(father != null)tmp.add(father.getText().trim());
        return tmp;
    }































}
