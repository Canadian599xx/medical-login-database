public class Medical_Staff extends User{
    private String department;

    public Medical_Staff(String id, String username, String password, String name, String email, String department){
        super(id,username,password,name,email);
        this.department = department;
    }


    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getID(){return id;}

    public String getUsername(){return username; }

    @Override
    public String toString() {
        String fullNotes = id + "," + username + "," + password + "," + name + "," + email + "," + department;
        return fullNotes;
    }
}
