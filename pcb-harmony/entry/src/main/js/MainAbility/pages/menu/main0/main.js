import store from '../../utils/store.js'
export default {
    data: {
        title: ""
    },
    async onInit() {
        this.title = "Hello World";
        let a = await store.get('token')
        console.log('token0: '+ a);
    }
}
