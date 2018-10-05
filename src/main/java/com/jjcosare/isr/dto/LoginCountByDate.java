package com.jjcosare.isr.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Created by jjcosare on 10/4/18.
 */
@Data
@NoArgsConstructor
public class LoginCountByDate {

    public static final String FIELD_LOGIN_DATE = "loginDate";
    public static final String FIELD_LOGIN_COUNT = "loginCount";

    private LocalDate loginDate;

    private long loginCount;

}
