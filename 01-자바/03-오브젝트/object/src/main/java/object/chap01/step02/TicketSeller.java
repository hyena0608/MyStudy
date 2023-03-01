package object.chap01.step02;

public class TicketSeller {

    private TicketOffice ticketOffice;

    public TicketSeller(TicketOffice ticketOffice) {
        this.ticketOffice = ticketOffice;
    }

    /**
     * TicketSeller 캡슐화
     * TicketOffice에 대한 접근은 오직 TicketSeller 안에만 존재하게 된다.
     * (기존 : Theater에서 TicketOffice 접근 가능했었다.)
     */
    public void sellTo(Audience audience) {
        ticketOffice.plusAmount(ticketOffice.getTicket().getFee());
    }
}
