package zjnu.huawei.pcb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Controller;

@Controller
@ServletComponentScan
@MapperScan("zjnu.huawei.pcb.mapper")
@SpringBootApplication
public class PcbServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PcbServerApplication.class, args);
    }

}
