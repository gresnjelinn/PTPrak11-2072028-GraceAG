package com.example.pt04prak2072028jdbc;

import com.example.pt04prak2072028jdbc.dao.CategoryDao;
import com.example.pt04prak2072028jdbc.dao.ItemsDao;
import com.example.pt04prak2072028jdbc.model.Category;
import com.example.pt04prak2072028jdbc.model.Items;
import com.example.pt04prak2072028jdbc.util.MyConnection;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ItemsController {

    public Menu menuFile;
    public MenuItem menuItemCategory;
    public TextField inputIdItems;
    public TextField InputNameItems;
    public TextField InputPriceItems;
    public TextField InputDescriptionItems;
    public ComboBox comboboxCategoryItems;
    public Button btnInsertItems;
    public Button btnResetItems;
    public Button btnUpdateItems;
    public Button btnDeleteItems;
    public TableView tableItems;
    public TableColumn columnIdItems;
    public TableColumn columnNameItems;
    public TableColumn columnPriceItems;
    public TableColumn columnCategoryItems;

    public Stage categoryStage;
    public MenuItem menuItemClose;
    public Menu menuReport;
    public MenuItem menuItemSimple;
    public MenuItem menuItemGroup;

    ObservableList<Items> iListTampilan;
    ObservableList<Category> cListTampilan;

    public void initialize() {
        ItemsDao dao = new ItemsDao();
        iListTampilan = dao.getData();
        tableItems.setItems(iListTampilan);
        columnIdItems.setCellValueFactory(new PropertyValueFactory<Items, Integer>("id"));
        columnNameItems.setCellValueFactory(new PropertyValueFactory<Items, String>("name"));
        columnPriceItems.setCellValueFactory(new PropertyValueFactory<Items, Double>("price"));
        columnCategoryItems.setCellValueFactory(new PropertyValueFactory<Items, String>("category"));

        CategoryDao categoryDao = new CategoryDao();
        cListTampilan = categoryDao.getData();
        comboboxCategoryItems.setItems(cListTampilan);

        menuItemCategory.setAccelerator(KeyCombination.keyCombination("Alt+F2"));
        menuItemClose.setAccelerator(KeyCombination.keyCombination("Alt+X"));
        menuItemSimple.setAccelerator(KeyCombination.keyCombination("Alt+S"));
        menuItemGroup.setAccelerator(KeyCombination.keyCombination("Alt+G"));
    }

    public void goToCategory(ActionEvent actionEvent) throws IOException {
        categoryStage = new Stage();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("category-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 291, 357);

        CategoryController cc = fxmlLoader.getController();

        categoryStage.setTitle("Category Management");
        categoryStage.setScene(scene);
        categoryStage.showAndWait();

        CategoryDao cDao = new CategoryDao();
        comboboxCategoryItems.setItems(cDao.getData());
    }

    public void resetInput(ActionEvent actionEvent) {
        inputIdItems.setText("");
        InputNameItems.setText("");
        InputPriceItems.setText("");
        InputDescriptionItems.setText("");
        comboboxCategoryItems.getSelectionModel().select(-1);
    }

    public void insertItems(ActionEvent actionEvent) {
        ItemsDao dao = new ItemsDao();
        Items i = new Items();
        showAlert();
        i.setId(Integer.parseInt(inputIdItems.getText()));
        i.setName(InputNameItems.getText());
        i.setPrice(Double.parseDouble(InputPriceItems.getText()));
        i.setDescription(InputDescriptionItems.getText());
        i.setCategory(cListTampilan.get(comboboxCategoryItems.getSelectionModel().getSelectedIndex()));
        dao.insertData(i);
        iListTampilan = dao.getData();
        refreshData(iListTampilan);
    }

    public void updateItems(ActionEvent actionEvent) {
        ItemsDao dao = new ItemsDao();
        int selectedRow = tableItems.getSelectionModel().getSelectedIndex();
        Items selectedItems = iListTampilan.get(selectedRow);
        showAlert();
        selectedItems.setName(InputNameItems.getText());
        selectedItems.setPrice(Double.parseDouble(InputPriceItems.getText()));
        selectedItems.setDescription(InputDescriptionItems.getText());
        selectedItems.setCategory(cListTampilan.get(comboboxCategoryItems.getSelectionModel().getSelectedIndex()));
        dao.updateData(selectedItems);
        iListTampilan = dao.getData();
        refreshData(iListTampilan);
    }

    public void deleteItems(ActionEvent actionEvent) {
        ItemsDao dao = new ItemsDao();
        int selectedRow = tableItems.getSelectionModel().getSelectedIndex();
        dao.deleteData(iListTampilan.get(selectedRow));
        iListTampilan = dao.getData();
        refreshData(iListTampilan);
    }

    public void rowSelected(MouseEvent mouseEvent) {
        int selectedRow = tableItems.getSelectionModel().getSelectedIndex();
        inputIdItems.setText(String.valueOf(iListTampilan.get(selectedRow).getId()));
        InputNameItems.setText(iListTampilan.get(selectedRow).getName());
        InputPriceItems.setText(String.valueOf(iListTampilan.get(selectedRow).getPrice()));
        InputDescriptionItems.setText(iListTampilan.get(selectedRow).getDescription());
        comboboxCategoryItems.setValue(iListTampilan.get(selectedRow).getCategory());
    }

    public void refreshData(ObservableList<Items> iListTampilan) {
        tableItems.setItems(iListTampilan);
        columnIdItems.setCellValueFactory(new PropertyValueFactory<Items, Integer>("id"));
        columnNameItems.setCellValueFactory(new PropertyValueFactory<Items, String>("name"));
        columnPriceItems.setCellValueFactory(new PropertyValueFactory<Items, Double>("price"));
        columnCategoryItems.setCellValueFactory(new PropertyValueFactory<Items, String>("category"));
    }

    public void closeItemsWindow(ActionEvent actionEvent) {
        ((Node) actionEvent.getSource()).getScene().getWindow().hide();
    }

    public void showAlert() {
        if (inputIdItems.getText().isEmpty() || InputNameItems.getText().isEmpty() ||
                InputPriceItems.getText().isEmpty() || InputDescriptionItems.getText().isEmpty() ||
                comboboxCategoryItems.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Please fill in all the field");
            alert.setTitle("Message");
            alert.showAndWait();
        }
    }

    public void goSimpleReport(ActionEvent actionEvent) {
        SampleThreadSimple sts = new SampleThreadSimple();
        ExecutorService exService = Executors.newCachedThreadPool();
        exService.execute(sts);
        exService.shutdown();
    }

    public void goGroupReport(ActionEvent actionEvent) {
        SampleThreadGroup stg = new SampleThreadGroup();
        ExecutorService exService = Executors.newCachedThreadPool();
        exService.execute(stg);
        exService.shutdown();
    }
}
