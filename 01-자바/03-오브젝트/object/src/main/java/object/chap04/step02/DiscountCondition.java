package object.chap04.step02;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * 할인 조건
 * - 순번 조건 sequence
 * - 기간 조건 dayOfWeek, startTime, endTime
 */
public class DiscountCondition {

    private DiscountConditionType type;

    private int sequence;

    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    /**
     * @param dayOfWeek
     * @param time
     *
     * 자신의 정보를 파라미터로 받는다. -> 내부의 변경이 외부로 퍼져나간다. -> 캡슐화 위반
     */
    public boolean isDiscountable(DayOfWeek dayOfWeek, LocalTime time) {
        if (type != DiscountConditionType.PERIOD) {
            throw new IllegalArgumentException();
        }

        return this.dayOfWeek.equals(dayOfWeek) &&
                this.startTime.compareTo(time) <= 0 &&
                this.endTime.compareTo(time) >= 0;
    }

    public boolean isDiscountable(int sequence) {
        if (type != DiscountConditionType.PERIOD) {
            throw new IllegalArgumentException();
        }

        return this.sequence == sequence;
    }

    public DiscountConditionType getType() {
        return type;
    }

}
