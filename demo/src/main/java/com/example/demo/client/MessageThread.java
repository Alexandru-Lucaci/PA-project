package com.example.demo.client;

import javax.swing.*;
import java.awt.*;

public class MessageThread extends Thread{
    @Override
    public void run() {
        try {

//            updateJCP();
            Thread.sleep(9000);
//            updateJCPInf();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    private void updateJCP(Frame frame, JScrollPane jcp, JList prieteni) {
//        frame.remove(jcp);
//        prieteni = new JList<>(listaFinala());
//        prieteni.setFont(new Font("MV Boli",Font.BOLD,13));
//        prieteni.setLayout(new FlowLayout());
//        jcp = new JScrollPane(prieteni);
////            jcp.updateUI();
//
//        frame.add(jcp, BorderLayout.CENTER);
//        frame.validate();
//        frame.repaint();
//        JScrollBar vertical = jcp.getVerticalScrollBar();
//        vertical.setValue( vertical.getMaximum() );
//
//    }


}
