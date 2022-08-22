package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MysqlMemberDao;
import spms.vo.Member;

@Component("/member/update")
public class MemberUpdateController implements Controller, DataBinding {

  MysqlMemberDao memberDao;

  public MemberUpdateController setMemberDao(MysqlMemberDao memberDao) {
    this.memberDao = memberDao;
    return this;
  }

  @Override
  public String execute(Map<String, Object> model) throws Exception {

    if (model.get("member") == null) { 
      Integer no = (Integer)model.get("no");
      Member member = memberDao.selectOne(no);
      model.put("member", member);
      return "/member/MemberUpdateForm.jsp";

    } else { 
      Member member = (Member)model.get("member");
      memberDao.update(member);
      return "redirect:list.do";
    }
  }

  @Override
  public Object[] getDataBinders() {
    return new Object[] {
            "no", Integer.class,
            "member", spms.vo.Member.class
    };
  }
}
