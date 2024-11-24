<script setup lang="ts">
import { GoogleMap } from "vue3-google-map";

type Props = {
	vehicles: any;
	customers: any;
};

const chargingStations = [
	{ lat: 48.13, lng: 11.53 },
	{ lat: 48.16, lng: 11.63 },
];

const center = { lat: 48.137234805727786, lng: 11.575433705689408 };
const mapRef = ref(null);

const routeState = "ACTIVE"; // Needs to be changed to a button input

const updateBlocked = ref(false);

const props = defineProps<Props>();

watch(
	//@ts-ignore
	() => mapRef.value?.ready,
	(ready) => {
		if (!ready) return;
		// updateMap();
	},
);

const filteredCustomers = computed(() => {
	if (!props.customers) return;
	return props.customers.filter((customer: any) => !customer.plannedToPickup && !customer.pickedUp);
});

function timeout_update() {
	setTimeout(() => {
		updateBlocked.value = true;
	}, 100);
}

function block() {
	updateBlocked.value = true;
}

function unblock() {
	updateBlocked.value = false;
}

function updateMap() {
	setInterval(() => {
		if (!updateBlocked) {
			mapRef.value?.map.setTilt(0);
		}
	}, 10);
}

function getCustomer(vehicle: any) {
	if (vehicle.targetToPickup) {
		return vehicle.targetToPickup;
	}
	if (vehicle.customerInTransport) {
		return vehicle.customerInTransport;
	}
	return null;
}

function getCustomerData(vehicle: any, attributeName: string) {
	const customer = getCustomer(vehicle);
	return customer ? customer[attributeName] : null;
}

function refactorIDs(id: string) {
	return id ? id.slice(-2).toUpperCase() : null;
}

function refactorPositions(point: { x: number; y: number }) {
	return point ? { lng: point.y, lat: point.x } : null;
}
</script>
<template>
	<GoogleMap @zoom_changed="timeout_update()" @dragstart="block()" @dragend="unblock()" ref="mapRef"
		api-key="YOUR_API_KEY" class="w-full h-screen" :center="center" :zoom="13" :disable-default-ui="true"
		:styles="jsonMapStyle">
		<BaseChargingStation :position="chargingStations[0]" />
		<BaseChargingStation :position="chargingStations[1]" />
		<BaseRoute v-for="vehicle of vehicles" :carPosition="refactorPositions(vehicle.currentPosition)"
			:nextDestination="refactorPositions(vehicle.targetPosition)" :customerTarget="refactorPositions(getCustomerData(vehicle, 'endPosition'))
				" :routeState="routeState" :userId="refactorIDs(getCustomerData(vehicle, 'id'))"
			:color="getCustomerData(vehicle, 'customerColor')" :plannedToPickup="vehicle.plannedToPickup"
			:customerInTransport="vehicle.customer?.customerInTransport !== null" :vehicle="vehicle" />

		<BaseMarker v-for="filteredCustomer of filteredCustomers" variant="start" :userId="refactorIDs(filteredCustomer.id)"
			:color="filteredCustomer.customerColor" :position="refactorPositions(filteredCustomer.currentPosition)" />
	</GoogleMap>
</template>
