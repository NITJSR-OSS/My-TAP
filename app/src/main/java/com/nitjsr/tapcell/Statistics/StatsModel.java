package com.nitjsr.tapcell.Statistics;

public class StatsModel {

    private int year1;
    private int year1stats;
    private int year2;
    private int year2stats;
    private int year3;
    private int year3stats;
    private int year4;
    private int year4stats;

    public StatsModel(){

    }

    public StatsModel(int year1, int year1stats, int year2, int year2stats, int year3, int year3stats, int year4, int year4stats) {
        this.year1 = year1;
        this.year1stats = year1stats;
        this.year2 = year2;
        this.year2stats = year2stats;
        this.year3 = year3;
        this.year3stats = year3stats;
        this.year4 = year4;
        this.year4stats = year4stats;
    }

    public int getYear1() {
        return year1;
    }

    public void setYear1(int year1) {
        this.year1 = year1;
    }

    public int getYear1stats() {
        return year1stats;
    }

    public void setYear1stats(int year1stats) {
        this.year1stats = year1stats;
    }

    public int getYear2() {
        return year2;
    }

    public void setYear2(int year2) {
        this.year2 = year2;
    }

    public int getYear2stats() {
        return year2stats;
    }

    public void setYear2stats(int year2stats) {
        this.year2stats = year2stats;
    }

    public int getYear3() {
        return year3;
    }

    public void setYear3(int year3) {
        this.year3 = year3;
    }

    public int getYear3stats() {
        return year3stats;
    }

    public void setYear3stats(int year3stats) {
        this.year3stats = year3stats;
    }

    public int getYear4() {
        return year4;
    }

    public void setYear4(int year4) {
        this.year4 = year4;
    }

    public int getYear4stats() {
        return year4stats;
    }

    public void setYear4stats(int year4stats) {
        this.year4stats = year4stats;
    }
}
