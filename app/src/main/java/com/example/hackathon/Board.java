package com.example.hackathon;

public class Board {

    private String title;
    private String contents;
    private String time;
    private String writer;

    public Board(String title, String contents, String time, int i, int i1) {
        setTitle(title);
        setContents(contents);
        setTime(time);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }
}
