package com.example.android.note;

/**
 *
 */

public class Note {

    private int id;
    private String title;
    private String content;
    private long modifyTime;

    public Note(){

    }
    public Note(int id, String title, String content, long modifyTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.modifyTime = modifyTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(long modifyTime) {
        this.modifyTime = modifyTime;
    }
}
