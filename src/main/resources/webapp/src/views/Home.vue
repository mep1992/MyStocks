<template>
    <div>
        <h1 class="title">{{ title }}</h1>
        <h2>Balance: {{ balance }}</h2>
        <div>
<!--            <md-field>-->
<!--                <label>Amount to add</label>-->
<!--                <md-input v-model="depositAmount" type="number"></md-input>-->
<!--            </md-field>-->
<!--            <md-button class="md-raised md-primary" @click="deposit">Deposit</md-button>-->
<!--            <md-field>-->
<!--                <label>Amount to remove</label>-->
<!--                <md-input v-model="withdrawAmount" type="number"></md-input>-->
<!--            </md-field>-->
<!--            <md-button class="md-raised md-primary" @click="withdraw">Withdraw</md-button>-->
        </div>
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
        depositAmount: 0,
        withdrawAmount: 0
      }
    },
    mounted () {
      this.getBalance()
    },
    methods: {
      getBalance() {
        const self = this
        const url = `http://localhost:8081/api/account/balance`
        axios.get(url).then(function (response) {
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
        this.$http.post(url, {amount: this.depositAmount}).then(function (response) {
          console.log(response.data)
          self.balance = response.data.amount
        }).catch(function (error) {
          console.log(error)
        })
      },
      withdraw() {
        const self = this
        const url = `http://localhost:8081/api/account/withdraw`
        this.$http.post(url, {amount: this.withdrawAmount}).then(function (response) {
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
