package com.jjcosare.isr.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by jjcosare on 10/5/18.
 */
@Data
@NoArgsConstructor
public class LoginCountByUser {

    public static final String FIELD_LOGIN_USER = "loginUser";
    public static final String FIELD_LOGIN_COUNT = "loginCount";

    private String loginUser;

    private long loginCount;

}
