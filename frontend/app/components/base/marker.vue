<script setup lang="ts">
import { CustomMarker } from "vue3-google-map";
import { FlagIcon } from "lucide-vue-next";

type Props = {
  active?: boolean;
  variant: "start" | "destination";
  position: {
    lat: number;
    lng: number;
  };
  userId?: string;
  color: string;
};

defineProps<Props>();

</script>

<template>
  <CustomMarker :options="{ position: position, anchorPoint: 'CENTER' }">
    <div
      class="rounded-full size-8 aspect-square content-center text-center text-white font-bold text-base grid place-items-center"
      :style="`background-color: ${color}`">
      <div v-if="active"
        class="pulse-circle absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 rounded-full size-14 aspect-square content-center text-center font-bold text-base grid place-items-center opacity-20"
        :style="`background-color: ${color}`"></div>
      <div class="relative z-10">
        <FlagIcon v-if="variant === 'destination'" fill="white" class="size-4"></FlagIcon>
        <span v-else>
          {{ userId }}
        </span>
      </div>
    </div>
  </CustomMarker>
</template>
<style>
@keyframes pulse-out {
  0% {
    transform: translate(-50%, -50%) scale(0);
    opacity: 100%;
  }

  100% {
    transform: translate(-50%, -50%) scale(1);
    opacity: 0%;
  }
}

.pulse-circle {
  animation-name: pulse-out;
  animation-iteration-count: infinite;
  animation-duration: 800ms;
}
</style>
