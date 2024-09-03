package zjnu.huawei.pcb.controller.harmony;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import zjnu.huawei.pcb.config.aop.PreAuthorize;
import zjnu.huawei.pcb.config.exception.CommonJsonException;
import zjnu.huawei.pcb.controller.BaseController;
import zjnu.huawei.pcb.dto.harmony.TaskDTO;
import zjnu.huawei.pcb.dto.harmony.TaskImgDTO;
import zjnu.huawei.pcb.entity.harmony.TaskImgEntity;
import zjnu.huawei.pcb.service.harmony.TaskImgService;
import zjnu.huawei.pcb.service.harmony.TaskService;
import zjnu.huawei.pcb.utils.HttpRequestReader;
import zjnu.huawei.pcb.utils.harmony.CommonUtil;
import zjnu.huawei.pcb.utils.harmony.ErrorEnum;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@PreAuthorize
@RequestMapping(value = "/api/taskImg")
public class TaskImgController extends BaseController {
    @Resource
    private TaskImgService taskImgService;

    private final Logger logger = LoggerFactory.getLogger(TaskImgController.class);

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public JSONObject query(@RequestBody JSONObject jsonObject) {
        try {
            return CommonUtil.successJson(taskImgService.query(jsonObject.getLong("taskId")));
        } catch (CommonJsonException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.toString());
            throw new CommonJsonException(ErrorEnum.E_400);
        }
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public JSONObject add(@RequestBody JSONObject jsonObject) {
        try {
            jsonObject.put("harmonyUserId", harmonyUserId);
            return CommonUtil.successJson(taskImgService.add(jsonObject));
        } catch (CommonJsonException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.toString());
            throw new CommonJsonException(ErrorEnum.E_400);
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JSONObject update(@RequestBody TaskImgDTO taskImgDTO) {
        try {
            return CommonUtil.successJson(taskImgService.update(taskImgDTO));
        } catch (CommonJsonException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.toString());
            throw new CommonJsonException(ErrorEnum.E_400);
        }
    }

    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public JSONObject remove() {
        try {
            JSONObject jsonObject = HttpRequestReader.getJsonObject(request);
            String imgIds = jsonObject.getString("imgIds");
            return CommonUtil.successJson(taskImgService.remove(imgIds));
        } catch (CommonJsonException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.toString());
            throw new CommonJsonException(ErrorEnum.E_400);
        }
    }

    @RequestMapping(value = "/detect", method = RequestMethod.POST)
    public JSONObject detect(@RequestBody List<TaskImgEntity> taskImgList) {
        try {
            return CommonUtil.successJson(taskImgService.detect(taskImgList));
        } catch (CommonJsonException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.toString());
            throw new CommonJsonException(ErrorEnum.E_400);
        }
    }
}
