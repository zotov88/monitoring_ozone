package monitoring_ozone.constants;

import java.util.List;

public interface WebSecurityConstants {

    List<String> RESOURCES_WHITE_LIST = List.of
            (
                    "/resources/**",
                    "/static/**",
                    "/js/**",
                    "/images/**",
                    "/css/**",
                    "/"
            );
    List<String> AUTH_LIST = List.of
            (
                    "/products/**"
            );

}
