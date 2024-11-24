<script setup lang="ts">
type Props = {
	placeholder: ""
}
defineProps<Props>();

const model = defineModel();
</script>
<template>
  <input
    class="rounded-lg border text-xs border-neutral-200 hover:shadow-lg min-w-0 text-neutral-600 flex items-center h-[32px] p-2"
    v-model="model"
    type="number"
    :placeholder="placeholder"
  />
</template>
