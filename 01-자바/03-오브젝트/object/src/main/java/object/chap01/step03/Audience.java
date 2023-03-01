package object.chap01.step03;

public class Audience {

    private Bag bag;

    public Audience(Bag bag) {
        this.bag = bag;
    }

    /**
     * Bag의 구현이 아닌 인터페이스에만 의존하도록 수정
     */
    public Long buy(Ticket ticket) {
        return bag.hold(ticket);
    }
}
