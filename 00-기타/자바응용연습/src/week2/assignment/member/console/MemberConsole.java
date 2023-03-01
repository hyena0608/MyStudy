package week2.assignment.member.console;

import week2.assignment.member.domain.Member;

import java.util.List;

public class MemberConsole {

    public void printWhenCreateMember() {
        System.out.print("등록할 멤버를 입력해주새요 : ");
    }

    public void printWantContinue() {
        System.out.print("계속 하시곘습니까? [y || n] : ");
    }

    public void printCurrentHeadcount(List<Member> memberList) {
        System.out.println("현재 인원은 " + memberList.size() + "명 입니다.\n");
    }

    public void printMemberList(List<Member> memberList) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("\n현재 그룹 멤버는 ");

        memberList.forEach(
                member -> stringBuffer.append(member.getName()).append(", ")
        );

        stringBuffer.append(" 입니다");

        System.out.println(stringBuffer.toString());
    }
}
