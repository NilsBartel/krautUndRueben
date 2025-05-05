package org.example.gymbrobox.Service;


import org.example.gymbrobox.database.ZutatRepo;
import org.example.gymbrobox.model.Zutat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZutatService {
    private final ZutatRepo zutatRepo;

    public ZutatService(ZutatRepo zutatRepo) {
        this.zutatRepo = zutatRepo;
    }



    public List<Zutat> getZutaten() {
        return zutatRepo.getAll();
    }


}
