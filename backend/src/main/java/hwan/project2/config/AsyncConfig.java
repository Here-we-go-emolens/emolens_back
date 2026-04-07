package hwan.project2.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "analysisExecutor")
    public Executor analysisExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(10000);
        executor.setThreadNamePrefix("ai-analysis-");
        executor.setRejectedExecutionHandler(new AnalysisRejectedHandler());
        executor.initialize();
        return executor;
    }

    /**
     * 큐가 가득 찼을 때 호출.
     * 작업을 버리지 않고 FAILED로 기록 → 재시도 스케줄러가 처리.
     */
    static class AnalysisRejectedHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            log.warn("AI 분석 큐 초과 - 작업 거부됨. 재시도 스케줄러가 처리합니다. " +
                    "activeCount={}, queueSize={}", executor.getActiveCount(), executor.getQueue().size());
            // 작업을 버림 → diary는 PENDING 또는 ANALYZING 상태로 남음
            // AnalysisRetryScheduler가 주기적으로 재처리
        }
    }
}
