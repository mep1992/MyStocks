import 'vue-material/dist/vue-material.min.css'
import 'vue-material/dist/theme/default.css'
import Vue from 'vue'
import App from './App.vue'
import VueMaterial from 'vue-material'
import Axios from 'axios'

Vue.use(VueMaterial)
Vue.prototype.$http = Axios

Vue.config.productionTip = false

new Vue({
  render: h => h(App),
}).$mount('#app')
