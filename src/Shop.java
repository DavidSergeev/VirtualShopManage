import java.util.*;

public class Shop {
    private final List<Customer> customers;
    private final List<Worker> workers;
    private final List<Product> products;

    public Shop() {

        customers = new ArrayList<>();
        workers = new LinkedList<>();
        products = new LinkedList<>();
        run();
    }

    private void run() {
       try {

           BaseOptions choice = null;
           Scanner intScanner = new Scanner(System.in);
           do {
               System.out.println("Choose the option: ");
               System.out.println("1 - Sign up.");
               System.out.println("2 - Sign in.");
               System.out.println("3 - Exit.");
               try {
                   choice = BaseOptions.valueOf(intScanner.nextInt());

                   if (choice != null) {
                       switch (choice) {
                           case OPTION_1 -> createUser();
                           case OPTION_2 -> signIn();
                           case OPTION_3 -> System.out.println("Exit");
                       }
                   }
                   else {
                       System.out.println("Unknown option (Allowed only 1, 2, 3)\n");
                   }

               } catch (InputMismatchException e) {
                   System.out.println("Enter only digits\n");
                   intScanner.nextLine();
               }
           } while (choice != BaseOptions.OPTION_3);

       } catch (Exception e) {
           System.out.println(Arrays.toString(e.getStackTrace()));
       }
    }

    private void createUser() {
        String userName, password, firstName, lastName;
        Scanner lnScanner = new Scanner(System.in);
        Scanner intScanner = new Scanner(System.in);

        System.out.println("Are you a worker? y/n");
        String userAnswer = lnScanner.nextLine();

        boolean isWorker = choseYesOption(userAnswer);
        userName = getCorrectUsername();
        password = getCorrectPassword();
        firstName = getCorrectName(true);
        lastName = getCorrectName(false);


        if (isWorker) {
            BaseOptions userRank = null;
            Worker workerUser = new Worker(firstName, lastName, userName, password);
            do {
                System.out.println("What is your rank?");
                System.out.println("1 - Labour.\n2 - Manager.\n3 - Member of the board of directors.\n");

                try {
                    userRank = BaseOptions.valueOf(intScanner.nextInt());

                    if (userRank != null) {
                        switch (userRank) {
                            case OPTION_1 -> workerUser.setRank(BaseOptions.OPTION_1);
                            case OPTION_2 -> workerUser.setRank(BaseOptions.OPTION_2);
                            case OPTION_3 -> workerUser.setRank(BaseOptions.OPTION_3);
                        }
                    }
                    else {
                        System.out.println("Unknown option (Allowed only 1, 2, 3)\n");
                    }

                } catch (InputMismatchException e) {
                    System.out.println("Enter only digits\n");
                    intScanner.nextLine();
                }
            } while (!(userRank == BaseOptions.OPTION_3 || userRank == BaseOptions.OPTION_2 || userRank == BaseOptions.OPTION_1));

            workers.add(workerUser);

        }
        else {
            Customer customerUser = new Customer(firstName, lastName, userName, password);
            System.out.println("Are you club member? y/n");
            userAnswer = lnScanner.nextLine();
            customerUser.setClubMember(choseYesOption(userAnswer));
            customers.add(customerUser);

        }

    }
    private String getCorrectUsername() {
        boolean existsInWorkers, existsInCustomers, exists;
        String userName;
        Scanner lnScanner = new Scanner(System.in);
        do {
            System.out.println("Enter user name: ");
            userName = lnScanner.nextLine();
            existsInWorkers = false;
            existsInCustomers = false;

                for (Worker worker : workers) {
                    if (worker != null) {
                        if (worker.getUserName().equals(userName)) {
                            existsInWorkers = true;
                            break;
                        }
                    }
                }

                for (Customer customer : customers) {
                    if (customer != null) {
                        if (customer.getUserName().equals(userName)) {
                            existsInCustomers = true;
                            break;
                        }
                    }
                }
            exists = existsInWorkers || existsInCustomers;

            if (exists) {
                System.out.println("The username already exists, chose another.");
            }
        } while (exists);

        return userName;
    }

