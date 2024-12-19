package com.HIS.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.HIS.Controller.DoctorTableModel;

public class BrowserPanel extends JPanel {
	JTable doctorTable;
    public BrowserPanel(CardLayout cardLayout, JPanel container, DoctorTableModel tableModel,JTable doctorTable) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createTitledBorder("Doctor Browser"));
        
        //JTable doctorTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(doctorTable);
        add(scrollPane, BorderLayout.CENTER);

        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchPanel.setBackground(new Color(0,150,139));
        searchPanel.add(new JLabel("Name:"));
        
        JTextField nameSearchField = new JTextField(15);
        searchPanel.add(nameSearchField);

        
        searchPanel.add(new JLabel("Department:"));
        JComboBox<String> departmentDropdown = new JComboBox<>();
        departmentDropdown.addItem(""); // Add empty item initially
        for (String department : tableModel.getDepartments()) {
            departmentDropdown.addItem(department);
        }
        searchPanel.add(departmentDropdown);
        
        searchPanel.add(new JLabel("Specialization:"));
        JComboBox<String> specializationDropdown = new JComboBox<>();
        specializationDropdown.setEnabled(false);
        searchPanel.add(specializationDropdown);
        
        
        
        // Populate specialization based on selected department
        departmentDropdown.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedDepartment = (String) e.getItem();
                specializationDropdown.removeAllItems();

                if (selectedDepartment == null || selectedDepartment.isEmpty()) {
                    specializationDropdown.setEnabled(false);
                } else {
                    String[] specializations = tableModel.getSpecializationsByDepartment(selectedDepartment);
                    if (specializations != null) {
                        specializationDropdown.setEnabled(true);
                        for (String specialization : specializations) {
                            specializationDropdown.addItem(specialization);
                        }
                    } else {
                        specializationDropdown.setEnabled(false);
                    }
                }
            }
        });
        JButton searchButton = new JButton("Search");
        searchButton.setBackground(new Color(255,255,255));
        searchButton.addActionListener(e -> {
            String nameQuery = nameSearchField.getText().trim().toLowerCase();
            String departmentQuery = (String) departmentDropdown.getSelectedItem();
            String specializationQuery = (String) specializationDropdown.getSelectedItem();

            if (!nameQuery.isEmpty()) {
                tableModel.filterByName(nameQuery);
            }

            if (departmentQuery != null) {
                tableModel.filterByDepartment(departmentQuery);
            }

            if (specializationQuery != null && specializationDropdown.isEnabled()) {
                tableModel.filterBySpecialization(specializationQuery);
            }
            tableModel.filterDoctors(nameQuery, departmentQuery, specializationQuery);
        });
        
        doctorTable.getColumnModel().getColumn(12).setMaxWidth(10);

        JButton resetButton = new JButton("Reset");
        resetButton.setBackground(new Color(255,255,255));
        resetButton.addActionListener(e -> {
        	nameSearchField.setText("");
        	departmentDropdown.setSelectedIndex(0);
        	specializationDropdown.setEnabled(false);
        	specializationDropdown.removeAllItems();
            tableModel.resetFilter();
        });
     
        searchPanel.add(searchButton);
        searchPanel.add(resetButton);
        add(searchPanel, BorderLayout.NORTH);

        JButton goToProfileButton = new JButton("Go to Profile Page");
        goToProfileButton.setBackground(new Color(0,150,139));
        goToProfileButton.addActionListener(e -> cardLayout.show(container, "Profile"));
        add(goToProfileButton, BorderLayout.SOUTH);
        
    }
}