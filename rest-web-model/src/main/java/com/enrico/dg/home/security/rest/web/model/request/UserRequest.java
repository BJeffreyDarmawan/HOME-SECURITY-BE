package com.enrico.dg.home.security.rest.web.model.request;

import com.enrico.dg.home.security.entity.CommonModel;

public class UserRequest extends CommonModel {

    private String name;
    private String email;
    private String password;
    private String role;
    private String macAddress;
    private String sosNumber;
    private String emergencyNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getSosNumber() {
        return sosNumber;
    }

    public void setSosNumber(String sosNumber) {
        this.sosNumber = sosNumber;
    }

    public String getEmergencyNumber() {
        return emergencyNumber;
    }

    public void setEmergencyNumber(String emergencyNumber) {
        this.emergencyNumber = emergencyNumber;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", macAddress='" + macAddress + '\'' +
                ", sosNumber='" + sosNumber + '\'' +
                ", emergencyNumber='" + emergencyNumber + '\'' +
                '}';
    }
}
