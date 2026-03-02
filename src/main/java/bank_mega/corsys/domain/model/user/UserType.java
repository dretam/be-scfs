package bank_mega.corsys.domain.model.user;

public enum UserType {
    INTERNAL,
    EXTERNAL;

    public static UserType fromString(String value) {
        try {
            return UserType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            return INTERNAL; // Default value
        }
    }

    public String toDatabaseValue() {
        return this.name();
    }
}
