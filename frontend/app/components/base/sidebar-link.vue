<script setup lang="ts">
type Props = {
  icon: Component;
  to: string;
  name: string;
};

defineProps<Props>();
</script>
<template>
  <NuxtLink :to class="group hover:text-neutral-800 flex">
    <div class="rounded-lg gap-2 text-neutral-600 w-full flex items-center h-[32px] p-2">
      <div class="size-6 grid place-items-center">
        <component :is="icon" class="size-5"></component>
      </div>
      <p class="text-sm">{{ name }}</p>
    </div>
  </NuxtLink>
</template>
<style>
.router-link-active > div {
  @apply bg-white shadow-lg;
}
</style>
