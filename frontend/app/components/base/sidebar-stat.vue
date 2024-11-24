<script setup lang="ts">
import type { FunctionalComponent } from "vue";

type Props = {
  icon: FunctionalComponent;
  name: string;
  number: number;
  color: string;
  suffix?: string;
};
defineProps<Props>();
</script>
<template>
  <div class="flex items-center gap-2 px-4 py-2 justify-between hover:bg-neutral-200">
    <div class="flex items-center gap-2">
      <component :is="icon" class="size-4" :class="color" />
      <p class="text-xs text-neutral-500">{{ name }}</p>
    </div>
    <div class="px-2 py-1 bg-neutral-200 rounded-full text-xs text-neutral-700">{{ number }} {{ suffix }}</div>
  </div>
</template>
