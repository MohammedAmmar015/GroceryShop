/*
 * <p>
 *   Copyright (c) All rights reserved Ideas2IT
 * </p>
 */
package com.ideas2it.groceryshop.audit;

import com.ideas2it.groceryshop.configuration.CustomUserDetails;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * <p>
 *      Provide implementation for AuditorAware, to find the current user or Auditor
 *      from security context if user made any changes to entity
 * </p>
 *
 * @author Mohammed Ammar
 * @version 1.0
 * @since 15-11-2022
 */
@Component
public class AuditorAwareImpl implements AuditorAware<Integer> {

    /**
     * <p>
     *      To get user id of principal or currently logged-in user from security context
     * </p>
     *
     * @return user id - used to store as created by, modified by
     */
    @Override
    public Optional<Integer> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return Optional.of(((CustomUserDetails) authentication.getPrincipal()).getUser().getId());
        }
        return Optional.empty();
    }
}
