<script setup lang="ts">
import { CustomMarker } from 'vue3-google-map'
import { FlagIcon } from 'lucide-vue-next'

type Props = {
    position: {
        lat: number
        lng: number
    },
    color: string
}

defineProps<Props>()

</script>

<template>
    <CustomMarker :options="{ position: position, anchorPoint: 'CENTER' }">
        <div class="rounded-full size-8 aspect-square content-center text-center text-white font-bold text-base grid place-items-center"
            :class="color">
            <FlagIcon fill="white" class="size-4"></FlagIcon>
        </div>
    </CustomMarker>
</template>