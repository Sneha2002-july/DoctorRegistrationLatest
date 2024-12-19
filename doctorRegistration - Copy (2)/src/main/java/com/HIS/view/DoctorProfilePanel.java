package com.HIS.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import com.HIS.Controller.DoctorTableModel;
import com.HIS.model.Doctor;
import com.toedter.calendar.JDateChooser;

public class DoctorProfilePanel extends JPanel {
	protected JTextField nameField,lnameField, contactField, emailField, addressField,adressField2,addressField3,pinField, ageField;
	protected JTextField idFeild;//
	protected JComboBox<String> genderDropDown;
	protected JComboBox<String> stateDropDown;
	protected JComboBox<String> qualificationDropDown;
	protected JComboBox<String> departmentDropdown, specializationDropdown;
	protected JSpinner startTimeSpinner, endTimeSpinner, availableFromDaySpinner, availableToDaySpinner;
	protected JDateChooser dobPicker;
	protected JCheckBox[] dayCheckBoxes;
	protected JTextField consultationFeeField;
	protected JButton saveButton,updateButton;
	JLabel header;
	protected final Map<String,List<String>>departmentSpecialisationMap=new HashMap<>();

	public DoctorProfilePanel(CardLayout cardLayout, JPanel container, DoctorTableModel tableModel) {
		setLayout(new BorderLayout(5, 5));
		setBackground(Color.WHITE);
		//updating...
		header = new JLabel("Doctor Registration", JLabel.CENTER);
		header.setOpaque(true);
		header.setBackground(new Color(0,150,139));
		header.setForeground(Color.WHITE);
		header.setFont(new Font("Arial", Font.BOLD, 20));
		add(header, BorderLayout.NORTH);

		initializeDepartmentSpecializationMap(); 


		JPanel mainFormPanel = new JPanel(new GridBagLayout());
		mainFormPanel.setBackground(Color.WHITE);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 2,2,2);

		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		//personal details panel

		JPanel personalDetailsPanel=createSectionPanel("Personal Details");
		personalDetailsPanel.setBackground(Color.WHITE);



		Border emptyBorder = BorderFactory.createEmptyBorder(0,470,0,300);
		//(0,300,0,300)
		Border titledBorder =BorderFactory.createTitledBorder("Personal Details");
		personalDetailsPanel.setBorder(BorderFactory.createCompoundBorder(titledBorder, emptyBorder));

		idFeild=new JTextField();
		idFeild.setVisible(false);
		idFeild.setEditable(false);
		idFeild.setBorder(BorderFactory.createEmptyBorder());
		Font boldFont=new Font("Arial",Font.BOLD,16);
		idFeild.setFont(boldFont);
		nameField = new JTextField(5);
		lnameField=new JTextField(5);
		genderDropDown=new JComboBox<>(new String[] {"Male","Female"});
		dobPicker = new JDateChooser();
		ageField = new JTextField(7);
		ageField.setEditable(false);  
		dobPicker.getDateEditor().addPropertyChangeListener("date", evt ->{
			Date selectedDate= dobPicker.getDate();
			if(selectedDate!=null) {
				LocalDate dob=selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				int age=calculateAge(dob);
				ageField.setText(String.valueOf(age));
			}
			else {
				ageField.setText("");
			}
		});
		addSectionField(personalDetailsPanel,"",idFeild,null,null,gbc,0);
		addSectionField(personalDetailsPanel,"First Name : *",nameField,"Last Name: *",lnameField,gbc,1);
		addSectionField(personalDetailsPanel, "Date of Birth: *", dobPicker, "Age: *", ageField, gbc, 2);
		addSectionField(personalDetailsPanel, "Gender: *", genderDropDown, null, null, gbc, 3);

		// contact details

