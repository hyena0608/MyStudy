package jpashop.domain;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@Data
public abstract class BaseEntity {

    private Date createdDate;
    private Date lastModifiedDate;
}
