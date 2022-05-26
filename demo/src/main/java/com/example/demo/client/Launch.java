package com.example.demo.client;

import com.github.javafaker.Faker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


import static java.lang.System.exit;

public class Launch implements ActionListener {

    JFrame frame=new JFrame();

    JButton exitButton = new JButton("Ieșire");
    JButton registerButton = new JButton("Înregistrare");
    JButton loginButton = new JButton("Logare");

    Launch(){



        exitButton.setBounds(100,160,200,40);
        exitButton.setFocusable(false);
        exitButton.addActionListener(this);
        exitButton.setBorder(BorderFactory.createEtchedBorder());
        exitButton.setFont(new Font(null,Font.BOLD,20));

        registerButton.setBounds(100,160,200,40);
        registerButton.setFocusable(false);
        registerButton.addActionListener(this);
        registerButton.setBorder(BorderFactory.createEtchedBorder());
        registerButton.setFont(new Font(null,Font.BOLD,20));

        loginButton.setBounds(100,160,200,40);
        loginButton.setFocusable(false);
        loginButton.addActionListener(this);
        loginButton.setBorder(BorderFactory.createEtchedBorder());
        loginButton.setFont(new Font(null,Font.BOLD,20));


        JPanel buttonPanel = new JPanel();
        JLabel info = new JLabel("Proiect - programare avansata  grupa 2B2");
        info.setLayout(new FlowLayout());
        info.setVerticalAlignment(JLabel.CENTER);
        info.setHorizontalAlignment(JLabel.CENTER);
        info.setPreferredSize(new Dimension(200,100));
        info.setFont(new Font("MV Boli",Font.PLAIN,20));
        info.setForeground(new Color(0x123456));

        JLabel updated = new JLabel("Ultima actualizare la data de 25.05.2022");
        updated.setLayout(new FlowLayout());
        updated.setVerticalAlignment(JLabel.CENTER);
        updated.setHorizontalAlignment(JLabel.CENTER);
        updated.setPreferredSize(new Dimension(200,100));
        updated.setFont(new Font("MV Boli",Font.PLAIN,20));



        buttonPanel.setLayout(new GridLayout(3,1,10,10));
//        buttonPanel.add(myButton);

        buttonPanel.add(registerButton);
        buttonPanel.add(loginButton);
        buttonPanel.add(exitButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setLayout(new BorderLayout());
        frame.add(info, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(updated,BorderLayout.SOUTH);
        ImageIcon icon= new ImageIcon("src/main/java/Icon.png");

        frame.setIconImage(icon.getImage());
        frame.setVisible(true);
        frame.setResizable(false);
//        frame.pack();;



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
        if(e.getSource()== registerButton)
        {
              File file = new File("D:\\git\\GitHub\\PA-project\\demo\\src\\main\\java\\com\\example\\demo\\html\\formForPost.html");
              if(file.exists())
              {
                  if(!Desktop.isDesktopSupported())
                      JOptionPane.showMessageDialog(null,"ceva nu prea e ok, nu pot deschide chestia asta","Error",JOptionPane.ERROR_MESSAGE);

                  else
                  {
                      Desktop desktop= Desktop.getDesktop();
                      try {
                          String [] options ={"Manual", "Crează automat"};
//                         int option = JOptionPane.showConfirmDialog(null,"Completeaza pagina web infromatiile necesare","MESSAGE",JOptionPane.YES_NO_CANCEL_OPTION);


                         String opt = String.valueOf(JOptionPane.showInputDialog(null,"Crearea contului","message", JOptionPane.INFORMATION_MESSAGE,null,options,0));
                          if(opt.equals("Manual")) desktop.open(file);
                          else if(opt.equals("Crează automat")) {
                                String username= generateUsername();
                                String password = generatePassword();
                                RestClient.callCreatePersonUSerAPi(username,password);
                                JOptionPane.showMessageDialog(null,"Contul creat este nume = " +username+
                                        " parola = "+ password,"Cont creat",JOptionPane.INFORMATION_MESSAGE);
                          }
                      } catch (IOException ex) {
                          ex.printStackTrace();
                      }
                  }
              }
        }

        if(e.getSource() == loginButton)
        {
            frame.dispose();
//           LoginFrame myWindow = new LoginFrame();
            LoginFrame frame = new LoginFrame();
            frame.setTitle("Login Form");
            frame.setVisible(true);
            frame.setBounds(10, 10, 370, 600);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
        }
        if(e.getSource()==exitButton)
        {
            exit(0);
        }
    }
}
