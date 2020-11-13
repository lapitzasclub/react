package com.llaparra.react.repository;

import com.llaparra.react.domain.Publicacion;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Publicacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PublicacionRepository extends JpaRepository<Publicacion, Long>, JpaSpecificationExecutor<Publicacion> {
}
