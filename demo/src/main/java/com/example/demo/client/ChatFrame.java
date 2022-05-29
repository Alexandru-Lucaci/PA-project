package com.example.demo.client;

import com.example.demo.controllers.Friendship;
import com.example.demo.controllers.Person;
import com.example.demo.database.PrietenDAO;
import com.github.javafaker.Faker;
import com.google.gson.Gson;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import static java.lang.System.exit;

public class ChatFrame extends JFrame implements ActionListener {
    private final JScrollPane jcp;
    private final int whoAmI;
    private final int talkingTo;

    JFrame frame=new JFrame();
    JTextField info = new JTextField();
    JButton exitButton = new JButton("Ieșire");
    JButton registerButton = new JButton("Înregistrare");
    JButton loginButton = new JButton("Logare");
    JButton sendMessage = new JButton("Trimite");
    JButton changePasswordButton = new JButton("Sterge prietenia");

    JButton removeButton= new JButton("LOGOUT");
    JButton backButton = new JButton("BACK");

    ChatFrame(int id, int idSecond){


        whoAmI=id;
        talkingTo =idSecond;
        String meJson = RestClient.callGeTPersonByIdAPI(whoAmI);
        String talkingToJson = RestClient.callGeTPersonByIdAPI(talkingTo);

        Person me = getPersonByJson(meJson);
        Person talkingTo = getPersonByJson(talkingToJson);
//        meJson=null;
//        talkingToJson=null;



        backButton.setFont(new Font(null,Font.BOLD,20));
        backButton.setBounds(100,160,200,60);
        backButton.setFocusable(false);
        backButton.addActionListener(this);
        backButton.setBorder(BorderFactory.createEtchedBorder());

        removeButton.setFont(new Font(null,Font.BOLD,20));
        removeButton.setFont(new Font(null,Font.BOLD,20));
        removeButton.setBounds(100,160,200,60);
        removeButton.setFocusable(false);
        removeButton.addActionListener(this);
        removeButton.setBorder(BorderFactory.createEtchedBorder());
        removeButton.setFont(new Font(null,Font.BOLD,20));

        changePasswordButton.setFont(new Font(null,Font.BOLD,20));
        changePasswordButton.setBounds(100,160,200,60);
        changePasswordButton.setFocusable(false);
        changePasswordButton.addActionListener(this);
        changePasswordButton.setBorder(BorderFactory.createEtchedBorder());
        changePasswordButton.setFont(new Font(null,Font.BOLD,20));

        sendMessage.setFont(new Font(null,Font.BOLD,20));
        sendMessage.setBounds(100,160,200,100);
        sendMessage.setFocusable(false);
        sendMessage.addActionListener(this);
        sendMessage.setBorder(BorderFactory.createEtchedBorder());
        sendMessage.setFont(new Font(null,Font.BOLD,20));



        info.setBounds(150, 150, 150, 30);





        JPanel buttonPanel = new JPanel();
        JPanel lowerPanel = new JPanel();



//     Basic text
        JLabel info3= new JLabel("Talking to  "+ talkingTo.getName());
        info3.setLayout(new FlowLayout());
        info3.setVerticalAlignment(JLabel.CENTER);
        info3.setHorizontalAlignment(JLabel.CENTER);
        info3.setPreferredSize(new Dimension(200,100));
        info3.setFont(new Font("MV Boli",Font.PLAIN,20));
        info3.setBounds(1,1,100,100);
        info3.setForeground(new Color(0x123456));

        Person p = getPersonByJson(RestClient.callGeTPersonByIdAPI(whoAmI));
//        Basic Text
        JLabel info2 = new JLabel(p.getName());
        info.setLayout(new FlowLayout());
        info2.setVerticalAlignment(JLabel.CENTER);
//        insert text
        info.setHorizontalAlignment(JLabel.CENTER);
        info.setPreferredSize(new Dimension(700,45));
        info.setFont(new Font("MV Boli",Font.PLAIN,20));
        info.setForeground(new Color(0x123456));
        info.setText("Scrie mesajul...");
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(1,2,10,10));

        info2.setLayout(new FlowLayout());
        info2.setHorizontalAlignment(JLabel.CENTER);
        info2.setPreferredSize(new Dimension(200,45));
        info2.setFont(new Font("MV Boli",Font.PLAIN,20));
        info2.setForeground(new Color(0x123456));
        topPanel.add(info2);
        topPanel.add(info3);

//        lowerPanel.setLayout(new GridLayout(1,2,10,5));


        lowerPanel.add(info);
        lowerPanel.add(sendMessage);



