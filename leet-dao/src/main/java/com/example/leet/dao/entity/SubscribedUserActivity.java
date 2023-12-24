package com.example.leet.dao.entity;

import com.example.leet.dao.Activity;
import jakarta.persistence.*;
import org.hibernate.annotations.JavaType;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "subscribed_user_activity")
public class SubscribedUserActivity {

    private Integer activityId;
    private Activity activity;
    private Long lastActivity;
    private SubscribedUser subscribedUser;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    @Column(name = "activity")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Column(name = "last_activity")
    public Long getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(Long lastActivity) {
        this.lastActivity = lastActivity;
    }

    @ManyToOne
    @JoinColumn(name = "subs_user")
    public SubscribedUser getSubscribedUser() {
        return subscribedUser;
    }

    public void setSubscribedUser(SubscribedUser subscribedUser) {
        this.subscribedUser = subscribedUser;
    }
}
