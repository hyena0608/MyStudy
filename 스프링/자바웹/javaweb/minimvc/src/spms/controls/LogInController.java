package spms.controls;

import java.util.Map;

import javax.servlet.http.HttpSession;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MysqlMemberDao;
import spms.vo.Member;

@Component("/auth/login")
public class LogInController implements Controller, DataBinding {
    MysqlMemberDao memberDao;

    public LogInController setMemberDao(MysqlMemberDao memberDao) {
        this.memberDao = memberDao;
        return this;
    }

    @Override
    public String execute(Map<String, Object> model) throws Exception {
        Member loginInfo = (Member) model.get("loginInfo");
        if (loginInfo.getEmail() == null) { // 입력폼을 요청할 때
            return "/auth/LogInForm.jsp";
        } else { // 회원 등록을 요청할 때

            Member member = memberDao.exist(
                    loginInfo.getEmail(),
                    loginInfo.getPassword());

            if (member.getEmail() != null) {
                HttpSession session = (HttpSession) model.get("session");
                session.setAttribute("member", member);
                return "redirect:../member/list.do";
            } else {
                return "/auth/LogInFail.jsp";
            }
        }
    }

    @Override
    public Object[] getDataBinders() {
        return new Object[]{
                "loginInfo", spms.vo.Member.class
        };
    }
}
