public class Patient extends User{
    private String treatment_notes;

    public Patient(String id, String username, String password, String name, String email, String treatment_notes){
        super(id,username,password,name,email);
        this.treatment_notes = treatment_notes;
    }


    public String getTreatment_notes() {
        return treatment_notes;
    }

    public void setTreatment_notes(String treatment_notes) {
        this.treatment_notes = treatment_notes;
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

    public String getUsername(){return username;}

    @Override
    public String toString() {
        String fullNotes = id + "," + username + "," + password + "," + name + "," + email + "," + treatment_notes;
        return fullNotes;
    }
}

