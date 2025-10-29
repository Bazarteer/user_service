package org.bazarteer.userservice.model;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document("users")
public class User {
    
    @Id
    private String Id;

    @Field(name = "name")
    private String name;
    @Field(name = "surname")
    private String surname;
    @Field(name = "username")
    private String username;
    @Field(name = "password")
    private String password;
    @Field(name = "auth_type")
    private AuthType auth_type;
    @Field(name = "bio")
    private String bio;
    @Field(name = "image")
    private String image;
    @Field(name = "num_sales")
    private int num_sales;
    @Field(name = "following")
    private List<FollowedUser> following = new ArrayList<FollowedUser>();

    public User() {}

    public User(String name, String surname, String username, String password, AuthType auth_type, String bio, String image, int num_sales, List<FollowedUser> following){
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.auth_type = auth_type;
        this.bio = bio;
        this.image = image;
        this.num_sales = num_sales;
        this.following = following;
    }

    @Override
    public String toString() {
        return String.format(
            "User[id=%s, name='%s', surname='%s', username='%s', auth_type='%s', bio='%s', image='%s', num_sales='%s', following='%s']",
            Id, name, surname, username, auth_type, bio, image, num_sales, following);
    }
}

@Getter @Setter 
class FollowedUser {
    @Field("id")
    private String Id;
    @Field("username")
    private String username;
    @Field("image") 
    private String image;

    public FollowedUser() {}

    public FollowedUser(String Id, String username, String image){
        this.Id = Id;
        this.username = username;
        this.image = image;
    }


    @Override
    public String toString() {
        return String.format(
            "User[id=%s, username='%s', image='%s']",
            Id, username, image);
    }
}

