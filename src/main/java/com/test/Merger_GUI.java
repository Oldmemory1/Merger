package com.test;

import lombok.extern.java.Log;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
@Log
public class Merger_GUI {
    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 4, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2), r -> new Thread(r, "线程:" + Thread.currentThread().getName()));
    private final Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
    private final Font font = new Font("微软雅黑", Font.PLAIN,16);
    private final PropertiesReader propertiesReader=new PropertiesReader("settings.properties");
    private boolean button1_clicked = false;
    public Merger_GUI(String Title) {
        JFrame frame =new JFrame(Title);
        Container container = frame.getContentPane();
        JLabel label1 = new JLabel(propertiesReader.ReadProperties("title_text"));
        label1.setBounds(50, 50, 300, 50);
        label1.setFont(font);
        label1.setBorder(border);
        container.add(label1);

        JButton button1 = new JButton(propertiesReader.ReadProperties("button1_text"));
        button1.setFont(font);
        button1.setBounds(50, 125, 300, 50);
        button1.setFocusPainted(false);
        container.add(button1);


        JLabel file1_SHA256 = new JLabel("SHA256");
        file1_SHA256.setBounds(375,125,600,50);
        file1_SHA256.setFont(font);
        file1_SHA256.setBorder(border);
        container.add(file1_SHA256);
        button1.addActionListener( (ActionEvent e) -> executor.execute(() -> {
            try {
                file1_SHA256.setText("正在计算文件哈希值...");
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                log.info(ex.getMessage());
            }
            try {
                String file1_SHA256_value = SHA256_Calculator.getFileSHA256(propertiesReader.ReadProperties("file1_name"));
                file1_SHA256.setText(file1_SHA256_value);
            } catch (NoSuchAlgorithmException ex) {
                log.info(ex.getMessage());
                file1_SHA256.setText("未知错误");
            }
            button1_clicked = true;
            log.info("按钮1已被点击");
        }));

        JButton button2 = new JButton(propertiesReader.ReadProperties("button2_text"));
        button2.setFont(font);
        button2.setBounds(50,200,300,50);
        button2.setFocusPainted(false);
        container.add(button2);


        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setBackground(Color.WHITE);
        frame.setVisible(true);
        frame.setSize(1200, 800);
    }
}
