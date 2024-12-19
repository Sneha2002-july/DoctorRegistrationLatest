package com.HIS.model;

import java.time.LocalDate;
import java.util.List;
public class Doctor {
    private static int idCounter;  
    private int id;
    private String name;
    private String lname;
    private String gender;

	private String contact;
	private String email;
	private String address;
	private String adress2;
	private String adress3;
	private String pincode;
	private String state;
	private LocalDate dob;
    private int age;
    private String department;
    private String specialization;
    private String qualification;
    private String startTime;
    private String endTime;
    private List<String> availableDays;
    

	private double consultationFee;
    
	public Doctor( String name,String lname,String gender, String contact, String email, String address,String adress2,String adress3,String pincode,String state,LocalDate dob, int age,
			String department, String specialization, String qualification, String startTime, String endTime,
			List<String> availableDays,double consultationFee) {
		super();
		this.id = idCounter++;
		this.name = name;
		this.lname=lname;
		this.gender=gender;
		this.contact = contact;
		this.email = email;
		this.address = address;
		this.adress2=adress2;
		this.adress3=adress3;
		this.pincode=pincode;
		this.state=state;
		this.dob = dob;
		this.age = age;
		this.department = department;
		this.specialization = specialization;
		this.qualification = qualification;
		this.startTime = startTime;
		this.endTime = endTime;
		this.availableDays = availableDays;
		
		this.consultationFee=consultationFee;
	}
	public Doctor() {
		super();
	}
	public static int getIdCounter() {
		return idCounter;
	}
	public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public int getAge() {
        return age;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
    public static void setIdCounter(int idCounter) {
		Doctor.idCounter = idCounter;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public LocalDate getDob() {
		return dob;
	}
	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public List<String> getAvailableDays() {
		return availableDays;
	}
	public void setAvailableDays(List<String> availableDays) {
		this.availableDays = availableDays;
	}
	public double getConsultationFee() {
		return consultationFee;
	}
	public void setConsultationFee(double consultationFee) {
		this.consultationFee = consultationFee;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getAdress2() {
		return adress2;
	}
	public void setAdress2(String adress2) {
		this.adress2 = adress2;
	}
	public String getAdress3() {
		return adress3;
	}
	public void setAdress3(String adress3) {
		this.adress3 = adress3;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}


	public Object[] toArray() {
        return new Object[]{name,lname,gender,contact, email, address,adress2,adress3,pincode,state, dob, age, department, specialization,
                qualification, startTime, endTime,String.join(",", availableDays) ,consultationFee};
    }
}