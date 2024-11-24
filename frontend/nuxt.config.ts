// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  compatibilityDate: "2024-11-01",
	future: {
    compatibilityVersion: 4,
  },
  css: ["@/app.css"],
  app: {
    head: {
			title: "T-Systems Fleet Controller",
      link: [{ rel: "icon", type: "image/x-icon", href: "/favicon.png" }],
    },
  },
  devtools: { enabled: false },
  postcss: {
    plugins: {
      tailwindcss: {},
      autoprefixer: {},
    },
  },
  ssr: false
});
