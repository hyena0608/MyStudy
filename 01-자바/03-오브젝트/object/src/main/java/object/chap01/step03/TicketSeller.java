package object.chap01.step03;

public class TicketSeller {

    private TicketOffice ticketOffice;

    public TicketSeller(TicketOffice ticketOffice) {
        this.ticketOffice = ticketOffice;
    }

    /**
     * TicketOffice의 자율권 침해
     * - TicketSeller가 TicketOffice의 구현이 아닌 인터페이스에만 의존하게 됐다.
     */
    public void sellTo(Audience audience) {
        ticketOffice.sellTicketTo(audience);
    }
}
