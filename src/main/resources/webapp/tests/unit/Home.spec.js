import { shallowMount, mount } from '@vue/test-utils'
import Home from '@/views/Home.vue'
import axios from 'axios'
import VueMaterial from 'vue-material'
import Vue from 'vue'

Vue.use(VueMaterial)
axios.get.mockResolvedValue({ data: {amount: 0} });

describe('Home.vue', () => {
  it('should render title prop when passed', () => {
    const title = 'MyStocks!'
    const wrapper = shallowMount(Home, {
      propsData: { title: title }
    })
    expect(wrapper.find('.title').text()).toEqual(title)
  })

  it('should have the balance once rendered', (done) => {
    const balanceAmount = 39;
    axios.get.mockResolvedValue({ data: {amount: balanceAmount} });
    const wrapper = shallowMount(Home)
    
    wrapper.vm.$forceUpdate()
    wrapper.vm.$nextTick(() => {
      expect(wrapper.text()).toContain(`Balance: ${balanceAmount}`)
      done()
    })
  });

  it('should make withdrawal on click', (done) => {
    const balanceAfterWithdraw = 22;
    axios.post.mockResolvedValue({ data: {amount: balanceAfterWithdraw} });
    const wrapper = mount(Home)

    wrapper.find('.withdraw-button').trigger('click')

    wrapper.vm.$nextTick(() => {
      expect(axios.post).toBeCalled()
      done()
    })
  });

  it('should make deposit on click', (done) => {
    const balanceAfterDeposit = 22;
    axios.post.mockResolvedValue({ data: {amount: balanceAfterDeposit} });
    const wrapper = mount(Home)

    wrapper.find('.deposit-button').trigger('click')

    wrapper.vm.$nextTick(() => {
      expect(axios.post).toBeCalled()
      done()
    })
  });
})
