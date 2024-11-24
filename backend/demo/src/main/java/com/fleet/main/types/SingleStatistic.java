package com.fleet.main.types;

import java.util.List;

import com.fleet.main.monitor.DataPoint;

public class SingleStatistic {
    public String name;
    public List<Double> absolute; 
    public double[] relative; 
    public List<Double> counter;
    public double average;

    public SingleStatistic(String name, DataPoint dp){
        this.name = name;
        absolute = dp.values.subList(0, dp.values.size() - 1);
        counter = dp.counter.subList(0, dp.counter.size() - 1);;
        relative = dp.getRelativeValueList();
        average = dp.avgLast(5);
    }
}
