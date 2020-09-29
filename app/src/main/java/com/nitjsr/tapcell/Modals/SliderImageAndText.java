package com.nitjsr.tapcell.Modals;

public class SliderImageAndText {
    private String image1;
    private String text1;
    private String image2;
    private String text2;
    private String image3;
    private String text3;

    public SliderImageAndText(){
    }

    public SliderImageAndText(String image1, String text1, String image2, String text2, String image3, String text3) {
        this.image1 = image1;
        this.text1 = text1;
        this.image2 = image2;
        this.text2 = text2;
        this.image3 = image3;
        this.text3 = text3;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getText3() {
        return text3;
    }

    public void setText3(String text3) {
        this.text3 = text3;
    }
}
