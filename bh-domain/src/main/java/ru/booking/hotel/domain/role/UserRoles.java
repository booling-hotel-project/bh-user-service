package ru.booking.hotel.domain.role;

public enum UserRoles {
    USER("Обычный пользователь"),
    MANAGER("Менеджер"),
    HOUSEKEEPING("Персонал уборки (горничные)"),
    MAINTENANCE("Персонал обслуживания (ремонт)");

    private final String description;

    UserRoles(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name();
    }
}
