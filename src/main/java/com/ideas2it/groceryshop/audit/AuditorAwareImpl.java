package com.ideas2it.groceryshop.audit;

import com.ideas2it.groceryshop.configuration.CustomUserDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * <p>
 *     This is Implementation class for AuditorAware,
 *     It is used to find the current user or Auditor
 * </p>
 * @author Mohammed Ammar
 * @since 15-11-2022
 * @version 1.0
 */
@Component
public class AuditorAwareImpl implements AuditorAware<Integer> {

    /**
     * <p>
     *     This method is used to get current user or auditor,
     *     who insert or update any data into database
     * </p>
     * @return id - current user id
     */
    public Optional<Integer> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return Optional.of(((CustomUserDetails) authentication.getPrincipal()).getUser().getId());
        }
        return Optional.empty();
    }
}
