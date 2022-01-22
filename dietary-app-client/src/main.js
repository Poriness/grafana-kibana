import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import "bootstrap";
import "bootswatch/dist/minty/bootstrap.min.css";
import { FontAwesomeIcon } from './plugins/font-awesome'
import FullCalendar from '@fullcalendar/vue3'

createApp(App)
    .use(router)
    .use(store)
    .component("font-awesome-icon", FontAwesomeIcon)
    .component("FullCalendar", FullCalendar)
    .mount("#app");
