<script setup lang="ts">
import { BatteryFull, FlagIcon, BatteryMedium, BatteryLow, ChevronsRight } from "lucide-vue-next";
type Props = {
  active?: boolean;
  vehicle: any;
};

const { active = false, vehicle } = defineProps<Props>();

const formattedTravelTime = computed(() => {
  return Math.round(vehicle.remainingTravelTime);
  let date = new Date(vehicle.remainingTravelTime);
  return date.toLocaleTimeString("de-DE", {
    timeStyle: "short",
  });
});

const distancePercentage = computed(() => {
  return (1 - vehicle.remainingDistancePercentage) * 100;
});

const batteryColor = computed(() => {
  if (vehicle.battery > 0.6) {
    return 'text-green-600';
  } else if (vehicle.battery > 0.3) {
    return 'text-amber-600';
  }
  return 'text-red-600';
});

const currentTask = computed(() => {
  if (vehicle.targetToPickup != null) return 'Driving to pickup';
  if (vehicle.customerInTransport != null) return 'Driving to destination';
  return 'Charging..';
});

</script>
<template>
  <div class="p-2 rounded-lg flex flex-col gap-2 border group" :class="active ? 'bg-white shadow-lg' : 'bg-neutral-100 hover:bg-neutral-200'
    ">
    <div class="flex justify-between">
      <div>
        <p class="text-neutral-800">{{ vehicle.modelName }}</p>
        <p class="text-xs text-neutral-400 uppercase">
          {{ `#${vehicle.id.slice(-5)}` }}
        </p>
      </div>
      <div>
        <BaseTag :class="batteryColor">
          <BatteryFull v-if="vehicle.battery > 0.6" class="size-5"></BatteryFull>
          <BatteryMedium v-else-if="vehicle.battery > 0.3" class="size-5"></BatteryMedium>
          <BatteryLow v-else class="size-5"></BatteryLow>
          <p class="text-xs">{{ Math.round(vehicle.battery * 100) }}%</p>
        </BaseTag>
      </div>
    </div>
    <div class="h-px w-full" :class="active ? 'bg-neutral-100' : 'bg-neutral-200 group-hover:bg-neutral-300'
      "></div>
    <div>
      <p class="text-xs text-neutral-400 mb-2">{{ currentTask }}</p>
      <div class="flex items-center gap-2">

        <div v-if="vehicle.customerInTransport != null"
          :style="`background-color: ${vehicle.customerInTransport.customerColor}`"
          class="size-6 bg-neutral-300 text-white rounded-full grid place-items-center font-bold text-xs">
          <span class="text-xs">
            {{ vehicle.customerInTransport.id.slice(-2).toUpperCase() }}
          </span>
        </div>
        <div v-else class="size-6 bg-neutral-300 rounded-full grid place-items-center">
          <ChevronsRight class="size-4" />
        </div>
        <div class="flex-1 flex flex-col gap-1">
          <div class="flex items-center gap-2 text-xs text-neutral-400">
            <p>{{ Math.round(formattedTravelTime * 0.5) }} minutes remaining</p>
          </div>
          <div class="p-[2px] bg-white rounded-full border border-neutral-200">
            <div class="bg-neutral-700 h-[6px] rounded-full transition-all duration-1000 ease-linear"
              :style="`width: ${distancePercentage}%;`"></div>
          </div>
        </div>

        <div v-if="vehicle.targetToPickup != null" :style="`background-color: ${vehicle.targetToPickup.customerColor}`"
          class="size-6 bg-neutral-300 text-white rounded-full grid place-items-center font-bold text-xs">
          <span class="text-xs">
            {{ vehicle.targetToPickup.id.slice(-2).toUpperCase() }}
          </span>
        </div>
        <div v-else-if="vehicle.customerInTransport != null"
          class="size-6 bg-neutral-300 text-green-600 fill-green-600 rounded-full grid content-center text-center place-items-center">
          <FlagIcon class="size-3" fill="fill-green-600"></FlagIcon>
        </div>
        <div v-else-if="vehicle.charging == true"
          class="size-6 bg-neutral-900 rounded-full text-white grid place-items-center">
          <BatteryMedium class="size-4 text-green-400" />
        </div>
      </div>
    </div>
  </div>
</template>
