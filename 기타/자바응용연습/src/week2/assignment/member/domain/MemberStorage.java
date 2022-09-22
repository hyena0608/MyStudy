package week2.assignment.member.domain;

import week2.assignment.money.domain.PocketMoney;

import java.util.List;

public class MemberStorage {
    private List<Member> memberList;
    private PocketMoney pocketMoney;


    public MemberStorage(List<Member> memberList, PocketMoney pocketMoney) {
        this.memberList = memberList;
        this.pocketMoney = pocketMoney;
    }

    public PocketMoney getPocketMoney() {
        return pocketMoney;
    }

}
