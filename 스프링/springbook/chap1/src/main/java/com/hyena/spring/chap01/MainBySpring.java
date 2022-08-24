package com.hyena.spring.chap01;

import org.springframework.context.support.GenericXmlApplicationContext;

public class MainBySpring {

    public static void main(String[] args) {
        String configLocation = "classpath:chap01_applicationContext.xml";
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext(configLocation);

        Project project = ctx.getBean("sampleProject", Project.class);
        project.build();
        ctx.close();
    }
}
