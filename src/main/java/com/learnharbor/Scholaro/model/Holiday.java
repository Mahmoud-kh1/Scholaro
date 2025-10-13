package com.learnharbor.Scholaro.model;


import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@Entity
@Table(name = "holidays")
public class Holiday  extends BaseEntity{
    private  String reason;
    @Id
    private  String day;


    @Enumerated(EnumType.STRING)
    private  Type type;



    public enum Type {
        FESTIVAL, FEDERAL
    }

}
