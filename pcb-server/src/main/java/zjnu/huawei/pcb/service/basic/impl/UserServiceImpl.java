package zjnu.huawei.pcb.service.basic.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;
import zjnu.huawei.pcb.dto.basic.HarmonyUserDTO;
import zjnu.huawei.pcb.dto.harmony.TaskImgDTO;
import zjnu.huawei.pcb.entity.basic.HarmonyUserEntity;
import zjnu.huawei.pcb.entity.basic.UserEntity;
import zjnu.huawei.pcb.mapper.basic.UserMapper;
import zjnu.huawei.pcb.service.basic.UserService;
import zjnu.huawei.pcb.utils.file.FileUtils;
import zjnu.huawei.pcb.utils.file.MinioUtil;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private MinioUtil minioUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer update(HarmonyUserDTO harmonyUserDTO) throws Exception {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        HarmonyUserEntity harmonyUserEntity = mapperFactory.getMapperFacade().map(harmonyUserDTO, HarmonyUserEntity.class);
        return userMapper.update(harmonyUserEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer resetPwd(HarmonyUserDTO harmonyUserDTO) throws Exception {
//        return userMapper.resetPwd(harmonyUserId, DigestUtils.md5DigestAsHex(loginPwd.getBytes()));
        return userMapper.resetPwd(harmonyUserDTO.getHarmonyUserId(), harmonyUserDTO.getLoginPwd());
    }

    @Override
    public HarmonyUserEntity getByUserId(Long harmonyUserId) throws Exception {
        return userMapper.queryById(harmonyUserId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer changeImage(JSONObject jsonObject) throws Exception {
        JSONArray imgBase64List = new JSONArray();
        imgBase64List.add(jsonObject.getString("file"));
        List<MultipartFile> files = FileUtils.base642MultipartFileList(imgBase64List, new JSONArray());
        if (files.size() > 0) {
            String saveName = "avatar_" + jsonObject.getString("harmonyUserId") + "_" + (System.currentTimeMillis()+".jpg");
            String fileUrl = minioUtil.upload(files.get(0), "image/" + saveName);
            try {
                return userMapper.changeImage(jsonObject.getLong("harmonyUserId"), fileUrl);
            } catch (Exception e) {
                minioUtil.remove(fileUrl);
                throw e;
            }
        }
        return 1;
    }
}
