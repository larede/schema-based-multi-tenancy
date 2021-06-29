package schema.multitenancy.auth;

import schema.multitenancy.entity.Tenant;
import schema.multitenancy.repository.TenantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class TokenAuthorizationFilter extends BasicAuthenticationFilter {
    private static final Logger log = LoggerFactory.getLogger(TokenAuthorizationFilter.class);
    private final TenantRepository tenantRepository;

    public TokenAuthorizationFilter(AuthenticationManager authenticationManager, ApplicationContext applicationContext) {
        super(authenticationManager);

        this.tenantRepository = applicationContext.getBean(TenantRepository.class);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        String tenantId = request.getHeader("X-TenantID");
        if (!StringUtils.isEmpty(tenantId)) {
            Optional<Tenant> tenant = this.tenantRepository.findByName(tenantId);
            if (tenant.isPresent()) {
                AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(tenantId, "", null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                log.error(tenantId + " not found in tenantRepository.");
            }
        }

        filterChain.doFilter(request, response);
    }
}
