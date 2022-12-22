package org.dmitriypososhkov.model.document;

public class Document {
    //Имя документа
    private String name;
    //Тип документа
    private DocType docType;
    //Размер бумаги
    private TypeSize size;

    //Количество листов в документе
    private int pages;
    private int printTime;

    public Document() {
    }

    public Document(String name, DocType docType, TypeSize size, int pages) {
        this.name = name;
        this.docType = docType;
        this.size = size;
        this.pages = pages;
        switch (size) {
            case A1 -> printTime = 1600 * pages;
            case A2 -> printTime = 1400 * pages;
            case A3 -> printTime = 1200 * pages;
            case A4 -> printTime = 1000 * pages;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DocType getDocType() {
        return docType;
    }

    public void setDocType(DocType docType) {
        this.docType = docType;
    }

    public TypeSize getSize() {
        return size;
    }

    public void setSize(TypeSize size) {
        this.size = size;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPrintTime() {
        return printTime;
    }

    public void setPrintTime(int printTime) {
        this.printTime = printTime;
    }

    @Override
    public String toString() {
        return "Document{" +
                "name='" + name + '\'' +
                ", docType=" + docType +
                ", size=" + size +
                ", pages" + pages +
                '}';
    }
}
