package monitoring_ozone.service;

import jakarta.security.auth.message.AuthException;
import monitoring_ozone.constants.Errors;
import monitoring_ozone.service.userdetails.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CheckAccessService {

    public static void checkAccess(final Long id) throws AuthException {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!id.equals(customUserDetails.getUserId()) &&
                !customUserDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CLIENT"))) {
            throw new AuthException(HttpStatus.FORBIDDEN + ": " + Errors.Users.USER_FORBIDDEN_ERROR);
        }
    }
}
