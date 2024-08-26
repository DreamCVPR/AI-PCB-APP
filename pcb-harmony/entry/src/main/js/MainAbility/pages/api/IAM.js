import request from '../utils/request'


export function getServiceToken (parameter) {
    return request({
        url: '/service/getServiceToken',
        method: 'post',
        data: parameter
    })
}
