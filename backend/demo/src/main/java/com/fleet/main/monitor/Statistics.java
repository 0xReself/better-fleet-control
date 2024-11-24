package com.fleet.main.monitor;

import com.fleet.main.types.SingleStatistic;

public class Statistics {
    public SingleStatistic averageDistanceTraveled = new SingleStatistic("average_distance_traveled", MonitorData.travelDistanceDatapoint); 
    public SingleStatistic waitingTime = new SingleStatistic("waiting_Time", MonitorData.waitingTimeDatapoint);
    public SingleStatistic emptyTravelDistance = new SingleStatistic("empty_travel_distance", MonitorData.emptyTravelDatapoint);
    public SingleStatistic fullTravelDistance = new SingleStatistic("full_travel_distance", MonitorData.fullTravelDatapoint);
    public SingleStatistic vehilceUtilization = new SingleStatistic("vehilce_utilization", MonitorData.vehilceUtilizationDatapoint);
    public SingleStatistic totalNumberOfTrips = new SingleStatistic("total_number_of_trips", MonitorData.numberOfTotalTripsDatapoint); 
}
