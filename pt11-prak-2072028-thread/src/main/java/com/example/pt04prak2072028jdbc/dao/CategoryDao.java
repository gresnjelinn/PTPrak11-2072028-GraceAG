package com.example.pt04prak2072028jdbc.dao;

import com.example.pt04prak2072028jdbc.model.Category;
import com.example.pt04prak2072028jdbc.util.MyConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryDao implements DaoInterface<Category> {

    @Override
    public ObservableList<Category> getData() {
        ObservableList<Category> cList;
        cList = FXCollections.observableArrayList();

        Connection conn = MyConnection.getConnection();
        String kalimat_sql = "select * from category";
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(kalimat_sql);
            ResultSet result = ps.executeQuery();
            while(result.next()) {
                Category c = new Category();
                c.setId(result.getInt("id"));
                c.setName(result.getString("name"));
                cList.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cList;
    }

    @Override
    public int insertData(Category data) {
        Connection conn = MyConnection.getConnection();
        int hasil = 0;
        try {
            conn.setAutoCommit(false);
            String kalimat_sql = "INSERT INTO category VALUES(?, ?)";
            PreparedStatement ps;
            try {
                ps = conn.prepareStatement(kalimat_sql);
                ps.setInt(1, data.getId());
                ps.setString(2, data.getName());
                hasil = ps.executeUpdate();
                if (hasil > 0) {
                    System.out.println("Data berhasil di insert");
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return hasil;
    }

    @Override
    public int deleteData(Category data) {

        return 0;
    }

    @Override
    public int updateData(Category data) {

        return 0;
    }
}
