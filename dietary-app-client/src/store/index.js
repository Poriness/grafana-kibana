import {createStore} from "vuex";
import {auth} from "./auth.module";

const store = createStore({
    modules: {
        auth,
    },
    state: {
        calendar: {
            dietitianId: 0,
            clientId: 0,
        }
    },
});

export default store;