package com.example.pt04prak2072028jdbc;

import com.example.pt04prak2072028jdbc.dao.CategoryDao;
import com.example.pt04prak2072028jdbc.model.Category;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class CategoryController {

    public TextField inputIdCategory;
    public TextField inputNameCategory;
    public Button btnSaveCategory;
    public TableView tableCategory;
    public TableColumn columnIdCategory;
    public TableColumn columnNameCategory;

    ObservableList<Category> cListTampilan;

    public void initialize() {
        CategoryDao dao = new CategoryDao();
        cListTampilan = dao.getData();
        tableCategory.setItems(cListTampilan);
        columnIdCategory.setCellValueFactory(new PropertyValueFactory<Category, Integer>("id"));
        columnNameCategory.setCellValueFactory(new PropertyValueFactory<Category, String>("name"));
    }

    public void insertCategory(ActionEvent actionEvent) {
        CategoryDao dao = new CategoryDao();
        Category c = new Category();
        showAlert();
        c.setId(Integer.parseInt(inputIdCategory.getText()));
        c.setName(inputNameCategory.getText());
        dao.insertData(c);
        cListTampilan = dao.getData();
        refreshData(cListTampilan);
    }

    public void showAlert() {
        if (inputIdCategory.getText().isEmpty() || inputNameCategory.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please fill in all the field");
            alert.setTitle("Message");
            alert.showAndWait();
        }
    }

    public void refreshData(ObservableList<Category> cListTampilan) {
        tableCategory.setItems(cListTampilan);
        columnIdCategory.setCellValueFactory(new PropertyValueFactory<Category, Integer>("id"));
        columnNameCategory.setCellValueFactory(new PropertyValueFactory<Category, String>("name"));
    }
}
