package object.chap04.step02;

import java.time.LocalDateTime;

public class Screening {

    private Movie movie;
    private int sequence;
    private LocalDateTime whenScreened;

    /**
     * DiscountCondition과 Movie의 내부 구현이 인터페이스에 노출
     * -> Screening은 노출된 구현에 직접적으로 의존
     * -> 낮은 응집도
     * DiscountCondition 판단 정보 변경 시
     * Movie의 isDiscountable 파라미터 종류가 변경되고
     * Screening에서 Movie를 호출하는 isDiscountable 메서드도 변경되야 한다.
     */
    public Money calculateFee(int audienceCount) {
        switch (movie.getMovieType()) {
            case AMOUNT_DISCOUNT:
                if (movie.isDiscountable(whenScreened, sequence)) {
                    return movie.calculateAmountDiscountedFee().times(audienceCount);
                }
                break;
            case PERCENT_DISCOUNT:
                if (movie.isDiscountable(whenScreened, sequence)) {
                    return movie.calculatePercentDiscountedFee().times(audienceCount);
                }
            case NONE_DISCOUNT:
                return movie.calculateNoneDiscountedFee().times(audienceCount);
        }

        return movie.calculateNoneDiscountedFee().times(audienceCount);
    }
}
