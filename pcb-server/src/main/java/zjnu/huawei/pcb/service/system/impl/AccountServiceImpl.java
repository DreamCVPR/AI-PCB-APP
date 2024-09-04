package zjnu.huawei.pcb.service.system.impl;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Service;
import zjnu.huawei.pcb.config.exception.CommonJsonException;
import zjnu.huawei.pcb.dto.system.HarmonyTokenDTO;
import zjnu.huawei.pcb.dto.basic.HarmonyUserDTO;

import zjnu.huawei.pcb.entity.basic.HarmonyUserEntity;
import zjnu.huawei.pcb.mapper.basic.UserMapper;
import zjnu.huawei.pcb.service.system.AccountService;
import zjnu.huawei.pcb.utils.JWTUtil;
import zjnu.huawei.pcb.utils.harmony.ErrorEnum;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class AccountServiceImpl implements AccountService {
    @Resource
    private UserMapper userMapper;

    @Override
    public HarmonyUserDTO login(HarmonyUserDTO harmonyUserDTO) throws Exception {
        HarmonyUserEntity harmonyUserEntity;
        if (harmonyUserDTO.getHarmonyUserId() != null) {
            harmonyUserEntity = userMapper.queryById(harmonyUserDTO.getHarmonyUserId());
            if (harmonyUserEntity == null && harmonyUserDTO.getUserName() == null) {
                throw new CommonJsonException(ErrorEnum.E_401);
            }
        } else {
            if (harmonyUserDTO.getLoginName() == null) {
                throw new CommonJsonException(ErrorEnum.E_401);
            }
            harmonyUserEntity = userMapper.query(harmonyUserDTO.getLoginName());
        }
        if (harmonyUserEntity != null && !harmonyUserEntity.getLoginPwd().equals(harmonyUserDTO.getLoginPwd())) {
            throw new CommonJsonException(ErrorEnum.E_10011);
        }
        if (harmonyUserEntity == null) {
            MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
            harmonyUserEntity = mapperFactory.getMapperFacade().map(harmonyUserDTO, HarmonyUserEntity.class);
            harmonyUserEntity.setUserName(harmonyUserEntity.getLoginName());
            harmonyUserEntity.setGmtCreate(new Date());
            userMapper.add(harmonyUserEntity);
        }
        String token = JWTUtil.createSign(new HarmonyTokenDTO(
                harmonyUserEntity.getHarmonyUserId(),
                harmonyUserEntity.getUserName(),
                harmonyUserEntity.getUserPhone()).toString(), 60*24*7);

        return new HarmonyUserDTO(
                harmonyUserEntity.getUserName(),
                harmonyUserEntity.getUserPhone(),
                token
        );
    }
}
