
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.BufferedWriter;


public class DisneyPinsStoreMain {


    public static void initiatePurchase()throws IOException {

        System.out.println("initiatePurchase") ;
            ArrayList<MyProductClass> purchasedItems = new ArrayList<>();
            double subtotal = 0.0;
            double taxRate = 0.13;
            double tax = 0.0;
            double total = 0.0;

            //System.out.println("initiatePurchase - Scanner s");
            Scanner s = new Scanner(System.in);
            //System.out.println("initiatePurchase - Scanner s - done");
            String searchName;
            int choice2 = 0;
            boolean continueSearch = true;
            String purchaseChoice;


            BufferedReader in = new BufferedReader(new FileReader("DisneyPins.csv"));

            int size = Integer.parseInt(in.readLine());

            MyProductClass[] pins = new MyProductClass[size];

            for (int i = 0; i < pins.length; ++i) {
                String csvLine = in.readLine();
                pins[i] = new MyProductClass(csvLine);


            }

            in.close();

            while (continueSearch) {
                //System.out.print(" initiate purchase - Enter pin to search for: ");
//                System.out.print("------Enter pin to search for: ");

                //TODO
                //System.out.print("");
                searchName = s.nextLine();

                System.out.print("Enter pin to search for: ");


                searchName = s.nextLine();


//                if (searchName.equals("")) {
//                    System.out.println("++++++++searchname: " + searchName + "  ");
//                    break;
//                }


                int count = 0;
                for (int i = 0; i < pins.length; i++) {
                    if (pins[i].getName().startsWith(searchName)) {
                        System.out.printf(" %3d. %s %n", i + 1, pins[i].getName());
                        count++;
                    }
                }

                System.out.println();
                System.out.printf("%d pins found.%n%n", count, (count == 1 ? "" : "s"));

                int i;
                while (true) {
                    System.out.println("");
                    System.out.print("Choose an option: ");
                    System.out.println("");
                    System.out.printf("%-17s %n", "(-1)  - Item Description \n");
                    System.out.printf("%-17s %n", "( 0)  - No purchase, accidental selection\n");
                    System.out.printf("%-17s %n", "( 3)  - Pick a number to see purchase\n");
                    choice2 = s.nextInt();

                    if (choice2 == -1) {
                        System.out.print("Which pin would you like a description for: \n");
                        int pinNumber = s.nextInt();
                        s.nextLine();
                        MyProductClass chosenPin = pins[pinNumber - 1];
                        System.out.println("Pin Description: \n" + chosenPin.getDescription());

                    } else if (choice2 == 0) {
                        //continueSearch = false;
                        break;

                    }
                    if (choice2 == 3) {
                        System.out.print("Pick a item you would like to purchase (item #):\n");
                        int itemPurchase = s.nextInt();
                        s.nextLine();
                        i = itemPurchase - 1;
                        System.out.println(i);


                        MyProductClass chosenPin = pins[itemPurchase - 1];
                        System.out.println("Pin Purchase: \n" + chosenPin.getName());
                        System.out.print("");
                        purchasedItems.add(chosenPin);

                        System.out.print("How many items would you like?");
                        int itemQuant = s.nextInt();
                        s.nextLine();


                        double itemCost = chosenPin.getPrice() * itemQuant;
                        subtotal += itemCost;

                        chosenPin.setQuantity(chosenPin.getQuantity() - itemQuant);

                        MyProductClass pinQuant = pins[itemPurchase - 1];
                        if (itemQuant <= chosenPin.getQuantity()) {
                            System.out.print("Pin #" + itemPurchase + ": IN STOCK - " + chosenPin.getQuantity());
                            System.out.println("");

                        } else {
                            System.out.print("Pin #" + itemPurchase + ": OUT OF STOCK - " + chosenPin.getQuantity());
                            System.out.println("");
                        }

                        do {
                            System.out.println("");
                            System.out.print("Would you like to make another purchase?: ");
                            purchaseChoice = s.nextLine();
                            if (purchaseChoice.equals("y") || purchaseChoice.equals("n")) {
                                break;
                            } else {
                                System.out.println("");
                                System.out.print("INVALID, TRY AGAIN! ( Y or N)\n");
                                System.out.println("");
                            }
                        } while (true);//while true

                        if (purchaseChoice.equals("y")) {
                            System.out.print("");
                            break;
                        } else {
                            System.out.print("ENDED");
                            // Generate invoice
                            System.out.println(purchasedItems.size());
                            System.out.println("\n------------------ Invoice ------------------");
                            for (MyProductClass item : purchasedItems) {
                                System.out.println("Item: " + item.getName());
                                System.out.println("Quantity: " + itemQuant);
                                System.out.println("Individual Cost: $" + item.getPrice());
                                System.out.println("Total Cost: $" + (item.getPrice() * itemQuant));
                                System.out.println("----------------------------------------------");
                            }

                            // Calculate subtotal, tax, and total
                            tax = subtotal * taxRate;
                            total = subtotal + tax;

                            // Display subtotal, tax, and total
                            System.out.println("\nSubtotal: $" + subtotal);
                            System.out.println("Tax (13% HST): $" + tax);
                            System.out.println("Total: $" + total);

                            // Update quantities in the file
                            BufferedWriter writer = new BufferedWriter(new FileWriter("DisneyPins.csv"));
                            writer.write(size + "\n");
                            for (MyProductClass pin : pins) {
                                writer.write(pin.toCSV() + "\n");
                            }
                            writer.close();

                            // Update sales-daily.txt file
                            BufferedWriter salesWriter = new BufferedWriter(new FileWriter("sales-daily.txt", true));
                            salesWriter.write(total + "\n");
                            salesWriter.close();
                            return;



                        }

                    }



                }


            }
        }



