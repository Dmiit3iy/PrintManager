package org.dmitriypososhkov.model.manager;
import org.dmitriypososhkov.model.document.Document;
import org.dmitriypososhkov.repository.Repository;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;


public class Manager {
    private boolean workOn = true;

    public void setWorkOn() {
       workOn =false;
    }

    private Object lock = new Object();
    private Repository repository;
    //очередь документов на печать
    private volatile LinkedList<Document> linkedList = new LinkedList<>();

    public void setLinkedList() {
        Document document = repository.getNextDocument();

        this.linkedList.add(document);
    }

    //имитация буфера непосредственной печати
    private ArrayBlockingQueue<Document> arrayBlockingQueue = new ArrayBlockingQueue<>(1);
    //для хранения напечатанных документов
    private ArrayList<Document> arrayList = new ArrayList<>();

    public ArrayList<Document> getArrayList() {
        return arrayList;
    }

    public LinkedList<Document> getLinkedList() {
        return linkedList;
    }

    public Manager(Repository repository) {
        this.repository = repository;
    }

    /**
     * Метод для отправки документа на печать из репозитория
     */
    public void startPrint() {
        linkedList.add(repository.getNextDocument());
    }


    /**
     * Метод перемещения из "буфера" очереди печати в "буфер" непосредственно на печать
     *
     * @throws InterruptedException
     */
    public void sendToPrintBuffer() throws InterruptedException {
        while (workOn) {
            while (!linkedList.isEmpty()) {
                Document document = linkedList.removeFirst();
                arrayBlockingQueue.put(document);
            }
        }
        Thread.currentThread().interrupt();
    }

    /**
     * Метод имитации печати
     *
     * @throws InterruptedException
     */
    public void documentPrinting() throws InterruptedException {
        while (workOn) {
            synchronized (lock) {
                while (!arrayBlockingQueue.isEmpty()) {
                    try {

                        long t1 = System.currentTimeMillis();
                        lock.wait(arrayBlockingQueue.peek().getPrintTime());
                        long t2 = System.currentTimeMillis();
                        if (t2 - t1 < arrayBlockingQueue.peek().getPrintTime()) {
                            System.out.println("Печать документа "+arrayBlockingQueue.peek().getName()+" отменена");
                            arrayBlockingQueue.clear();
                        } else {
                            Document document = arrayBlockingQueue.take();
                            arrayList.add(document);
                            System.out.println("Документ "+document.getName()+ " напечатан");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        Thread.currentThread().interrupt();
    }

    /**
     * Метод для отмены печатающегося документа
     */
    public void cancelPrinting() {
        synchronized (lock) {
            lock.notifyAll();
        }
    }

    /**
     * Метод подсчета среднего времени печати
     * @return
     */
   public double avgPrintTime(){
      return  arrayList.stream().mapToInt(e -> e.getPrintTime()).average().orElse(0)/1000;
    }
}
