package com.example.pinocchio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Chapter {

    private ArrayList<Page> pages;
    private int currentPageIndex;

    public Chapter(int chapterNumber) {
        pages = new ArrayList<>();
        currentPageIndex = 0;
        readFromFile("Italian_Chapter_" + chapterNumber, true);
        readFromFile("English_Chapter_" + chapterNumber, false);
    }

    public void readFromFile(String fileName, boolean italian) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("." + File.separator + "src" +
                    File.separator + "main" + File.separator + "resources" + File.separator + fileName + ".txt"));
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            int index = 0;
            while ((line = reader.readLine()) != null) {
                if (line.equals("[page]")) {
                    if (italian) {
                        Page page = new Page();
                        page.setItalianText(stringBuilder.toString());
                        pages.add(page);
                    } else pages.get(index++).setEnglishText(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                } else stringBuilder.append(line).append("\n");
            }
        } catch (Exception ignored) {}
    }

    public Page getCurrentPage() {
        return pages.get(currentPageIndex);
    }

    public Page nextPage() {
        if (pages.size() < currentPageIndex + 2) return null;
        return pages.get(++currentPageIndex);
    }

    public Page previousPage() {
        if (currentPageIndex - 1 < 0) return null;
        return pages.get(--currentPageIndex);
    }

}
