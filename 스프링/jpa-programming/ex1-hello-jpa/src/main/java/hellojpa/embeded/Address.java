package hellojpa.embeded;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class Address {

    @Column(name = "city")
    String city;
    String street;
    String state;
    @Embedded Zipcode zipcode;

    protected Address() {}

    public Address(String city, String state, Zipcode zipcode) {
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

}
