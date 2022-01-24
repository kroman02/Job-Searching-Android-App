package bbk.finalyearproject.constructionjobs;

public class Listing {

    private int lid;
    private String title;
    private String body;
    private String contact;
    private String location;
    private String author;
    private String date;

    public Listing(int lid, String title, String body, String contact, String location, String author, String date) {
        this.lid = lid;
        this.title = title;
        this.body = body;
        this.contact = contact;
        this.location = location;
        this.author = author;
        this.date = date;
    }

    public int getLid() {
        return lid;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString(){
        return "{title: "+this.getTitle() +", body: "
                +this.getBody()+", contact: "
                +this.getContact()+", location: "
                +this.getLocation()+", author: "
                +this.getAuthor()+", date: "
                +this.getDate();
    }
}

