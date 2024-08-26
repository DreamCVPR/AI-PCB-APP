package zjnu.huawei.pcb.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Jpython {
    public static String pythonTrackApi (String mmsi, String btime,String etime) {
        Process proc;
        try {
//            proc = Runtime.getRuntime().exec("sudo python3 /home/ubuntu/server/ship.py" + " " + mmsi + " " + btime
//                    + " " + etime);// 执行py文件 服务器端部署
            proc = Runtime.getRuntime().exec("python .\\src\\main\\resources\\shipTrackLocal.py" + " " + mmsi + " " + btime
            + " " + etime);// 执行py文件
            proc.waitFor();
            return "success";
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "error";
        }
    }
    public static void FileWriteDate (String beginTime) {
        try {
            File writeFile = new File(".\\src\\main\\resources\\last_time.txt");
            if (!writeFile.isFile()) {
                writeFile.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(".\\src\\main\\resources\\last_time.txt"));
            writer.write(beginTime + "\r\n");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void FileWriteSingleString (String mmsi) {
        try {
            File writeFile = new File(".\\src\\main\\resources\\mmsi.txt");
            if (!writeFile.isFile()) {
                writeFile.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(".\\src\\main\\resources\\mmsi.txt"));
            writer.write(mmsi + "\r\n");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void FileWrite (List<String> mmsiNumbers) {
        try {
            File writeFile = new File(".\\src\\main\\resources\\mmsi.txt");
            if (!writeFile.isFile()) {
                writeFile.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(".\\src\\main\\resources\\mmsi.txt"));
            for (String mmsi:mmsiNumbers) {
                writer.write(mmsi + "\r\n");
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String pythonAisApi () {
        Process proc;
        try {
//            proc = Runtime.getRuntime().exec("sudo python3 /home/ubuntu/server/ship.py" + " " + mmsi + " " + btime
//                    + " " + etime);// 执行py文件 服务器端部署
            proc = Runtime.getRuntime().exec("python .\\src\\main\\resources\\shipAisLocal.py");// 执行py文件
//            proc.waitFor();
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
    public static void main (String[] args) {
//        List<String> s = new ArrayList<String>();
//        s.add("567891234");
//        FileWrite(s);
//        File directory = new File("./src/main/resources/ship.py");
//        System.out.println(directory.getPath());
    }
}
