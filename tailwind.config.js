/**
 * Minimal Tailwind config. This project currently uses Bootstrap CDN for quick styling.
 * If you want to enable Tailwind, install it and configure the build pipeline (npm, PostCSS).
 */
module.exports = {
  content: ['./src/main/resources/templates/**/*.html', './src/main/resources/static/js/**/*.js'],
  theme: {
    extend: {},
  },
  plugins: [],
};
