package entities;

public class Exercise {

    // Declare that this attribute is the id

    private Long id;

    private String name;
    private String type;

    public Exercise() { }

    public Exercise(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String gettype() {
        return type;
    }

    public void settype(String type) {
        this.type = type;
    }

    // This is for easier debug.
    @Override
    public String toString() {
        return String.format(
                "Exercise[name=%s, type=%s]",
                name,type);
    }
}
