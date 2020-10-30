package com.ilkin.simpletodo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "TODOITEMS")
@ToString
@EqualsAndHashCode
@Getter
@Setter
@RequiredArgsConstructor
public class TodoItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "itemId")
//    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private long itemId;

    @Column(name = "taskName", length = 50, nullable = false)
    @Length(max = 50, message = "{task.name.length}")
    @NotEmpty(message = "{task.name.notempty}")
    private String taskName;

    @CreationTimestamp
    @Column(name = "CREATED_TIME", updatable = false)
    @JsonIgnore
    private LocalDateTime createdTime;

    @UpdateTimestamp
    @Column(name = "UPDATE_TIME")
    @JsonIgnore
    private LocalDateTime updateTime;


    @Column(name = "isDone")
    private boolean isDone = false;
}
