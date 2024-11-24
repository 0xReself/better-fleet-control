<script setup lang="ts">
import { CustomMarker } from "vue3-google-map";

type Props = {
  position: {
    lat: number;
    lng: number;
  };
  destination?: {
    lat: number;
    lng: number;
  };
  userColor?: string;
	vehicle: any;
};

const rotatedCar = computed(() => {
  return {
    transform: `rotate(${calculateAngle()}deg)`,
  };
});

function calculateAngle() {
  if (!props.destination || !props.position) {
    return Math.floor(Math.random() * 360) + 0;
  }
  return (
    (Math.atan2(
      props.destination.lng - props.position.lng,
      props.destination.lat - props.position.lat,
    ) *
      180) /
    Math.PI
  );
}

const props = defineProps<Props>();
</script>

<template>
  <CustomMarker :options="{ position: position, anchorPoint: 'CENTER' }">
    <div :style="rotatedCar" class="relative">
      <img src="/img/Car.svg" alt="Car" width="30" class="z-10 relative" />
      <div
				v-if="vehicle.customerInTransport !== null"
        class="size-10 absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 rounded-full blur-md"
        :style="`background-color: ${userColor}`"
      ></div>
    </div>
  </CustomMarker>
</template>
