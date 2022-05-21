package com.waracle.cakemanagerserver.jpa.repositories;

import com.waracle.cakemanagerserver.jpa.model.CakeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CakeRepository extends JpaRepository<CakeEntity, String> {

    Optional<CakeEntity> getByTitle(final String title);

}
