package com.doubleo.passservice.domain.pass.scheduler;

import com.doubleo.passservice.domain.pass.domain.Pass;
import com.doubleo.passservice.domain.pass.enums.IssuanceStatus;
import com.doubleo.passservice.domain.pass.repository.PassRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class PassStatusScheduler {

    private final PassRepository passRepository;

    @Scheduled(cron = "0 0 10 * * *")
    @Transactional
    public void expirePasses() {
        log.info("Expire passes");

        List<Pass> expiredPasses =
                passRepository.findAllByExpiredAtBeforeAndIssuanceStatusNot(
                        LocalDateTime.now(), IssuanceStatus.EXPIRED);

        expiredPasses.forEach(pass -> pass.updateStatus(IssuanceStatus.EXPIRED));
        passRepository.saveAll(expiredPasses);

        log.info("{} passes expired", expiredPasses.size());
    }
}
