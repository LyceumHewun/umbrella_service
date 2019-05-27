package cc.lyceum.umbrella;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;

//@EnableRedisWebSession(redisFlushMode = RedisFlushMode.IMMEDIATE, maxInactiveIntervalInSeconds = 259200)
@EnableSwagger2Doc
@SpringBootApplication
public class UmbrellaApplication {

    public static void main(String[] args) {
        SpringApplication.run(UmbrellaApplication.class, args);
    }

}
