package spms.controls;

import spms.annotation.Component;

import java.util.Map;

import javax.servlet.http.HttpSession;

@Component("/auth/logout")
public class LogOutController implements Controller {

    @Override
    public String execute(Map<String, Object> model) throws Exception {
        HttpSession session = (HttpSession) model.get("session");
        session.invalidate();

        return "redirect:login.do";
    }
}
