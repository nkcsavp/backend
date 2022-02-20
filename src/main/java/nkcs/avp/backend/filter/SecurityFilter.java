package nkcs.avp.backend.filter;

import nkcs.avp.backend.domain.User;
import nkcs.avp.backend.util.SessionUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/info/*","/submit"}, filterName = "SecurityFilter")
public class SecurityFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException, IOException {
        HttpSession session = ((HttpServletRequest)servletRequest).getSession();
        User user = (User) session.getAttribute("user");
        if(user == null) {
            servletResponse.getWriter().write("[ERROR]Need Log In");
            return;
        }
        if(!SessionUtil.sessionSave.get(user.getMail()).equals(session.getId())) {
            servletResponse.getWriter().write("[ERROR]Your Account has Signed On the Other Device");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}