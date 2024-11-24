<script setup lang="ts">
import { MoreHorizontal } from "lucide-vue-next";
</script>
<template>
  <BaseSidebarSection class="flex justify-between">
    <div class="flex items-center gap-2">
      <img src="/img/profile-image.png" />
      <p class="text-md">T-Systems</p>
    </div>
    <div class="size-6 grid place-items-center">
      <MoreHorizontal class="size-5" />
    </div>
  </BaseSidebarSection>
</template>
