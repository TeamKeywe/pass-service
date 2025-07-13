package com.doubleo.passservice.domain.pass.repository;

import com.doubleo.passservice.domain.pass.domain.Pass;
import com.doubleo.passservice.domain.pass.domain.PassArea;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassAreaRepository extends JpaRepository<PassArea, Long> {
    List<PassArea> findAllByPass(Pass pass);
}
