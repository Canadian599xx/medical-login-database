public class Driver {
    public static void main(String[] args){
        boolean loginloop = true;

        while(loginloop) {
            try {
                Login login = new Login();
                login.startSession();
                loginloop = false;
            }
            catch (LoginFailException e) {
                System.out.println(" ");
            }
            catch (AccessDeniedException e){
                System.out.println("Access denied\n");
            }
        }

    }
}
