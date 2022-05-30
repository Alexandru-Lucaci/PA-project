package com.example.demo.controllers;


import com.example.demo.database.MessageFriendDAO;
import com.example.demo.database.PrietenDAO;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @GetMapping
    public List<Message> getMessages(){
        List<Message> list= new ArrayList<>();
        try {
            list = new MessageFriendDAO().findAllMessages();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    @PostMapping
    public int createProduct(@RequestParam int idFirst, @RequestParam int idSecond,@RequestParam String message) {
        int id=-1;
        try{

            // add to the database
            new MessageFriendDAO().create(message,idFirst,idSecond);

            // generate all the messages
            List<Message> messageList = new MessageFriendDAO().findAllMessages();
//            System.out.println(messageList);
            for(Message mesaj : messageList)
                try {
                    if (mesaj.getIdSender() == idFirst && mesaj.getIdReciever() == idSecond && mesaj.getMessage().equals(message.toUpperCase())) {
//                    System.out.println(mesaj);
                        // get the new id message that i added
                        id = mesaj.getId();
                    }
                }catch (NullPointerException e)
                {
                    System.out.println("");
                }
        }
        catch (SQLException e){
            e.printStackTrace();

        }
        return id;
    }

}
