package com.example.demo.client;


import java.awt.*;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class JFrameDemo extends JFrame
{
    //Sample 02: Create a Label
    JLabel label = new JLabel();
    public JFrameDemo(String title) throws HeadlessException
    {
        super(title);
        //Sample 01: Set Size and Position
        setBounds(100, 100, 200, 200);
        Container ControlHost = getContentPane();
        ControlHost.setLayout(new FlowLayout());

        //Sample 03: Create List of Fruit Items




        Button[] fruits= new Button[8];

        String[] Fruits = new String[8];
        Fruits[0] = "Apple";
        Fruits[1] = "Mango";
        Fruits[2] = "Banana";
        Fruits[3] = "Grapes";
        Fruits[4] = "Cherry";
        Fruits[5] = "Lemon";
        Fruits[6] = "Orange";
        Fruits[7] = "Strawberry";

        //Sample 04: Create JList to Show Fruit Name
        JList ListFruits = new JList(Fruits);
        ListFruits.setVisibleRowCount(4);

        //Sample 05: Hand-Over the JList to ScrollPane & Display
        JScrollPane jcp = new JScrollPane(ListFruits);
        ControlHost.add(jcp);
        ControlHost.add(label);



        
        //Sample 06: Handle the JList Event
        ListFruits.addListSelectionListener(new ListSelectionListener() {



            @Override
            public void valueChanged(ListSelectionEvent e) {
                String SelectedFruit = (String) ListFruits.getSelectedValue();
                label.setText(SelectedFruit);
            }
        });
    }
}