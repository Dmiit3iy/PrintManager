package org.dmitriypososhkov;
import org.dmitriypososhkov.model.manager.Manager;
import org.dmitriypososhkov.model.menu.Menu;
import org.dmitriypososhkov.repository.Repository;


public class Main {
    public static void main(String[] args) throws InterruptedException {

        Repository repository = new Repository();
        Manager manager = new Manager(repository);
        Menu menu = new Menu(manager);


        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        manager.sendToPrintBuffer();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        ;
                    }
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    try {
                        manager.documentPrinting();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Thread userMenu = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    menu.startMenu();
                }
            }
        });

        userMenu.start();
        thread1.start();
        thread2.start();

    }
}
