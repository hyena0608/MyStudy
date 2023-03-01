package chap03;

/**
 * 예제 3-12
 * - 요약 정보를 저장하는 도메인 객체
 * - 도메인 객체는 자신의 도메인과 관련된 클래스의 인스턴스
 * - 도메인 객체를 이용하면 결합을 깰 수 있다.
 * - 새로운 요구사항이 생겨서 추가 정보를 내보내야 한다면 기존 코드를 바꿀 필요 없이 샐오운 클래스의 일부로 이를 구현할 수 있다.
 */
public class SummaryStatistics {

    private final double sum;
    private final double max;
    private final double min;
    private final double average;

    public SummaryStatistics(double sum, double max, double min, double average) {
        this.sum = sum;
        this.max = max;
        this.min = min;
        this.average = average;
    }

    public double getSum() {
        return sum;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

    public double getAverage() {
        return average;
    }
}
