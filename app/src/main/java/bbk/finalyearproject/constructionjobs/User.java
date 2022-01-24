package bbk.finalyearproject.constructionjobs;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
    private int uid;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String profession;
    private String contact;
    private String location;

    public User(int uid, String email, String password, String name, String surname, String profession, String contact, String location) {
        this.uid = uid;
        this.username = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.profession = profession;
        this.contact = contact;
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public User(Parcel in){
        this.uid = in.readInt();
        this.username = in.readString();
        this.password = in.readString();
        this.name = in.readString();
        this.surname = in.readString();
        this.profession = in.readString();
        this.contact = in.readString();
        this.location = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.uid);
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeString(this.name);
        dest.writeString(this.surname);
        dest.writeString(this.profession);
        dest.writeString(this.contact);
        dest.writeString(this.location);
    }

    @Override
    public String toString(){
        return "{Username: "+this.getUsername()+", Password: "+this.getPassword()+", First Name: "+this.getName()+", Last Name: "+this.getSurname()+", Profession: "+this.getProfession()+", Contact: "+this.getContact()+", Location: "+this.getLocation()+"}";
    }
}
