package com.company;

public class ModelPerson {



    //private int id;
    private String first_name;
    private String last_name;
    private String notes;

    public ModelPerson(String first_name, String last_name, String notes) {
        //this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.notes = notes;
    }
/*
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }*/

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
