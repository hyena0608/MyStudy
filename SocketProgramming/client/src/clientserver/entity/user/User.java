package clientserver.entity.user;

public class User {

    private static String username;
    private static String userCondition;
    private static String partnerUsername;

    public User(String username) {
        this.username = username;
        this.userCondition = String.valueOf(UserCondition.ROOM);
    }

    public static void setUserCondition(String userCondition) {
        userCondition = userCondition;
    }

    public static String getUsername() {
        return username;
    }

    public static String getUserCondition() {
        return userCondition;
    }

    public static void setUsername(String username) {
        User.username = username;
    }

    public static String getPartnerUsername() {
        return partnerUsername;
    }

    public static void setPartnerUsername(String partnerUsername) {
        User.partnerUsername = partnerUsername;
    }
}
