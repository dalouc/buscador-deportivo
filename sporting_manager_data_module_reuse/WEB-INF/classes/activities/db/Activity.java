//Class that represents an activity

package activities.db;

public class Activity {
  int id;
  String name;
  String description;
  String initial;
  float cost;
  String pavname;
  int total;
  int occupied;

  public Activity(int id, String name, String description, String initial, float cost, String pavname, int total, int occupied) {
    this.id=id;
	this.name=name;
    this.description=description;
    this.initial=initial;
	this.cost=cost;
	this.pavname=pavname;
	this.total=total;
	this.occupied=occupied;
  }

  public void setid(int id) {
    this.id=id;
  }
  public int getid() {
    return this.id;
  }
  public void setname(String name) {
    this.name=name;
  }
  public String getname() {
    return this.name;
  }
  public void setdescription(String description) {
    this.description=description;
  }
  public String getdescription() {
    return this.description;
  }
  public void setinitial(String initial) {
    this.initial=initial;
  }
  public String getinitial() {
    return this.initial;
  }
  public void setcost(Float cost) {
    this.cost=cost;
  }
  public Float getcost() {
    return this.cost;
  }
  public void setpavname(String pavname) {
    this.pavname=pavname;
  }
  public String getpavname() {
    return this.pavname;
  }
  public void settotal(int total) {
    this.total=total;
  }
  public int gettotal() {
    return this.total;
  }
  public void setoccupied(int occupied) {
    this.occupied=occupied;
  }
  public int getoccupied() {
    return this.occupied;
  }

}
