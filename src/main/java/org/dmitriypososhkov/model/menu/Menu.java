package org.dmitriypososhkov.model.menu;
import org.dmitriypososhkov.model.document.Document;
import org.dmitriypososhkov.model.manager.Manager;
import java.util.*;
import java.util.stream.Collectors;


public class Menu {
    private boolean isQuit = false;
    public void setQuit() {
        isQuit = true;
    }
    Manager manager;

    public Menu(Manager manager) {
        this.manager = manager;
    }

    public void startMenu() {

        Scanner scan = new Scanner(System.in);
        String menu = "Menu:\n" + "1 - Принять документ на печать. \n" +
                "2 - Отменить печать принятого документа\n" +
                "3 - Получить отсортированный список напечатанных документов\n" +
                "\t31 - по порядку печати\n" +
                "\t32 - по типу документов\n" +
                "\t33 - по продолжительности печати\n" +
                "\t34 - по размеру бумаги\n" +

                "4 - Рассчитать среднюю продолжительность печати напечатанных документов\n" +
                "5 - Остановка диспетчера." +"На выходе список ненапечатанных документов\n";


        while (!isQuit) {
            System.out.println(menu);

           int number = scan.nextInt();

            switch (number) {

                case 1:
                    manager.startPrint();
                    System.out.println("Очередь на печать: " + manager.getLinkedList());
                    break;
                case 2:
                    manager.cancelPrinting();
                    break;
                case 31:
                    System.out.println(manager.getArrayList());
                    break;
                case 32:
                   List<Document> newList = manager.getArrayList().stream().collect(Collectors.toList());
                    Collections.sort(newList, new Comparator<Document>() {
                        @Override
                        public int compare(Document o1, Document o2) {
                            return o1.getDocType().toString().compareTo(o2.getDocType().toString());
                        }});
                    System.out.println(newList);
                    break;
                case 33:
                    List<Document> newList1 = manager.getArrayList().stream().collect(Collectors.toList());
                    Collections.sort(newList1, new Comparator<Document>() {
                        @Override
                        public int compare(Document o1, Document o2) {
                            return Integer.compare(o1.getPrintTime(),o2.getPrintTime());
                        }});
                    System.out.println(newList1);
                    break;
                case 34:
                   List<Document> newList2 = manager.getArrayList().stream().collect(Collectors.toList());
                    Collections.sort(newList2, new Comparator<Document>() {
                        @Override
                        public int compare(Document o1, Document o2) {
                            return o1.getSize().toString().compareTo(o2.getSize().toString());
                        }});
                    System.out.println(newList2);
                    break;
                case 4:
                    System.out.println("Среднее время печати, напечатанных документов: " + manager.avgPrintTime()+" секунд(ы)");
                    break;

                case 5:
                    System.out.println("Ненапечатанные документы: " + manager.getLinkedList());
                    manager.setWorkOn();
                    setQuit();
                    break;

                default:
                    System.out.println("Выбран неверный пункт меню");
                    break;
            }
        }
        Thread.currentThread().interrupt();
    }
}
