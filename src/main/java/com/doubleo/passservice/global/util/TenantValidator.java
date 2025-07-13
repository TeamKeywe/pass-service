package com.doubleo.passservice.global.util;

import com.doubleo.passservice.domain.common.model.Tenant;
import com.doubleo.passservice.global.exception.CommonException;
import com.doubleo.passservice.global.exception.errorcode.TenantErrorCode;
import com.doubleo.tenantcontext.TenantContextHolder;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TenantValidator<T extends Tenant> {

    public String getTenantId() {
        return Optional.ofNullable(TenantContextHolder.getTenantId())
                .orElseThrow(() -> new CommonException(TenantErrorCode.TENANT_ID_NOT_FOUND));
    }

    public T validateTenant(T entity) {
        String currentTenantId = getTenantId();
        log.info("validateTenant: currentTenantId={}", currentTenantId);
        if (!entity.getTenantId().equals(currentTenantId)) {
            throw new CommonException(TenantErrorCode.INVALID_TENANT_ID);
        }
        return entity;
    }
}
