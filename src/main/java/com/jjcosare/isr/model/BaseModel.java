package com.jjcosare.isr.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

/**
 * Created by jjcosare on 10/4/18.
 */
@Data
@NoArgsConstructor
public abstract class BaseModel {

    @Id
    private String id;

    @Version
    private Long version;

}
