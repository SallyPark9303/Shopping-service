package shoppingproject.shop.domain;

public enum Gender {
    Woman("여자"),
    Man("남자");
    private final String description;

    Gender(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }


}
