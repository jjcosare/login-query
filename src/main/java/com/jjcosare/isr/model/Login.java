package com.jjcosare.isr.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Created by jjcosare on 10/3/18.
 */
@Data
@NoArgsConstructor
@Document
public class Login extends BaseModel {

    public static final String FIELD_LOGIN_TIME = "loginTime";
    public static final String FIELD_USER = "user";
    public static final String FIELD_ATTRIBUTE1 = "attribute1";
    public static final String FIELD_ATTRIBUTE2 = "attribute2";
    public static final String FIELD_ATTRIBUTE3 = "attribute3";
    public static final String FIELD_ATTRIBUTE4 = "attribute4";

    @Field("login_time")
    @NotNull
    private LocalDateTime loginTime;

    @Indexed
    @NotNull
    private String user;

    private String attribute1;

    private String attribute2;

    private String attribute3;

    private String attribute4;

}
