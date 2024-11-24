<script setup lang="ts">
definePageMeta({
  layout: "map",
});

const vehicles = ref(null);
const customers = ref(null);
const useLerp = ref(true);

onMounted(async () => {
  setInterval(async () => {
    vehicles.value = await queryData("/vehicles");
    customers.value = await queryData("/customers");
		if(useLerp) {
			lerpStuff();
		}
  }, 1000);
});

const updateSpeed = 10; 
function lerpStuff() {
  const interval = setInterval(() => {
		if(!vehicles.value) return;
		for (let vehicle of vehicles.value) {
			if(vehicle.targetPosition) {
				vehicle.currentPosition = lerp(vehicle.currentPosition, vehicle.targetPosition, updateSpeed / 100, vehicle.vehicleSpeed)
			}
		}
  }, updateSpeed);
  setTimeout(() => {
    clearInterval(interval);
  }, 1000);
}

const filteredVehicles = computed(() => {
  if (!vehicles.value) return;
  let filtered = [];
  for (let vehicle of vehicles.value) {
    if (vehicle.targetPosition) filtered.push(vehicle);
  }
  return filtered;
});
</script>
<template>
  <div>
    <!--<pre class="absolute top-0 z-50 bg-white">
      {{ vehicles }}
</pre>-->
    <BaseMap :vehicles="vehicles" :customers="customers"></BaseMap>
    <BaseSidebar class="absolute right-0 top-0">
      <BaseSidebarSection class="flex justify-between">
        <p>Activity</p>
        <BaseTag>
          <div class="size-2 bg-red-500 animate-pulse rounded-full"></div>
          <p class="uppercase">Live</p>
        </BaseTag>
      </BaseSidebarSection>
      <BaseSidebarSection
        class="min-w-[300px] flex flex-col gap-2 flex-1 overflow-auto"
      >
        <BaseCarActivityCard
          v-for="vehicle in filteredVehicles"
          :id="`#${vehicle.id.slice(-5)}`"
          :vehicle="vehicle"
        />
      </BaseSidebarSection>
    </BaseSidebar>
  </div>
</template>
