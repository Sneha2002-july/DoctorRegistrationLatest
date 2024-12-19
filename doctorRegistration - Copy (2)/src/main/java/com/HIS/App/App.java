package com.HIS.App;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.HIS.Controller.DoctorTableModel;
import com.HIS.view.BrowserPanel;
import com.HIS.view.DoctorProfilePanel;
public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        	try {
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception e) {
                System.err.println("Nimbus Look and Feel not applied. Falling back to default.");
            }
            JFrame frame = new JFrame("Doctor Management System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setResizable(true);
            CardLayout cardLayout = new CardLayout();
            JPanel container = new JPanel(cardLayout);
            DoctorTableModel tableModel = new DoctorTableModel();
            final JTable table=new JTable(tableModel);
            tableModel.setupTableButtons(table, cardLayout, container);
            DoctorProfilePanel profilePanel = new DoctorProfilePanel(cardLayout, container, tableModel);
            BrowserPanel browserPanel = new BrowserPanel(cardLayout, container, tableModel,table);
            container.add(profilePanel, "Profile");
            container.add(browserPanel, "Browser");
            frame.add(container);
            frame.setVisible(true);
        });
    }
}
