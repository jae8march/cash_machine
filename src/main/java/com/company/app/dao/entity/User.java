package com.company.app.dao.entity;

import com.company.app.dao.entity.enumeration.UserRole;

import java.io.Serializable;
import java.util.Objects;

/**
 * User entity with getter and setter.
 */
public class User implements Serializable {
    static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private String surname;
    private String login;
    private String password;
    private UserRole userRole;

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(String name, String surname, String login, String password, UserRole userRole) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.userRole = userRole;
    }

    /** Getters */
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    /** Setters */
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                userRole == user.userRole;
    }

    @Override
    public int hashCode() {
        int result = 31;
        result = (int) (31 * result + id + ( (name != null) ? name.hashCode() : 0));
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", form='" + password + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}
