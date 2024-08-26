package zjnu.huawei.pcb.service.basic.impl;

import zjnu.huawei.pcb.dto.basic.UserDTO;
import zjnu.huawei.pcb.dto.harmony.HarmonyTokenDTO;
import zjnu.huawei.pcb.entity.basic.UserEntity;
import zjnu.huawei.pcb.mapper.basic.UserMapper;
import zjnu.huawei.pcb.service.basic.UserService;
import zjnu.huawei.pcb.utils.JWTUtil;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updateUser(UserDTO userDTO) throws Exception {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        UserEntity userEntity = mapperFactory.getMapperFacade().map(userDTO, UserEntity.class);
        List<UserDTO> judgeUser = userMapper.judgeUser(userEntity);
        Integer judgeUserInfo = userMapper.judgeUserInfo(userEntity);
        if ((judgeUser.size() > 0 && !judgeUser.get(0).getDdId().equals(userDTO.getDdId())) || judgeUserInfo == 0) {
            return "-1";
        }
        userEntity.setGmtCreate(new Date());
        userMapper.updateUser(userEntity);
        HarmonyTokenDTO ddTokenDTO = new HarmonyTokenDTO();
        return JWTUtil.createSign(ddTokenDTO.toString(), 240);
    }
}
