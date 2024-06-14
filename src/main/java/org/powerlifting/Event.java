package org.powerlifting;

public class Event {
    private int Member_ID;
    private String Event_Type;
    private String Event_Date;
    private String Event_Location;

    public Event(int member_ID, String event_Type, String event_Date, String event_Location) {
        Member_ID = member_ID;
        Event_Type = event_Type;
        Event_Date = event_Date;
        Event_Location = event_Location;
    }

    public int getMember_ID() {
        return Member_ID;
    }

    public void setMember_ID(int member_ID) {
        Member_ID = member_ID;
    }

    public String getEvent_Type() {
        return Event_Type;
    }

    public void setEvent_Type(String event_Type) {
        Event_Type = event_Type;
    }

    public String getEvent_Date() {
        return Event_Date;
    }

    public void setEvent_Date(String event_Date) {
        Event_Date = event_Date;
    }

    public String getEvent_Location() {
        return Event_Location;
    }

    public void setEvent_Location(String event_Location) {
        Event_Location = event_Location;
    }

    @Override
    public String toString() {
        return "Event{" +
                "Member_ID =" + Member_ID +
                ", Event_Type='" + Event_Type + '\'' +
                ", Event_Date='" + Event_Date + '\'' +
                ", Event_Location='" + Event_Location + '\'' +
                '}';
    }
}
