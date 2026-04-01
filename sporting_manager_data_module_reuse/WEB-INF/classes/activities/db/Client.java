//Class that represents a Client

package activities.db;

public class Client {
  String login;
  String password;
  String name;
  String surname;
  String address;
  String phone;

  public Client(String login, String password, String name, String surname, String address, String phone) {
    this.login=login;
    this.password=password;
    this.name=name;
    this.surname=surname;
	this.address=address;
	this.phone=phone;
  }

  public void setlogin(String login) {
    this.login=login;
  }
  public String getlogin() {
    return this.login;
  }
  public void setpassword(String password) {
    this.password=password;
  }
  public String getpassword() {
    return this.password;
  }
  public void setname(String name) {
    this.name=name;
  }
  public String getname() {
    return this.name;
  }
  public void setsurname(String surname) {
    this.surname=surname;
  }
  public String getsurname() {
    return this.surname;
  }
  public void setaddress(String address) {
    this.address=address;
  }
  public String getaddress() {
    return this.address;
  }
  public void setphone(String phone) {
    this.phone=phone;
  }
  public String getphone() {
    return this.phone;
  }
}
