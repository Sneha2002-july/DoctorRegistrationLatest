package com.HIS.Controller;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Image;
import java.net.URL;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;

import com.HIS.model.Doctor;
import com.HIS.view.DoctorProfilePanel;

class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private DoctorTableModel tableModel;
    private Doctor doctor;
    private DoctorProfilePanel editorPanel;
    private CardLayout cardLayout;
    private JPanel container;
    private JTable doctorTable;

    public ButtonEditor(JCheckBox checkBox,DoctorTableModel tableModel,CardLayout cardLayout,JPanel container, JTable doctorTable) {
        super(checkBox);
        
     // Load and resize the icon
        URL imageUrl = getClass().getResource("/pencil-revision-svgrepo-com-ezgif.com-png-to-jpg-converter.jpg");
		
		ImageIcon originalIcon = new ImageIcon(imageUrl);  // Path to the icon
        Image resizedImage = originalIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        button = new JButton(resizedIcon);
     //   button.setSize(1,0); 
        button.setOpaque(true);
//        button.setContentAreaFilled(false); // Optional: Transparent background
        button.setBorderPainted(false);     // Optional: Remove border
        
        this.tableModel=tableModel;
        this.cardLayout=cardLayout;
        this.container=container;
        this.doctorTable=doctorTable;
        button.addActionListener(e->updateDoctorProfile());
    }
    private void updateDoctorProfile() {
    	int row=doctorTable.getSelectedRow();
    	doctor=tableModel.getDoctorAt(row);
    	
    	editorPanel=new DoctorProfilePanel(cardLayout,container,tableModel,doctor);
    	container.add(editorPanel,"Doctor Editor Panel");
    	cardLayout.show(container,"Doctor Editor Panel");
    }
    //
     public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        
        return button;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        
        return button;
    }

    
}