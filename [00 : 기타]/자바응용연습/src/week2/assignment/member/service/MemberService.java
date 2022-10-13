package week2.assignment.member.service;

import week2.assignment.member.domain.Member;
import week2.assignment.member.console.MemberConsole;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemberService {
    private final MemberConsole memberConsole = new MemberConsole();

    private final Scanner sc = new Scanner(System.in);

    public List<Member> createMemberList() {
        List<Member> memberList = new ArrayList<>();

        while (true) {
            Member member = createMember();
            if (isMemberNull(member)) {
                break;
            }
            memberList.add(member);
            if (!wantContinue()) {
                memberConsole.printMemberList(memberList);
                break;
            }
            memberConsole.printCurrentHeadcount(memberList);
        }

        return memberList;
    }

    private boolean isMemberNull(Member member) {
        if (member == null) {
            return true;
        }
        return false;
    }

    private Member createMember() {
        memberConsole.printWhenCreateMember();
        return new Member(sc.nextLine());
    }

    private boolean wantContinue() {
        memberConsole.printWantContinue();
        String answer = sc.nextLine();
        if (answer.equals("y")) {
            return true;
        }
        return false;
    }
}
