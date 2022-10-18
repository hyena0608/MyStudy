package chap05.step03;

public class Rectangle {

    private int width;
    private int height;

    public void increaseHeight(Rectangle rec) {
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
