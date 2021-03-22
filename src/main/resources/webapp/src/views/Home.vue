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
    },
    mounted () {
      this.getBalance()
    },
    methods: {
      getBalance() {
        const self = this
        const url = `http://localhost:8081/api/account/balance`
        this.$http.get(url).then(function (response) {
          console.log(response.data)
          self.balance = response.data.amount
        }).catch(function (error) {
          self.balance = 50
          console.log(error)
        })
      }

    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
