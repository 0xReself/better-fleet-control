<script setup lang="ts">
import {
  User2Icon,
  FlagIcon,
  ArrowBigRightDash,
  BatteryCharging,
  ChevronsRight,
  Clock,
} from "lucide-vue-next";

const statsData = ref({});
onMounted(async () => {
  setInterval(async () => {
    statsData.value = await queryData("/getSingleInfos");
  }, 1000);
});

const vehicleStats = computed(() => {
  return [
    {
      icon: FlagIcon,
      name: "Trips",
      color: "text-neutral-500",
      number: statsData.value.numTotalTrips || 0,
    },
    {
      icon: ArrowBigRightDash,
      color: "text-neutral-500",
      name: "Total Distance",
      number: statsData.value.totalDistanceTraveled || 0,
      suffix: "km",
    },
    {
      icon: BatteryCharging,
      color: "text-green-600",
      name: "Charging",
      number: statsData.value.numChargingVehicle || 0,
    },
    {
      icon: ChevronsRight,
      color: "text-blue-600",
      name: "In use",
      number: statsData.value.numBlockedVehicle || 0,
    },
    {
      icon: Clock,
      color: "text-amber-600",
      name: "Idle",
			number: statsData.value.numAvailableVehicle || 0,
    },
  ];
});
</script>
<template>
  <div class="bg-neutral-100 rounded-xl">
    <BaseSidebarStat
      v-for="stat in vehicleStats"
      :icon="stat.icon"
      :name="stat.name"
      :number="stat.number"
      :color="stat.color"
      :suffix="stat.suffix ? stat.suffix : null"
    />
  </div>
</template>
