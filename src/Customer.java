public class Customer extends User {
    private boolean clubMember;


    public Customer(String firstName, String lastName, String userName, String password) {
        super(firstName, lastName, userName, password);
    }

    public boolean isClubMember() {
        return clubMember;
    }

    public void setClubMember(boolean clubMember) {
        this.clubMember = clubMember;
    }

}
