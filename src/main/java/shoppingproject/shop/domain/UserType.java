package shoppingproject.shop.domain;


public enum UserType {
    USER("일반 회원"),
    ADMIN("관리자");

    private final String description;
    UserType(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

}
