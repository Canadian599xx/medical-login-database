import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Manager {
    Patient currPat;
    User currUser;
    boolean run = true;
    boolean run2 = true;
    Scanner scanID = new Scanner(System.in);
    String welcome = """
            
            dP   dP   dP          dP                                      \s
            88   88   88          88                                      \s
            88  .8P  .8P .d8888b. 88 .d8888b. .d8888b. 88d8b.d8b. .d8888b.\s
            88  d8'  d8' 88ooood8 88 88'  `"" 88'  `88 88'`88'`88 88ooood8\s
            88.d8P8.d8P  88.  ... 88 88.  ... 88.  .88 88  88  88 88.  ...\s
            8888' Y88'   `88888P' dP `88888P' `88888P' dP  dP  dP `88888P'\s
            
            """;

    ArrayList<Patient> patList;
    
    public Manager(User currUser, ArrayList<Patient> patList) throws AccessDeniedException{
        this.currUser = currUser;
        this.patList = patList;
        sortPatientsById();
        if (currUser instanceof Patient){
            currPat = (Patient) currUser;
        }
        System.out.println(welcome);

        if (currUser instanceof Patient){

            while(run) {
                System.out.println("\nYou are in Patient mode\n");
                System.out.println("What would you like to do?");
                String options = """
                        1. View current profile
                        2. Select a user and view their info (STAFF ONLY)
                        3. Edit information
                        4. Obtain a report""";
                System.out.println(options);
                int choice1 = scanID.nextInt();

                switch (choice1) {
                case 1:
                    viewInfo(currPat);
                    break;
                case 2:
                    try {
                        lookup(currPat, patList);
                    } catch (AccessDeniedException e) {
                        System.out.println("Access denied");
                    }
                    break;
                case 3:
                    try {
                        editData(currPat, patList);
                    } catch (AccessDeniedException e) {
                        System.out.println("Access denied");
                    }
                    break;
                case 4:
                    reporting(patList);
                    break;
                default:
                    System.out.println("Goodbye");
                    run = false;
                    break;

                }
            }



        }

        else{
            while(run) {
                System.out.println("\nYou are in Staff mode\n");
                System.out.println("What would you like to do?");
                String options = """
                        1. View current profile
                        2. Select a user and view their info (STAFF ONLY)
                        3. Edit information
                        4. Obtain a report""";
                System.out.println(options);
                int choice1 = scanID.nextInt();

                switch (choice1) {
                    case 1:
                        viewInfo(currUser);
                        break;
                    case 2:
                        lookup(currUser, patList);
                        break;
                case 3:
                    if (currPat == null){
                        System.out.println("No patient selected");
                    }
                    else {
                        try {
                            editData(currUser, patList);
                        } catch (AccessDeniedException e) {
                            System.out.println("Access denied");
                        }
                    }
                    break;
                case 4:
                    reporting(patList);
                    break;
                default:
                    System.out.println("Goodbye");
                    run = false;
                    break;

                }
            }
        }
    }

    private void sortPatientsById(){
        int n = patList.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (patList.get(j).id.compareTo(patList.get(j + 1).id) > 0) {
                    Patient temp = patList.get(j);
                    patList.set(j, patList.get(j + 1));
                    patList.set(j + 1, temp);
                }
            }
        }
    }

    public void viewInfo(User user){
        System.out.println("ID: " + user.id);
        System.out.println("Username: " + user.username);
        System.out.println("Password: " + user.password);
        System.out.println("Name: " + user.name);
        System.out.println("Email: " + user.email);

        if (user instanceof Patient){
            System.out.println("Notes: " + ((Patient) user).getTreatment_notes());
        }
        else{
            System.out.println("Department: " + ((Medical_Staff) user).getDepartment());
        }

    }

    public void lookup(User user, ArrayList<Patient> patList) throws AccessDeniedException{
        if (user instanceof Patient){
            throw new AccessDeniedException();
        }
        scanID.nextLine();
        System.out.println("Enter patient ID: ");

        String check = scanID.nextLine();
        int index = binarySearch(check, patList);
        if (index != -1){
            currPat = patList.get(index);
            viewInfo(currPat);
        }
        else{
            System.out.println("Patient not found");
        }
    }

    private int binarySearch(String id, ArrayList<Patient> list){
        int left = 0;
        int right = list.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int comparison = list.get(mid).id.compareTo(id);
            if (comparison == 0) {
                return mid;
            }
            if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    public void editData(User user, ArrayList<Patient> patList) throws AccessDeniedException{
        if (user instanceof Patient){
            if (!((Patient) user).id.equals(currPat.id)){
                throw new AccessDeniedException();
            }
        }
        if (currPat == null){
            System.out.println("No patient selected");
            return;
        }
        run2 = true;
        while(run2) {
            String things = """
                    What would you like to change?
                    1. Password
                    2. Name
                    3. Email
                    4. Notes
                    5. Save Changes
                    Type the number that matches the option, and press enter
                    type any other number to return to the login page
                    (YOU MUST SAVE IN ORDER FOR CHANGES TO PERMANENTLY TAKE EFFECT)""";

            System.out.println(things);
            int editOption = scanID.nextInt();
            scanID.nextLine();

            switch (editOption) {
                case 1:
                    System.out.println("New password:");
                    currPat.setPassword(scanID.nextLine());
                    break;
                case 2:
                    System.out.println("New name:");
                    currPat.setName(scanID.nextLine());
                    break;
                case 3:
                    System.out.println("New email:");
                    currPat.setEmail(scanID.nextLine());
                    break;
                case 4:
                    System.out.println("New notes:");
                    currPat.setTreatment_notes(scanID.nextLine());
                    break;
                case 5:
                    try{
                    BufferedWriter overWrite = new BufferedWriter(new FileWriter("data/patient.csv"));
                    for (int i = 0; i < patList.size(); i++){
                        overWrite.write(String.valueOf(patList.get(i)));
                        overWrite.newLine();
                        }
                    overWrite.close();
                    System.out.println("Saved");
                    }
                    catch (IOException e){
                        System.out.println("Save failed");
                    }
                    break;

                default:
                    run2 = false;
                    break;

            }
        }
    }

    public void reporting(ArrayList<Patient> patList){
        scanID.nextLine();
        System.out.println("Filename (no extension):");
        String fileName = scanID.nextLine();
        try {
            BufferedWriter reportFile = new BufferedWriter(new FileWriter("data/" + fileName + ".txt"));
            System.out.println("Choose which report you would like");
            String reportOpt = """
                    1. Patient List (ID)
                    2. Patient List (Name)
                    3. Emails List
                    4. Account type plus info""";
            System.out.println(reportOpt);
            int reportNum = scanID.nextInt();

            switch (reportNum){
                case 1:
                    ArrayList<Patient> sortedById = new ArrayList<>(patList);
                    int n1 = sortedById.size();
                    for (int i = 0; i < n1 - 1; i++) {
                        for (int j = 0; j < n1 - i - 1; j++) {
                            if (sortedById.get(j).id.compareTo(sortedById.get(j + 1).id) > 0) {
                                Patient temp = sortedById.get(j);
                                sortedById.set(j, sortedById.get(j + 1));
                                sortedById.set(j + 1, temp);
                            }
                        }
                    }
                    for (Patient p : sortedById) {
                        reportFile.write(p.id + "," + p.name + "," + p.email + "\n");
                    }
                    reportFile.close();
                    break;

                case 2:
                    ArrayList<Patient> sortedByName = new ArrayList<>(patList);
                    int n2 = sortedByName.size();
                    for (int i = 0; i < n2 - 1; i++) {
                        for (int j = 0; j < n2 - i - 1; j++) {
                            if (sortedByName.get(j).name.compareToIgnoreCase(sortedByName.get(j + 1).name) > 0) {
                                Patient temp = sortedByName.get(j);
                                sortedByName.set(j, sortedByName.get(j + 1));
                                sortedByName.set(j + 1, temp);
                            }
                        }
                    }
                    for (Patient p : sortedByName) {
                        reportFile.write(p.id + "," + p.name + "," + p.email + "\n");
                    }
                    reportFile.close();
                    break;

                case 3:
                    ArrayList<String> emails = new ArrayList<>();
                    for (Patient p : patList) {
                        emails.add(p.email);
                    }
                    int n3 = emails.size();
                    for (int i = 0; i < n3 - 1; i++) {
                        for (int j = 0; j < n3 - i - 1; j++) {
                            if (emails.get(j).compareToIgnoreCase(emails.get(j + 1)) > 0) {
                                String temp = emails.get(j);
                                emails.set(j, emails.get(j + 1));
                                emails.set(j + 1, temp);
                            }
                        }
                    }
                    for (String email : emails) {
                        reportFile.write(email + "\n");
                    }
                    reportFile.close();
                    break;
                    
                case 4:
                    if (currUser instanceof Patient) {
                        Patient p = (Patient) currUser;
                        reportFile.write(p.id + "\n");
                        reportFile.write(p.username + "\n");
                        reportFile.write(p.password + "\n");
                        reportFile.write(p.name + "\n");
                        reportFile.write(p.email + "\n");
                        reportFile.write(p.getTreatment_notes() + "\n");
                    } else {
                        Medical_Staff s = (Medical_Staff) currUser;
                        reportFile.write(s.id + "\n");
                        reportFile.write(s.username + "\n");
                        reportFile.write(s.password + "\n");
                        reportFile.write(s.name + "\n");
                        reportFile.write(s.email + "\n");
                        reportFile.write(s.getDepartment() + "\n");
                    }
                    reportFile.close();
                    break;
                default:
                    reportFile.close();
                    break;

            }

        }
        catch (IOException e){
            System.out.println("File error");
        }

    }
}
