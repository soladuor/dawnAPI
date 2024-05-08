package com.soladuor.config;

import com.soladuor.utils.BaseUtil;
import com.soladuor.utils.zydsoft.DialecticalCloud;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration      // 1. 标记配置类，使springboot容器能够扫描到（其实用@component注解也行的）
@EnableScheduling   // 2. 开启定时任务
@Slf4j
public class ScheduleConfig {

    // 3. 添加一个定时任务
    // 这里的corn表达式为：【秒 分 时 日 月 星期】
    // @Scheduled(cron = "0 0 */2 * * ?")
    // fixedDelay: 上一次执行完毕时间点之后多长时间再执行（单位毫秒）
    // fixedRate: 上一次开始执行时间点之后多长时间再执行（单位毫秒）
    @Scheduled(fixedDelay = 7000 * 1000)
    public void taskToken() {
        String tokenFromApi = DialecticalCloud.getTokenFromApi();
        if (!BaseUtil.isEmpty(tokenFromApi)) {
            // log.info("token更新成功 ==> " + LocalDateTime.now());
            System.out.println("Token update successful!!!");
        } else {
            // log.info("token更新失败 ==> " + LocalDateTime.now());
            System.out.println("Token update failed");
        }
    }

}
