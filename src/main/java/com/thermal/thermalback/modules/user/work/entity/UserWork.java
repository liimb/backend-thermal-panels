package com.thermal.thermalback.modules.user.work.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
@Entity(name = "user_works")
@EqualsAndHashCode(of = "id")
public class UserWork {

    @Id
    @Column(name = "id")
    @JsonProperty("id")
    private UUID id;

    @Column(name = "work_id")
    @JsonProperty("workId")
    private UUID workId;

    @Column(name = "order_id")
    @JsonProperty("orderId")
    private UUID orderId;

    @Column(name = "count")
    @JsonProperty("count")
    private Float count;

    @Column(name = "comment")
    @JsonProperty("comment")
    private String comment;
}
