package object.chap02.step01;

/**
 * 영화를 예매하기 위한 협력
 * - Screening, Movie, Reservation 인스턴스들은 서로의 메서드를 호출하며 협력한다.
 * <p>
 * 협력이란 ?
 * - 시스템의 어떤 기능을 구현하기 위해 객체 사이에 이뤄지는 상호작용
 */
public class Reservation {

    private Customer customer;
    private Screening screening;
    private Money fee;
    private int audienceCount;

    public Reservation(Customer customer, Screening screening, Money fee, int audienceCount) {
        this.customer = customer;
        this.screening = screening;
        this.fee = fee;
        this.audienceCount = audienceCount;
    }
}