    private String getCorrectPassword() {
        String password;
        int currentPasswordLen, allowedPasswordLen = 5;
        boolean allowed;
        Scanner lnScanner = new Scanner(System.in);
        do {
            System.out.println("Enter password (min 6 chars): ");
            password = lnScanner.nextLine();
            currentPasswordLen = password.length();

            allowed = currentPasswordLen > allowedPasswordLen;
            if (!allowed) {
                System.out.println("The password too short.");

            }

        } while (!allowed);

        return password;
    }

    private String getCorrectName(boolean isFirstName) {
        Scanner lnScanner = new Scanner(System.in);
        String name;
        boolean correct;
        do {
            correct=true;

            if (isFirstName) {
                System.out.println("Enter the first name: ");
            }
            else {
                System.out.println("Enter the last name: ");
            }

            name = lnScanner.nextLine();

            for (int i = 0; i < name.length(); i++) {
                char nameCharacter = name.charAt(i);
                if (nameCharacter >= 48 && nameCharacter <= 57) {
                    System.out.println("Name can't contains numbers");
                    correct = false;
                    break;
                }
            }
        } while (!correct);

        return name;

    }

    private void signIn() {
        Scanner lnScanner = new Scanner(System.in);
        String userAns, userName, password;
        boolean isWorker;
        int succeeded;


        System.out.println("Are you  worker? y/n");
        userAns = lnScanner.nextLine();
        isWorker = choseYesOption(userAns);

        System.out.println("Enter the user name: ");
        userName = lnScanner.nextLine();
        System.out.println("Enter the password: ");
        password = lnScanner.nextLine();

        succeeded=findUser(isWorker, userName, password);
        if (succeeded < 0) {
            System.out.println("Wrong user name or password.");
        }
        else {

            openUserMenu(isWorker, succeeded);

        }
    }

