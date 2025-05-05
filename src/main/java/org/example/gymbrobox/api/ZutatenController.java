package org.example.gymbrobox.api;


import org.example.gymbrobox.model.Zutat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ZutatenController {



    @GetMapping("/zutaten")
    @CrossOrigin(origins = "http://localhost:3000")
    @ResponseBody
    public List<Zutat> getZutaten() {

        // TODO:


        return new ArrayList<Zutat>();
    }




}
