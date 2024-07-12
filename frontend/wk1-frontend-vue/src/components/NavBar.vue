<template>
  <div class="navbar">
    <router-link to="/welcome">Home</router-link>

    <div class="dropdown">
      <button class="dropbtn">Offers
        <i class="fa fa-caret-down"></i>
      </button>
      <div class="dropdown-content">
        <router-link to="/offers/overview31">All Offers Overview</router-link>
        <router-link to="/offers/overview32">Offers Detail (comp)</router-link>
        <router-link to="/offers/overview33">Offers Edit (router)</router-link>
        <router-link to="/offers/overview34">Offers edit (managed)</router-link>
        <router-link to="/offers/overview37">Offers edit REST API</router-link>
      </div>
    </div>
    <div class="dropdown">
      <button class="dropbtn">My Bids
        <i class="fa fa-caret-down"></i>
      </button>
      <div class="dropdown-content">
        <a>Active</a>
        <a>History</a>
      </div>
    </div>
    <a>My Account</a>

    <div class="right">
      <router-link to="/sign-up" >Sign up</router-link>
      <router-link to="/sign-in" class="edge">Log in</router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: "NavBar"
}
</script>

<style scoped>

  .navbar {
    overflow: hidden;
    font-family: "Andale Mono";
  }

  .navbar a {
    float: left;
    font-size: 16px;
    text-align: center;
    padding: 14px 16px;
    text-decoration: none;
    border: solid black 3px;
    margin: 5px 5px 0 0;
  }

  router-link {
    color: black;
    text-decoration: none;
  }

  .right {
    float: right;
  }

  .dropdown {
    float: left;
    font-size: 16px;
    overflow: hidden;
    background-color: white;
    border: solid black 3px;
    margin: 5px 5px 0 0;
  }

  .dropdown .dropbtn {
    font-size: 16px;
    border: none;
    outline: none;
    font-family: inherit;
    padding: 14px 16px;
    background-color: white;
    margin: 0;

  }

  .navbar a:hover, .dropdown:hover .dropbtn {
    background-color: lightgrey;
  }

  a.router-link-active {
    background-color: lightgrey;
  }

  .dropdown-content {
    display: none;
    position: absolute;
    min-width: 160px;
    box-shadow: 0px 8px 16px 0px rbga(0,0,0,0.2);
    z-index: 1;
  }

  .dropdown-content a{
    float: none;
    color: black;
    padding: 12px 16px;
    text-decoration: none;
    background-color: white;
    display: block;
    text-align: left;
  }

  .dropdown-content a:hover {
    background-color: lightgrey;
  }

  .dropdown:hover .dropdown-content {
    display: block;
  }


  .edge {
    margin-right: 0 !important;
  }
  @media  only screen and (max-width: 600px) {
    .navbar a {
      float: left;
    }

    .right {
      float: left;
    }
  }
</style>