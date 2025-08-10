package com.test;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class AlertGUI {
    private final Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
    private final Font mainFont = new Font("微软雅黑", Font.PLAIN,16);
    private final Font labelFont = new Font("微软雅黑", Font.PLAIN,14);

    public AlertGUI(Frame parent,String title,String information) {

       JDialog dialog = new JDialog(parent,title,true);
        Container container = dialog.getContentPane();

        JLabel label1 = new JLabel(information);
        label1.setBounds(50, 50, 200, 50);
        label1.setFont(mainFont);
        container.add(label1);

        JButton closeButton = new JButton("关闭窗口");
        closeButton.setFont(mainFont);
        closeButton.setBounds(50, 125, 200, 50);
        closeButton.setFocusPainted(false);
        container.add(closeButton);

        closeButton.addActionListener( e ->{
            dialog.dispose();
        });


        container.setLayout(null);
        dialog.setSize(500, 500);
        dialog.setLocationRelativeTo(parent); // 居中显示
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setVisible(true); // setVisible 会阻塞，直到对话框关闭
    }
}
