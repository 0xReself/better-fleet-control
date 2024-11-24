package com.fleet.main.monitor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

public class DataPoint {
    public List<Double> values = new ArrayList<Double>();
    public List<Double> counter = new ArrayList<Double>();

    public void addPoint(double value){
        this.counter.set(this.counter.size() - 1, this.counter.get(this.counter.size() - 1) + 1);
        this.values.set(this.values.size() - 1, this.values.get(this.values.size() - 1) + value);
    }

    public DataPoint(){
        this.values.add(0.);
        this.counter.add(0.);
    }

    public void endTimeStep(){
        this.values.add(0.);
        this.counter.add(0.);
    }

    public List<Double> getAbsoluteValueList(){
        return values;
    }

    public double[] getRelativeValueList(){
        double[] relList = new double[this.values.size() - 1];
        for(int i = 0; i < this.values.size() - 1; i++){
            double value = this.counter.get(i);
            if(value != 0){
                relList[i] = this.values.get(i) / value;
            }
            
        }
        return relList;
    }

    public double avgLast(int windowSize){
        int len = Math.min(windowSize, this.values.size() - 1);
        double sum = 0;
        for(int i = 0; i < len; i++){
            sum += (this.values.get(this.values.size() - i - 2) / this.counter.get(this.values.size() - i - 2));
        }
        if(len == 0){
            return 0;
        }
        return sum / len;
    }
}
