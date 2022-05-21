package com.waracle.cakemanagerserver.service;

import com.waracle.cakemanagerserver.controller.model.AddCake;
import com.waracle.cakemanagerserver.controller.model.Cake;
import com.waracle.cakemanagerserver.jpa.model.CakeEntity;
import com.waracle.cakemanagerserver.jpa.repositories.CakeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Cakes service.
 */
@Service
public class CakesService {

    private final CakeRepository cakeRepository;

    /**
     * Cakes service constructor.
     *
     * @param cakeRepository cake repository
     */
    public CakesService(final CakeRepository cakeRepository) {
        Assert.notNull(cakeRepository, "cakeRepository cannot be null.");
        this.cakeRepository = cakeRepository;
    }

    /**
     * Get list of cakes.
     *
     * @return list of {@link Cake}
     */
    @Transactional(readOnly = true)
    public List<Cake> getCakes() {
        final var cakes = cakeRepository.findAll();
        return cakes
                .stream()
                .map(cakeEntity -> new Cake(cakeEntity.getId(), cakeEntity.getTitle(), cakeEntity.getDescription(), cakeEntity.getImageUrl()))
                .collect(Collectors.toList());
    }

    /**
     * Get cake by title
     *
     * @param title cake title
     * @return optional {@link CakeEntity}
     */
    public Optional<CakeEntity> getByTitle(final String title) {
        return cakeRepository.getByTitle(title);
    }

    /**
     * Add cake.
     *
     * @param addCake add cake
     * @return {@link Cake}
     */
    @Transactional
    public Cake addCake(final AddCake addCake) {

        final var cakeEntity = new CakeEntity();
        cakeEntity.setTitle(addCake.getTitle());
        cakeEntity.setDescription(addCake.getDescription());
        cakeEntity.setImageUrl(addCake.getImageUrl());

        final var savedCake = cakeRepository.save(cakeEntity);
        return new Cake(savedCake.getId(), savedCake.getTitle(), savedCake.getDescription(), savedCake.getImageUrl());

    }

}
