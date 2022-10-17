package object.chap01.step03;

public class TicketSeller {

    private TicketOffice ticketOffice;

    public TicketSeller(TicketOffice ticketOffice) {
        this.ticketOffice = ticketOffice;
    }
    public void sellTo(Audience audience) {
        ticketOffice.plusAmount(ticketOffice.getTicket().getFee());
    }
}
