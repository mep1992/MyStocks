import { shallowMount } from '@vue/test-utils'
import Home from '@/views/Home.vue'
import axios from 'axios'
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
})
