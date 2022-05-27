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

public class LoggedInFrame extends JFrame implements ActionListener {
    private final JScrollPane jcp;
    private final int whoAmI;
    JFrame frame=new JFrame();
    JTextField info = new JTextField();
    JButton exitButton = new JButton("Ieșire");
    JButton registerButton = new JButton("Înregistrare");
    JButton loginButton = new JButton("Logare");
    JButton adaugaPrietenButton = new JButton("Adauga prieten");
    JButton changePasswordButton = new JButton("Schimba parola");

    JButton removeButton= new JButton("DELETE ACCOUNT");
    JButton backButton = new JButton("BACK");

    LoggedInFrame(int id){

        whoAmI=id;
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

        adaugaPrietenButton.setFont(new Font(null,Font.BOLD,20));
        adaugaPrietenButton.setBounds(100,160,200,60);
        adaugaPrietenButton.setFocusable(false);
        adaugaPrietenButton.addActionListener(this);
        adaugaPrietenButton.setBorder(BorderFactory.createEtchedBorder());
        adaugaPrietenButton.setFont(new Font(null,Font.BOLD,20));



        info.setBounds(150, 150, 150, 30);





        JPanel buttonPanel = new JPanel();
//     Basic text
        JLabel info3= new JLabel("Lista de prieteni ");
        info3.setLayout(new FlowLayout());
        info3.setVerticalAlignment(JLabel.CENTER);
        info3.setHorizontalAlignment(JLabel.CENTER);
        info3.setPreferredSize(new Dimension(200,45));
        info3.setFont(new Font("MV Boli",Font.PLAIN,20));
        info3.setForeground(new Color(0x123456));

        Person p = getPersonByJson(RestClient.callGeTPersonByIdAPI(whoAmI));
//        Basic Text
        JLabel info2 = new JLabel(p.getName());
        info.setLayout(new FlowLayout());
        info2.setVerticalAlignment(JLabel.CENTER);
//        insert text
        info.setHorizontalAlignment(JLabel.CENTER);
        info.setPreferredSize(new Dimension(100,45));
        info.setFont(new Font("MV Boli",Font.PLAIN,20));
        info.setForeground(new Color(0x123456));
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2,2,10,10));
//        topPanel.add(new JPanel("Lista de prieteni "));
        info2.setLayout(new FlowLayout());
//        info.setVerticalAlignment(JLabel.CENTER);
        info2.setHorizontalAlignment(JLabel.CENTER);
        info2.setPreferredSize(new Dimension(200,45));
        info2.setFont(new Font("MV Boli",Font.PLAIN,20));
        info2.setForeground(new Color(0x123456));
        topPanel.add(info2);
        topPanel.add(info3);
        topPanel.add(info);
        topPanel.add(adaugaPrietenButton);



        JLabel updated = new JLabel("Ultima actualizare la data de 27.05.2022");
        updated.setLayout(new FlowLayout());
        updated.setVerticalAlignment(JLabel.CENTER);
        updated.setHorizontalAlignment(JLabel.CENTER);
        updated.setPreferredSize(new Dimension(200,100));
        updated.setFont(new Font("MV Boli",Font.PLAIN,20));



        buttonPanel.setLayout(new GridLayout(1,3,10,10));
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



            @Override
            public void valueChanged(ListSelectionEvent e) {
                String SelectedFruit = (String) ListFruits.getSelectedValue();
                label.setText(SelectedFruit);
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
        frame.add(buttonPanel,BorderLayout.SOUTH);


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
            backfunction(frame);

        }
        if(e.getSource()==removeButton){
            Person p = getPersonByJson(RestClient.callGeTPersonByIdAPI(whoAmI));
            int answer = JOptionPane.showConfirmDialog(null,"Esti sigur ca vrei sa stergi contul "+ p.getName()+"?","IMPORTANT",JOptionPane.YES_NO_CANCEL_OPTION);

            if(answer==0) {
                System.out.println(RestClient.callDeletepersonByIdAPi(whoAmI));
                backfunction(frame);
            }

        }

        if(e.getSource()==changePasswordButton)
        {
            Person p = getPersonByJson(RestClient.callGeTPersonByIdAPI(whoAmI));
            int answer = JOptionPane.showConfirmDialog(null, "Esti sigur ca vrei sa schimbi parola contului "+p.getName()+"?","IMPORTANT", JOptionPane.YES_NO_OPTION);
            if(answer==0)
            {
                String newPassword = JOptionPane.showInputDialog("Introdu parola noua: ");
                Person person= RestClient.changePassword(whoAmI,newPassword);
                System.out.println(person);
                backfunction(frame);
            }


        }
        if(e.getSource()==adaugaPrietenButton){
            String text = info.getText().toUpperCase();
            System.out.println(text);
            // get the id from the bd using username
            String cautat = RestClient.callGetPersonByName(text);

            if(cautat==null){

                JOptionPane.showMessageDialog(null, "Nu exista contul asta, scuze");
            }
            else{
                Gson gson= new Gson();
                Person person = gson.fromJson(cautat,Person.class);
                int id= person.getId();
                Friendship fr =new Friendship(whoAmI,id);
                System.out.println(fr);

                if(RestClient.callCreateFriendshipUSerAPi(fr).equals(fr)){
                    JOptionPane.showMessageDialog(null, "L-am adaugat pe "+fr.getNameSecond()+" in lista cu prieteni","SUCCESFULLY ADDED",JOptionPane.INFORMATION_MESSAGE);
                }

                frame.dispose();
                LoggedInFrame f = new LoggedInFrame(whoAmI);

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
