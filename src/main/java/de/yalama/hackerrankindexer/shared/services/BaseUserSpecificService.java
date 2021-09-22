package de.yalama.hackerrankindexer.shared.services;

import de.yalama.hackerrankindexer.shared.models.BaseEntity;

import java.util.List;

/**
 * An interface for all services that deal with "user specific data" (19.9.21: Only submissions)
 */
public interface BaseUserSpecificService<T extends BaseEntity> {
    /**
     * Filters all data by userId
     * @param userId the userId to filter by
     * @return All data with matching userId
     */
    List<T> findAllByUserId(Long userId);
}
