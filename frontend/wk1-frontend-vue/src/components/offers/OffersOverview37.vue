<template>
  <h1> Overview of all offers </h1>

  <div class="containerForComponent">
    <section class="offersList">
      <table ref="table">
        <tr>
          <th>Offers title:</th>
        </tr>
        <tr v-for="offer in Offers" @click="getSelectedOffer(offer)" :key="offer.id">
          <td :class="{highlightSelectedOffer: this.selectedOffer === offer}"> {{ offer.title }}</td>
        </tr>
        <button @click="onNewOffer">New Offer</button>
      </table>
    </section>
    <section class="detailOffer">
      <p v-if="this.selectedOffer === null"> Select an offer at the left </p>
      <router-view v-else
                   :offer="selectedOffer" @on-change="onChange" @refresh="refreshList"></router-view>
    </section>
  </div>
</template>

<script>

import {Offer} from "@/models/offer"

export default {
  name: "OffersOverview37.vue",
  inject:['offersService', 'sessionService'],
  components: {},

  data() {
    return {
      Offers: [],
      lastId: this.lastId,
      selectedOffer: null,
    }
  },

  mounted() {
    window.addEventListener('beforeunload', this.beforeWindowUnload);
  },
  beforeUnmount() {
    window.removeEventListener('beforeunload', this.beforeWindowUnload);
  },

  async created() {

    this.Offers = await this.offersService.asyncFindAll();
    if(this.sessionService.isAuthenticated()){
      this.selectedOffer = this.findSelectedFromRouteParam(this.$route);
    }



  },

  methods: {
    onChange(offer) {
      this.selectedOffer = this.Offers.find((test) => test.id === offer.id);
    },
    async onNewOffer() {

      const newOffer = Offer.createSampleOffer(0);
      const offerOfBackend = await this.offersService.asyncSave(newOffer);

      //adds newly created saved offer to the list and selects it.
      this.Offers.push(await this.offersService.asyncFindById(offerOfBackend.id));
      this.getSelectedOffer(offerOfBackend);
    },
    beforeWindowUnload(e) {
      window.confirm('Do you really want to leave? ');

      e.preventDefault();

      e.returnValue = '';
    },
    /**
     * Selects the clicked offer and puts it into variable
     *
     * @author Rowin Schenk
     *
     * @param offer
     */
    getSelectedOffer(offer) {
      if (this.selectedOffer === offer) {
        this.$router.push('/offers/overview37')
      } else {
        // router.push({path: `/offers/overview33/${offer.id}`})
        this.$router.push(this.$route.matched[0].path + "/" + offer.id)
      }
    },
    findSelectedFromRouteParam(route) {
      const offerId = +route.params.id;

      if (route.params.id === null || !this.Offers.find(offer => offer.id === offerId)) {
        return null;
      } else {
        return this.Offers.find(offer => offer.id === offerId)
      }

    },
    async refreshList(){
      this.Offers = await this.offersService.asyncFindAll();
      this.selectedOffer = null;
      this.$router.push('/offers/overview37')
    }
  },
  watch: {
    '$route'() {
      if(this.sessionService.isAuthenticated()) {
        this.selectedOffer = this.findSelectedFromRouteParam(this.$route)
      }
    }
  }
  ,
}
</script>

<style scoped>

.containerForComponent {
  display: flex;
  width: 100vw;
}

.offersList {
  font-family: "Andale Mono";
  width: 100%;
}

.detailOffer {
  font-family: "Andale Mono";
  width: 100%;
}

table {
  width: 90%;
}

th, tr, td {
  border: solid black 3px;
  padding: 10px;
}

th {
  font-weight: 50000;
  font-size: larger;
}

button {
  position: absolute;
  font-size: 16px;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
  border: solid black 3px;
  margin: 5px 5px 0 0;
  background-color: white;
  left: 39%;
  font-family: "Andale Mono";
}

button:hover {
  background-color: lightgrey;
}

.highlightSelectedOffer {
  background-color: lightgrey;
}
</style>
// for (const offer in this.Offers) {
//   if (copyOffer.id === offer.id){
//     offer.title = copyOffer.title;
//     offer.description = copyOffer.description;
//     offer.status = copyOffer.status;
//     offer.sellDate = copyOffer.sellDate;
//     offer.valueHighestBid = copyOffer.valueHighestBid;