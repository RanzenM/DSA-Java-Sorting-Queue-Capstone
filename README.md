# IT Help Desk Ticketing System

A console-based Java application that manages IT support tickets using **manually implemented sorting algorithms** (Bubble Sort and Insertion Sort) and a **FIFO queue**. Built procedurally (no custom classes) for the DSA Java Sorting and Queue Capstone.

## Project Description

Schools and offices receive many technical support requests (Wi-Fi problems, locked accounts, printer issues). Without an organized system, requests get lost or are handled unfairly. This application lets a help desk:

- record every support ticket,
- line tickets up in a queue so they are handled **first come, first served**, and
- sort the ticket records (by ticket number, name, or date) for easy review, **without disturbing the queue order**.

## Objectives

- Implement two sorting algorithms (Bubble Sort, Insertion Sort) without using `Collections.sort()` or `Arrays.sort()`.
- Apply queue operations (enqueue, dequeue, peek, display, empty check) following the FIFO principle.
- Use parallel `ArrayList`s to store and manage ticket records.
- Apply loops, conditionals, methods, and input validation in a menu-driven console program.
- Follow the procedural approach: only `static` methods, no custom classes or objects.

## Use Cases and Features

| Menu | Feature | Description |
|------|---------|-------------|
| 1 | Create ticket | Stores requester name, issue, and submission date (auto-numbered from 1001) |
| 2 | Display all tickets | Shows every record in a table |
| 3 | Search ticket | By ticket number or by (partial) requester name |
| 4 | Update ticket | Edit the issue description |
| 5 | Delete ticket | Removes the record from all lists (and from the queue if waiting) |
| 6 | Enqueue | Adds a registered ticket to the support queue |
| 7 | Peek | Views the next ticket without removing it |
| 8 | Dequeue / Process | Processes the oldest ticket and marks it *Resolved* |
| 9 | Display queue | Shows waiting tickets from front to back |
| 10 | Check empty | Reports whether the queue is empty |
| 11 | Bubble Sort | Sorts records by ticket number, name, or date |
| 12 | Insertion Sort | Sorts records by ticket number, name, or date |
| 13 | Load sample data | Adds 5 sample tickets for quick testing |
| 0 | Exit | Ends the program properly |

Ticket status flow: `Registered` → `Queued` → `Resolved`.

## Data Structure Explanation

### ArrayLists (record storage)

Five parallel `ArrayList`s hold the records. The value at index `i` in every list belongs to the **same ticket**:

```java
ArrayList<Integer> ticketNumbers;
ArrayList<String>  requesterNames;
ArrayList<String>  issues;
ArrayList<String>  datesSubmitted;   // yyyy-MM-dd
ArrayList<String>  statuses;         // Registered / Queued / Resolved
```

To keep them synchronized, records are added with one method (`addRecord`), deleted from all lists at the same index, and sorted using `swapRecords(i, j)`, which swaps the data in **all** lists together.

### Queue (support workflow)

```java
Queue<Integer> supportQueue = new LinkedList<>();
```

The queue stores **ticket numbers** only, in arrival order:

- `add()` → enqueue
- `peek()` → view the next ticket
- `poll()` → dequeue and process
- `isEmpty()` → empty check

Because the queue holds ticket numbers (not positions in the lists), sorting the records never changes the processing order. The first ticket enqueued is always the first processed (FIFO).

## Sorting Algorithm Explanation

Both algorithms sort the ticket records by the field chosen by the user and print the records **before and after** sorting.

**1. Bubble Sort** (menu 11)
Repeatedly compares neighboring records and swaps them if they are out of order. It stops early when a full pass makes no swaps. Suitable for a help desk because ticket lists are small and the algorithm is simple and easy to trace.

**2. Insertion Sort** (menu 12)
Builds a sorted section one record at a time, moving each new record backward until it sits in the correct place. Suitable because it is efficient for small or nearly sorted lists, such as a list that only receives a few new tickets at a time.

Neither algorithm uses Java's built-in sorting methods.

## How to Run the Program

Requirements: Java JDK 8 or higher.

```bash
javac Main.java
java Main
```

Tip: choose option **13** to load sample data, then try the other options.

## Screenshots of the System