    public static void adminOptions()  throws IOException{

        BufferedReader in = new BufferedReader(new FileReader("DisneyPins.csv"));
        int size = Integer.parseInt(in.readLine());
        MyProductClass[] pins = new MyProductClass[size];

        for (int i = 0; i < pins.length; ++i) {
            String csvLine = in.readLine();
            pins[i] = new MyProductClass(csvLine);
        }

        in.close();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the pin number to modify: ");
        int pinNumber = scanner.nextInt();
        scanner.nextLine();

        if (pinNumber < 1 || pinNumber > pins.length) {
            System.out.println("Invalid pin number!");
            return;
        }

        MyProductClass pinToModify = pins[pinNumber - 1];

        System.out.println("Current pin details:");
        System.out.println(pinToModify);

        System.out.println("Choose a variable to modify:");
        System.out.println("1. Name");
        System.out.println("2. Cost");
        System.out.println("3. Quantity");
        System.out.println("4. Description");

        System.out.print("Enter your choice: ");
        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1:
                System.out.print("Enter the new name: ");
                String newName = scanner.nextLine();
                pinToModify.setName(newName);
                break;
            case 2:
                System.out.print("Enter the new cost: ");
                double newCost = scanner.nextDouble();
                pinToModify.setPrice(newCost);
                break;
            case 3:
                System.out.print("Enter the new quantity: ");
                int newQuantity = scanner.nextInt();
                pinToModify.setQuantity(newQuantity);
                break;
            case 4:
                System.out.print("Enter the new description: ");
                String newDescription = scanner.nextLine();
                pinToModify.setDescription(newDescription);
                break;
            default:
                System.out.println("Invalid choice!");
                return;
        }

        BufferedWriter out = new BufferedWriter(new FileWriter("DisneyPins.csv"));
        out.write(Integer.toString(pins.length));
        out.newLine();

        for (MyProductClass pin : pins) {
            out.write(pin.toCSV());
            out.newLine();
        }

