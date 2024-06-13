package org.powerlifting;

import java.sql.Date;

public class Member {
    private int Member_ID;
    private String First_Name;
    private String Last_Name;
    private String Gender;
    private String Email;
    private int Total_Practices_Attended;
    private Date Date_of_Birth;
    private Date Grad_Date;
    private float Weight_Class;
    private float Best_Total_KG;
    private String Password_Hash;
    private int Semester_ID;

    //for add member
    public Member(int semester_ID, String first_Name, String last_Name, Date date_of_Birth, Date grad_Date, float weight_Class, float best_Total_KG, String gender, String email) {
        Semester_ID = semester_ID;
        First_Name = first_Name;
        Last_Name = last_Name;
        Date_of_Birth = date_of_Birth;
        Grad_Date = grad_Date;
        Weight_Class = weight_Class;
        Best_Total_KG = best_Total_KG;
        Gender = gender;
        Email = email;
    }
    public Member(String first_Name, String last_Name, String gender, String email, float weight_Class, float best_Total_KG) {
        First_Name = first_Name;
        Last_Name = last_Name;
        Gender = gender;
        Email = email;
        Weight_Class = weight_Class;
        Best_Total_KG = best_Total_KG;
    }

    //for displaying member detail screen
    public Member(String first_Name, String last_Name, String gender, String email, float weight_Class, float best_Total_KG, int total_Practices_Attended) {
        First_Name = first_Name;
        Last_Name = last_Name;
        Gender = gender;
        Email = email;
        Weight_Class = weight_Class;
        Best_Total_KG = best_Total_KG;
        Total_Practices_Attended = total_Practices_Attended;
    }

    public Member() {}

    public String getFirst_Name() {
        return First_Name;
    }

    public void setFirst_Name(String first_Name) {
        First_Name = first_Name;
    }

    public String getLast_Name() {
        return Last_Name;
    }

    public void setLast_Name(String last_Name) {
        Last_Name = last_Name;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getTotal_Practices_Attended() {
        return Total_Practices_Attended;
    }

    public void setTotal_Practices_Attended(int total_Practices_Attended) {
        Total_Practices_Attended = total_Practices_Attended;
    }
    public Date getDate_of_Birth() {
        return Date_of_Birth;
    }
    public void setDate_of_Birth(Date date_of_Birth) {
        Date_of_Birth = date_of_Birth;
    }
    public Date getGrad_Date() {
        return Grad_Date;
    }
    public void setGrad_Date(Date grad_Date) {
        Grad_Date = grad_Date;
    }
    public float getWeight_Class() {
        return Weight_Class;
    }
    public void setWeight_Class(float weight_Class) {
        Weight_Class = weight_Class;
    }
    public float getBest_Total_KG() {
        return Best_Total_KG;
    }
    public void setBest_Total_KG(float best_Total_KG) {
        Best_Total_KG = best_Total_KG;
    }
    public int getSemester_ID() {
        return Semester_ID;
    }
    public void setSemester_ID(int semester_ID) {
        Semester_ID = semester_ID;
    }

}