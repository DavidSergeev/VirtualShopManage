public class Worker extends User {
    private BaseOptions rank;
    public Worker(String firstName, String lastName, String userName, String password) {
        super(firstName, lastName, userName, password);
    }

    public BaseOptions getRank() {
        return rank;
    }

    public void setRank(BaseOptions rank) {
        this.rank = rank;
    }

}
