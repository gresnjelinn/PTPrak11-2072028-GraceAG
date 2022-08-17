package com.example.pt04prak2072028jdbc.dao;

import javafx.collections.ObservableList;

public interface DaoInterface<T> {
    ObservableList<T> getData();
    int insertData(T data);
    int deleteData(T data);
    int updateData(T data);
}
