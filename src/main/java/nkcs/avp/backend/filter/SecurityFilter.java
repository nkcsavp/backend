package nkcs.avp.backend.filter;

import nkcs.avp.backend.domain.User;
import nkcs.avp.backend.util.SessionUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/info/*","/submit"}, filterName = "SecurityFilter")
public class SecurityFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException, IOException {
        HttpSession session = ((HttpServletRequest)servletRequest).getSession();
        User user = (User) session.getAttribute("user");
        if(user == null) {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write("{\"code\":\"403\",\"msg\":\"Need Log In\"}");
            response.setStatus(403);
            response.getWriter().flush();
            return;
        }
        if(!SessionUtil.sessionSave.get(user.getMail()).equals(session.getId())) {
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write("{\"code\":\"403\",\"msg\":\"Your Account has Signed On Other Devices\"}");
            response.setStatus(403);
            response.getWriter().flush();
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}