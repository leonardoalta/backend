package com.blacksystem.system.models.dto;

import com.blacksystem.system.models.User;
public class UserProfileDTO {

    public Long idUser;
    public String nameUser;
    public String lastNameUser;
    public String middleNameUser;
    public String emailUser;
    public String phoneUser;

    public static UserProfileDTO from(User user) {
        UserProfileDTO dto = new UserProfileDTO();
        dto.idUser = user.getIdUser();
        dto.nameUser = user.getNameUser();
        dto.lastNameUser = user.getLastNameUser();
        dto.middleNameUser = user.getMiddleNameUser();
        dto.emailUser = user.getEmailUser();
        dto.phoneUser = user.getPhoneUser();
        return dto;
    }
}
