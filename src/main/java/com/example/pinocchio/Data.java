package com.example.pinocchio;

import java.io.*;

public class Data implements Serializable {

    private int chapterNumber;
    private int currentPageIndex;

    public Data() {
        chapterNumber = 1;
        currentPageIndex = 0;
    }

    public void setChapterNumber(int chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public int getChapterNumber() {
        return chapterNumber;
    }

    public void setCurrentPageIndex(int currentPageIndex) {
        this.currentPageIndex = currentPageIndex;
    }

    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    public void save() {
        try {
            FileOutputStream fos = new FileOutputStream("pinocchio_data.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.flush();
            oos.close();
        } catch (Exception ignored) {}
    }

    public static Data load() {
        try {
            FileInputStream fis = new FileInputStream("pinocchio_data.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Data data = (Data) ois.readObject();
            ois.close();
            return data;
        } catch (Exception e) {
            return new Data();
        }
    }

}
