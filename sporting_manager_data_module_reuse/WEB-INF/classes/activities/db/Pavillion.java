//Class that represents a Pavillion

package activities.db;


public class Pavillion {
  String name;
  String location;

  public Pavillion(String name, String location) {
    this.name=name;
    this.location=location;
  }

  public void setname(String name) {
    this.name=name;
  }
  public String getname() {
    return this.name;
  }
  public void setlocation(String location) {
    this.location=location;
  }
  public String getlocation() {
    return this.location;
  }
}
