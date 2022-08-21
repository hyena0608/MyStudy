package spms.servlets;

import spms.controller.Controller;
import spms.controller.MemberListController;
import spms.vo.Member;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        String servletPath = request.getServletPath();

        try {
            ServletContext sc = this.getServletContext();

            HashMap<String, Object> model = new HashMap<>();
            model.put("memberDao", sc.getAttribute("memberDao"));

            String pageControllerPath = null;
            Controller pageController = null;

            if ("/member/list.do".equals(servletPath)) {
                pageController = new MemberListController();
                pageControllerPath = "/member/list.do";
            } else if ("/member/add.do".equals(servletPath)) {
                pageControllerPath = "/member/add";
                if (request.getParameter("email") != null) {
                    request.setAttribute("member", new Member()
                            .setEmail(request.getParameter("email"))
                            .setPassword(request.getParameter("password"))
                            .setName(request.getParameter("name")));
                }
            } else if ("/member/update.do".equals(servletPath)) {
                pageControllerPath = "/member/update";
                if (request.getParameter("email") != null) {
                    request.setAttribute("member", new Member()
                            .setNo(Integer.parseInt(request.getParameter("no")))
                            .setEmail(request.getParameter("email"))
                            .setName(request.getParameter("name")));
                }

            } else if ("/member/delete.do".equals(servletPath)) {
                pageControllerPath = "/member/delete";
            } else if ("/auth/login.do".equals(servletPath)) {
                pageControllerPath = "/auth/login";
            } else if ("/auth/logout.do".equals(servletPath)) {
                pageControllerPath = "/auth/logout";
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher(pageControllerPath);
            dispatcher.include(request, response);

            String viewUrl = pageController.execute(model);

            for (String key : model.keySet()) {
                request.setAttribute(key, model.get(key));
            }

            if (viewUrl.startsWith("redirect:")) {
                response.sendRedirect(viewUrl.substring(9));
                return;
            } else {
                dispatcher = request.getRequestDispatcher(viewUrl);
                dispatcher.include(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Error.jsp");
            dispatcher.forward(request, response);
        }
    }
}
