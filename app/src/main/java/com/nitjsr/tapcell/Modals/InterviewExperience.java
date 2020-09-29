package com.nitjsr.tapcell.Modals;

import java.io.Serializable;

public class InterviewExperience implements Serializable {
    private int cnfStatus;
    private String studName;
    private String branch;
    private String whatsappNum;
    private String linkedInId;
    private boolean isInternshipReview;
    private String companyName;
    private String jobTitle;
    private String internCompany;
    private String photoUrl;
    private String projectDesc;
    private String onlineRound;
    private String techRound;
    private String hrRound;
    private String interviewMode;
    private String interviewDifficulty;
    private String wordsToJr;
    private String childKey;

    public InterviewExperience() {}

    public InterviewExperience(int cnfStatus, String studName, String branch, String whatsappNum, String linkedInId, boolean isInternshipReview, String companyName, String jobTitle, String internCompany, String photoUrl, String projectDesc, String onlineRound, String techRound, String hrRound, String interviewMode, String interviewDifficulty, String wordsToJr, String childKey) {
        this.cnfStatus = cnfStatus;
        this.studName = studName;
        this.branch = branch;
        this.whatsappNum = whatsappNum;
        this.linkedInId = linkedInId;
        this.isInternshipReview = isInternshipReview;
        this.companyName = companyName;
        this.jobTitle = jobTitle;
        this.internCompany = internCompany;
        this.photoUrl = photoUrl;
        this.projectDesc = projectDesc;
        this.onlineRound = onlineRound;
        this.techRound = techRound;
        this.hrRound = hrRound;
        this.interviewMode = interviewMode;
        this.interviewDifficulty = interviewDifficulty;
        this.wordsToJr = wordsToJr;
        this.childKey = childKey;
    }

    public int getCnfStatus() {
        return cnfStatus;
    }

    public void setCnfStatus(int cnfStatus) {
        this.cnfStatus = cnfStatus;
    }

    public String getStudName() {
        return studName;
    }

    public void setStudName(String studName) {
        this.studName = studName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getWhatsappNum() {
        return whatsappNum;
    }

    public void setWhatsappNum(String whatsappNum) {
        this.whatsappNum = whatsappNum;
    }

    public String getLinkedInId() {
        return linkedInId;
    }

    public void setLinkedInId(String linkedInId) {
        this.linkedInId = linkedInId;
    }

    public boolean isInternshipReview() {
        return isInternshipReview;
    }

    public void setInternshipReview(boolean internshipReview) {
        isInternshipReview = internshipReview;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getInternCompany() {
        return internCompany;
    }

    public void setInternCompany(String internCompany) {
        this.internCompany = internCompany;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    public String getOnlineRound() {
        return onlineRound;
    }

    public void setOnlineRound(String onlineRound) {
        this.onlineRound = onlineRound;
    }

    public String getTechRound() {
        return techRound;
    }

    public void setTechRound(String techRound) {
        this.techRound = techRound;
    }

    public String getHrRound() {
        return hrRound;
    }

    public void setHrRound(String hrRound) {
        this.hrRound = hrRound;
    }

    public String getInterviewMode() {
        return interviewMode;
    }

    public void setInterviewMode(String interviewMode) {
        this.interviewMode = interviewMode;
    }

    public String getInterviewDifficulty() {
        return interviewDifficulty;
    }

    public void setInterviewDifficulty(String interviewDifficulty) {
        this.interviewDifficulty = interviewDifficulty;
    }

    public String getWordsToJr() {
        return wordsToJr;
    }

    public void setWordsToJr(String wordsToJr) {
        this.wordsToJr = wordsToJr;
    }

    public String getChildKey() {
        return childKey;
    }

    public void setChildKey(String childKey) {
        this.childKey = childKey;
    }
}
