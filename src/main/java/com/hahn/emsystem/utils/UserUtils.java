package com.hahn.emsystem.utils;


import com.hahn.emsystem.entity.Employee;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class UserUtils {

    /**
     * Retrieves the authenticated Employee object from the security context.
     *
     * @return The authenticated Employee object or null if no authentication exists.
     */
    public static String getAuthenticatedUserUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }

        return null;
    }



    /**
     * Checks if the current user has a specific role.
     *
     * @param role The role to check (e.g., "ROLE_MANAGER").
     * @return true if the user has the role; otherwise, false.
     */
    public static boolean hasRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(role));
    }
}

