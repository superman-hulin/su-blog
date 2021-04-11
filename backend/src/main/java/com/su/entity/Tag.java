package com.su.entity;

import com.su.utils.Entity;
import lombok.Data;

@Data
public class Tag extends Entity {
    private Integer id;
    private String tagName;
}
