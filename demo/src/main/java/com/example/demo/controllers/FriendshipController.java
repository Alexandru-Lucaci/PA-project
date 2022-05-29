package com.example.demo.controllers;


import com.example.demo.database.ContDAO;
import com.example.demo.database.PrietenDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/friendship")
public class FriendshipController {

    @GetMapping
    public List<Friendship> getFriendship(){
        List<Friendship> list= new ArrayList<>();
        try {
            list = new PrietenDAO().findAll();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    @PostMapping
    public int createProduct(@RequestParam int idFirst, @RequestParam int idSecond) {
        int id=-1;
        try{
            new PrietenDAO().create(idFirst,idSecond);
        }
        catch (SQLException e){
            e.printStackTrace();

        }
        return id;
    }
    @PostMapping("/add")
    public ResponseEntity<String> createProduct(@RequestParam Friendship friendship) {

        try{
            new PrietenDAO().create(friendship.getIdFirst(), friendship.getIdSecond());
        }
        catch (SQLException e){
            e.printStackTrace();

        }
        return new ResponseEntity<>(
                "Product created successfully", HttpStatus.CREATED);
    }



    private int getMaxValKey(Map<Integer,Integer> map){

        int maximum =0;
        int savedKey=-1;
        for( Integer m: map.keySet()){
            if(map.get(m)>maximum) {
                maximum = map.get(m);
                savedKey=m;
            }
        }
        return savedKey;

    }

    private Map<Integer,Integer> getFrequency(List<Friendship> friendshipList)
    {
        Map<Integer, Integer> frequency=new HashMap<>();
        for(Friendship friendship : friendshipList)
        {
            frequency.put(friendship.getIdFirst(),0);
            frequency.put(friendship.getIdSecond(),0);
        }
        for(Friendship friendship : friendshipList)
        {
            frequency.put(friendship.getIdFirst(),frequency.get(friendship.getIdFirst())+1);
            frequency.put(friendship.getIdSecond(),frequency.get(friendship.getIdSecond())+1);
        }
        return frequency;
    }


    @DeleteMapping("/{id1}/{id2}")
    public ResponseEntity<String> deleteFriendshipBetween2(@PathVariable int id1, @PathVariable int id2)
    {

        try{
            new PrietenDAO().deleteFriendshipWith(id1,id2);
            return new ResponseEntity<>("Am reusit sa inchei relatia", HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }

        return  new ResponseEntity<>("Ceva nu a fost ok", HttpStatus.GONE);

    }



    @GetMapping("/{k}")
    public List<Person> getFirstK(@PathVariable int k){
        List<Friendship> friendshipList = getFriendship();
///     verific daca am suficiente persoane

        if(new PrietenDAO().numberOfIds()<k )
        {
            System.out.println("Not enough relationships");
            return null;
        }

        // vector de frecventa pentru toate id-urile
        else {
            Map<Integer, Integer> frequency=getFrequency(friendshipList);
            int max;
            List<Person> response = new ArrayList<>();
            while(k!=0)
            {
                max = getMaxValKey(frequency);
                try {
                    response.add( new ContDAO().getFullPersonById(max));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                frequency.remove(max);
                k--;
            }
            return response;
        }
    }

}