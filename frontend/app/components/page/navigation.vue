<script setup lang="ts">
import { Map, LineChart, Car } from "lucide-vue-next";
</script>
<template>
  <BaseSidebar class="max-w-[280px]">
    <div class="flex-1">
      <BaseUserSwitcher />
      <nav class="p-3 gap-1 flex flex-col">
        <BaseSidebarLink to="/" name="Live Map" :icon="Map" />
        <BaseSidebarLink to="/statistics" name="Statistics" :icon="LineChart" />
        <BaseSidebarLink to="/fleet" name="Fleet" :icon="Car" />
      </nav>
    </div>
    <div>
      <BaseSidebarStats />
      <BaseSidebarGenerator />
    </div>
  </BaseSidebar>
</template>