        out.close();
    }



    public static void JITMoniter() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("DisneyPins.csv"));
        int size = Integer.parseInt(in.readLine());
        MyProductClass[] pins = new MyProductClass[size];

        for (int i = 0; i < pins.length; ++i) {
            String csvLine = in.readLine();
            pins[i] = new MyProductClass(csvLine);
        }
        in.close();

        System.out.println("JIT Inventory Monitor");
        System.out.println("---------------------");
        System.out.println("Product Name\tQuantity");

        for (MyProductClass pin : pins) {
            int jitTrigger = pin.getJitTrigger(); // Retrieve the JIT trigger amount for the current product
            int quantity = pin.getQuantity();
            String productName = pin.getName();

            if (quantity <= jitTrigger) {
                System.err.println(productName + "\t" + quantity);
            } else {
                System.out.println(productName + "\t" + quantity);
            }
        }
    }

    public static void displaySalesAmount() throws IOException {
        double dailySalesTotal=0;
        double grandTotal=0;
        double lineTotal=0.0;


        //good
        BufferedReader salesTotalReader = new BufferedReader(new FileReader("sales-daily.txt"));

        String line;
        while ((line = salesTotalReader.readLine()) != null)   {
            // Print the content on the console


            dailySalesTotal += Double.parseDouble(line);
            System.out.println(dailySalesTotal);


        }
        salesTotalReader.close();


        //grand total reader
        BufferedReader grandTotalReader = new BufferedReader(new FileReader("sales-total.txt"));

        while ((line = grandTotalReader.readLine()) != null)   {
            // Print the content on the console
            System.out.println ("grand total line is " + line);

            grandTotal += Double.parseDouble(line);
            System.out.println(grandTotal);


        }
        grandTotalReader.close();






        System.out.println("Session Sales Amount: $" + dailySalesTotal);
        System.out.println("Grand Total: $" + grandTotal);

        Scanner input = new Scanner(System.in);
        System.out.print("Do you want to roll over to a new day? (Type 'Y' to add the daily sales to the grand total): ");
        String rollOverChoice = input.nextLine();
        if (rollOverChoice.equalsIgnoreCase("Y")) {
            grandTotal += dailySalesTotal;
            dailySalesTotal = 0.0;

            BufferedWriter salesTotalWriter = new BufferedWriter(new FileWriter("sales-total.txt"));
            salesTotalWriter.write(Double.toString(grandTotal));
            salesTotalWriter.close();

            salesTotalWriter = new BufferedWriter(new FileWriter("sales-daily.txt"));
            salesTotalWriter.write(Double.toString(dailySalesTotal));
            salesTotalWriter.close();
        }
    }



































    public static void main(String[]args)throws IOException{



        System.out.println("Olivia's Disney Pin Store");
        System.out.println();
        System.out.println();
        System.out.println("Welcome to Olivia's DPS! Your satisfaction is our guarantee!");
        System.out.println("This program will help you determine the cost for your disney pins.");
        System.out.println();
        System.out.println();


            int choice1=0;




        //marks report art
            System.out.println("-----------------------------------------");
            System.out.println("     OLIVIA'S DISNEY PINtastic STORE          ");
            System.out.println("------------------------------------------");
            System.out.println("");

        //scanner for input
        Scanner input = new Scanner(System.in);
            //main while loop
            while(true){
                //user chooses option
                System.out.println("");
                System.out.printf("%-17s %n", "1 - Initiate a purchase");
                System.out.printf("%-17s %n", "2 - Administrative options ");
                System.out.printf("%-17s %n", "3 - JIT inventory monitor");
                System.out.printf("%-17s %n", "4 - Session sales amount");
                System.out.printf("%-17s %n", "5 - HELP");
                System.out.printf("%-17s %n", "6 - Exit program");

                //error trap choice (1-3)
                do {
                    System.out.println("");
                    System.out.print("Choose an option: ");
                    choice1= input.nextInt();
                    System.out.println("-------");
                    if (choice1==1|choice1==2|choice1==3|choice1==4|choice1==5|choice1==6){
                        break;
                    }else{
                        System.out.println("");
                        System.err.print("INVALID, TRY AGAIN! (1-3)\n");
                        System.out.println("");
                    }
                }while(true);//while true

                //based on number choice, user will call a different method
                //System.out.println("if (choice1==1){") ;
                if (choice1==1){
                    initiatePurchase();
                }

                if (choice1==2){
                    adminOptions();
                }

                if (choice1==3){
                    JITMoniter();
                }

                if (choice1==4){
                    displaySalesAmount();

                }

                if (choice1==5){

                }

                if (choice1==6){
                    System.err.print("*PROGRAM ENDED*");
                    break;
                }


            }




            }

        }






















