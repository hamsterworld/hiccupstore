package hiccup.hiccupstore.commonutil;

import hiccup.hiccupstore.commonutil.aspect.LogTraceAspect;
import hiccup.hiccupstore.commonutil.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AopConfig {

    @Bean
    public LogTraceAspect logTraceAspect(LogTrace logTrace){
        return new LogTraceAspect(logTrace);
    }

    @Bean
    public LogTrace logTrace(){
        return new LogTrace();
    }

}
