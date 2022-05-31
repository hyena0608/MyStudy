package clientserver.entity.user;

public class User {

    private String username;
    private String userCondition;
    private String partnerUsername;

    public User(String username) {
        this.username = username;
        this.userCondition = String.valueOf(UserCondition.ROOM);
    }

    public void setUserCondition(String userCondition) {
        userCondition = userCondition;
    }

    public String getUsername() {
        return username;
    }

    public String getUserCondition() {
        return userCondition;
    }

    public String getPartnerUsername() {
        return this.partnerUsername;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPartnerUsername(String partnerUsername) {
        this.partnerUsername = partnerUsername;
    }
}
