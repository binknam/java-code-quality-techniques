package vn.kms.launch.cleancode.module;

import vn.kms.launch.cleancode.annotations.*;

public class Contact {

  @Column(name="id")
  @NotNull
  private int id;

  @Column(name="first_name")
  @NotEmpty
  @MaxLength(value = 10)
  private String firstName;

  @Column(name="last_name")
  @NotEmpty
  @MaxLength(value = 10)
  private String lastName;

  @Column(name="address")
  @MaxLength(value = 20)
  private String address;

  @Column(name="city")
  @MaxLength(value = 15)
  private String city;

  @Column(name="state")
  @ValidState
  private String state;

  @Column(name="zip")
  @ValidSpecialCharacter(value = "^\\d{4,5}$")
  private String zipCode;

  @Column(name="phone1")
  @ValidSpecialCharacter(value = "^\\d{3}\\-\\d{3}\\-\\d{4}$")
  private String mobilePhone;

  @Column(name="email")
  @ValidSpecialCharacter(value = "^.+@.+\\..+$")
  private String email;

  @Column(name="date_of_birth")
  @NotNull
  @EqualLength(value = 10)
  private String dateOfBirth;

  private int age;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public String getMobilePhone() {
    return mobilePhone;
  }

  public void setMobilePhone(String mobilePhone) {
    this.mobilePhone = mobilePhone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getDayOfBirth() {
    return dateOfBirth;
  }

  public void setDayOfBirth(String dayOfBirth) {
    this.dateOfBirth = dayOfBirth;
  }
}
