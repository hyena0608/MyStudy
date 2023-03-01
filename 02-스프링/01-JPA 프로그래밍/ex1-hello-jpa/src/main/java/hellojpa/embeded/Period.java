package hellojpa.embeded;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Embeddable
public class Period {

    @Temporal(TemporalType.DATE) Date startDate;
    @Temporal(TemporalType.DATE) Date endDate;

    public boolean isWork(Date date) {
        return true;
    }
}