		JPanel contactDetailsPanel = createSectionPanel("Contact Details");
		contactDetailsPanel.setBackground(Color.WHITE);
		Border emptyBorders = BorderFactory.createEmptyBorder(0,450,0,300);  //(0,0,0,680)
		Border titledBorders = BorderFactory.createTitledBorder("Contact Details");
		contactDetailsPanel.setBorder(BorderFactory.createCompoundBorder(titledBorders, emptyBorders));
		contactField = new JTextField(7);
		emailField = new JTextField(7);
		addressField = new JTextField(7);
		adressField2 = new JTextField(7);
		addressField3 = new JTextField(7);
		pinField = new JTextField(7);
		stateDropDown=new JComboBox<>(new String[] {"Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh", "Goa", "Gujarat", "Haryana", 
				"Himachal Pradesh", "Jharkhand", "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", 
				"Odisha", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal"});

		addSectionField(contactDetailsPanel, "Contact: *", contactField, "Email: *", emailField, gbc, 0);
		addSectionField(contactDetailsPanel, "Address Line 1: *", addressField, "Address Line 2:", adressField2, gbc, 1);
		addSectionField(contactDetailsPanel, "Address Line 3:", addressField3, "Pincode: *", pinField, gbc, 2);
		addSectionField(contactDetailsPanel, "State :", stateDropDown,null,null, gbc, 3);



		// ADDITIONAL DETAILS PANEL
		JPanel additionalDetailsPanel = createSectionPanel("Additional Details");
		additionalDetailsPanel.setBackground(Color.WHITE);
		Border emptyBordered = BorderFactory.createEmptyBorder(0,450,0,300);
		Border titledBordered = BorderFactory.createTitledBorder("Additional Details");
		additionalDetailsPanel.setBorder(BorderFactory.createCompoundBorder(titledBordered, emptyBordered));
		additionalDetailsPanel.setSize(200,400);
		qualificationDropDown=new JComboBox<>(new String[] {
				"MBBS,MD","MBBS,MS","MBBS,DNB","MBBS,MCh","MD,DM","MBBS,FRCS","MBBS,MD,FRCP","MBBS,DO"
		});

		departmentDropdown = new JComboBox<>(departmentSpecialisationMap.keySet().toArray(new String[0]));
		specializationDropdown = new JComboBox<>();

		updateSpecializations();

		departmentDropdown.addActionListener(e->updateSpecializations());

		startTimeSpinner = new JSpinner(new SpinnerDateModel());
		endTimeSpinner = new JSpinner(new SpinnerDateModel());

		// Format spinners
		startTimeSpinner.setEditor(new JSpinner.DateEditor(startTimeSpinner, "HH:mm"));
		endTimeSpinner.setEditor(new JSpinner.DateEditor(endTimeSpinner, "HH:mm"));


		String[] days= {"Mon", "Tue", "Wed", "Thur", "Fri", "Sat", "Sun"};
		dayCheckBoxes=new JCheckBox[days.length];
		JPanel daysPanel=new JPanel(new FlowLayout(FlowLayout.CENTER,2,2));
		for(int i=0;i<days.length;i++) {
			dayCheckBoxes[i]=new JCheckBox(days[i]);
			daysPanel.add(dayCheckBoxes[i]);
		}
		//consultation fee
		consultationFeeField=new JTextField(3);
		// Add fields to the form


		addSectionField(additionalDetailsPanel, "Qualification: *", qualificationDropDown, null,null, gbc, 0);
		addSectionField(additionalDetailsPanel, "Department: *", departmentDropdown,"Speciality: *", specializationDropdown,  gbc, 1);
		addSectionField(additionalDetailsPanel,"Consultation Start Time: *", startTimeSpinner, "Consultation Endtime: *", endTimeSpinner,gbc, 2);
		addSectionField(additionalDetailsPanel, "Consulting Days: *", daysPanel, "Consultation Fee: *", consultationFeeField, gbc, 3);
		addSectionField(additionalDetailsPanel, "",new JLabel(""), "", new JLabel(""), gbc, 4);


		// Buttons for Save and Clear
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		buttonPanel.setBackground(Color.WHITE);
		saveButton = new JButton("Save");
		saveButton.setBackground(new Color(0,150,139));
		updateButton = new JButton("Update");
		updateButton.setBackground(new Color(0,150,139));
		saveButton.addActionListener(e -> saveDoctorProfile(tableModel));
		JButton clearButton = new JButton("Clear");
		clearButton.setBackground(new Color(0,150,139));
		clearButton.addActionListener(e -> clearFields());
		buttonPanel.add(saveButton);
		buttonPanel.add(clearButton);
		buttonPanel.add(updateButton);
		updateButton.setVisible(false);
		saveButton.setVisible(true);

		mainFormPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		// Add Sections to Main Form Panel
		gbc.gridy = 0;
		mainFormPanel.add(personalDetailsPanel, gbc);
		gbc.gridy++;
		mainFormPanel.add(contactDetailsPanel, gbc);
		gbc.gridy++;
		mainFormPanel.add(additionalDetailsPanel, gbc);
		gbc.gridy++;
		mainFormPanel.add(buttonPanel, gbc);

		add(mainFormPanel, BorderLayout.CENTER);

		JButton goToBrowserButton = new JButton("Go to Browser Page");
		goToBrowserButton.setBackground(new Color(0,153,139));
		goToBrowserButton.addActionListener(e -> cardLayout.show(container, "Browser"));

		add(goToBrowserButton, BorderLayout.SOUTH);
	}

	public DoctorProfilePanel(CardLayout cardLayout, JPanel container, DoctorTableModel tableModel,Doctor doctor) {
		this(cardLayout,container,tableModel);
		updateButton.setVisible(true);
		saveButton.setVisible(false);
		populateFields(doctor);
		updateButton.addActionListener(e-> updateDoctorProfile(tableModel,doctor));
	}

	public void initializeDepartmentSpecializationMap() {
		departmentSpecialisationMap.put("Cardiology",Arrays.asList("Pediatric Cardiology","Electrophysiology"));
		departmentSpecialisationMap.put("Dermatology",Arrays.asList("Cosmetic Dermatology","Pediatric Dermatology"));
		departmentSpecialisationMap.put("Neurology",Arrays.asList("Neuroimunology","Epileptologist","Pediatric Neurology"));
		departmentSpecialisationMap.put("Pediatrics",Arrays.asList("Pediatric Cardiology","Pediatric Neurology","Pediatric Opthamology"));
		departmentSpecialisationMap.put("Opthamology",Arrays.asList("Pediatric Opthamologist","Retina specialist"));
	}
	private void updateSpecializations() {
		String selectedDepartment=(String)departmentDropdown.getSelectedItem();
		List <String> specializations=departmentSpecialisationMap.getOrDefault(selectedDepartment, Collections.emptyList());
		specializationDropdown.removeAllItems();
		for(String specialization:specializations) {
			specializationDropdown.addItem(specialization);
		}

	}
	private JPanel createSectionPanel(String title) {
		JPanel sectionPanel = new JPanel(new GridBagLayout());
		sectionPanel.setBackground(Color.WHITE);

		sectionPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.GRAY), title,
				TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP, new Font("Arial", Font.BOLD, 14)));
		if(title.equals("Additional Details")) {
			sectionPanel.setPreferredSize(new java.awt.Dimension(200,500));
		}
		else {
			sectionPanel.setPreferredSize(new java.awt.Dimension(200,500));
		}
		sectionPanel.setSize(300,500);
		return sectionPanel;
	}

	private void addSectionField(JPanel panel, String label1, JComponent field1, String label2, JComponent field2, GridBagConstraints gbc, int row) {
		// Minimal insets to remove padding
		gbc.insets = new Insets(5, 5, 5, 5); // Adjust insets for better spacing
		gbc.ipadx = 5; // Add some internal padding
		gbc.ipady = 5;

		// Ensure uniform alignment for both labels and fields
		gbc.anchor = GridBagConstraints.EAST;

		Dimension feildSize=new Dimension(100,25);

		// Label 1
		gbc.gridx = 0;
		gbc.gridy = row;
		gbc.weightx = 0.5; // No additional space for label
		panel.add(new JLabel(label1), gbc);

		// Field 1
		gbc.gridx = 1;
		gbc.weightx = 2.5; // Field takes horizontal space
		field1.setPreferredSize(feildSize); // Set preferred field size
		panel.add(field1, gbc);

		if (label2 != null && field2 != null) {
			// Label 2
			gbc.gridx = 2;
			gbc.weightx = 0.5; // Label doesn't expand
			panel.add(new JLabel(label2), gbc);

			// Field 2
			gbc.gridx = 3;
			gbc.weightx = 2.5;
			field2.setPreferredSize(feildSize); // Fixed field size
			panel.add(field2, gbc);
		}
	}    

	protected void saveDoctorProfile(DoctorTableModel tableModel) {
		String name = nameField.getText().trim();
		String lname = lnameField.getText().trim();
		String gender = (String) genderDropDown.getSelectedItem();
		String contact = contactField.getText().trim();
		String email = emailField.getText().trim();
		String address = addressField.getText().trim();
		String adress2 = adressField2.getText().trim();
		String adress3 = addressField3.getText().trim();
		String pincode = pinField.getText().trim();
		String state=(String)stateDropDown.getSelectedItem();
		String ageText = ageField.getText().trim();
		String qualification = (String) qualificationDropDown.getSelectedItem();
		String department = (String) departmentDropdown.getSelectedItem();
		String specialization = (String) specializationDropdown.getSelectedItem();
		Date startTime = (Date) startTimeSpinner.getValue();
		Date endTime = (Date) endTimeSpinner.getValue();
		List<String> availableDays = new ArrayList<>();
		
		double consultationFee;

		LocalDate dob = dobPicker.getDate() != null
				? dobPicker.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
						: null;

		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		String formattedStartTime = timeFormat.format(startTime);
		String formattedEndTime = timeFormat.format(endTime);

		if (name.isEmpty() || lname.isEmpty() || contact.isEmpty() || email.isEmpty() || address.isEmpty() || ageText.isEmpty() ||
				qualification.isEmpty() || dob == null) {
			JOptionPane.showMessageDialog(this, "Please fill all fields!", "Validation Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (!contact.matches("\\d{10}")) {
			JOptionPane.showMessageDialog(this, "Contact must be a 10 digit number!", "Validation Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (!email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
			JOptionPane.showMessageDialog(this, "Please enter a valid email address!", "Validation Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(!pincode.matches("\\d{6}")) {
			JOptionPane.showMessageDialog(this, "Pincode must be a 6 digit number!", "Validation Error", JOptionPane.ERROR_MESSAGE);
			return;

		}
		LocalTime startLocalTime = LocalTime.ofInstant(startTime.toInstant(), ZoneId.systemDefault());
		LocalTime endLocalTime = LocalTime.ofInstant(endTime.toInstant(), ZoneId.systemDefault());

		if (startLocalTime.isAfter(endLocalTime) || startLocalTime.equals(endLocalTime)) {
			JOptionPane.showMessageDialog(this, "End time must be greater than start time!", "Validation Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		for (JCheckBox checkBox : dayCheckBoxes) {
			if (checkBox.isSelected()) {
				availableDays.add(checkBox.getText());
			}
		}
		if (availableDays.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please select at least one available day", "Validation Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {
			int age = calculateAge(dob);
			if (age <= 0) {
				JOptionPane.showMessageDialog(this, "Age must be greater than 0!", "Validation Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			try {
				consultationFee = Double.parseDouble(consultationFeeField.getText().trim());
				if (consultationFee < 0) {
					JOptionPane.showMessageDialog(this, "Consultation fee must be non-negative", "Validation Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Consultation fee must be a valid number", "Validation Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			Doctor doctor = new Doctor(name, lname, gender, contact, email, address, adress2, adress3, pincode,state, dob, age, department, specialization,
					qualification, formattedStartTime, formattedEndTime, availableDays, consultationFee);
			tableModel.addDoctor(doctor);
			JOptionPane.showMessageDialog(this, "Doctor Profile Saved Successfully!");
			clearFields();
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Age must be a valid number!", "Validation Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	protected void addDoctorProfile(DoctorTableModel tableModel) {
		String name = nameField.getText().trim();
		String lname = lnameField.getText().trim();
		String gender = (String) genderDropDown.getSelectedItem();
		String contact = contactField.getText().trim();
		String email = emailField.getText().trim();
		String address = addressField.getText().trim();
		String adress2 = adressField2.getText().trim();
		String adress3 = addressField3.getText().trim();
		String pincode = pinField.getText().trim();
		String state=(String)stateDropDown.getSelectedItem();
		String ageText = ageField.getText().trim();
		String qualification = (String) qualificationDropDown.getSelectedItem();
		String department = (String) departmentDropdown.getSelectedItem();
		String specialization = (String) specializationDropdown.getSelectedItem();
		Date startTime = (Date) startTimeSpinner.getValue();
		Date endTime = (Date) endTimeSpinner.getValue();
		List<String> availableDays = new ArrayList<>();
		for (JCheckBox checkBox : dayCheckBoxes) {
			if (checkBox.isSelected()) {
				availableDays.add(checkBox.getText());
			}
		}
		double consultationFee;
		LocalDate dob = dobPicker.getDate() != null
				? dobPicker.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
						: null;

		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		String formattedStartTime = timeFormat.format(startTime);
		String formattedEndTime = timeFormat.format(endTime);

		LocalTime startLocalTime = LocalTime.ofInstant(startTime.toInstant(), ZoneId.systemDefault());
		LocalTime endLocalTime = LocalTime.ofInstant(endTime.toInstant(), ZoneId.systemDefault());





		if (name.isEmpty() || lname.isEmpty() || contact.isEmpty() || email.isEmpty() || address.isEmpty() || ageText.isEmpty() ||
				qualification.isEmpty() || dob == null) {
			JOptionPane.showMessageDialog(this, "Please fill all fields!", "Validation Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (!contact.matches("\\d{10}")) {
			JOptionPane.showMessageDialog(this, "Contact must be a 10 digit number!", "Validation Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if(!pincode.matches("\\d{6}")) {
			JOptionPane.showMessageDialog(this, "Pincode must be a 6 digit number!", "Validation Error", JOptionPane.ERROR_MESSAGE);
			return;

		}

		if (startLocalTime.isAfter(endLocalTime) || startLocalTime.equals(endLocalTime)) {
			JOptionPane.showMessageDialog(this, "End time must be greater than start time!", "Validation Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (!email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
			JOptionPane.showMessageDialog(this, "Please enter a valid email address!", "Validation Error", JOptionPane.ERROR_MESSAGE);
			return;
		}


		if (availableDays.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please select at least one available day", "Validation Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		try {
			int age = calculateAge(dob);
			if (age <= 0) {
				JOptionPane.showMessageDialog(this, "Age must be greater than 0!", "Validation Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			try {
				consultationFee = Double.parseDouble(consultationFeeField.getText().trim());
				if (consultationFee < 0) {
					JOptionPane.showMessageDialog(this, "Consultation fee must be non-negative", "Validation Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Consultation fee must be a valid number", "Validation Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			Doctor doctor = new Doctor(name, lname, gender, contact, email, address, adress2, adress3, pincode,state, dob, age, department, specialization,
					qualification, formattedStartTime, formattedEndTime, availableDays, consultationFee);
			tableModel.addDoctor(doctor);
			JOptionPane.showMessageDialog(this, "Doctor Profile Saved Successfully!");
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Age must be a valid number!", "Validation Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void clearFields() {
		nameField.setText("");
		genderDropDown.setSelectedIndex(0);
		lnameField.setText("");
		contactField.setText("");
		emailField.setText("");
		addressField.setText("");
		adressField2.setText("");
		addressField3.setText("");
		pinField.setText("");
		stateDropDown.setSelectedIndex(0);
		ageField.setText("");
		qualificationDropDown.setSelectedIndex(0);
		departmentDropdown.setSelectedIndex(0);
		specializationDropdown.setSelectedIndex(0);
		startTimeSpinner.setValue(new SpinnerDateModel().getDate());
		endTimeSpinner.setValue(new SpinnerDateModel().getDate());        
		consultationFeeField.setText("");
		for(JCheckBox checkBox:dayCheckBoxes) {
			checkBox.setSelected(false);
		}
	}
	private int calculateAge(LocalDate dob) {
		LocalDate currentDate=LocalDate.now();
		if(dob!=null&& dob.isBefore(currentDate)) {
			return Period.between(dob,currentDate).getYears();
		}
		return 0;
	}
	private void populateFields(Doctor doctor) {
		idFeild.setText("Doctor ID : "+new Integer(doctor.getId()).toString());
		idFeild.setVisible(true);
		header.setText("Doctor Updation"); //upda
		nameField.setText(doctor.getName());
		lnameField.setText(doctor.getLname());
		genderDropDown.setSelectedItem(doctor.getGender());
		contactField.setText(doctor.getContact());
		emailField.setText(doctor.getEmail());
		addressField.setText(doctor.getAddress());
		adressField2.setText(doctor.getAdress2());
		addressField3.setText(doctor.getAdress3());
		pinField.setText(doctor.getPincode());
		stateDropDown.setSelectedItem(doctor.getState());
		qualificationDropDown.setSelectedItem(doctor.getQualification());
		departmentDropdown.setSelectedItem(doctor.getDepartment());
		specializationDropdown.setSelectedItem(doctor.getSpecialization());
		dobPicker.setDate(Date.from(doctor.getDob().atStartOfDay(ZoneId.systemDefault()).toInstant()));
		consultationFeeField.setText(String.valueOf(doctor.getConsultationFee()));
		// Populate available days
		for (JCheckBox checkBox : dayCheckBoxes) {
			checkBox.setSelected(doctor.getAvailableDays().contains(checkBox.getText()));
		}
		try {
			startTimeSpinner.setValue(new SimpleDateFormat("HH:mm").parse(doctor.getStartTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			endTimeSpinner.setValue(new SimpleDateFormat("HH:mm").parse(doctor.getEndTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	protected void updateDoctorProfile(DoctorTableModel tableModel,Doctor doctor) {
		//saveDoctorProfile(tableModel);
		// Update the existing doctor in the list
		String name = nameField.getText();
		String lname = lnameField.getText();
		String gender = (String) genderDropDown.getSelectedItem();
		String contact = contactField.getText();
		String email = emailField.getText();
		String address = addressField.getText();
		String adress2 = adressField2.getText();
		String adress3 = addressField3.getText();
		String pincode = pinField.getText();
		String state=(String)stateDropDown.getSelectedItem();
		String ageText = ageField.getText();
		Double consultationFee = null;
		
		List<String> availableDays = new ArrayList<String>();
		for (JCheckBox checkBox : dayCheckBoxes) {
			boolean isDayAvailable = doctor.getAvailableDays().contains(checkBox.getText());
			checkBox.setSelected(isDayAvailable);
			if(isDayAvailable) {
				availableDays.add(checkBox.getText());
			}
		}
		int age = 0;
		LocalDate dob=dobPicker.getDate() != null
				? dobPicker.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
						: null;
		String qualification = (String) qualificationDropDown.getSelectedItem();
		String department = (String) departmentDropdown.getSelectedItem();
		String specialization = (String) specializationDropdown.getSelectedItem();
		Date startTime = (Date) startTimeSpinner.getValue();
		Date endTime = (Date) endTimeSpinner.getValue();

		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		String formattedStartTime = timeFormat.format(startTime);
		String formattedEndTime = timeFormat.format(endTime);

		LocalTime startLocalTime = LocalTime.ofInstant(startTime.toInstant(), ZoneId.systemDefault());
		LocalTime endLocalTime = LocalTime.ofInstant(endTime.toInstant(), ZoneId.systemDefault());

		if (name.isEmpty() || lname.isEmpty() || contact.isEmpty() || email.isEmpty() || address.isEmpty() || ageText.isEmpty() ||
                qualification.isEmpty() || dob== null) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!contact.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(this, "Contact must be a 10 digit number!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(!pincode.matches("\\d{6}")) {
        	JOptionPane.showMessageDialog(this, "Pincode must be a 6 digit number!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        	
        }
        
        if (startLocalTime.isAfter(endLocalTime) || startLocalTime.equals(endLocalTime)) {
            JOptionPane.showMessageDialog(this, "End time must be greater than start time!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        

        try {
            
       	 age = calculateAge(dob);
            if (age <= 0) {
                JOptionPane.showMessageDialog(this, "Age must be greater than 0!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                consultationFee = Double.parseDouble(consultationFeeField.getText().trim());
                if (consultationFee < 0) {
                    JOptionPane.showMessageDialog(this, "Consultation fee must be non-negative", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Consultation fee must be a valid number", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Age must be a valid number!", "Validation Error", JOptionPane.ERROR_MESSAGE);
        }

			if (availableDays.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Please select at least one available day", "Validation Error", JOptionPane.ERROR_MESSAGE);
				return;
			}




		doctor.setName(name);

		doctor.setLname(lname);
		doctor.setGender(gender);
		doctor.setAge(age);
		doctor.setContact(contact);

		doctor.setEmail(email);

		doctor.setAddress(address);
		doctor.setAdress2(adress2);
		doctor.setAdress3(adress3);
		doctor.setPincode(pincode);
		doctor.setState(state);
		doctor.setQualification(qualification);
		doctor.setDepartment(department);
		doctor.setSpecialization( specialization);
		doctor.setAvailableDays(availableDays);
		doctor.setDob(dobPicker.getDate().toInstant().atZone(ZoneId.systemDefault()).
				toLocalDate());

		doctor.setConsultationFee(consultationFee);

		doctor.setStartTime(formattedStartTime);
		doctor.setEndTime(formattedEndTime);


		JOptionPane.showMessageDialog(this, "Doctor Profile Updated Successfully!");
		tableModel.updateDoctor();


	}}