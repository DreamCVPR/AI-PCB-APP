package zjnu.huawei.pcb.controller.file;

import com.alibaba.fastjson.JSONObject;
import zjnu.huawei.pcb.config.MinioConfig;
import zjnu.huawei.pcb.config.aop.PreAuthorize;
import zjnu.huawei.pcb.config.exception.CommonJsonException;
import zjnu.huawei.pcb.controller.BaseController;
import zjnu.huawei.pcb.utils.HttpRequestReader;
import zjnu.huawei.pcb.utils.JWTUtil;
import zjnu.huawei.pcb.utils.file.MinioUtil;
import io.minio.GetObjectArgs;
import io.minio.GetObjectResponse;
import io.minio.MinioClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import zjnu.huawei.pcb.utils.harmony.CommonUtil;
import zjnu.huawei.pcb.utils.harmony.ErrorEnum;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;

@CrossOrigin
@RestController
@PreAuthorize
@RequestMapping("/api/file")
public class FileController extends BaseController {
    @Resource
    private MinioUtil minioUtil;

    private final Logger logger = LoggerFactory.getLogger(FileController.class);

    /*
     * @Description 下载图片
     **/
    @GetMapping("/download/image/{fileName}")
    public void downloadImage(@PathVariable("fileName") String fileName, HttpServletResponse httpResponse) {
        if (fileName == null || "null".equals(fileName)) {
           return;
        }
        try {
            GetObjectResponse object = minioUtil.getObject(fileName);
            byte[] buf = new byte[1024];
            int length = 0;
            httpResponse.reset();
            httpResponse.setHeader("Content-Disposition", "filename=" + URLEncoder.encode(fileName, "UTF-8"));
            httpResponse.setContentType(object.headers().get("Content-Type"));
            httpResponse.setCharacterEncoding("utf-8");
            OutputStream outputStream = httpResponse.getOutputStream();
            while ((length = object.read(buf)) > 0) {
                outputStream.write(buf, 0, length);
            }
            outputStream.flush();
            outputStream.close();
        } catch (Exception ex) {
            logger.info("下载失败" + ex.getMessage());
        }
    }

    /*
     * @Description 上传文件
     **/
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public JSONObject upload(HttpServletRequest request) {
        MultipartHttpServletRequest req =(MultipartHttpServletRequest)request;
        MultipartFile file = req.getFile("file");
        assert file != null;
        String fileName = file.getOriginalFilename();
        try {
            String saveName= harmonyUserId + (System.currentTimeMillis()+fileName.substring(fileName.lastIndexOf('.')));
            minioUtil.upload(file, "image/" + saveName);
            JSONObject res = new JSONObject();
            res.put("saveName",saveName);
            res.put("size", file.getSize());
            return CommonUtil.successJson(res);
        } catch (CommonJsonException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.toString());
            throw new CommonJsonException(ErrorEnum.E_400);
        }
    }

    /*
     * @Description 删除文件
     **/
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public JSONObject remove(HttpServletRequest request) {
        try {
            JSONObject jsonObject = HttpRequestReader.getJsonObject(request);
            String fileName = jsonObject.getString("fileName");
            minioUtil.remove(fileName, "image");
            return CommonUtil.successJson("删除成功");
        } catch (CommonJsonException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.toString());
            throw new CommonJsonException(ErrorEnum.E_400);
        }
    }
}
