package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MysqlMemberDao;

import javax.xml.crypto.Data;

@Component("/member/delete")
public class MemberDeleteController implements Controller, DataBinding {
  MysqlMemberDao memberDao;

  public MemberDeleteController setMemberDao(MysqlMemberDao memberDao) {
    this.memberDao = memberDao;
    return this;
  }

  @Override
  public String execute(Map<String, Object> model) throws Exception {

    Integer no = (Integer)model.get("no");
    memberDao.delete(no);
    
    return "redirect:list.do";
  }

  @Override
  public Object[] getDataBinders() {
    return new Object[] {
            "no", Integer.class
    };
  }
}
