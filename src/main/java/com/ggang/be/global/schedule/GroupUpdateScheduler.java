package com.ggang.be.global.schedule;

import com.ggang.be.api.group.everyGroup.service.EveryGroupService;
import com.ggang.be.api.group.onceGroup.service.OnceGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GroupUpdateScheduler {

    private final OnceGroupService onceGroupService;
    private final EveryGroupService everyGroupService;

    @Async("asyncEveryGroupUpdater")
    @Scheduled(cron = "0 0,30 * * * *")
    public void updateEveryGroup(){
        log.info("updateEveryGroup");
        onceGroupService.updateStatus();
        log.info("finish updateEveryGroup");
    }

    @Async("asyncOnceGroupUpdater")
    @Scheduled(cron = "0 0,30 * * * *")
    public void updateOnceGroup(){
        log.info("updateOnceGroup");
        everyGroupService.updateStatus();
        log.info("finish updateEveryGroup");
    }

}