    private void openUserMenu(boolean isWorker, int userIndex) {
        if (isWorker) {
            Worker currentWorker = workers.get(userIndex);
            String rankMessage = "";
            BaseOptions workerRank = currentWorker.getRank();

            switch (workerRank) {
                case OPTION_1 -> rankMessage = " (Labour)!";
                case OPTION_2 -> rankMessage = " (Manager)!";
                case OPTION_3 -> rankMessage = " (Director)!";
            }

            System.out.println("Hello " + currentWorker + rankMessage);
            WorkerMenuOptions workerChoice = null;

            do{
                try {
                    Scanner intScanner = new Scanner(System.in);
                    System.out.println("\nChose the option: ");
                    System.out.println("""
                            1 - Print all customers\s
                            2 - Print all club members\s
                            3 - Print all buyers\s
                            4 - Print max buyer\s
                            5 - Add product\s
                            6 - Change product status\s
                            7 - Buy product\s
                            8 - Exit\s
                            """);
                    workerChoice = WorkerMenuOptions.valueOf(intScanner.nextInt());
                    if (workerChoice != null) {
                        switch (workerChoice) {
                            case PRINT_ALL_CUSTOMERS -> printCustomOrClubMemberOrBuyers(BaseOptions.OPTION_1);
                            case PRINT_ALL_CLUB_MEMBERS -> printCustomOrClubMemberOrBuyers(BaseOptions.OPTION_2);
                            case PRINT_ALL_BUYERS -> printCustomOrClubMemberOrBuyers(BaseOptions.OPTION_3);
                            case PRINT_MAX_BUYER -> printMaxBuyer();
                            case ADD_PRODUCT -> addToProductsList();
                            case CHANGE_PRODUCT_STATUS -> changeProductStatus();
                            case BUY_PRODUCT -> {
                                setWorkerDiscountPrice(currentWorker.getRank());
                                buyProduct(currentWorker, BaseOptions.OPTION_1);
                            }
                            case EXIT -> System.out.println("Exit");

                        }

                    } else {
                        System.out.println("Selection should be less than 9 and above 0");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Enter only digits");
                }

            } while (workerChoice != WorkerMenuOptions.EXIT);

        }
        else {
            Customer currentCustomer = customers.get(userIndex);
            BaseOptions option;
            System.out.print("Hello ");
            if (currentCustomer.isClubMember()) {
                System.out.println(currentCustomer + " (VIP)!");
                option = BaseOptions.OPTION_2;
            }
            else {
                System.out.println(currentCustomer + "!");
                option = BaseOptions.OPTION_3;
            }
            setCustomerDiscountPrice();
            buyProduct(currentCustomer, option);
        }

    }


    private int findUser(boolean isWorker, String userName, String password) {

        int index = -1;
        if (isWorker) {
            for (int i = 0; i < workers.size(); i++) {
                Worker currentWorker = workers.get(i);
                if (currentWorker != null) {
                        if (userName.equals(currentWorker.getUserName()) && password.equals(currentWorker.getPassword())) {
                            index = i;
                            break;
                    }
                }
            }
        } else {
            for (int i = 0; i < customers.size(); i++) {
                Customer currentCustomer = customers.get(i);
                if (currentCustomer != null) {
                    if (userName.equals(currentCustomer.getUserName()) && password.equals(currentCustomer.getPassword())) {
                        index = i;
                        break;
                    }
                }
            }

        }
        return index;
    }

    private boolean choseYesOption(String input) {
        return input.equals("Y") || input.equals("y") || input.equals("yes");
    }

    private void buyProduct(User currentUser, BaseOptions option) {
        int userChoice;
        double spended = 0;
        Scanner intScanner = new Scanner(System.in);
        do {
            userChoice = -1;
            try {
                int i=0, j=1;
                Product currentProduct;
                Map<Integer, Integer> inStockProdIndexes = new HashMap<>();
                if (products.size() == 0) {
                    System.out.println("Temporary no products, enter -1 to exit.");
                } else {
                    while (i < products.size()) {
                        currentProduct = products.get(i);
                        inStockProdIndexes.put(j, i);
                        if (currentProduct != null && currentProduct.isInStock()) {
                            System.out.println(j + ". " + currentProduct);
                            
                            j++;
                        }
                        i++;
                    }
                    System.out.println("Enter the number of product you want to buy (-1 to exit): ");
                }
                userChoice = intScanner.nextInt();
                if (userChoice < j && userChoice >= 1) {
                    currentUser.addToBasket(products.get(inStockProdIndexes.get(userChoice)));
                    currentUser.printBasket(option);
                } else {
                    if (userChoice != -1) {
                        System.out.println("Can't find product with index " + userChoice);
                    }
                }

                if (userChoice == -1) {
                    spended = currentUser.getBasket().getTotalBasketPrice();
                }

            } catch (InputMismatchException e) {
                System.out.println("Error");
            }

        } while ((userChoice != -1));


        currentUser.getBasket().setTotalBasketPrice(0);
        currentUser.getBasket().getInBasket().clear();

        System.out.println("Total basket price: " + spended +"$\n");
        if (spended > 0) {
            currentUser.setSpended(spended+currentUser.getSpended());
            currentUser.setPurchaseCounter(currentUser.getPurchaseCounter()+1);
        }

    }

    private void setWorkerDiscountPrice(BaseOptions rank) {
        float discount = switch (rank) {
            case OPTION_1 -> 10;
            case OPTION_2 -> 20;
            case OPTION_3 -> 30;
        };

        for (Product product : products) {
            if (product != null) {
                double productPrice = product.getPrice();
                product.setDiscountPrice(Math.ceil(productPrice - (discount/100)*productPrice));
            }
        }
    }

    private void setCustomerDiscountPrice() {
        for (Product product : products) {
            if (product != null) {
                double productPrice = product.getPrice();
                product.setDiscountPrice(Math.ceil(productPrice - (product.getDiscount()/100)*productPrice));
            }
        }
    }

    private void printCustomOrClubMemberOrBuyers(BaseOptions options){
        String message = switch (options) {

            case OPTION_1 -> "Customers list: ";
            case OPTION_2 -> "Club members list: ";
            case OPTION_3 -> "Buyers list: ";

        };

        System.out.println(message);

        for (Customer currentCustomer : customers) {
            if (currentCustomer != null) {
                    switch (options) {
                        case OPTION_1:
                            printCustomerData(currentCustomer);
                            break;
                        case OPTION_2:
                            if (currentCustomer.isClubMember()) {
                                printCustomerData(currentCustomer);
                            }
                            break;
                        case OPTION_3:
                            if (currentCustomer.getPurchaseCounter() > 0) {
                                printCustomerData(currentCustomer);
                            }
                            break;
                    }
            }
        }
        if (options == BaseOptions.OPTION_3) {
            for (Worker worker : workers) {
                if(worker != null) {
                    if (worker.getPurchaseCounter() > 0) {
                        printWorkerData(worker);
                    }
                }
            }
        }
    }

    void addToProductsList(){
        Scanner scanner = new Scanner(System.in);
        String aboutProduct;
        double price, discount;
        boolean inProductList = false;

        System.out.println("Enter product description: ");
        aboutProduct = scanner.nextLine();
        try {
            System.out.println("Enter product price: ");
            price = scanner.nextDouble();
            System.out.println("Enter product discount: ");
            discount = scanner.nextDouble();

            if (discount < 100 && discount >=0 && price > 0) {

                Product newProduct = new Product(price, aboutProduct, discount);

                for (Product currentProduct : products) {
                    if (currentProduct.equals(newProduct)) {
                        inProductList = true;
                        break;
                    }
                }

                if (!inProductList) {
                    products.add(newProduct);
                    System.out.println("Product successfully added.");
                }
            }
            else {
                System.out.println("Illegal price or discount percentage");
            }

        } catch (InputMismatchException e) {
            System.out.println("Input error");
            scanner.nextLine();
        }

    }

    void changeProductStatus(){
        Scanner intScanner = new Scanner(System.in);
        Scanner lnScanner = new Scanner(System.in);
        String inStockDescription;
        int productIndex = -1;
        if (products.size() != 0) {
            do {
                int i=0;
                try {
                    while (i < products.size()) {
                        System.out.println(i + ". " + products.get(i));
                        i++;
                    }
                    System.out.println("Enter product index");
                    productIndex = intScanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Input error");
                    intScanner.nextLine();
                }
            } while (!(productIndex >= 0 && productIndex < products.size()));

            System.out.println("Is product in stock? y/n");
            inStockDescription = lnScanner.nextLine();
            Product product = products.get(productIndex);
            product.setInStock(choseYesOption(inStockDescription));

            System.out.println("Product now" + (product.isInStock() ? " " : " not ") + "in stock.");
        } else {
            System.out.println("No products found to change status\n");
        }
    }

    private void printMaxBuyer() {
        customers.sort(Collections.reverseOrder());
        if (customers.size() != 0) {
            Customer customer = customers.get(0);
            if (customer != null) {
                System.out.println("Max buyer (Customer): ");
                printCustomerData(customer);
            }
        }

        workers.sort(Collections.reverseOrder());
        if (workers.size() != 0) {
            Worker worker = workers.get(0);
            if (worker != null) {
                System.out.println("Max buyer (Worker): ");
                printWorkerData(worker);
            }
        }
    }

    private void printCustomerData(Customer customer) {
        System.out.println("Customer account name: " + customer.getUserName() + ", name: " +
                           customer + ", total spended: " + customer.getSpended() +
                           "$ , total purchases: " + customer.getPurchaseCounter() +
                           ", is club member: " + customer.isClubMember());

    }

    private void printWorkerData(Worker worker) {
        String workerRank = switch (worker.getRank()) {
            case OPTION_1 -> "Labour";
            case OPTION_2 -> "Manager";
            case OPTION_3 -> "Director";
        };

        System.out.println("Worker account name: " + worker.getUserName() + ", name: " +
                worker + ", total spended: " + worker.getSpended() +
                "$ , total purchases: " + worker.getPurchaseCounter() + ", worker rank: " + workerRank);

    }
}
