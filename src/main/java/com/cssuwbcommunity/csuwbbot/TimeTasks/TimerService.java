package com.cssuwbcommunity.csuwbbot.TimeTasks;

import com.cssuwbcommunity.csuwbbot.registration.SettingsService;
import com.google.gson.JsonObject;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service("timerService")
public class TimerService {
    private final SettingsService settingsService;
    private static final long MILLISECONDS_IN_DAY = 1000 * 60 * 60 * 24;
    private final Timer dailyTimer;
    @Autowired
    public TimerService(final SettingsService settingsService) {
        this(settingsService, new Timer("DailyTasks"));
    }
    public TimerService(final SettingsService settingsService,
                        final Timer dailyTimer){
        this.settingsService = settingsService;
        this.dailyTimer = dailyTimer;
    }
    public void scheduleDailyTask(final TimerTask task) {
        final Calendar now = Calendar.getInstance();
        final Calendar nextExecutionTime = getNextExecutionTime();
        long delay = 0;
        if(nextExecutionTime.after(now)) {
            delay = nextExecutionTime.getTimeInMillis()
                - now.getTimeInMillis();
        }
        else {
            delay = now.getTimeInMillis()
                - nextExecutionTime.getTimeInMillis();
        }
        dailyTimer.schedule(task, delay, MILLISECONDS_IN_DAY);
    }
    private Calendar getNextExecutionTime() {
        final JsonObject settingsObject = settingsService
            .getSettingsObject();
        final JsonObject timeObject = settingsObject
            .get("discord")
            .getAsJsonObject()
            .get("daily_time")
            .getAsJsonObject();
        final Calendar now = Calendar.getInstance();
        final int scheduleHour = timeObject
            .get("hour")
            .getAsInt();
        final int scheduleMinute = timeObject
            .get("minute")
            .getAsInt();
        final Calendar nextDate = Calendar.getInstance();
        nextDate.set(Calendar.HOUR_OF_DAY, scheduleHour);
        nextDate.set(Calendar.MINUTE, scheduleMinute);
        if(now.after(nextDate)) {
            nextDate.add(Calendar.DAY_OF_MONTH, 1);
        }
        return nextDate;
    }
}
