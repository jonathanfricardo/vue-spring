<template>
  <h1>Please provide your login credentials:</h1>
  <form method="post"
        v-on:submit.prevent="signIn()">
    <table ref="table">
      <tr>
        <td>User email:</td>
        <td><input type="email" name="email" v-model.trim="userEmail" required></td>
      </tr>
      <tr>
        <td>Password</td>
        <td><input type="password" name="password" v-model="userPassword" required minlength="3"></td>
      </tr>
    </table>
    <button type="submit">Sign in</button>
    <p v-if="!correctLogin">Wachtwoord en email komen niet overeen</p>
  </form>
</template>

<script>

export default {
  name: "SignIn",
  inject: ["sessionService"],
  data() {
    return {
      userEmail: null,
      userPassword: null,
      account: null,
      sessionService: this.sessionService,
      correctLogin: true
    }
  },


  created() {
    if (this.$route.query.signOff === 'true') {
      this.sessionService.signOut();
      console.log("signoff gelukt")
    }
  },

  methods: {
    async signIn() {
      //retrieves account if credentials match
      this.account = await this.sessionService.asyncSignIn(this.userEmail, this.userPassword);

      //validates correct login
      this.correctLogin = this.account !== null;

      if (this.account !== null) {
        this.$router.push("/welcome")
      }
    }
  }
}
</script>

<style scoped>

table {
  width: 80%;
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
  right: 20%;
  font-family: "Andale Mono";
}

button:hover {
  background-color: lightgrey;
}

tr.row:hover {
  background-color: lightgrey;
}

p {
  color: red;
}


</style>