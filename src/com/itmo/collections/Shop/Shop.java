package com.itmo.collections.Shop;

import java.util.*;

public class Shop {

    private static final String BUY_CMD = "buy";
    private static final String EXIT_CMD = "exit";
    private static final String HELP_CMD = "help";
    private static final String SHOWCART_CMD = "show cart";
    private static final String SHOWSHOP_CMD = "show shop";
    private static final String PRINT_CHECK_CMD = "print check";
    private static final String LOGIN_CMD = "login";
    private static final String LOGOUT_CMD = "logout";

    private static final String SEPARATOR_1 = " ";
    private static final String SEPARATOR_2 = ",";

    private double account;
    private HashMap<Product, Integer> productMap = new HashMap<>();
    private List<User> usersList = new ArrayList<>();
    private User currentUser;
    private List<Transaction> transactions = new ArrayList<>();

    //contructor
    public Shop() {

        //make users list
        for (int i = 0; i < 3; i++)
            usersList.add(new User("user" + i, "password" + i, 100));

        //make product list
        productMap.put(new Product("Onion", "red onion small size", 3), 10);
        productMap.put(new Product("Bread", "black", 1), 30);
        productMap.put(new Product("Tomato", "", 5), 20);
        productMap.put(new Product("Cucumber", "", 3), 20);
        productMap.put(new Product("Chees", "parmezan", 8), 2);
        productMap.put(new Product("Milk", "", 4), 12);
        productMap.put(new Product("Yogurt", "", 2), 20);
        productMap.put(new Product("Fish", "", 7), 15);

        account = 500;
    }

    public static void main(String[] args) {

        Shop shp = new Shop();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome!");
        printUsage();

        while (true) {
            String line = scanner.nextLine().trim();

            if (SHOWCART_CMD.equals(line))
                if (shp.currentUser != null)
                    shp.currentUser.showCartBalance();
                else
                    System.out.println("You are not logon");
            else if (BUY_CMD.equals(line))
                if (shp.currentUser != null)
                    shp.makeTransaction();
                else
                    System.out.println("You are not logon");

            else if (EXIT_CMD.equals(line))
                return;

            else if (PRINT_CHECK_CMD.equals(line))
                shp.printCheck();

            else if (HELP_CMD.equals(line))
                printUsage();

            else if (LOGIN_CMD.equals(line.substring(0, 5))) {
                String[] uspsw = line.substring(6).split(SEPARATOR_1);
                shp.loginUser(uspsw[0], uspsw[1]);
            }

            else if (SHOWSHOP_CMD.equals(line))
                shp.showShopBalance();

            else if (LOGOUT_CMD.equals(line))
                shp.logoutUser();
            else if (!line.isEmpty())
                if (shp.currentUser != null)
                    shp.currentUser.addToCart(shp.parthLineToMap(line));
                else
                    System.out.println("You are not logon");
        }
    }

    private HashMap<Product, Integer> parthLineToMap(String line){

        HashMap<Product, Integer> hm = new HashMap<>();
        String[] messageSplit = line.split(SEPARATOR_2);
        lab:
        for (String mes : messageSplit) {

            String[] argSplit = mes.trim().split(SEPARATOR_1);

            String productName = argSplit[0];
            Integer productQuantity = Integer.parseInt(argSplit[1]);

            for (Map.Entry<Product, Integer> entry : productMap.entrySet()) {
                if (entry.getKey().getTitle().equals(productName)){
                    int quantityBalance = entry.getValue();
                    entry.setValue(quantityBalance - Math.min(quantityBalance, productQuantity));
                    hm.put(entry.getKey(), Math.min(quantityBalance, productQuantity));
                    continue lab;
                }
            }
            System.out.println("Product called " + productName + " haven't found.");
        }

        return hm;
    }

    private void loginUser(String log, String pass){

        for(User u : usersList){
            if (u.getName().equals(log) && u.getPassword().equals(pass)) {
                System.out.println("You are logon with user: " + u.getName());
                currentUser = u;
                return;
            }
        }
        System.out.println("User or password is wrong");
        currentUser = null;

    }

    private void logoutUser(){
        String name = currentUser.getName();
        currentUser = null;
        System.out.println("User " + name + " was log out");
    }

    private void showShopBalance(){
        System.out.println("Product balance in shop is:");
        for (Map.Entry<Product, Integer> entry: productMap.entrySet())
            System.out.println(entry.getKey().toString() + ", quantity - " + entry.getValue());
    }

    private static void printUsage() {
        System.out.println("Usage: ");
        System.out.println("Login user, using command 'login <UserName> <Password>'");
        System.out.println("Logout current user, using command 'logout'");
        System.out.println("Enter messages separated by comma or enter to put products to cart.\nProduct name and quantity are separated by space. F.e.:");
        System.out.println("Onion 3, Bread 1, Tomato 2");
        System.out.println("It will be put to cart min value of your count and balance in shop");
        System.out.println();
        System.out.println("Type commands:");
        System.out.println("'show cart' to show cart of current user.");
        System.out.println("'show shop' to show products of shop.");
        System.out.println("'print check' to see all transactions.");
        System.out.println("'buy' to make a sail tran       saction.");
        System.out.println("'exit' to exit.");
    }

    public void makeTransaction() {

        double counter = 0;
        boolean a = true;

        for (Map.Entry<Product, Integer> entry : currentUser.userCart.entrySet()) {
            double productPrice = entry.getKey().getPrice();
            double productSum = productPrice * entry.getValue();
            double usrAccount = currentUser.getAccount();

            if (counter + productSum <= usrAccount) {
                if (a) {
                    System.out.println(currentUser.getName() + " bought: ");
                    a = !a;
                }

                counter += productSum;
                System.out.println(entry.getKey() + " quantity: " + entry.getValue() + " sum: " + productSum);
            }
            else{
                int i = 0;
                for (i = 1; i <= entry.getValue();){
                    if (i * productPrice + counter < usrAccount)
                        i++;
                    else{
                        i = i - 1;
                        break;
                    }
                }
                if (i > 0)
                    if (a) {
                        System.out.println(currentUser.getName() + " bought: ");
                        a = !a;
                    }
                    System.out.println(entry.getKey() + " quantity: " + i + " sum: " + i * productPrice);

                productMap.put(entry.getKey(), entry.getValue() - i);
                counter += productPrice * i;
            }
        }
        account += counter;
        currentUser.setAccount(currentUser.getAccount() - counter);
        currentUser.userCart.clear();

        transactions.add(new Transaction(counter, currentUser));

    }

    public void printCheck(){
        for (Transaction t : transactions)
            System.out.println(t.toString());
    }
}
