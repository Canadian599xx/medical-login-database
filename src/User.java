public abstract class User {
     protected final String id;
     protected final String username;
     protected String password;
     protected String name;
     protected String email;

    public User(String id, String username, String password, String name, String email){
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }


    public abstract String toString();

    public abstract String getPassword();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getID();

    public abstract String getUsername();

    public abstract void setPassword(String password);

    public abstract void setName(String name);

    public abstract void setEmail(String email);


}
