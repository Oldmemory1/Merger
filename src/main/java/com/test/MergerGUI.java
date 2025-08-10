package com.test;

import lombok.extern.java.Log;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Log
public class MergerGUI {
    private final Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
    private final Font mainFont = new Font("微软雅黑", Font.PLAIN,16);
    private final PropertiesReader propertiesReader=new PropertiesReader("settings.properties");
    private final Font labelFont = new Font("微软雅黑", Font.PLAIN,14);
    private boolean button1Clicked = false;
    private boolean button2Clicked = false;
    private boolean button3Clicked = false;
    private boolean button4Clicked = false;
    private String outputFile = null;
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

        button1.addActionListener(e -> startSHA256Task(frame,file1_SHA256, "file1_name", () -> {
            button1Clicked = true;
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

        button2.addActionListener(e -> startSHA256Task(frame,file2_SHA256, "file2_name", () -> {
            button2Clicked = true;
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

        button3.addActionListener(e -> startSHA256Task(frame,file3_SHA256, "file3_name", () -> {
            button3Clicked = true;
            log.info("按钮3已被点击");
        }
        ));

        JButton button4 = new JButton("选择输出的文件地址");
        button4.setFont(mainFont);
        button4.setBounds(50,350,300,50);
        button4.setFocusPainted(false);
        container.add(button4);

        JLabel output_file_path = new JLabel("请选择输出的文件地址");
        output_file_path.setBounds(375,350,600,50);
        output_file_path.setFont(labelFont);
        output_file_path.setBorder(border);
        container.add(output_file_path);

        button4.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int i = fileChooser.showOpenDialog(frame.getContentPane());
            if (i == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String s = selectedFile.getAbsolutePath();
                outputFile = s+"\\"+propertiesReader.ReadProperties("output_file_name");
                log.info("选定文件路径:"+selectedFile.getAbsolutePath());
                log.info("选定文件路径"+outputFile);
                button4Clicked = true;
                log.info("按钮4已被点击");
                output_file_path.setText(outputFile);
            }
        });

        JButton button5 =new JButton("开始合并");
        button5.setFont(mainFont);
        button5.setBounds(50,425,300,50);
        button5.setFocusPainted(false);
        container.add(button5);

        JLabel currentStatus = new JLabel("等待合并");
        currentStatus.setBounds(375,425,600,50);
        currentStatus.setFont(labelFont);
        currentStatus.setBorder(border);
        container.add(currentStatus);

        button5.addActionListener(e -> {
            if(button1Clicked && button2Clicked && button3Clicked && button4Clicked){
                mergeBinaryFiles(frame,outputFile, propertiesReader, currentStatus, () -> log.info("合并结束"));
            }else{
               new AlertGUI(frame,"警告","您没有点击按钮校验文件的哈希值或未选择输出路径");
            }
        });

        JButton exitButton = new JButton("退出程序");
        exitButton.setFont(mainFont);
        exitButton.setBounds(50,500,300,50);
        exitButton.setFocusPainted(false);
        container.add(exitButton);
        exitButton.addActionListener(e -> System.exit(0));

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setBackground(Color.WHITE);
        frame.setVisible(true);
        frame.setSize(1200, 800);
    }



    private void startSHA256Task(Frame frame,JLabel label, String fileKey, Runnable afterTask) {
        label.setText("正在计算文件哈希值...");
        SwingWorker<String,Void> swingWorker = new SwingWorker<>() {
            @Override
            protected String doInBackground() {
                try {
                    Thread.sleep(1000); // 模拟耗时
                    return SHA256Calculator.getFileSHA256(propertiesReader.ReadProperties(fileKey));
                } catch (NoSuchAlgorithmException e) {
                    log.info(e.getMessage());
                    new AlertGUI(frame,"警告","发生了未知错误");
                    return "未知错误";
                } catch (InterruptedException e) {
                    log.info(e.getMessage());
                    new AlertGUI(frame,"警告","任务被中断");
                    return "任务被中断";
                } catch (IOException e) {
                    log.info(e.getMessage());
                    new AlertGUI(frame,"警告","找不到对应的文件:"+propertiesReader.ReadProperties(fileKey));
                    return "找不到文件";
                }
            }

            @Override
            protected void done() {
                try {
                    label.setText(get());
                } catch (Exception e) {
                    label.setText("发生了未知错误");
                    log.info(e.getMessage());
                }
                if (afterTask != null) {
                    afterTask.run();
                }
            }
        };
        swingWorker.execute();
    }

    private void mergeBinaryFiles(Frame frame,String outputFilePath,PropertiesReader propertiesReader,JLabel label,Runnable afterTask) {
        label.setText("合并中...请勿退出程序");
        //合并顺序 file1 file2 file3
        SwingWorker<String,Void> swingWorker = new SwingWorker<>() {
            @Override
            protected String doInBackground() {
                String file1 = propertiesReader.ReadProperties("file1_name");
                String file2 = propertiesReader.ReadProperties("file2_name");
                String file3 = propertiesReader.ReadProperties("file3_name");
                log.info(outputFilePath);
                List<String> inputFiles = Arrays.asList(file1, file2, file3);
                try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputFilePath))) {
                    byte[] buffer =new byte[8192];
                    for(String inputFile : inputFiles){
                        try(InputStream inputStream = new BufferedInputStream(new FileInputStream(inputFile))){
                            int bytesRead;
                            while ((bytesRead = inputStream.read(buffer)) != -1) {
                                outputStream.write(buffer, 0, bytesRead);
                            }
                        }
                    }
                    new AlertGUI(frame,"信息","合并完成");
                    return "合并完成";
                } catch (IOException e) {
                    log.info(e.getMessage());
                    new AlertGUI(frame,"错误","合并失败,请去对应目录删除错误的文件");
                    return "合并失败";
                }
            }

            @Override
            protected void done() {
                try {
                    label.setText(get());
                } catch (Exception e) {
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
