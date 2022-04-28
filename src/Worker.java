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

    public void printWorkerData() {
        String workerRank = switch (rank) {
            case OPTION_1 -> "Labour";
            case OPTION_2 -> "Manager";
            case OPTION_3 -> "Director";
        };

        System.out.println("Worker account name: " + this.getUserName() + ", name: " +
                this + ", total spended: " + this.getSpended() +
                "$ , total purchases: " + this.getPurchaseCounter() + ", worker rank: " + workerRank);

    }

}
