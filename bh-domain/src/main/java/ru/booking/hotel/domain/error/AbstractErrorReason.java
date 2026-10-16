package ru.booking.hotel.domain.error;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractErrorReason implements ErrorReason {

    private String template;
    private Map<String, Object> parameters;

    @Override
    public String getCode() {
        return getClass().getSimpleName();
    }

    @Override
    public Map<String, Object> getParameters() {
        if (parameters == null) {
            parameters = new ConcurrentHashMap<>();
            for (var field : getClass().getFields()) {
                try {
                    parameters.put(field.getName(), field.get(this));
                } catch (IllegalAccessException e) {
                    throw new IllegalStateException(
                            "Не удалось прочитать поле '" + field.getName() + "'", e
                    );
                }
            }
        }
        return parameters;
    }

    public String getMessage() {
        if (template == null) {
            return null;
        }

        String result = template;
        for (var entry : getParameters().entrySet()) {
            result = result.replace(
                    "${" + entry.getKey() + "}",
                    String.valueOf(entry.getValue())
            );
        }
        return result;
    }

    protected void template(String template) {
        this.template = template;
    }

}
