package org.dmitriypososhkov.model.document;

/**
 * Здесь задаем типы документов
 */

public enum DocType {
    TXT("Простой текстовый файл"),
    DOCX("Текст в формате MS Word"),
    JPG("Файл с растровым изображением"),
    PDF("Межплатформенный электронный документ");

    private String s;

    DocType(String s) {
        this.s = s;
    }


}
