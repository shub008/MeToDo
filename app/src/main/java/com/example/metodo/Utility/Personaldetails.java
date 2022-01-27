package com.example.metodo.Utility;

public class Personaldetails {
    private String content;
    private String title;
//    private String date;
//    private String photo;
//, String date/*,String date*/
    public Personaldetails(String title, String content) {
        this.content = content;
        this.title = title;
//        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

//    public String getDate() {
//        return date;
//    }
//
//    public void setDate(String date) {
//        this.date = date;
//    }
}
