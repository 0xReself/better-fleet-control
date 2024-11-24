<script setup lang="ts">
import { GoogleMap, Polyline } from "vue3-google-map";

definePageMeta({
  layout: "map",
});

const center = { lat: 0, lng: -180 };
</script>
<template>
  <div>
    <GoogleMap
      api-key="AIzaSyCvikaFJtZTs8UpU7OvzASTjKHq_t6673g"
      class="w-full h-dvh"
      :center="center"
      :disable-default-ui="true"
      :zoom="4"
      :styles="jsonMapStyle"
    >
      <Polyline :options="flightPath" />
    </GoogleMap>
  </div>
</template>
