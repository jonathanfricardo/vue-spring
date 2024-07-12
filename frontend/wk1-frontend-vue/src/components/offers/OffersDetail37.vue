<template>

  <form v-if="copyOfOffer !== null">
    <header> {{ copyOfOffer.id }}</header>
    <!-- eslint-disable vue/no-mutating-props-->
    <table>
      <tr>
        <td>Title:</td>
        <td><input type="text" v-model="copyOfOffer.title"></td>
      </tr>
      <tr>
        <td>Description:</td>
        <td><input type="text" v-model="copyOfOffer.description"></td>
      </tr>
      <tr>
        <td>Status:</td>
        <td><select v-model="copyOfOffer.status">
          <option v-for="status in this.statuses" :key="status">{{ status }}</option>
        </select></td>
      </tr>
      <tr>
        <td>Sell date:</td>
        <td><input type="date" v-model="sellDateUpdater"></td>
      </tr>
      <tr>
        <td>Highest bid:</td>
        <td><input type="text" v-model="copyOfOffer.valueHighestBid"></td>
      </tr>
    </table>
    <div class="button-container">
      <button type="button" @click="onSave" :disabled="!hasChanged">Save Offer</button>
      <button type="button" @click="resetOffer" >Reset Offer</button>
      <button type="button" @click="emptyAllInputs" >Clear Offer</button>
      <button type="button" @click="cancelButton" :disabled="!hasChanged">Cancel Offer</button>
      <button type="button" @click="onDelete" :disabled="hasChanged">Delete Offer</button>
    </div>
  </form>
</template>

<script>
import {Offer} from "@/models/offer"

export default {
  name: "OffersDetail37.vue",
  inject: ['offersService'],

  beforeRouteLeave() {
    if(this === null) {
      return true;
    }

    if(this.hasChanged) {
      return confirm(this.confirmMessage);
    }
    return true;
  },

  beforeRouteUpdate() {
    if(this.hasChanged) {
      if(confirm("Are you sure you want to discard unsaved changes in " + this.offer.id)) {
        return true
      } else {
        this.$emit("on-change", this.copyOfOffer)
        return false
      }
    }
    return true;
  },

  props: {
    // offer: {
    //   type: Object,
    //   required: true
    // }
  },
  emits: ['on-change', 'refresh'],

  data() {
    return {
      statuses: Offer.statuses,
      copyOfOffer: null,
      offer: null,
      confirmMessage: "Are you sure you want to discard unsaved changes in " + this.$route?.params?.id,
      edited: false,
    }
  },
  methods: {
    async reInitialise(){
      this.offer = await this.offersService.asyncFindById(this.$route?.params?.id);
      this.createCopyOfOffer();
    },
    async onSave() {
      await this.offersService.asyncSave(this.copyOfOffer);
      this.$emit('refresh');
    },
    async onDelete() {
      if(confirm("Are you sure you want to delete offer " + this.copyOfOffer.id + "?")) {
        await this.offersService.asyncDelete(this.copyOfOffer.id);
        this.$emit('refresh');
      }
    },
    createCopyOfOffer() {
      this.copyOfOffer = Offer.copyConstructor(this.offer);
    },
    emptyAllInputs() {
      if(this.hasChanged) {
        if(confirm(this.confirmMessage)) {
          this.copyOfOffer.title = "";
          this.copyOfOffer.description = "";
          this.copyOfOffer.status = null;
          this.copyOfOffer.sellDate = new Date(null);
          this.copyOfOffer.valueHighestBid = null;
        }
      } else {
        this.copyOfOffer.title = "";
        this.copyOfOffer.description = "";
        this.copyOfOffer.status = null;
        this.copyOfOffer.sellDate = new Date(null);
        this.copyOfOffer.valueHighestBid = null;
      }
    },
    cancelButton() {
      if(this.hasChanged){
        if(confirm(this.confirmMessage)) {
          this.$router.push('/offers/overview37');
        }
      }
    },
    resetOffer(){
      if(this.hasChanged){
        if(confirm(this.confirmMessage)) {
          this.reInitialise();
        }
      }
    }
  },
  computed: {
    hasChanged() {
      const oldDate = this.offer.sellDate.toISOString().substring(0, 10);
      const newDate = this.copyOfOffer.sellDate.toISOString().substring(0, 10);
      return this.copyOfOffer.title !== this.offer.title
          || this.copyOfOffer.description !== this.offer.description
          || this.copyOfOffer.status !== this.offer.status
          || oldDate !== newDate
          || this.copyOfOffer.valueHighestBid !== this.offer.valueHighestBid;
    },
    sellDateUpdater: {
      get() {
        return this.copyOfOffer.sellDate.toISOString().substring(0, 10)
      },
      set(localDateTime) {
        this.copyOfOffer.sellDate = new Date(localDateTime);
      }
    }
  },
  created() {
    this.reInitialise();
  },
  watch: {
    $route() {
      this.reInitialise();
    }
  }


}
</script>

<style scoped>
form {
  width: 100%;
}

header {
  border: solid black 3px;
  padding: 10px;
  font-weight: 50000;
  font-size: larger;
  width: 85%;
  text-align: center;
}

table {
  width: 90%;
//display: flex;
}

th, tr, td {
  border: solid black 3px;
  padding: 10px;
}

th {
  font-weight: 50000;
  font-size: larger;
}

select {
  width: 40%;
}

input {
  width: 40%;
}

button {
  font-size: 16px;
  text-align: center;
  padding: 14px 16px;
  text-decoration: none;
  border: solid black 3px;
  margin: 5px 5px 0 0;
  background-color: white;
  font-family: "Andale Mono";
}

button:hover {
  background-color: lightgrey;
  cursor: pointer;
}

.button-container {
  overflow: hidden;
  display: flex;
}


</style>