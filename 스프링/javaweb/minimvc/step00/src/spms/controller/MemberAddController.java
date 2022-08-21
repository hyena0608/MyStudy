package spms.controller;

import spms.dao.MemberDao;
import spms.vo.Member;

import java.util.Map;

public class MemberAddController implements Controller {

    @Override
    public String execute(Map<String, Object> model) throws Exception {
        if (model.get("member") == null) {
            return "/member/MemberForm";
        } else {
            MemberDao memberDao = (MemberDao) model.get("member");

            Member member = (Member) model.get("member");
            memberDao.insert(member);

            return "redirect:list.do";
        }
    }
}
