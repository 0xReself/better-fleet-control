<script setup lang="ts">
import { Polyline } from "vue3-google-map";

type Props = {
  carPosition: {
    lat: number;
    lng: number;
  };
  nextDestination?: {
    lat: number;
    lng: number;
  };
  customerTarget?: {
    lat: number;
    lng: number;
  };
  color?: string;
  routeState: "HIDDEN" | "DEFAULT" | "ACTIVE";
  userId?: string;
  plannedToPickup?: boolean;
	customerInTransport: boolean;
	vehicle: any;
};

const props = defineProps<Props>();

const defaultPathColor = computed(()=>{
	return props.color || '#A3A3A3'
})

const pathDotted = computed(() => {
  return {
    path: [props.nextDestination, props.customerTarget],
    geodesic: true,
    strokeColor: defaultPathColor.value,
    strokeOpacity: 0,
    scale: 3,
    icons: [
      {
        icon: {
          path: "M 0,-1 0,1",
          strokeOpacity: 1,
          scale: 3,
        },
        offset: "0",
        repeat: "15px",
      },
    ],
  };
});

const pathSolid = computed(() => {
  return {
    path: [props.carPosition, props.nextDestination],
    geodesic: true,
    strokeColor: defaultPathColor.value,
    strokeOpacity: 1.0,
    strokeWeight: 7,
  };
});
</script>

<template>
  <template v-if="props.nextDestination">
    <Polyline v-if="routeState != 'HIDDEN'" :options="pathSolid" />
    <BaseMarker
			v-if="!customerInTransport"
      variant="start"
      :position="props.nextDestination"
      :userId="props.userId"
      :color="props.color || ''"
      :active="routeState == 'ACTIVE'"
    />

    <BaseMarker
      v-if="props.customerTarget"
      variant="destination"
      :position="props.customerTarget"
      :color="props.color || ''"
      :active="routeState == 'ACTIVE'"
    />
    <Polyline
      v-if="!props.plannedToPickup && routeState == 'ACTIVE'"
      :options="pathDotted"
    />
  </template>

  <BaseCar
    :position="props.carPosition"
    :destination="props.nextDestination"
    :userColor="props.color"
		:userId="props.userId"
    :isCarEmpty="false"
		:vehicle="vehicle"
  />
</template>

