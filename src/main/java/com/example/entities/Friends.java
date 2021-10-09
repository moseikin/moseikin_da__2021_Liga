package com.example.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "friends")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class Friends implements Serializable {

    @EmbeddedId
    private FriendsPk friendsPk;

    @Column(name = "are_friends")
    private Boolean areFriends;

    public Friends(FriendsPk friendsPk, Boolean areFriends) {
        this.friendsPk = friendsPk;
        this.areFriends = areFriends;
    }

    @Embeddable
    @NoArgsConstructor
    @Setter
    @Getter
    public static class FriendsPk implements Serializable{

        @Column(name = "owner_id")
        protected Long ownerId;

        @Column(name = "usr_id")
        protected Long usrId;

        public FriendsPk(Long ownerId, Long usrId) {
            this.ownerId = ownerId;
            this.usrId = usrId;
        }
    }

    @ManyToOne
    @JoinColumn(name = "owner_id", updatable = false, insertable = false)
    private Usr usr;

    @Override
    public String toString() {
        return "\n Friends: " +
                "Init by: " + friendsPk.ownerId +
                ", to: " + friendsPk.usrId +
                ", accepted: " + areFriends;
    }
}
