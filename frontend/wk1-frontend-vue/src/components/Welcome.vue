<template>
  <section class="welcome">
    <div class="left">
      <h3>Pick a date:</h3>
      <input type="date">
      <h3>Pick a catagory</h3>
      <ul>
        <li><a v-bind:href="antiqueLink">Antique</a></li>
        <li><a v-bind:href="carLink">Cars</a></li>
        <li><a v-bind:href="ticketLink">Event Tickets</a></li>
        <li><a v-bind:href="holidayLink">Holidays</a></li>
        <li><a v-bind:href="collectableLink">Collectables</a></li>
      </ul>
    </div>
    <div class="middle">
      <a target="_blank" v-bind:href="antiqueLink"><img  class="image" v-bind:src="vaseImage"></a>
      <a target="_blank" v-bind:href="carLink"><img class="image" v-bind:src="carImage"></a>
      <a target="_blank" v-bind:href="ticketLink"><img class="image" v-bind:src="ticketImage"></a>
    </div>
    <div class="right">
      <h3>Test</h3>
    </div>
  </section>

</template>

<script>
export default {
  name: "App-Welcome",
  data() {
    return {
      vaseImage: require("../assets/vase.png"),
      carImage: require("../assets/sedan.png"),
      ticketImage: require("../assets/ticket.png"),

      antiqueLink: "https://www.google.com/search?q=antique",
      carLink: "https://google.com/search?=cars",
      ticketLink: "https://www.google.com/search?q=event+tickets",
      holidayLink: "https://www.google.com/search?q=holidays",
      collectableLink: "https://www.google.com/search?q=collectables"


    }
  }
}
</script>

<style scoped>

  .welcome {
    display: flex;
    border: black 3px;
    font-family: "Andale Mono";
    height: 100%;
  }

  .left {
    width: 20%;
    height: 100vh;
    border: solid black 3px;
    float: left;
    margin: 5px 5px 5px 0;
    padding: 5px;
  }

  .middle {
    display: flex;
    width: 60%;
    height: 100vh;
    border: solid black 3px;
    margin: 5px;
    padding: 5px;
    flex-wrap: wrap;
  }

  .right {
    width: 20%;
    height: 100vh;
    border: solid black 3px;
    margin: 5px 0 5px 5px;
    padding: 5px;
  }

  .image {
    display: flex;
    width: 200px;
    height: 200px;
    padding: 10px;

  }
  
  @media  only screen and (max-width: 600px) {
    .welcome {
      display: block;
    }

    .left {
      width: 95%;
      height: 100%;
      margin: 5px 0;
    }

    .middle {
      width: 95%;
      height: 100%;
      margin: 5px 0 0;
    }

    .right {
      width: 95%;
      height: 100%;
      margin: 5px 0 0;
    }
  }


</style>