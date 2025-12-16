package com.blacksystem.system.models.dto;

import com.blacksystem.system.models.User;

public record UserProfileDTO(
        Long id,
        String nameUser,
        String lastNameUser,
        String middleNameUser,
        String emailUser,
        String phoneUser
) {
    public static UserProfileDTO from(User user) {
        return new UserProfileDTO(
                user.getIdUser(),
                user.getNameUser(),
                user.getLastNameUser(),
                user.getMiddleNameUser(),
                user.getEmailUser(),
                user.getPhoneUser()
        );
    }
}
