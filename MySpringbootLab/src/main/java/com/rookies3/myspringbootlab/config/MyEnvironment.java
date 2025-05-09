package com.rookies3.myspringbootlab.config;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class MyEnvironment {
    private String mode;

}