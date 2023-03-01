package chap05.step04.소스_코드_의존과_런타임_의존;

public class FlowController {

    ByteSource reader;

    public FlowController() {
        this.reader = new FileDataReader();
    }

    public void process() {
        reader.read();
    }
}
