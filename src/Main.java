import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {

    static ArrayList<Integer> ticketNumbers = new ArrayList<>();
    static ArrayList<String> requesterNames = new ArrayList<>();
    static ArrayList<String> issues = new ArrayList<>();
    static ArrayList<String> datesSubmitted = new ArrayList<>();   
    static ArrayList<String> statuses = new ArrayList<>();         

    
    static Queue<Integer> supportQueue = new LinkedList<>();

    static int nextTicketNumber = 1001;
    static Scanner sc = new Scanner(System.in);

    
    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            showMenu();
            int choice = readInt("Enter your choice: ", 0, 13);
            System.out.println();

            switch (choice) {
                case 1:  createTicket(); break;
                case 2:  displayTickets(); break;
                case 3:  searchTicket(); break;
                case 4:  updateTicket(); break;
                case 5:  deleteTicket(); break;
                case 6:  enqueueTicket(); break;
                case 7:  peekQueue(); break;
                case 8:  processNext(); break;
                case 9:  displayQueue(); break;
                case 10: checkQueueEmpty(); break;
                case 11: bubbleSortTickets(); break;
                case 12: insertionSortTickets(); break;
                case 13: loadSampleData(); break;
                case 0:
                    System.out.println("Thank you for using the IT Help Desk Ticketing System. Goodbye!");
                    running = false;
                    break;
            }
        }
        sc.close();
    }

    
    static void showMenu() {
        System.out.println();
        System.out.println("==========================================");
        System.out.println("     IT HELP DESK TICKETING SYSTEM");
        System.out.println("==========================================");
        System.out.println(" -- Ticket Records --");
        System.out.println("  1. Create new ticket");
        System.out.println("  2. Display all tickets");
        System.out.println("  3. Search ticket");
        System.out.println("  4. Update ticket issue");
        System.out.println("  5. Delete ticket");
        System.out.println(" -- Support Queue (FIFO) --");
        System.out.println("  6. Enqueue ticket (add to queue)");
        System.out.println("  7. Peek next ticket");
        System.out.println("  8. Process next ticket (dequeue)");
        System.out.println("  9. Display queue");
        System.out.println(" 10. Check if queue is empty");
        System.out.println(" -- Sorting --");
        System.out.println(" 11. Bubble Sort tickets");
        System.out.println(" 12. Insertion Sort tickets");
        System.out.println(" -- Other --");
        System.out.println(" 13. Load sample data");
        System.out.println("  0. Exit");
        System.out.println("==========================================");
    }

    
    static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value < min || value > max) {
                    System.out.println("Invalid input. Please enter a number from " + min + " to " + max + ".");
                } else {
                    return value;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    
    static String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            if (line.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
            } else {
                return line;
            }
        }
    }

    static String readDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            if (isValidDate(line)) {
                return line;
            }
            System.out.println("Invalid date. Use the format yyyy-MM-dd (example: 2026-10-23).");
        }
    }

    static boolean isValidDate(String s) {
        if (!s.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return false;
        }
        int month = Integer.parseInt(s.substring(5, 7));
        int day = Integer.parseInt(s.substring(8, 10));
        return month >= 1 && month <= 12 && day >= 1 && day <= 31;
    }


    static void addRecord(String name, String issue, String date) {
        ticketNumbers.add(nextTicketNumber);
        requesterNames.add(name);
        issues.add(issue);
        datesSubmitted.add(date);
        statuses.add("Registered");
        nextTicketNumber++;
    }


    static int findIndex(int ticketNo) {
        for (int i = 0; i < ticketNumbers.size(); i++) {
            if (ticketNumbers.get(i) == ticketNo) {
                return i;
            }
        }
        return -1;
    }

    static void printHeader() {
        System.out.printf("%-8s %-18s %-30s %-12s %-11s%n",
                "Ticket#", "Requester", "Issue", "Date", "Status");
        System.out.println("-------------------------------------------------------------------------------------");
    }

    static void printRow(int i) {
        System.out.printf("%-8d %-18s %-30s %-12s %-11s%n",
                ticketNumbers.get(i), requesterNames.get(i), issues.get(i),
                datesSubmitted.get(i), statuses.get(i));
    }


    static void createTicket() {
        System.out.println("--- Create New Ticket ---");
        String name = readText("Requester name: ");
        String issue = readText("Issue description: ");
        String date = readDate("Date submitted (yyyy-MM-dd): ");

        int number = nextTicketNumber;
        addRecord(name, issue, date);
        System.out.println("Ticket #" + number + " created successfully. (Status: Registered)");
        System.out.println("Tip: use option 6 to add it to the support queue.");
    }

    static void displayTickets() {
        System.out.println("--- All Tickets ---");
        if (ticketNumbers.isEmpty()) {
            System.out.println("No tickets have been recorded yet.");
            return;
        }
        printHeader();
        for (int i = 0; i < ticketNumbers.size(); i++) {
            printRow(i);
        }
        System.out.println("Total tickets: " + ticketNumbers.size());
    }

    static void searchTicket() {
        System.out.println("--- Search Ticket ---");
        if (ticketNumbers.isEmpty()) {
            System.out.println("No tickets to search. Create a ticket first.");
            return;
        }
        System.out.println("1. Search by ticket number");
        System.out.println("2. Search by requester name");
        int option = readInt("Choose search type: ", 1, 2);

        if (option == 1) {
            int no = readInt("Enter ticket number: ", 1, Integer.MAX_VALUE);
            int idx = findIndex(no);
            if (idx == -1) {
                System.out.println("Ticket #" + no + " was not found.");
            } else {
                printHeader();
                printRow(idx);
            }
        } else {
            String keyword = readText("Enter name (or part of it): ").toLowerCase();
            boolean found = false;
            for (int i = 0; i < requesterNames.size(); i++) {
                if (requesterNames.get(i).toLowerCase().contains(keyword)) {
                    if (!found) {
                        printHeader();
                        found = true;
                    }
                    printRow(i);
                }
            }
            if (!found) {
                System.out.println("No ticket found for requester \"" + keyword + "\".");
            }
        }
    }

    static void updateTicket() {
        System.out.println("--- Update Ticket Issue ---");
        if (ticketNumbers.isEmpty()) {
            System.out.println("No tickets to update.");
            return;
        }
        int no = readInt("Enter ticket number to update: ", 1, Integer.MAX_VALUE);
        int idx = findIndex(no);
        if (idx == -1) {
            System.out.println("Ticket #" + no + " was not found.");
            return;
        }
        String newIssue = readText("New issue description: ");
        issues.set(idx, newIssue);
        System.out.println("Ticket #" + no + " updated successfully.");
    }

    static void deleteTicket() {
        System.out.println("--- Delete Ticket ---");
        if (ticketNumbers.isEmpty()) {
            System.out.println("No tickets to delete.");
            return;
        }
        int no = readInt("Enter ticket number to delete: ", 1, Integer.MAX_VALUE);
        int idx = findIndex(no);
        if (idx == -1) {
            System.out.println("Ticket #" + no + " was not found.");
            return;
        }
        ticketNumbers.remove(idx);
        requesterNames.remove(idx);
        issues.remove(idx);
        datesSubmitted.remove(idx);
        statuses.remove(idx);
        supportQueue.remove(Integer.valueOf(no));
        System.out.println("Ticket #" + no + " deleted.");
    }


    static void enqueueTicket() {
        System.out.println("--- Enqueue Ticket ---");
        if (ticketNumbers.isEmpty()) {
            System.out.println("No tickets available. Create a ticket first.");
            return;
        }
        int no = readInt("Enter ticket number to add to the queue: ", 1, Integer.MAX_VALUE);
        int idx = findIndex(no);
        if (idx == -1) {
            System.out.println("Ticket #" + no + " was not found.");
            return;
        }
        if (!statuses.get(idx).equals("Registered")) {
            System.out.println("Ticket #" + no + " cannot be enqueued. Current status: " + statuses.get(idx));
            return;
        }
        supportQueue.add(no);               
        statuses.set(idx, "Queued");
        System.out.println("Ticket #" + no + " added to the support queue. Position: " + supportQueue.size());
    }

    static void peekQueue() {
        System.out.println("--- Peek Next Ticket ---");
        if (supportQueue.isEmpty()) {
            System.out.println("The queue is empty. There is no next ticket.");
            return;
        }
        int no = supportQueue.peek();      
        int idx = findIndex(no);
        System.out.println("Next ticket to be processed (not removed):");
        printHeader();
        printRow(idx);
    }

    static void processNext() {
        System.out.println("--- Process Next Ticket ---");
        if (supportQueue.isEmpty()) {
            System.out.println("The queue is empty. No ticket to process.");
            return;
        }
        int no = supportQueue.poll();       
        int idx = findIndex(no);
        statuses.set(idx, "Resolved");
        System.out.println("Now processing ticket #" + no + " from " + requesterNames.get(idx) + ".");
        System.out.println("Issue: " + issues.get(idx));
        System.out.println("Ticket #" + no + " marked as Resolved.");
        System.out.println("Tickets still waiting: " + supportQueue.size());
    }

    static void displayQueue() {
        System.out.println("--- Current Support Queue (front -> back) ---");
        if (supportQueue.isEmpty()) {
            System.out.println("The queue is empty.");
            return;
        }
        int position = 1;
        for (int no : supportQueue) {       
            int idx = findIndex(no);
            System.out.println(position + ". Ticket #" + no + " | " + requesterNames.get(idx)
                    + " | " + issues.get(idx));
            position++;
        }
        System.out.println("Tickets waiting: " + supportQueue.size());
    }

    static void checkQueueEmpty() {
        System.out.println("--- Queue Status ---");
        if (supportQueue.isEmpty()) {
            System.out.println("The queue is EMPTY.");
        } else {
            System.out.println("The queue is NOT empty. Tickets waiting: " + supportQueue.size());
        }
    }

    
    static int chooseSortField() {
        System.out.println("Sort by:");
        System.out.println("1. Ticket number");
        System.out.println("2. Requester name (A-Z)");
        System.out.println("3. Date submitted (oldest first)");
        return readInt("Choose field: ", 1, 3);
    }


    static int compareRecords(int i, int j, int field) {
        int result;
        if (field == 1) {
            result = Integer.compare(ticketNumbers.get(i), ticketNumbers.get(j));
        } else if (field == 2) {
            result = requesterNames.get(i).compareToIgnoreCase(requesterNames.get(j));
        } else {
            result = datesSubmitted.get(i).compareTo(datesSubmitted.get(j));
        }
        if (result == 0) {   
            result = Integer.compare(ticketNumbers.get(i), ticketNumbers.get(j));
        }
        return result;
    }


    static void swapRecords(int i, int j) {
        int tNo = ticketNumbers.get(i);
        ticketNumbers.set(i, ticketNumbers.get(j));
        ticketNumbers.set(j, tNo);

        String tName = requesterNames.get(i);
        requesterNames.set(i, requesterNames.get(j));
        requesterNames.set(j, tName);

        String tIssue = issues.get(i);
        issues.set(i, issues.get(j));
        issues.set(j, tIssue);

        String tDate = datesSubmitted.get(i);
        datesSubmitted.set(i, datesSubmitted.get(j));
        datesSubmitted.set(j, tDate);

        String tStatus = statuses.get(i);
        statuses.set(i, statuses.get(j));
        statuses.set(j, tStatus);
    }


    static void bubbleSortTickets() {
        System.out.println("--- Bubble Sort ---");
        if (ticketNumbers.size() < 2) {
            System.out.println("Need at least 2 tickets to sort.");
            return;
        }
        int field = chooseSortField();

        System.out.println("\nBEFORE sorting:");
        printHeader();
        for (int i = 0; i < ticketNumbers.size(); i++) printRow(i);

        int n = ticketNumbers.size();
        for (int pass = 0; pass < n - 1; pass++) {
            boolean swapped = false;
            for (int j = 0; j < n - pass - 1; j++) {
                if (compareRecords(j, j + 1, field) > 0) {
                    swapRecords(j, j + 1);
                    swapped = true;
                }
            }
            if (!swapped) break;   
        }

        System.out.println("\nAFTER Bubble Sort:");
        printHeader();
        for (int i = 0; i < ticketNumbers.size(); i++) printRow(i);
        System.out.println("\nNote: the support queue order was NOT changed (still FIFO).");
    }

    static void insertionSortTickets() {
        System.out.println("--- Insertion Sort ---");
        if (ticketNumbers.size() < 2) {
            System.out.println("Need at least 2 tickets to sort.");
            return;
        }
        int field = chooseSortField();

        System.out.println("\nBEFORE sorting:");
        printHeader();
        for (int i = 0; i < ticketNumbers.size(); i++) printRow(i);

        for (int i = 1; i < ticketNumbers.size(); i++) {
            int j = i;
            while (j > 0 && compareRecords(j - 1, j, field) > 0) {
                swapRecords(j - 1, j);
                j--;
            }
        }

        System.out.println("\nAFTER Insertion Sort:");
        printHeader();
        for (int i = 0; i < ticketNumbers.size(); i++) printRow(i);
        System.out.println("\nNote: the support queue order was NOT changed (still FIFO).");
    }


    static void loadSampleData() {
        addRecord("Maria Santos", "Cannot connect to campus Wi-Fi", "2026-10-05");
        addRecord("Juan Dela Cruz", "Forgot student portal password", "2026-10-03");
        addRecord("Ana Reyes", "Projector not detected in Lab 2", "2026-10-09");
        addRecord("Carlo Mendoza", "Printer jam in library", "2026-10-01");
        addRecord("Bea Villanueva", "Email account locked", "2026-10-07");
        System.out.println("5 sample tickets loaded (#" + (nextTicketNumber - 5) + " to #" + (nextTicketNumber - 1) + ").");
    }
}
