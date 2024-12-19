package com.HIS.Controller;

import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

import com.HIS.model.Doctor;
import com.HIS.model.JsonHandler;

public class DoctorTableModel extends AbstractTableModel {
    private static ArrayList<Doctor> doctors;
    private ArrayList<Doctor> filteredDoctors;
    private final String[] columnNames = {"ID", "Name","Gender","Age", "Contact", "Email","Department", "Speciality","Qualification", "Consulation Time","Consulting Days","Consultation Fee"};
    private static JsonHandler jsonHandler;
   // private JButton edit = new JButton("Edit");
    static {
    	jsonHandler = new JsonHandler();
    	doctors = jsonHandler.readFromJson();
    }
    
    private final Map<String,String[]>departmentSpecializationMap=new HashMap<>();
    public DoctorTableModel() {
    	
    	this.filteredDoctors = jsonHandler.readFromJson();
    	Doctor.setIdCounter(filteredDoctors.size()+1);
    	if(doctors==null){
    		doctors = new ArrayList<>();
    	}
    	
    	if(filteredDoctors==null) {
    		this.filteredDoctors = new ArrayList<>();
    	}
    	departmentSpecializationMap.put("Cardiology", new String[] {"Pediatric Cardiology","Electrophysiology"});
    	departmentSpecializationMap.put("Dermatology",new String[] {"Cosmetic Dermatology","Pediatric Dermatology"});
    	departmentSpecializationMap.put("Neurology",new String[] {"Neuroimunology","Epileptologist","Pediatric Neurology"});
    	departmentSpecializationMap.put("Pediatrics",new String[] {"Pediatric Cardiology","Pediatric Neurology","Pediatric Opthamology"});
    	departmentSpecializationMap.put("Opthamology",new String[] {"Pediatric Opthamologist","Retina specialist"});
    }
    public String[] getDepartments() {
    	return departmentSpecializationMap.keySet().toArray(new String[0]);
    }
    public String[] getSpecializationsByDepartment(String department) {
    	return departmentSpecializationMap.get(department);
    }
    
    public void filterBySpecialization(String query) {
        filteredDoctors.clear();
        for (Doctor doctor : doctors) {
            if (doctor.getSpecialization().toLowerCase().contains(query.toLowerCase())) {
                filteredDoctors.add(doctor);
            }
        }
        fireTableDataChanged();
    }

    
    public int getSelectedRow(JTable table) {
    	return table.getSelectedRow();
    	
    }
    public Doctor getDoctorAt(int rowIndex) {
    	if(rowIndex>=0 && rowIndex<filteredDoctors.size()) {
    		return filteredDoctors.get(rowIndex);
    	}
    	return null;
    	
    }
    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
        jsonHandler.writeToJson(doctors);
        filteredDoctors.add(doctor);
        fireTableDataChanged();
    }
    public void filterByName(String query) {
        filteredDoctors.clear();
        for (Doctor doctor : doctors) {
            if (doctor.getName().toLowerCase().contains(query)) {
                    
                filteredDoctors.add(doctor);
            }
        }
        fireTableDataChanged();
    }
    
    
    public void filterByDepartment(String query) {
        filteredDoctors.clear();
        for (Doctor doctor : doctors) {
            if (doctor.getDepartment().toLowerCase().contains(query)) {
                    
                filteredDoctors.add(doctor);
            }
        }
        fireTableDataChanged();
    }
    
    public void filterByNameAndDepartment(String nameQuery, String departmentQuery) {
        filteredDoctors.clear();
        for (Doctor doctor : doctors) {
            if (doctor.getName().toLowerCase().contains(nameQuery) &&
                doctor.getDepartment().toLowerCase().contains(departmentQuery)) {
                filteredDoctors.add(doctor);
            }
        }
        fireTableDataChanged();
    }

    public void resetFilter() {
        filteredDoctors.clear();
        filteredDoctors.addAll(doctors);
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return filteredDoctors.size();
    }
    @Override
    public int getColumnCount() {               //for update
        return columnNames.length+1;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	
        Doctor doctor = filteredDoctors.get(rowIndex);
        switch (columnIndex) {
            case 0:return doctor.getId();
            case 1: return String.format("%s %s",doctor.getName(),doctor.getLname() ) ;
            
            case 2:return doctor.getGender();
            case 4: return doctor.getContact();
            case 5: return doctor.getEmail();
            
            case 3: return doctor.getAge();
            case 6: return doctor.getDepartment();
            case 7: return doctor.getSpecialization();
            case 8: return doctor.getQualification();
            case 9: return String.format("%s - %s", doctor.getStartTime(),doctor.getEndTime() );
            
            case 10: return doctor.getAvailableDays(); 
            case 11:return doctor.getConsultationFee();
            
            default: return null;
        }
    }
   public boolean isCellEditable(int rowIndex, int columnIndex) {   //for update
	        return columnIndex==12;
	   
   }
   public void updateDoctor(Doctor doctor) {         //for update
	    int index = doctors.indexOf(doctor);
	    if (index != -1) {
	        doctors.set(index, doctor);
	        jsonHandler.writeToJson(doctors); // Save changes
	        fireTableDataChanged();
	    }
	}
   public void updateDoctor() {         //for update
	   	jsonHandler.writeToJson(filteredDoctors);
	        fireTableDataChanged();
	}

    @Override
    public String getColumnName(int column) {
    	if (column==columnNames.length) {         //update
    		return "Update";
    	}
        return columnNames[column];
    }
    //update
    public void setupTableButtons(JTable table, CardLayout cardLayout, JPanel container) {
    	TableColumn column=table.getColumnModel().getColumn(12);
        
        ButtonEditor buttonEditor = new ButtonEditor(new JCheckBox(),this,cardLayout,container,table);
        column.setCellEditor(buttonEditor);
        ButtonRenderer buttonRenderer = new ButtonRenderer();
        column.setCellRenderer(buttonRenderer);
        column.setPreferredWidth(20);
       
    }
    public void filterDoctors(String nameQuery, String departmentQuery, String specializationQuery) {
        filteredDoctors.clear();

        for (Doctor doctor : doctors) {
            boolean matchesName = nameQuery.isEmpty() || doctor.getName().toLowerCase().contains(nameQuery);
            boolean matchesDepartment = departmentQuery == null || departmentQuery.isEmpty()
                    || doctor.getDepartment().equalsIgnoreCase(departmentQuery);
            boolean matchesSpecialization = specializationQuery == null || specializationQuery.isEmpty()
                    || doctor.getSpecialization().equalsIgnoreCase(specializationQuery);

            // Include the doctor only if all conditions are true
            if (matchesName && matchesDepartment && matchesSpecialization) {
                filteredDoctors.add(doctor);
            }
        }
        fireTableDataChanged();
    }
}