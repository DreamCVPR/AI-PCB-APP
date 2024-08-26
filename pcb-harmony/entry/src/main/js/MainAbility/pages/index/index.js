import router from '@ohos.router'
import store from '../utils/store.js'

export default {
    data: {
        menu_routes: [
            '../menu/main0/main.hml',
            '../menu/main1/main.hml',
            '../menu/main2/main.hml',
            '../menu/person/person.hml'
        ],
        nowPage: '',
        isDispatcher: 0
    },
    // watch: {
    //     $route: {
    //         handler (val, oldval) {
    //             this.nowPath = val.path
    //         },
    //         // 深度观察监听
    //         deep: true
    //     }
    // },
    async onInit() {
        this.nowPage = this.menu_routes[3]
        await store.set('token', 'abc123')
        let a = await store.get('token')
        console.log('token: ' + a);
        // this.nowPath = this.$route.path
        // if (getPageQuery().key) {
        //     this.key = getPageQuery().key
        //     this.$store.commit('SET_WxTOKEN', this.key)
        //     storage.set(WX_TOKEN, this.key)
        //     this.getRoutes()
        // } else if (storage.get(WX_TOKEN)) {
        //     this.key = storage.get(WX_TOKEN)
        //     this.getRoutes()
        // }
        // this.getUserInfo()
    },
    goPage (index) {
        this.nowPage = this.menu_routes[index]
        console.info("nowPage: "+this.nowPage)
    },
    getRoutes () {
        // for (const route of constantRouterMap) {
        //     if (route.path === '/wx') {
        //         for (const child of route.children) {
        //             if (child.isMenu) {
        //                 this.routes.push({
        //                     path: '/wx/' + child.path,
        //                     title: child.title
        //                 })
        //             }
        //         }
        //     }
        // }
    },
    getUserInfo () {
        // getUserInfo()
        //     .then(res => {
        //         if (res.data.message !== 'error') {
        //             const userInfo = res.data.result
        //             this.isDispatcher = userInfo.isDispatcher
        //             this.$forceUpdate()
        //             this.$store.commit('SET_WxTOKEN', userInfo.token)
        //             storage.set(WX_TOKEN, userInfo.token)
        //             this.$store.commit('SET_WxINFO', userInfo)
        //             storage.set(WX_INFO, userInfo)
        //         } else {
        //             this.$message.error('操作失败！')
        //         }
        //     })
    }
}



