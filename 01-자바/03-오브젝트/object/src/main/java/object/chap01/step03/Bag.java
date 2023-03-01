package object.chap01.step03;

public class Bag {
    private Long amount;    // 현금
    private Invitation invitation;  // 초대장
    private Ticket ticket;  // 티켓

    public Bag(long amount) {
        this(null, amount);
    }

    public Bag(Invitation invitation, long amount) {
        this.invitation = invitation;
        this.amount = amount;
    }

    /**
     * Bag 자율적인 존재로 변환
     * - Bag 내부 상태에 접근하는 모든 로직을 Bag 안으로 캡슐화해서 결합도를 낮춘다.
     */
    public Long hold(Ticket ticket) {
        if (hasInvitation()) {
            setTicket(ticket);
            return 0L;
        }              else {
            setTicket(ticket);
            minusAmount(ticket.getFee());
            return ticket.getFee();
        }
    }

    private boolean hasInvitation() {
        return invitation != null;
    }

    private void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    private void minusAmount(Long amount) {
        this.amount -= amount;
    }

}