        JLabel updated = new JLabel("Ultima actualizare la data de 27.05.2022");
        updated.setLayout(new FlowLayout());
        updated.setVerticalAlignment(JLabel.CENTER);
        updated.setHorizontalAlignment(JLabel.CENTER);
        updated.setPreferredSize(new Dimension(200,100));
        updated.setFont(new Font("MV Boli",Font.PLAIN,20));



        buttonPanel.setLayout(new GridLayout(3,1,10,10));
//        buttonPanel.add(myButton);

        buttonPanel.add(changePasswordButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(backButton);
        buttonPanel.setPreferredSize(new Dimension(200,50));


        ImageIcon icon= new ImageIcon("src/main/java/Icon.png");

        Container controlHost = getContentPane();
        controlHost.setLayout((new FlowLayout()));


//        List<String> control = new ArrayList<>();


        JPanel listWithFriends =new JPanel();
        listWithFriends.setLayout(new GridLayout(1,1,10,10));

        List<Integer> friendsWithMe = getFriendsWithID(whoAmI);
        List<Person> friends = new ArrayList<>();
        for(Integer el:friendsWithMe)
        {
            friends.add(getPersonByJson(RestClient.callGeTPersonByIdAPI(el)));

        }
        String[] listaFinala = new String[friends.size()];
        int contor=-1;


        for(Person el: friends) //System.out.println(el.getName());
        {
            contor++;
            listaFinala[contor] = el.getName();
        }
        JList prieteni = new JList<>(listaFinala);
        prieteni.setFont(new Font("MV Boli",Font.BOLD,13));
        prieteni.setLayout(new FlowLayout());


        prieteni.addListSelectionListener(new ListSelectionListener() {
            Label label= new Label();


            @Override
            public void valueChanged(ListSelectionEvent e) {
                String SelectedFruit = (String) prieteni.getSelectedValue();
                label.setText(SelectedFruit);
                String chatWith = label.getText();
                String personJson = RestClient.callGetPersonByName(chatWith);

                Person personChatWith = getPersonByJson(personJson);


                chatWith=null;
                personJson=null;
                frame.dispose();

            }
        });



        prieteni.setVisibleRowCount(4);
        jcp = new JScrollPane(prieteni);
        frame.setIconImage(icon.getImage());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setLayout(new BorderLayout());
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(jcp, BorderLayout.CENTER);
        frame.add(buttonPanel,BorderLayout.EAST);
        frame.add(lowerPanel,BorderLayout.SOUTH);


        frame.setVisible(true);
        frame.setResizable(false);
//        frame.pack();;



    }
    protected Person getPersonByJson(String json)
    {
        return new Gson().fromJson(json,Person.class);
    }
    private List<Integer> getFriendsWithID(int id)
    {
        return new PrietenDAO().seAllFriendsWithId(id);
    }
    private String generateUsername(){
        Faker faker=new Faker();
        return faker.name().username().toLowerCase();
    }
    private String generatePassword(){
        return new Faker().internet().password(8,16,true,false,true);
    }




    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==backButton)
        {
            frame.dispose();
            LoggedInFrame fr= new LoggedInFrame(whoAmI);

        }
        if(e.getSource()==removeButton){
            Person p = getPersonByJson(RestClient.callGeTPersonByIdAPI(whoAmI));
            int answer = JOptionPane.showConfirmDialog(null,"Esti sigur ca vrei sa te delogezi de pe contul "+ p.getName()+"?","IMPORTANT",JOptionPane.YES_NO_CANCEL_OPTION);

            if(answer==0) {
                System.out.println(RestClient.callDeletepersonByIdAPi(whoAmI));
                backfunction(frame);
            }

        }

        if(e.getSource()==changePasswordButton)
        {
            Person p = getPersonByJson(RestClient.callGeTPersonByIdAPI(whoAmI));
            Person p2= getPersonByJson(RestClient.callGeTPersonByIdAPI(talkingTo));
            int answer = JOptionPane.showConfirmDialog(null, "Esti sigur ca vrei sa stergi prietenia dintre "+p.getName()+" si " + p2.getName()+
                    " ?","IMPORTANT", JOptionPane.YES_NO_OPTION);
            if(answer==0)
            {

                RestClient.callDeleteFriendship(whoAmI, talkingTo);
                System.out.println("done");
                frame.dispose();
                LoggedInFrame frame1 = new LoggedInFrame(whoAmI);
//                System.out.println(person);
//                backfunction(frame);
            }


        }

    }

    static void backfunction(JFrame frame2) {
        frame2.dispose();
        LoginFrame frame = new LoginFrame();
        frame.setTitle("Login Form");
        frame.setVisible(true);
        frame.setBounds(10, 10, 370, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
    }


}
