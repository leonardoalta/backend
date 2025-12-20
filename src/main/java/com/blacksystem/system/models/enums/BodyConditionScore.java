package com.blacksystem.system.models.enums;

public enum BodyConditionScore {

    VERY_THIN(1),
    THIN(2),
    IDEAL(3),
    OVERWEIGHT(4),
    OBESE(5);

    private final int value;

    BodyConditionScore(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    // 🔥 Conversión segura desde int (Flutter)
    public static BodyConditionScore fromValue(int value) {
        for (BodyConditionScore bcs : values()) {
            if (bcs.value == value) {
                return bcs;
            }
        }
        throw new IllegalArgumentException("BCS inválido: " + value);
    }
}
