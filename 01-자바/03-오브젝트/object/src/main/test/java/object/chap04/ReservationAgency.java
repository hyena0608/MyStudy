package java.object.chap04;

/**
 * === ReservationAgency를 수정해야할 경우 ===
 * - 할인 정책이 추가되는 경우
 * - 할인 정책별로 할인 요금을 계산하는 방법이 변경될 경우
 * - 할인 조건이 추가되는 경우
 * - 할인 조건별로 할인 여부를 판단하느 방법이 변경될 경우
 * - 예매 요금을 계산하는 방법이 변경될 경우
 */
public class ReservationAgency {
    public Reservation reserve(Screening screening, Customer customer, int audienceCount) {
        Money fee = screening.calculateFee(audienceCount);
        return new Reservation(customer, screening, fee, audienceCount);
    }
}
