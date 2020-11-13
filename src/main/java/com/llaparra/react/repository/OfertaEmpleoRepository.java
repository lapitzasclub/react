package com.llaparra.react.repository;

import com.llaparra.react.domain.OfertaEmpleo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the OfertaEmpleo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfertaEmpleoRepository extends JpaRepository<OfertaEmpleo, Long>, JpaSpecificationExecutor<OfertaEmpleo> {
}
