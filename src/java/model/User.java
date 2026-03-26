package model;

public class User {
    private int userId;
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private String cccd;
    private String address;
    private String avatar;
    private String gender;
    private double walletHours;

    public User() {
    }

    public User(int userId, String fullName, String email, String password, String phone, String cccd, String address, String avatar, String gender, double walletHours) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.cccd = cccd;
        this.address = address;
        this.avatar = avatar;
        this.gender = gender;
        this.walletHours = walletHours;
    }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getCccd() { return cccd; }
    public void setCccd(String cccd) { this.cccd = cccd; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public double getWalletHours() { return walletHours; }
    public void setWalletHours(double walletHours) { this.walletHours = walletHours; }
}
