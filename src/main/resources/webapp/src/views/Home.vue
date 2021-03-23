<template>
    <div class="title">
        <h1>{{ title }}</h1>
        <h2>Balance: {{ balance }}</h2>
        <div>
            <md-field>
                <label>Amount to add</label>
                <md-input v-model="addAmount" type="number"></md-input>
            </md-field>
            <md-button class="md-raised md-primary" @click="deposit">Add</md-button>
            <md-field>
                <label>Amount to remove</label>
                <md-input v-model="removeAmount" type="number"></md-input>
            </md-field>
            <md-button class="md-raised md-primary" @click="withdraw">Remove</md-button>
        </div>
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
        balance: 0,
        addAmount: 0,
        removeAmount: 0
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
      },
      deposit() {
        const self = this
        const url = `http://localhost:8081/api/account/deposit`
        this.$http.post(url, {amount: this.addAmount}).then(function (response) {
          console.log(response.data)
          self.balance = response.data.amount
        }).catch(function (error) {
          console.log(error)
        })
      },
      withdraw() {
        const self = this
        const url = `http://localhost:8081/api/account/withdraw`
        this.$http.post(url, {amount: this.removeAmount}).then(function (response) {
          console.log(response.data)
          self.balance = response.data.amount
        }).catch(function (error) {
          console.log(error)
        })
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
