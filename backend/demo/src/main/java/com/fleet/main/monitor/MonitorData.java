package com.fleet.main.monitor;

import com.fleet.main.ScenarioRunner;

public class MonitorData {
    public static DataPoint waitingTimeDatapoint = new DataPoint(); 
    public static DataPoint travelDistanceDatapoint = new DataPoint(); 
    public static DataPoint emptyTravelDatapoint = new DataPoint(); 
    public static DataPoint fullTravelDatapoint = new DataPoint(); 
    public static DataPoint vehilceUtilizationDatapoint = new DataPoint(); 
    public static DataPoint numberOfTotalTripsDatapoint = new DataPoint(); 

    public static void endStep(){
        waitingTimeDatapoint.endTimeStep();
        travelDistanceDatapoint.endTimeStep();
        emptyTravelDatapoint.endTimeStep();
        fullTravelDatapoint.endTimeStep();
        vehilceUtilizationDatapoint.endTimeStep();
        numberOfTotalTripsDatapoint.endTimeStep();
    }

}
