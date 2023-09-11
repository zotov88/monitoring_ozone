package monitoring_ozone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MonitoringOzoneApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitoringOzoneApplication.class, args);
    }

}
