package calendar;

public class Event {
    private int id;
    private String title;
    private String date;
    private String description;
    
    private String name;

    public Event(int id, String title, String date, String description, String name) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.description = description;
        this.name = name;
    }

    public Event() {
		// TODO Auto-generated constructor stub
	}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

   

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
