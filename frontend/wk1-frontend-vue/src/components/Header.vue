<template>
  <section class="header">
    <div class="image-container">
      <img  class="hammer" alt="" v-bind:src="hammerImage">
    </div>
    <div class="title-container">
      <h1 class="title">The Auctioneer</h1>
      <div class="sub-title-box">
        <p>today is</p>
        <p class="date"> {{ welcomeMessage }}</p>
        <p class="sub-title">Offered to you by hva.nl</p>
      </div>
    </div>
    <div class="image-container">
      <img class="auction" alt="" v-bind:src="auctionImage">
    </div>
  </section>
</template>

<script>

export default {
  name: "App-Header",
  data() {
    return {
      hammerImage: require("../assets/4440467.png"),
      auctionImage: require("../assets/auction.png"),
      welcomeMessage: new Date().toDateString()
    }
  },
  methods: {
  }

}
</script>

<style scoped>

  .header {
    display: flex;
    font-family: "Andale Mono";
    height: 20%;
    border: solid black 3px;
  }

  .image-container {
    display: flex;
    padding: 5px;
    width: 10%;
    height: 10%;
  }

  .title-container {
    display: flow;
    bottom: 0;
    width: 80%;
    height: 100%;
  }

  .title-container p {
    bottom: 0;
  }

  .title {
    padding-left: 5px;
    font-size: 30px;
  }

  .sub-title-box {
    display: inline-block;
    width: 100%;
  }

  .sub-title {
    padding: 5px;
    font-size: 10px;
    margin-left: auto;
    float: right;
  }

  .hammer {
    float: left;
    width: 100%;
    height: 100%;
  }
  .auction {
    float: right;
    width: 100%;
    height: 100%;
  }

  .date {
    overflow: auto;
  }



</style>