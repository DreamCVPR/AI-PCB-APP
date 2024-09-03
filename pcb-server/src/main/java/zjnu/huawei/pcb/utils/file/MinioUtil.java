package zjnu.huawei.pcb.utils.file;

import com.alibaba.fastjson.JSONArray;
import io.minio.*;
import lombok.Cleanup;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import sun.misc.BASE64Decoder;
import zjnu.huawei.pcb.config.MinioConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import zjnu.huawei.pcb.controller.harmony.TaskController;

import javax.annotation.Resource;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class MinioUtil {
    @Resource
    private MinioConfig minioConfig;
    @Resource
    private MinioClient client;

    private final Logger logger = LoggerFactory.getLogger(MinioUtil.class);

    /*
     * @Description 下载
     **/
    public GetObjectResponse getObject(String fileUrl) throws Exception {
        if (fileUrl == null) {
            return null;
        }
        GetObjectArgs args = GetObjectArgs.builder()
                .bucket(minioConfig.getBucketName())
                .object("image/"+fileUrl)
                .build();
        return client.getObject(args);
    }

    /*
     * @Description 下载
     **/
    public MultipartFile getFile(String fileUrl, String originName) throws Exception {
        if (fileUrl == null) {
            return null;
        }
        GetObjectResponse object = getObject(fileUrl);
        @Cleanup ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int length;
        while ((length = object.read(buf)) > 0) {
            outputStream.write(buf, 0, length);
        }
        byte[] bytes = outputStream.toByteArray();
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        FileItem fileItem = factory.createItem("images", object.headers().get("Content-Type"), false, originName);
        IOUtils.copy(new ByteArrayInputStream(bytes), fileItem.getOutputStream());
        return new CommonsMultipartFile(fileItem);
    }

    /*
     * @Description 上传
     **/
    public String upload(MultipartFile file, String fileUrl) throws Exception {
        if (fileUrl == null) {
            return null;
        }
        try {
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(fileUrl)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build();
            client.putObject(args);
            return fileUrl;
        } catch (Exception e) {
            logger.error(e.toString());
            return null;
        }
    }

    /*
     * @Description 删除
     **/
    public void remove(String fileUrl) throws Exception {
        RemoveObjectArgs args = RemoveObjectArgs.builder()
                .bucket(minioConfig.getBucketName())
                .object("image/"+fileUrl)
                .build();
        client.removeObject(args);
    }
}
