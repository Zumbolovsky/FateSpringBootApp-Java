package br.com.zumbolovsky.fateapp.config.security;

import br.com.zumbolovsky.fateapp.domain.DefaultRoles;
import br.com.zumbolovsky.fateapp.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import liquibase.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Objects;

@Configuration
@EnableMethodSecurity(
    prePostEnabled = true,
    jsr250Enabled = true,
    securedEnabled = true
)
class SecurityConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);


    private final UserService userService;
    private final JwtTokenFilter jwtTokenFilter;

    public SecurityConfig(UserService userService, JwtTokenFilter jwtTokenFilter) {
        this.userService = userService;
        this.jwtTokenFilter = jwtTokenFilter;
    }

    public static class SecurityConstants {
        public static final String[] SECURED_URLS = new String[]{"/fate/secured/**", "/fate/authorization/**"};
        private static final String HIERARCHY = DefaultRoles.ADMIN + " > " + DefaultRoles.STAFF + " \n " + DefaultRoles.STAFF + " > " + DefaultRoles.USER;
        public static final String KEY = "q3t6w9z$C&F)J@NcQfTjWnZr4u7x!A%D*G-KaPdSgUkXp2s5v8y/B?E(H+MbQeTh";
        public static final String ISSUER = "br/com/zumbolovsky/fateapp";
        //30 Minutes
        public static final Integer EXPIRATION_TIME = 1000 * 60 * 30;

        @Bean
        public RoleHierarchy roleHierarchy() {
            final RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
            roleHierarchy.setHierarchy(HIERARCHY);
            LOGGER.info("Role hierarchy set to '{}'", HIERARCHY);
            return roleHierarchy;
        }

        @Bean
        public MethodSecurityExpressionHandler methodSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
            final DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
            expressionHandler.setRoleHierarchy(roleHierarchy);
            expressionHandler.setDefaultRolePrefix(null);
            return expressionHandler;
        }
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.cors(
                corsConfigurer -> {
                    final UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
                    configurationSource.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
                    corsConfigurer.configurationSource(configurationSource);
                })
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(it -> it.requestMatchers(SecurityConstants.SECURED_URLS).authenticated().anyRequest().permitAll())
                .exceptionHandling(it ->
                        it.authenticationEntryPoint((request, response, authException) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage())))
                .sessionManagement(it -> it.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(
                        jwtTokenFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider())
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) {
                final UserDetails userDetails = userService.loadUserByUsername(authentication.getName());
                var username = authentication.getName();
                var password = MD5Util.computeMD5(authentication.getCredentials().toString());
                return Objects.equals(userDetails.getUsername(), username) && Objects.equals(userDetails.getPassword(), password) ?
                    new UsernamePasswordAuthenticationToken(username, password) : null;
            }

            @Override
            public boolean supports(Class <?> authentication) {
                return authentication == UsernamePasswordAuthenticationToken.class;
            }
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}