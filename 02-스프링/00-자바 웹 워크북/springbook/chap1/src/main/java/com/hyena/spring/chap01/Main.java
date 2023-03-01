package com.hyena.spring.chap01;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        MavenBuildRunner buildRunner = new MavenBuildRunner();
        buildRunner.setMavenPath("대충 메이븐 경로라 칩시다 !");

        Project sameProject = new Project();
        List<String> srcDirs = new ArrayList<>();
        srcDirs.add("src");
        srcDirs.add("srcResources");
        sameProject.setSrcDirs(srcDirs);
        sameProject.setBinDir("bin");
        sameProject.setBuildRunner(buildRunner);

        sameProject.build();
    }
}
