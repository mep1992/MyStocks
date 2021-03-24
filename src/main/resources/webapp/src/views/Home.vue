<template>
    <div>
        <h1 class="title">{{ title }}</h1>
        <h2>Balance: {{ balance }}</h2>
        <md-field>
            <label>Amount to deposit</label>
            <md-input v-model="depositAmount" type="number"></md-input>
        </md-field>
        <md-button class="md-raised md-primary deposit-button" @click="deposit">Deposit</md-button>
        <md-field>
            <label>Amount to withdraw</label>
            <md-input class="withdraw-input" v-model="withdrawAmount" type="number"></md-input>
        </md-field>
        <md-button class="md-raised md-primary withdraw-button" @click="withdraw">Withdraw</md-button>
        <md-field>
            <label>Stock to buy</label>
            <md-input v-model="buyStock"></md-input>
        </md-field>
        <md-field>
            <label>Quantity to buy</label>
            <md-input v-model="buyQuantity" type="number"></md-input>
        </md-field>
        <md-button class="md-raised md-primary" @click="buy">Buy</md-button>
        <md-field>
            <label>Stock to sell</label>
            <md-input v-model="sellStock"></md-input>
        </md-field>
        <md-field>
            <label>Quantity to sell</label>
            <md-input v-model="sellQuantity" type="number"></md-input>
        </md-field>
        <md-button class="md-raised md-primary" @click="sell">Sell</md-button>
    </div>
</template>

<script>
  import axios from 'axios'

  export default {
    name: 'Home',
    props: {
      title: String
    },
    data: function () {
      return {
        balance: 0,
        depositAmount: null,
        withdrawAmount: null,
        buyStock: null,
        buyQuantity: null,
        sellStock: null,
        sellQuantity: null
      }
    },
    mounted() {
      this.getBalance()
    },
    methods: {
      getBalance() {
        const self = this
        const url = `http://localhost:8081/api/account/balance`
        axios.get(url).then(function (response) {
          console.log('getBalance:', response.data)
          self.balance = response.data.amount
        }).catch(function (error) {
          self.balance = 50
          console.log(error)
        })
      },
      deposit() {
        const self = this
        const url = `http://localhost:8081/api/account/deposit`
        axios.post(url, {amount: this.depositAmount}).then(function (response) {
          console.log('deposit:', response.data)
          self.balance = response.data.amount
        }).catch(function (error) {
          console.log(error)
        })
      },
      withdraw() {
        const self = this
        const url = `http://localhost:8081/api/account/withdraw`
        axios.post(url, {amount: this.withdrawAmount}).then(function (response) {
          console.log('withdraw:', response.data)
          self.balance = response.data.amount
        }).catch(function (error) {
          console.log(error)
        })
      },
      buy() {
        const self = this
        const url = `http://localhost:8081/api/order/buy`
        axios.post(url, {stock: self.buyStock, quantity: self.buyQuantity}).then(function (response) {
          console.log('buy:', response.data)
          self.balance = response.data.balance
        }).catch(function (error) {
          console.log(error)
        })
      },
      sell() {
        const self = this
        const url = `http://localhost:8081/api/order/sell`
        axios.post(url, {stock: self.sellStock, quantity: self.sellQuantity}).then(function (response) {
          console.log('buy:', response.data)
          self.balance = response.data.balance
        }).catch(function (error) {
          console.log(error)
        })
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
    .md-field {
        width: 50%;
        left: 25%
    }
</style>
