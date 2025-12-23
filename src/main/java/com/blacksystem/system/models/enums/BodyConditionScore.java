package com.blacksystem.system.models.enums;

public enum BodyConditionScore {
    MUY_DELGADO(1),
    DELGADO(2),
    IDEAL(3),
    SOBREPESO(4),
    OBESO(5);

    private final int value;

    BodyConditionScore(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    // 🔥 ESTE MÉTODO ES CLAVE
    public static BodyConditionScore fromValue(int value) {
        for (BodyConditionScore bcs : values()) {
            if (bcs.value == value) {
                return bcs;
            }
        }
        throw new IllegalArgumentException("BCS inválido: " + value);
    }
}
