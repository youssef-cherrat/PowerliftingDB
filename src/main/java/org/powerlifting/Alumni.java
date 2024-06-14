package org.powerlifting;

public class Alumni {
    private int Alumni_ID;
    private String First_Name;
    private String Last_Name;
    private int Class_Year;
    private String Email;
    private int Semester_ID;

    public Alumni(int alumni_ID, String first_Name, String last_Name, int class_Year, String email, int semester_ID) {
        Alumni_ID = alumni_ID;
        First_Name = first_Name;
        Last_Name = last_Name;
        Class_Year = class_Year;
        Email = email;
        Semester_ID = semester_ID;
    }

    // Getters and setters

    public int getAlumni_ID() {
        return Alumni_ID;
    }

    public void setAlumni_ID(int alumni_ID) {
        Alumni_ID = alumni_ID;
    }

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

    public int getClass_Year() {
        return Class_Year;
    }

    public void setClass_Year(int class_Year) {
        Class_Year = class_Year;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getSemester_ID() {
        return Semester_ID;
    }

    public void setSemester_ID(int semester_ID) {
        Semester_ID = semester_ID;
    }
}
