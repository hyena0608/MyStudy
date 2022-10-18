package chap05.step03.원칙을_지키지_않는_문제;

public class Rectangle {

    private int width;
    private int height;

    /**
     * LSP 위반
     */
    public void increaseHeight(Rectangle rec) {
        if (rec.getHeight() <= rec.getWidth()) {
            rec.setHeight(rec.getWidth() + 10);
        }
    }

    /**
     * 실제 타입이 Square일 경우 기능 사용 X
     * 하지만 instanceof 연산자 사용 -> LSP 위반
     * increaseHeight 메서드는 Rectange의 확장에 열려있지 않다.
     */
    public void increaseHeight_2(Rectangle rec) {
        if (rec instanceof Square) {
            throw new CantSupportSquareException();
        }
        if (rec.getHeight() <= rec.getWidth()) {
            rec.setHeight(rec.getWidth() + 10);
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
