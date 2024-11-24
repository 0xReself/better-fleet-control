<script setup lang="ts">
const numVehicles = ref("");
const numCustomer = ref("");

async function resetScenario() {
  await postData(
    `/resetScenario?num_vehicles=${numVehicles.value}&num_customers=${numCustomer.value}&ENV_STEP_SPEED=1.`,
  );
	numVehicles.value = ""
	numCustomer.value = ""
  console.log("reset");
}
</script>

<template>
  <form
    @submit.prevent="resetScenario"
    class="flex flex-col align-items-center p-3 gap-2"
  >
    <div class="flex gap-2 w-full">
      <BaseInput class="flex-1" v-model="numVehicles" placeholder="vehicles" />
      <BaseInput class="flex-1" v-model="numCustomer" placeholder="customers" />
    </div>
    <button
      @click="resetScenario()"
      class="h-[28px] text-xs text-white bg-black rounded-lg w-full"
    >
      Create random scenario
    </button>
  </form>
</template>
