package com.test;

import lombok.extern.java.Log;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
@Log
public class Merger_GUI {
    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 4, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2), r -> new Thread(r, "线程:" + Thread.currentThread().getName()));
    private final Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
    private final Font font = new Font("微软雅黑", Font.PLAIN,16);
    public Merger_GUI(String Title) {
        JFrame frame =new JFrame(Title);
        Container container = frame.getContentPane();
        JLabel label1 = new JLabel("Merger");
        label1.setBounds(50, 50, 300, 50);
        label1.setFont(font);
        label1.setBorder(border);
        container.add(label1);
        JButton button1 = new JButton("检测SHA256值");
        button1.setBounds(50, 125, 300, 50);
        container.add(button1);
        JLabel file1_SHA256 = new JLabel("SHA256");
        file1_SHA256.setBounds(375,125,300,50);
        file1_SHA256.setFont(font);
        file1_SHA256.setBorder(border);
        container.add(file1_SHA256);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setBackground(Color.WHITE);
        frame.setVisible(true);
        frame.setSize(1000, 800);
    }
}
