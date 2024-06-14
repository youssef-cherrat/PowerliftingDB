package org.powerlifting;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PowerliftingService {
    private DatabaseDriver dbDriver;

    public PowerliftingService(DatabaseDriver dbDriver) {
        this.dbDriver = dbDriver;
    }

    public boolean checkCredentials(String email, String password) {
        boolean userValid;
        try {
            dbDriver.connect();
            userValid = dbDriver.checkCredentials(email, password);
            dbDriver.disconnect();
        } catch (SQLException e) {
            userValid = false;
        }
        return userValid;
    }

    public List<Member> getMemberData() {
        List<Member> membersList = new ArrayList<>();
        try {
            dbDriver.connect();
//            membersList = dbDriver.getMemberDataForSearch();
            membersList = dbDriver.getAllMemberData();
            dbDriver.disconnect();
        } catch (SQLException e) {
            //
        }
        return membersList;
    }

//    public List<Member> searchMembersByFirstName(String firstName) {
//        List<Member> members = new ArrayList<>();
//        try {
//            dbDriver.connect();
//            members = dbDriver.searchMembersByFirstName(firstName);
//            dbDriver.disconnect();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return members;
//    }
//
//    public List<Member> searchMembersByLastName(String lastName) {
//        List<Member> members = new ArrayList<>();
//        try {
//            dbDriver.connect();
//            members = dbDriver.searchMembersByLastName(lastName);
//            dbDriver.disconnect();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return members;
//    }
//
//    public List<Member> searchMembersByEmail(String email) {
//        List<Member> members = new ArrayList<>();
//        try {
//            dbDriver.connect();
//            members = dbDriver.searchMembersByEmail(email);
//            dbDriver.disconnect();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return members;
//    }
//search weight, result, gender
//public List<Member> searchMembersByWeight(float weight) {
//    List<Member> members = new ArrayList<>();
//    try {
//        dbDriver.connect();
//        members = dbDriver.searchMembersByWeightClass(weight);
//        dbDriver.disconnect();
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//    return members;
//}
//
//    public List<Member> searchMembersByResult(float result) {
//        List<Member> members = new ArrayList<>();
//        try {
//            dbDriver.connect();
//            members = dbDriver.searchMembersByBestResult(result);
//            dbDriver.disconnect();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return members;
//    }
//
//    public List<Member> searchMembersByGender(String gender) {
//        List<Member> members = new ArrayList<>();
//        try {
//            dbDriver.connect();
//            members = dbDriver.searchMembersByGender(gender);
//            dbDriver.disconnect();
//        } catch(SQLException e) {
//            e.printStackTrace();
//        }
//        return members;
//    }

    public List<Member> searchMembers(String firstName, String lastName, String email, String gender, Float weight, Float result) {
        List<Member> members = new ArrayList<>();
        try {
            dbDriver.connect();
            members = dbDriver.searchMembers(firstName, lastName, email, gender, weight, result);
            dbDriver.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }

    public void changePassword(String email, String oldPassword, String newPassword){
        // check if email matches with the old password
        // if they do, change the password hash in data table with new password
        try {
            dbDriver.connect();
            dbDriver.changePassword(email, oldPassword, newPassword);
            dbDriver.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void addMember(Member member) throws SQLException {
        try {
            dbDriver.connect();
            dbDriver.addMember(member);
            dbDriver.disconnect();
        } catch (SQLException e) {
            dbDriver.disconnect();
            throw e;
        }
    }

    public List<Event> searchMemberEvents (int memberId) {
        List<Event> eventsList = new ArrayList<>();
        try {
            dbDriver.connect();
            eventsList = dbDriver.searchMemberEvents(memberId);
            dbDriver.disconnect();
        } catch (SQLException e) {
            //
        }
        return eventsList;
    }

    //load alum data
    public List<Alumni> getAlumniData() {
        List<Alumni> alumniList = new ArrayList<>();
        try {
            dbDriver.connect();
            alumniList = dbDriver.getAllAlumniData();
            dbDriver.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alumniList;
    }

    //search alum
    public List<Alumni> searchAlumni(String firstName, String lastName, String email, Integer classYear) {
        List<Alumni> alumniList = new ArrayList<>();
        try {
            dbDriver.connect();
            alumniList = dbDriver.searchAlumni(firstName, lastName, email, classYear);
            dbDriver.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alumniList;
    }

    //delete member
    public void deleteMember(int memberId) throws SQLException {
        try {
            dbDriver.connect();
            dbDriver.deleteMember(memberId);
            dbDriver.disconnect();
        } catch (SQLException e) {
            dbDriver.disconnect();
            throw e;
        }
    }

    public void addPracticeEvent(int memberId, String eventDate, String eventLocation) throws SQLException {
        dbDriver.connect();
        dbDriver.addPracticeEvent(memberId, eventDate, eventLocation);
        dbDriver.disconnect();
    }

    public void addCompetitionEvent(int memberId, String eventDate, String eventLocation) throws SQLException {
        dbDriver.connect();
        dbDriver.addCompetitionEvent(memberId, eventDate, eventLocation);
        dbDriver.disconnect();
    }

}