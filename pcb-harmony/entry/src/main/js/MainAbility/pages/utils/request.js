import store from '../utils/store.js'
import http from '@ohos.net.http'

const baseUrl = 'http://192.168.0.114:6666/api'

async function request(options) {
    let {url, method, data} = options
    if (url.substring(0, 4) !== "http") {
        url = baseUrl + url
    }

    let httpRequest = http.createHttp();

    let header = {
        'Content-Type': 'application/json;charset=UTF-8'
    }
    const token = await store.get('token')
    const serviceToken = await store.get('serviceToken')
    if (token) header['Access-Token'] = token
    if (serviceToken) header['x-auth-token'] = serviceToken

    return new Promise((resolve, reject) => {
        httpRequest.request(
            // 填写HTTP请求的URL地址，可以带参数也可以不带参数。URL地址需要开发者自定义。请求的参数可以在extraData中指定
            url,
            {
                method: method.toUpperCase(), // 可选，默认为http.RequestMethod.GET
                // 开发者根据自身业务需要添加header字段
                header: header,
                // 当使用POST请求时此字段用于传递内容
                extraData: data,
                connectTimeout: 60000, // 可选，默认为60000ms
                readTimeout: 60000, // 可选，默认为60000ms
                // usingProtocol: http.HttpProtocol.HTTP1_1, // 可选，协议类型默认值由系统自动指定
            }, (err, data) => {
            if (!err) {
                resolve(JSON.parse(data.result))
            } else {
                console.info('error:' + JSON.stringify(err));
                reject(false)
            }
            // 取消订阅HTTP响应头事件
            httpRequest.off('headersReceive');
            // 当该请求使用完毕时，调用destroy方法主动销毁。
            httpRequest.destroy();
        })
    })
}
// 创建 axios 实例
// const request = axios.create({
//     baseURL: 'http://localhost:6666/api',
//     timeout: 6000
// })

// // 异常拦截处理器
// const errorHandler = (error) => {
//     if (error.response) {
//         const data = error.response.data
//         prompt.showDialog({
//             title: '请求失败',
//             message: data.status + data.message,
//             // buttons: [{
//             //     text: '确定',
//             //     color: '#333333' // 按钮文字颜色
//             // }]
//         }, (err, data) => {
//             if (err) {
//                 console.error('对话框显示失败', err);
//                 return;
//             }
//             // 这里处理用户点击按钮后的操作
//             console.log('用户点击了对话框的按钮');
//         });
//     }
//     return Promise.reject(error)
// }
//
// // request interceptor
// request.interceptors.request.use(async config => {
//     const token = await store.get('token')
//     // 如果 token 存在
//     // 让每个请求携带自定义 token 请根据实际情况自行修改
//     if (token) {
//         config.headers['Access-Token'] = token
//     }
//     return config
// }, errorHandler)


export default request
