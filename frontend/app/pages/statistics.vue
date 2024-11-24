<script setup lang="ts">
// Main styles
import "@adyen/lume-vue3/styles";
import { LumeLineChart, LumeAxis } from "@adyen/lume-vue3";
import { BanknoteIcon, CarIcon, ClockIcon, LeafIcon, PercentIcon } from "lucide-vue-next";

//  average distance - relative x
//  waiting_Time - relative 
//  empty_travel_distance - relative
//  full_travel_distance - relative
//  vehilce_utilization - relative
//  total_number_of_trips - absolute x

const labels = ref(["1", "2"]);
const statistics = ref(null);

const climateBenefit = ref(0);
const waiting_selected = ref(true);
const money_selected = ref(false);
const vehicle_utilization = ref(0);
const total_number_of_trips = ref(0);
const end_point_total_number_of_trips = ref(0);
const avarageWaitingTime = ref(0);

const profit = ref(0);


const profit_graph = reactive([
  {
    label: "Expenses",
    color: "red",
    values: [1, 1, 1, 1, 2, 3],
  },
  {
    label: "Income",
    color: "green",
    values: [3, 4, 5, 3, 3, 1],
  }
]);

const waiting_time = reactive([
  {
    label: "Waiting time in min",
    color: "gold",
    values: [1, 1, 1, 1, 2, 3],
  },
]);

onMounted(async () => {
  setInterval(async () => {
    statistics.value = await queryData("/statistic");
    climateBenefit.value = await queryData("/climateBenefit");
    end_point_total_number_of_trips.value = await queryData("/getSingleInfos");


    waiting_time[0]!.values = statistics.value?.waitingTime.relative;
    profit_graph[0]!.values = multiplyArray(statistics.value?.emptyTravelDistance.absolute, 0.25);
    profit_graph[1]!.values = multiplyArray(statistics.value?.fullTravelDistance.absolute, 2.44); 
    vehicle_utilization.value = statistics.value?.vehilceUtilization.average;
    total_number_of_trips.value = end_point_total_number_of_trips.value?.numTotalTrips;
    avarageWaitingTime.value = statistics.value?.waitingTime.average.toFixed(2);
    profit.value = sumArray(profit_graph[1]!.values) - sumArray(profit_graph[0]!.values);
  }, 1000);
});

function multiplyArray(array: number[], constant: number) {
  return array.map((value) => (value * constant).toFixed(2));
}

function sumArray(array: string[]) {
  let arrayF = array.map((value) => parseFloat(value));
  
  return arrayF.reduce((a, b) => a + b, 0).toFixed(2);
}

</script>
<template>
  <div class="py-8 px-20 w-full">
    <h1 class="text-neutral-800 font-bold text-4xl">Statistics</h1>
    <p class="textx-xs text-neutral-500 mt-2 mb-4">
      All the information you need to know about your fleet
    </p>
    <div class="grid grid-cols-3 gap-6 w-full h-32 mx-auto ">
      <div class="col-span-1 bg-white h-full rounded-2xl overflow-hidden shadow-md border hover:bg-gray-100 hover:cursor-pointer" v-on:click="money_selected = true; waiting_selected = false;">
        <div class="p-4 flex justify-between">
          <p class="text-neutral-800 text-sm p-1 font-bold">Profits</p>
          <BanknoteIcon class="size-8 mr-2 text-zinc-300"></BanknoteIcon>
        </div>
        <div class="flex justify-between">
          <div class="pl-5">
            <p class="text-zinc-800 text-3xl font-bold -mt-3">
            {{ profit.toFixed(2) }} €
            </p>
            <p class="text-neutral-500 text-sm">Total amount of money profited</p>
          </div>
        </div>
      </div>

      <div class="col-span-1 bg-white h-full rounded-2xl overflow-hidden shadow-md border hover:bg-gray-100 hover:cursor-pointer" v-on:click="money_selected = false; waiting_selected = true;">
        <div class="p-4 flex justify-between">
          <p class="text-neutral-800 text-sm p-1 font-bold">Avarage waiting minutes</p>
          <ClockIcon class="size-8 mr-2 text-zinc-300"></ClockIcon>
        </div>
        <div class="pl-5">
          <p class="text-zinc-800 text-3xl font-bold -mt-3">
            {{ avarageWaitingTime }} min
          </p>
          <p class="text-neutral-500 text-sm">await-time after ordering a vehicle</p>
        </div>
      </div>
    </div>



    <div class="grid grid-cols-6 gap-6 w-full mt-6">
      <div class="col-span-4 bg-white flex-1 rounded-2xl overflow-hidden shadow-md h-[600px] p-8 pb-12">
        <template v-if="waiting_selected && !money_selected">
          <p class="font-bold text-lg">Avarage waiting time</p>
          <LumeLineChart v-if="waiting_time" :data="waiting_time"></LumeLineChart>
        </template>
        <template v-if="!waiting_selected && money_selected">
          <p class="font-bold text-lg">Income and expenses</p>
          <LumeLineChart v-if="profit_graph" :data="profit_graph"></LumeLineChart>
        </template>
      </div>

      <div class="col-span-2 bg-white h-full rounded-2xl overflow-hidden shadow-md">
        <div class="p-4 flex justify-between">
          <p class="text-neutral-800 text-sm p-1 font-bold">Amount of CO₂ safed</p>
          <LeafIcon class="size-8 mr-2 text-green-300"></LeafIcon>
        </div>
        <div class="pl-5 pb-5 border-b-[1px] -mt-3">
          <p class="text-zinc-800 text-3xl font-bold -mt-3">
            {{ climateBenefit.toFixed(2) }} kg
          </p>
          <p class="text-neutral-500 text-sm">Amount of CO₂ saved while using electric vehicles instead of gas powered ones.</p>
        </div>

        <div class="p-4 flex justify-between">
          <p class="text-neutral-800 text-sm p-1 font-bold">Number of trips</p>
          <CarIcon class="size-8 mr-2 text-zinc-300"></CarIcon>
        </div>
        <div class="pl-5 pb-5 border-b-[1px]">
          <p class="text-zinc-800 text-3xl font-bold -mt-3">
            {{ total_number_of_trips }}
          </p>
          <p class="text-neutral-500 text-sm">All the trips, all cars have done together</p>
        </div>

        <div class="p-4 flex justify-between">
          <p class="text-neutral-800 text-sm p-1 font-bold">Vehicle utilization</p>
          <PercentIcon class="size-8 mr-2 text-zinc-300"></PercentIcon>
        </div>
        <div class="pl-5 border-b-[1px] pb-4">
          <p class="text-zinc-800 text-3xl font-bold -mt-3">
            {{ (vehicle_utilization * 100).toFixed(2) }} %
          </p>
          <p class="text-neutral-500 text-sm">Percantage of time that the vehicle is active and not idling</p>
        </div>
        <p class="w-full text-center text-3xl text-gray-400">. . .</p>
      </div>
    </div>
  </div>
</template>
