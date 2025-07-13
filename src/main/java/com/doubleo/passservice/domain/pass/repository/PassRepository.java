package com.doubleo.passservice.domain.pass.repository;

import com.doubleo.passservice.domain.pass.domain.Pass;
import com.doubleo.passservice.domain.pass.enums.IssuanceStatus;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassRepository extends JpaRepository<Pass, Long> {
    List<Pass> findAllByMemberId(Long memberId);

    Page<Pass> findAllByTenantIdAndIssuanceStatus(
            String tenantId, IssuanceStatus issuanceStatus, Pageable pageable);

    List<Pass> findAllByExpiredAtBeforeAndIssuanceStatusNot(
            LocalDateTime now, IssuanceStatus issuanceStatus);
}
