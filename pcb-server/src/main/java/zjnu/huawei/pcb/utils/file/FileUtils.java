package zjnu.huawei.pcb.utils.file;

import com.alibaba.fastjson.JSONArray;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.net.ssl.*;
import java.io.*;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.X509Certificate;
import java.util.*;


public class FileUtils
{
    private static final Logger log = LoggerFactory.getLogger(FileUtils.class);

    public static MultipartFile base642MultipartFile(String base64String, String fileName) throws Exception {
        byte[] bytes = Base64.getDecoder().decode(base64String);
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        FileItem fileItem = factory.createItem("images", "images/"+fileName.split("\\.")[1].replace("jpg", "jpeg"), false, fileName);
        IOUtils.copy(new ByteArrayInputStream(bytes), fileItem.getOutputStream());
        return new CommonsMultipartFile(fileItem);
    }

    public static List<MultipartFile> base642MultipartFileList(JSONArray base64String, JSONArray fileName) throws Exception {
        List<MultipartFile> res = new ArrayList<>();
        for (int i = 0; i < base64String.size(); i++) {
            res.add(base642MultipartFile(base64String.getString(i), fileName.getString(i)));
        }
        return res;
    }

    public static void compressImage(InputStream inputStream, OutputStream outputStream, Double quality) throws IOException {
        Thumbnails.of(inputStream)
                .scale(1.0)
                .outputQuality(quality)
                .toOutputStream(outputStream);
    }
}
