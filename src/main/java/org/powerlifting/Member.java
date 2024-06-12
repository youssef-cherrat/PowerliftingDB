package org.powerlifting;

public class Member {
    private String First_Name;
    private String Last_Name;
    private String Gender;
    private String Email;
    private int Total_Practices_Attended;


    public Member(String first_Name, String last_Name, String gender, String email, int total_Practices_Attended) {
        First_Name = first_Name;
        Last_Name = last_Name;
        Gender = gender;
        Email = email;
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
}