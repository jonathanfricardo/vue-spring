import { createRouter, createWebHashHistory } from 'vue-router'
import Welcome from "@/components/Welcome";
import OffersOverview31 from "@/components/offers/OffersOverview31";
import UnkownRoute from "@/components/UnkownRoute";
import OffersOverview32 from "@/components/offers/OffersOverview32";
import OffersOverview33 from "@/components/offers/OffersOverview33";
import OffersDetail32 from "@/components/offers/OffersDetail32";
import OffersDetail34 from "@/components/offers/OffersDetail34";
import OffersOverview34 from "@/components/offers/OffersOverview34.vue";
import OffersOverview37 from "@/components/offers/OffersOverview37";
import OffersDetail37 from "@/components/offers/OffersDetail37";
import SignIn from "@/components/SignIn.vue";

const routes = [
    {path: '/welcome', component: Welcome},
    {path: '/sign-in', component: SignIn},
    {path: '/sign-out', redirect: '/sign-in?signOff=true', component: SignIn},
    {path: '/offers/overview31', component: OffersOverview31},
    {path: '/offers/overview32', component: OffersOverview32},
    {path: '/offers/overview33', component: OffersOverview33,
        children: [
            {path: ':id', component: OffersDetail32, props: true},
            {path: ':pathMatch(.*)*', component: UnkownRoute},
        ]},
    {path: '/offers/overview34', component: OffersOverview34,
        children: [
            {path: ':id', component: OffersDetail34},
            {path: ':pathMatch(.*)*', component: UnkownRoute}
        ]},
    {path: '/offers/overview37', component: OffersOverview37,
        children: [
            {path: ":id", component: OffersDetail37},
            {path: ':pathMatch(.*)*', component: UnkownRoute}
        ]},
    {path: '/:pathMatch(.*)*', component: UnkownRoute}

]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
