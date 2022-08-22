package com.example.passthecard;

public class studentdetailsmodel {

    String firstname;
    String lastname;
    String moodleid;
    String srno;

    public studentdetailsmodel(String firstname, String lastname, String moodleid, String srno) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.moodleid = moodleid;
        this.srno = srno;
    }

    public studentdetailsmodel() {
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMoodleid() {
        return moodleid;
    }

    public void setMoodleid(String moodleid) {
        this.moodleid = moodleid;
    }

    public String getSrno() {
        return srno;
    }

    public void setSrno(String srno) {
        this.srno = srno;
    }
}
