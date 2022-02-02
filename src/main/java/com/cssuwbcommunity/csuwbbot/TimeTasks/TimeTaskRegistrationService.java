package com.cssuwbcommunity.csuwbbot.TimeTasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service("timeTaskRegistrationService")
public class TimeTaskRegistrationService {
    private Logger logger = LoggerFactory
        .getLogger(TimeTaskRegistrationService.class);
    //Timer service
    private final TimerService timerService;
    //Tasks
    private final AskRedditTimeTask askRedditTimeTask;
    private final LeetcodeProblemTimeTask leetcodeProblemTimeTask;
    @Autowired
    public TimeTaskRegistrationService(final TimerService timerService,
        final AskRedditTimeTask askRedditTimeTask,
        final LeetcodeProblemTimeTask leetcodeProblemTimeTask) {
        this.timerService = timerService;
        this.askRedditTimeTask = askRedditTimeTask;
        this.leetcodeProblemTimeTask = leetcodeProblemTimeTask;
    }
    @Bean
    private void registerTasks() {
        logger.info("Registering daily tasks");
        timerService.scheduleDailyTask(askRedditTimeTask);
        timerService.scheduleDailyTask(leetcodeProblemTimeTask);
        logger.info("Daily tasks registered");
    }
}
