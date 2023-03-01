package spms.listeners;

// 서버에서 제공하는 DataSource 사용하기
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import spms.context.ApplicationContext;
import spms.controls.*;
import spms.dao.MysqlMemberDao;

@WebListener
public class ContextLoaderListener implements ServletContextListener {

  static ApplicationContext applicationContext;

  public static ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  @Override
  public void contextInitialized(ServletContextEvent event) {
    try {
      ServletContext sc = event.getServletContext();
      
      String propertiesPath = sc.getRealPath(
              sc.getInitParameter("contextConfigLocation")
      );
      applicationContext = new ApplicationContext(propertiesPath);

    } catch(Throwable e) {
      e.printStackTrace();
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent event) {}
}
