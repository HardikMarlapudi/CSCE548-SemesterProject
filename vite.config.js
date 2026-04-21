import { defineConfig } from "vite";

export default defineConfig({
  plugin: [react()],
  /* Required for GitHub Pages */
  base: "/CSCE548-SemesterProject/",
});
