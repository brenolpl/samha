package com.samha.domain.log;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class AuditCompositeKey implements Serializable {
    private static final long serialVersionUID = -8619406593704953837L;
    private Integer id;
    private Integer rev;
}
