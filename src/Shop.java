import java.util.*;

public class Shop {
    private final List<User> users;
    private final List<Product> products;

    public Shop() {

        users = new LinkedList<>();
        products = new LinkedList<>();
        products.add(new Product(100,"baba", 1));
        products.add(new Product(100,"gaga", 8));
        products.add(new Product(1000,"dada", 12));
        products.add(new Product(10100,"yaya", 3));
        Product p = new Product(10100,"uauaa", 13);
        p.setInStock(false);
        products.add(p);
        products.add(new Product(10100,"fafa", 19.5f));
        products.add(new Product(10100,"jaja", 39));
        run();
    }

    private void run() {
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
    }

    private void createUser() {
        String userName, password, firstName, lastName;
        Scanner lnScanner = new Scanner(System.in);
        Scanner intScanner = new Scanner(System.in);
        BaseOptions userRank = null;
        userName = getCorrectUsername();
        password = getCorrectPassword();
        firstName = getCorrectName(true);
        lastName = getCorrectName(false);


        System.out.println("Are you a worker? y/n");
        String userAnswer = lnScanner.nextLine();

        if (choseYesOption(userAnswer)) {
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

            users.add(workerUser);

        }
        else {
            Customer customerUser = new Customer(firstName, lastName, userName, password);
            System.out.println("Are you club member? y/n");
            userAnswer = lnScanner.nextLine();
            customerUser.setClubMember(choseYesOption(userAnswer));
            users.add(customerUser);

        }

    }
    private String getCorrectUsername() {
        boolean exists;
        String userName;
        Scanner lnScanner = new Scanner(System.in);
        do {
            System.out.println("Enter user name: ");
            userName = lnScanner.nextLine();
            exists = false;
            for (User user: users) {
                if (user != null) {
                    if (user.getUserName().equals(userName)) {
                        exists = true;
                        break;
                    }
                }
            }
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
        User currentUser = users.get(userIndex);
        if (isWorker) {
            String rankMessage = "";
            BaseOptions workerRank = ((Worker) currentUser).getRank();

            switch (workerRank) {
                case OPTION_1 -> rankMessage = " (Labour)!";
                case OPTION_2 -> rankMessage = " (Manager)!";
                case OPTION_3 -> rankMessage = " (Director)!";
            }

            System.out.println("Hello " + currentUser + rankMessage);
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
                            case BUY_PRODUCT -> buyProduct(currentUser, true);

                        }

                    } else {
                        System.out.println("Employee selection should be less than 9 and above 0");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Enter only digits");
                }

            } while (workerChoice != WorkerMenuOptions.EXIT);

        }
        else {
            System.out.print("Hello ");
            System.out.println(((Customer) currentUser).isClubMember() ? (currentUser + " (VIP)!") : (currentUser + "!"));
            buyProduct(currentUser, false);
        }

    }


    private int findUser(boolean isWorker, String userName, String password) {
        String className;
        if(isWorker) {
            className = "Worker";

        }
        else {
            className = "Customer";
        }
        int index = -1;

        for (int i = 0; i < users.size(); i++) {
            User currentUser = users.get(i);
            if (currentUser != null) {
                if (className.equals(currentUser.getClass().getTypeName())) {
                    if (userName.equals(currentUser.getUserName()) && password.equals(currentUser.getPassword())) {
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

    private void buyProduct(User currentUser, boolean isWorker) {

        if (isWorker) {
            setWorkerDiscountPrice(((Worker) currentUser).getRank());
            }
        else {
            setCustomerDiscountPrice();
        }

        int userChoice;
        Scanner intScanner = new Scanner(System.in);
        System.out.println("Enter the number of product you want to buy (-1 to exit): ");
        do {
            userChoice = -1;
            try {
                int i=0, j=1;
                Product currentProduct;
                Map<Integer, Integer> inStockProdIndexes = new HashMap<>();
                while (i < products.size()) {
                        currentProduct = products.get(i);
                        inStockProdIndexes.put(j, i);
                        if (currentProduct != null && currentProduct.isInStock()) {
                            System.out.println(j + ". " + currentProduct);
                            j++;
                        }
                        i++;
                    }

                userChoice = intScanner.nextInt();
                if (userChoice < j && userChoice >= 1) {
                     currentUser.addToBasket(products.get(inStockProdIndexes.get(userChoice)));
                     if (isWorker) {
                         currentUser.printBasket(BaseOptions.OPTION_1);
                     } else {

                         if (((Customer) currentUser).isClubMember()) {
                             currentUser.printBasket(BaseOptions.OPTION_2);
                         }
                         else {
                             currentUser.printBasket(BaseOptions.OPTION_3);
                         }
                     }
                }


            } catch (InputMismatchException e) {
                System.out.println("Error");
            }

        } while ((userChoice != -1));

        double spended = currentUser.getBasket().getTotalBasketPrice();
        System.out.println("Total basket price: " + spended +"$\n");
        if (spended > 0) {
            currentUser.setMadePurchase(true);
            currentUser.setSpended(spended+currentUser.getSpended());
            int purchaseCounter = currentUser.getPurchaseCounter();
            purchaseCounter++;
            currentUser.setPurchaseCounter(purchaseCounter);
            System.out.println("Total spended " + currentUser.getSpended());
        }
        currentUser.getBasket().getInBasket().clear();
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
        for (User currentCustomer : users) {
            if (currentCustomer != null) {
                boolean isCustomer = currentCustomer.getClass().getTypeName().equals("Customer");
                if (isCustomer) {
                    Customer customerRef = (Customer) currentCustomer;
                    switch (options) {
                        case OPTION_1:
                            printCustomerData(customerRef);
                            break;
                        case OPTION_2:
                            if (customerRef.isClubMember()) {
                                printCustomerData(customerRef);
                            }
                            break;
                        case OPTION_3:
                            if (customerRef.isMadePurchase()) {
                                printCustomerData(customerRef);
                            }
                            break;
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
            }
        } while (!(productIndex >= 0 && productIndex < products.size()));

        System.out.println("Is product in stock? y/n");
        inStockDescription = lnScanner.nextLine();
        Product product = products.get(productIndex);
        product.setInStock(choseYesOption(inStockDescription));

        System.out.println("Product now" + (product.isInStock() ? " " : " not ") + "in stock.");
    }

    private void printMaxBuyer() {
        users.sort(Collections.reverseOrder());
        if (users.size() != 0) {
          printCustomerData((Customer) users.get(0));
        }
    }

    private void printCustomerData(Customer customer) {
        System.out.println("User name: " + customer.getUserName() + ", name: " +
                           customer + ", total spended: " + customer.getSpended() +
                           ", total purchases: " + customer.getPurchaseCounter() +
                           ", is club member: " + customer.isClubMember());

    }
}
