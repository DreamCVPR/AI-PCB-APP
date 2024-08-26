package zjnu.huawei.pcb.utils;

import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Image2Base64 {
    public String getImageBase64(String imagePath) {
        byte[] data = null;
        try {
            InputStream in = new FileInputStream(imagePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }
}
