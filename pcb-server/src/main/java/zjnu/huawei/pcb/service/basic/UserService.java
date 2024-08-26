package zjnu.huawei.pcb.service.basic;

import zjnu.huawei.pcb.dto.basic.UserDTO;
import zjnu.huawei.pcb.entity.basic.UserEntity;

public interface UserService {
    /**
     * @Author: dy
     * @Date: 2021/10/27
     * @Description: 更新用户信息
     */
    public String updateUser(UserDTO userDTO) throws Exception;
}
