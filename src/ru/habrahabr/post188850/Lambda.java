package ru.habrahabr.post188850;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author phpusr
 *         Date: 16.04.14
 *         Time: 11:05
 */

/**
 * Демонстрация использования Lambda-выражений
 */
public class Lambda {
    static Thread threadCounter = null;

    public static void main(String[] args) throws InterruptedException {
        listener();
        thread();
    }

    static void listener() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);
        panel.setLayout(new FlowLayout());
        frame.setLocationRelativeTo(null);

        JButton button = new JButton("Start thread");
        panel.add(button);

        // Реализация слушателя с помощью Lambda
        button.addActionListener(e -> {
            System.out.println("Start thread");
            threadCounter.start();
        });


        JButton button2 = new JButton("Interrupt thread");
        panel.add(button2);

        // Обычная реализация слушателя
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Interrupt thread");
                threadCounter.interrupt();
            }
        });


        frame.setVisible(true);
    }

    /**
     * Демонстрация создания потока по-новому
     */
    static void thread() throws InterruptedException {
        int[] count = new int[1];

        threadCounter = new Thread(() -> {

            boolean interrupt = false;
            while(!interrupt) {
                count[0]++;
                System.out.println("Thread work count: " + count[0]);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    interrupt = true;
                    e.printStackTrace();
                }
            }
        });
    }

}
