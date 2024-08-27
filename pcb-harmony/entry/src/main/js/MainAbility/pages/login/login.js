import {login, getToken} from '../api/account.js'
import {predict} from '../api/service.js'

import featureAbility from '@ohos.ability.featureAbility';
import store from '../utils/store.js';
// import http from '@ohos.net.http';
export default {
    data: {
        title: "登录"
    },
    onInit() {
        // let context = featureAbility.getContext();
        // let array = ["ohos.permission.INTERNET"];
        // //requestPermissionsFromUser会判断权限的授权状态来决定是否唤起弹窗
        // context.requestPermissionsFromUser(array, 1)
        //     .then(function(data) {
        //         console.log("dataPermissions:" + JSON.stringify(data));
        //         console.log("data permissions:" + JSON.stringify(data.permissions));
        //         console.log("data result:" + JSON.stringify(data.authResults));
        //         this.loginHuawei()
        //     }, (err) => {
        //         console.error('Failed to start ability', err.code);
        //     });
        // this.loginHuawei()
        this.getToken()
    },
    getToken() {
        predict()
            .then(async (res)=>{
                console.log("successgetinfo:"+res)
                await store.set('serviceToken', res.result.token)
                this.loginHuawei()
                // let strMap = new Map();
            })
            .catch((err)=>{
                console.log("errgetinfo:"+err)
            })
    },
    loginHuawei() {
        this.getHuaweiUserInfo("abc123")
    },
    getHuaweiUserInfo(IdToken) {
        login({"idToken": IdToken})
            .then((res)=>{
                console.log("successgetinfo:"+res)
                // let strMap = new Map();
            })
            .catch((err)=>{
                console.log("errgetinfo:"+err)
            })
    },
}
