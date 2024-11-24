package com.fleet.main.monitor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fleet.main.types.SingleStatistic;

@RestController
public class MonitorController {
    
    @GetMapping(value = "/statistic")
    public Statistics getStatistic() {
        return new Statistics();
    }
    
}
