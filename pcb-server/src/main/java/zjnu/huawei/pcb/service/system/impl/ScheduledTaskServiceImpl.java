package zjnu.huawei.pcb.service.system.impl;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zjnu.huawei.pcb.mapper.harmony.TaskImgMapper;
import zjnu.huawei.pcb.mapper.harmony.TaskMapper;
import zjnu.huawei.pcb.service.system.ScheduledTaskService;
import zjnu.huawei.pcb.utils.file.MinioUtil;

import javax.annotation.Resource;
import java.util.List;

@Service
@EnableScheduling
public class ScheduledTaskServiceImpl implements ScheduledTaskService {
    @Resource
    private TaskMapper taskMapper;
    @Resource
    private TaskImgMapper taskImgMapper;

    @Resource
    private MinioUtil minioUtil;

    private static final Integer EXPIRED_DAYS = 3;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Scheduled(cron = "0 5 2 * * ?") // 每天2点05执行一次
    public void removeExpired() throws Exception {
        List<String> imgUrlList = taskImgMapper.queryExpired(EXPIRED_DAYS);
        taskMapper.removeExpired(EXPIRED_DAYS);
        taskImgMapper.removeExpired(EXPIRED_DAYS);
        for (String imgUrl : imgUrlList) {
            minioUtil.remove(imgUrl);
        }
    }
}
