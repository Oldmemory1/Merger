package com.test;

import lombok.extern.java.Log;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.security.NoSuchAlgorithmException;

@Log
public class MergerGUI {
    private final Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
    private final Font mainFont = new Font("微软雅黑", Font.PLAIN,16);
    private final PropertiesReader propertiesReader=new PropertiesReader("settings.properties");
    private final Font labelFont = new Font("微软雅黑", Font.PLAIN,14);
    private boolean button1_clicked = false;
    private boolean button2_clicked = false;
    private boolean button3_clicked = false;
    public MergerGUI(String Title) {
        JFrame frame =new JFrame(Title);
        Container container = frame.getContentPane();

        JLabel label1 = new JLabel(propertiesReader.ReadProperties("title_text"));
        label1.setBounds(50, 50, 300, 50);
        label1.setFont(mainFont);
        label1.setBorder(border);
        container.add(label1);

        JButton button1 = new JButton(propertiesReader.ReadProperties("button1_text"));
        button1.setFont(mainFont);
        button1.setBounds(50, 125, 300, 50);
        button1.setFocusPainted(false);
        container.add(button1);

        JLabel file1_SHA256 = new JLabel("SHA256");
        file1_SHA256.setBounds(375,125,600,50);
        file1_SHA256.setFont(labelFont);
        file1_SHA256.setBorder(border);
        container.add(file1_SHA256);

        button1.addActionListener(e -> startSHA256Task(file1_SHA256, "file1_name", () -> {
            button1_clicked = true;
            log.info("按钮1已被点击");
        }));

        JButton button2 = new JButton(propertiesReader.ReadProperties("button2_text"));
        button2.setFont(mainFont);
        button2.setBounds(50,200,300,50);
        button2.setFocusPainted(false);
        container.add(button2);

        JLabel file2_SHA256 = new JLabel("SHA256");
        file2_SHA256.setBounds(375,200,600,50);
        file2_SHA256.setFont(labelFont);
        file2_SHA256.setBorder(border);
        container.add(file2_SHA256);

        button2.addActionListener(e -> startSHA256Task(file2_SHA256, "file2_name", () -> {
            button2_clicked = true;
            log.info("按钮2已被点击");
        }));

        JButton button3 = new JButton(propertiesReader.ReadProperties("button3_text"));
        button3.setFont(mainFont);
        button3.setBounds(50,275,300,50);
        button3.setFocusPainted(false);
        container.add(button3);

        JLabel file3_SHA256 = new JLabel("SHA256");
        file3_SHA256.setBounds(375,275,600,50);
        file3_SHA256.setFont(labelFont);
        file3_SHA256.setBorder(border);
        container.add(file3_SHA256);

        button3.addActionListener(e -> startSHA256Task(file3_SHA256, "file3_name", () -> {
            button3_clicked = true;
            log.info("按钮3已被点击");
        }));


        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setBackground(Color.WHITE);
        frame.setVisible(true);
        frame.setSize(1200, 800);
    }
    private void startSHA256Task(JLabel label, String fileKey, Runnable afterTask) {
        label.setText("正在计算文件哈希值...");
        SwingWorker<String,Void> swingWorker = new SwingWorker<>() {
            @Override
            protected String doInBackground() {
                try {
                    Thread.sleep(1000); // 模拟耗时
                    return SHA256Calculator.getFileSHA256(propertiesReader.ReadProperties(fileKey));
                } catch (NoSuchAlgorithmException e) {
                    log.info(e.getMessage());
                    return "未知错误";
                } catch (InterruptedException e) {
                    log.info(e.getMessage());
                    return "任务被中断";
                }
            }

            @Override
            protected void done() {
                try {
                    label.setText(get());
                } catch (Exception e) {
                    label.setText("未知错误");
                    log.info(e.getMessage());
                }
                if (afterTask != null) {
                    afterTask.run();
                }
            }
        };
        swingWorker.execute();
    }
}