### 1. Main Menu
![Main Menu](https://raw.githubusercontent.com/RanzenM/DSA-Java-Sorting-Queue-Capstone/main/main-menu.png)
*The menu-driven interface of the system.*

### 2. Adding Records and Displaying Stored Records
![Load Sample Data](https://raw.githubusercontent.com/RanzenM/DSA-Java-Sorting-Queue-Capstone/main/load-sample-data.png)
*Sample tickets are added to the system.*

![Create Ticket](https://raw.githubusercontent.com/RanzenM/DSA-Java-Sorting-Queue-Capstone/main/add-create-ticket.png)
*Creating a new ticket (#1006) with requester name, issue, and date.*

![Display Records](https://raw.githubusercontent.com/RanzenM/DSA-Java-Sorting-Queue-Capstone/main/add-display-records.png)
*Displaying all stored records, including the newly created ticket.*

### 3. Queue Operations
![Enqueue](https://raw.githubusercontent.com/RanzenM/DSA-Java-Sorting-Queue-Capstone/main/fifo-1-enqueue-1004.png)
*Enqueue: ticket #1004 is added to the support queue.*

![Peek](https://raw.githubusercontent.com/RanzenM/DSA-Java-Sorting-Queue-Capstone/main/queue-peek.png)
*Peek: the next ticket is shown without being removed.*

![Dequeue](https://raw.githubusercontent.com/RanzenM/DSA-Java-Sorting-Queue-Capstone/main/fifo-5-process-1004.png)
*Dequeue: the front ticket is processed and marked Resolved.*

![Display Queue](https://raw.githubusercontent.com/RanzenM/DSA-Java-Sorting-Queue-Capstone/main/fifo-4-queue-display.png)
*Display queue: waiting tickets listed from front to back.*

### 4. FIFO Behavior
Tickets are enqueued in the order 1004, 1002, 1005.

![Enqueue 1004](https://raw.githubusercontent.com/RanzenM/DSA-Java-Sorting-Queue-Capstone/main/fifo-1-enqueue-1004.png)
![Enqueue 1002](https://raw.githubusercontent.com/RanzenM/DSA-Java-Sorting-Queue-Capstone/main/fifo-2-enqueue-1002.png)
![Enqueue 1005](https://raw.githubusercontent.com/RanzenM/DSA-Java-Sorting-Queue-Capstone/main/fifo-3-enqueue-1005.png)
*The first ticket added gets position 1, the next position 2, then position 3.*

![Queue Before Processing](https://raw.githubusercontent.com/RanzenM/DSA-Java-Sorting-Queue-Capstone/main/fifo-4-queue-display.png)
*The queue shows the tickets in the order they were added.*

![Process 1004](https://raw.githubusercontent.com/RanzenM/DSA-Java-Sorting-Queue-Capstone/main/fifo-5-process-1004.png)
![Process 1002](https://raw.githubusercontent.com/RanzenM/DSA-Java-Sorting-Queue-Capstone/main/fifo-6-process-1002.png)
![Process 1005](https://raw.githubusercontent.com/RanzenM/DSA-Java-Sorting-Queue-Capstone/main/fifo-7-process-1005.png)
*Tickets are processed in the same order they entered: 1004, then 1002, then 1005.*

![Queue Empty](https://raw.githubusercontent.com/RanzenM/DSA-Java-Sorting-Queue-Capstone/main/fifo-8-queue-empty.png)
*After all tickets are processed, the queue reports that it is empty.*

### 5. Sorting Algorithm 1 - Bubble Sort (by date submitted)
![Bubble Sort Before](https://raw.githubusercontent.com/RanzenM/DSA-Java-Sorting-Queue-Capstone/main/bubble-1-before.png)
*Records before Bubble Sort.*

![Bubble Sort After](https://raw.githubusercontent.com/RanzenM/DSA-Java-Sorting-Queue-Capstone/main/bubble-2-after.png)
*Records after Bubble Sort, oldest date first. The queue order is not changed.*

### 6. Sorting Algorithm 2 - Insertion Sort (by requester name)
![Insertion Sort Before](https://raw.githubusercontent.com/RanzenM/DSA-Java-Sorting-Queue-Capstone/main/insertion-1-before.png)
*Records before Insertion Sort.*

![Insertion Sort After](https://raw.githubusercontent.com/RanzenM/DSA-Java-Sorting-Queue-Capstone/main/insertion-2-after.png)
*Records after Insertion Sort, sorted A-Z by requester name.*

### 7. Input Validation
![Invalid Menu Input](https://raw.githubusercontent.com/RanzenM/DSA-Java-Sorting-Queue-Capstone/main/validation-1-invalid-menu.png)
*Letters and out-of-range numbers are rejected at the menu.*

![Empty Name](https://raw.githubusercontent.com/RanzenM/DSA-Java-Sorting-Queue-Capstone/main/validation-2-empty-name.png)
*Empty input is rejected when creating a ticket.*

![Invalid Date](https://raw.githubusercontent.com/RanzenM/DSA-Java-Sorting-Queue-Capstone/main/validation-3-bad-date.png)
*A wrongly formatted date is rejected and the program asks again.*

## Repository Structure

```
DSA-Java-Sorting-Queue-Capstone/
├── Main.java
├── README.md
└── (screenshot .png files)
```
