import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Login {
    Scanner loggingIn = new Scanner(System.in);
    ArrayList<Patient> patientBase = new ArrayList<>();
    ArrayList<Medical_Staff> staffBase = new ArrayList<>();

    public Login() throws LoginFailException, AccessDeniedException {
        try{
            BufferedReader patFile = new BufferedReader(new FileReader("data/patient.csv"));
            BufferedReader staffFile = new BufferedReader(new FileReader("data/medicalstaff.csv"));

            String line;
            while((line = patFile.readLine()) != null){
                String[] currLine = line.split(",");
                Patient p1 = new Patient(currLine[0], currLine[1], currLine[2], currLine[3],currLine[4],currLine[5]);
                patientBase.add(p1);

            }
            patFile.close();
            while ((line = staffFile.readLine()) != null){
                String[] currStaff = line.split(",");
                Medical_Staff m1 = new Medical_Staff(currStaff[0], currStaff[1], currStaff[2], currStaff[3],currStaff[4],currStaff[5]);
                staffBase.add(m1);
            }
            staffFile.close();
        }

        catch(FileNotFoundException e){
            System.out.println("File not found");
        }
        catch (IOException e2){
            System.out.println("Read error");
        }
    }

    public User loginAttempt() throws LoginFailException, AccessDeniedException{
        System.out.println("Enter your username, then press enter: ");
        String username = loggingIn.nextLine();


        for (Patient p : patientBase){

            if (p.username.equals(username)){
                System.out.println("Enter your password, then press enter: ");
                String password = loggingIn.nextLine();
                if (p.password.equals(password)){
                    System.out.println("Login successful");

                    return p;


                }
                else{
                    System.out.println("Invalid password");
                    throw new LoginFailException();
                }

            }
        }

        for (Medical_Staff s : staffBase){

            if (s.username.equals(username)){
                System.out.println("Enter your password, then press enter: ");
                String password = loggingIn.nextLine();
                if (s.password.equals(password)){
                    System.out.println("Login successful");
                    return s;


                }
                else{
                    System.out.println("Invalid password");
                    throw new LoginFailException();
                }

            }
        }

        System.out.println("Invalid username");
        throw new LoginFailException();

    }

    public Manager startSession() throws AccessDeniedException {

        while (true) {
            try {

                User loggedInUser = loginAttempt();
                Manager manager = new Manager(loggedInUser, patientBase);
                return manager;

            } catch (LoginFailException e) {
                System.out.println(" ");
            }
        }
    }


}
