package com.example.demo.client;

import com.example.demo.controllers.Message;
import com.example.demo.controllers.Person;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static com.example.demo.client.ChatFrame.getAllMessageFromAll;
import static com.example.demo.client.RestClient.getPersonByJson;

public class DaemonThread extends Thread{
    public DaemonThread(String name){
        super(name);
    }
    private String[] listaFinala(int whoAmI, int talkingToo){
        String meJson = RestClient.callGeTPersonByIdAPI(whoAmI);
        String talkingToJson = RestClient.callGeTPersonByIdAPI(talkingToo);
        Person me = getPersonByJson(meJson);
        Person talkingTo = getPersonByJson(talkingToJson);
        List<Message> friends= getAllMessageFromAll();
        String[] listsFinals = new String[friends.size()];
        int contor=-1;

        for(Message el: friends) //System.out.println(el.getName());
        {
            if((el.getIdSender()== me.getId() && el.getIdReciever()== talkingTo.getId())|| (el.getIdSender()==talkingTo.getId() && el.getIdReciever()==me.getId()) )
            {contor++;
                if(el.getIdSender() == whoAmI)
                    listsFinals[contor]= me.getName();
                else
                    listsFinals[contor] = talkingTo.getName();

                listsFinals[contor] += " :"+ el.getMessage();
            }
        }
        return  listsFinals;
    }
    public void updateJCP(Frame frame, JScrollPane jcp,JList prieteni, int id1, int id2){
        frame.remove(jcp);
        prieteni = new JList<>(listaFinala(id1,id2));
        prieteni.setFont(new Font("MV Boli",Font.BOLD,13));
        prieteni.setLayout(new FlowLayout());
        jcp = new JScrollPane(prieteni);
//            jcp.updateUI();

        frame.add(jcp, BorderLayout.CENTER);
        frame.validate();
        frame.repaint();
        JScrollBar vertical = jcp.getVerticalScrollBar();
        vertical.setValue( vertical.getMaximum() );
    }
    public void run(Frame frame, JScrollPane jcp,JList prieteni, int id1, int id2 ){
        if(Thread.currentThread().isDaemon())
            do{
                try{
                    Thread.sleep(7000);
                    updateJCP(frame, jcp,prieteni, id1,id2);
                }catch (Exception e) {
                    e.printStackTrace();
                }


            }while(true);


        else
        {
            System.out.println(getName() + " is User thread");
        }


    }
}
