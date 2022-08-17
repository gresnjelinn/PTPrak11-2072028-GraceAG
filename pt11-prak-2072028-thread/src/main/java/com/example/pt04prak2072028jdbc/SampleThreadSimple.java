package com.example.pt04prak2072028jdbc;

import com.example.pt04prak2072028jdbc.util.MyConnection;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class SampleThreadSimple extends Thread {
    @Override
    public void run() {
        JasperPrint jp;
        Connection conn = MyConnection.getConnection();
        Map param = new HashMap();
        try {
            jp = JasperFillManager.fillReport("report/Items.jasper", param, conn);
            JasperViewer viewer = new JasperViewer(jp, false);
            viewer.setTitle("All Data Report");
            viewer.setVisible(true);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }
}
