import {createRouter, createWebHistory} from "vue-router";
import Home from "./components/Home.vue";
import Login from "./components/Login.vue";
import Register from "./components/Register.vue";

//import Calendar from "./components/CalendarClient.vue";
// lazy-loaded
const Profile = () => import("./components/Profile.vue")
const BoardAdmin = () => import("./components/BoardAdmin.vue")
const BoardModerator = () => import("./components/BoardCustomerService.vue")
const BoardUser = () => import("./components/BoardUser.vue")
const CalendarClient = () => import("./components/CalendarClient.vue")
const CalendarDietitian = () => import("./components/CalendarDietitian.vue")
const CalendarCustomerService = () => import("./components/CalendarCustomerService.vue")
const RegisterCS = () => import("./components/RegisterCS.vue")
const Activation = () => import("./components/Activation.vue")
const Assignment = () => import("./components/Assignment.vue")

const routes = [
    {
        path: "/",
        name: "home",
        component: Home,
    },
    {
        path: "/home",
        component: Home,
    },
    {
        path: "/login",
        component: Login,
    },
    {
        path: "/register",
        component: Register,
    },
    {
        path: "/profile",
        name: "profile",
        // lazy-loaded
        component: Profile,
    },
    {
        path: "/admin",
        name: "admin",
        // lazy-loaded
        component: BoardAdmin,
    },
    {
        path: "/mod",
        name: "moderator",
        // lazy-loaded
        component: BoardModerator,
    },
    {
        path: "/user",
        name: "user",
        // lazy-loaded
        component: BoardUser,
    },
    {
        path: "/calendar-client",
        name: "client-calendar",
        // lazy-loaded
        component: CalendarClient,
    },
    {
        path: "/cs-calendar",
        name: "customer-service-calendar",
        // lazy-loaded
        component: CalendarCustomerService,
    },
    {
        path: "/calendar-dietitian",
        name: "dietitian-calendar",
        // lazy-loaded
        component: CalendarDietitian,
    },
    {
        path: "/cs-register",
        name: "cs-register",
        // lazy-loaded
        component: RegisterCS,
    },

    {
        path: "/cs-activate",
        name: "cs-activation",
        // lazy-loaded
        component: Activation,
    },
    {
        path: "/cs-assign",
        name: "assign",
        // lazy-loaded
        component: Assignment,
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

router.beforeEach((to, from, next) => {
    const publicPages = ['/login', '/register', '/home'];
    //const csPages = ['/cs-calendar', '/cs-register', '/cs-activate', '/cs-assign'];
    const authRequired = !publicPages.includes(to.path);
    const loggedIn = localStorage.getItem('user');
    // const isCs = loggedIn.role === 'CUSTOMERSERVICE'

    // trying to access a restricted page + not logged in
    // redirect to login page
    if (authRequired && !loggedIn) {
        next('/login');
    } else {
        next();
    }
});

export default router;