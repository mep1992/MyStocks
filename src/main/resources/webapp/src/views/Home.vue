<template>
    <div class="hello">
        <h1>{{ title }}</h1>
        <h2>Balance: {{ balance }}</h2>
    </div>
</template>

<script>
  export default {
    name: 'Home',
    props: {
      title: String
    },
    data: function () {
      return {
        balance: 0
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
