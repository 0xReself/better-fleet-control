<script setup lang="ts">
import { CustomMarker } from "vue3-google-map";
import { BatteryCharging } from "lucide-vue-next";
type Props = {
  position: { lat: number; lng: number };
};

const props = defineProps<Props>();
</script>
<template>
  <CustomMarker :options="{ position: props.position, anchorPoint: 'CENTER' }">
    <div class="bg-neutral-800 size-10 place-items-center grid rounded-xl">
      <BatteryCharging class="text-green-300" />
    </div>
  </CustomMarker>
</template>
