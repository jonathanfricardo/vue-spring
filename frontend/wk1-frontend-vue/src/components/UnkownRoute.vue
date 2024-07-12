<template>
  <h1>Oops, an error has occurred...</h1>
  <p> There is no component available at route  {{ id }}</p>
</template>

<script>
export default {
  name: "UnkownRoute",

  data() {
    return {
      id: this.id,
    }
  },

  methods: {
    loadUnknownPage(route) {
      this.id = route.params.id;
    }
  },

  created() {
    this.loadUnknownPage(this.$route)
  },

  watch: {
    $route(unknownRoute) {
      this.loadUnknownPage(unknownRoute)
    }
  }

}




</script>

<style scoped>

</style>