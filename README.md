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

All screenshots were taken from the actual running program. They are collected in
[Screenshots-Documentation.pdf](https://github.com/RanzenM/DSA-Java-Sorting-Queue-Capstone/blob/main/screenshots/Screenshots-Documentation.pdf), where each screenshot has a caption explaining what it shows.

The PDF covers all the required screenshots:

| # | Required screenshot | What it shows |
|---|---------------------|---------------|
| 1 | Main menu | The menu-driven interface with all options |
| 2 | Adding and displaying records | Creating a ticket (#1006) and listing all stored tickets |
| 3 | Queue operations | Enqueue, peek, dequeue, and display queue |
| 4 | FIFO behavior | Tickets enqueued as 1004, 1002, 1005 are processed in that same order |
| 5 | Sorting algorithm 1 | Bubble Sort by date, records before and after |
| 6 | Sorting algorithm 2 | Insertion Sort by requester name, records before and after |
| 7 | Input validation | Invalid menu input, empty name, and wrong date format are rejected |

## Repository Structure

```
DSA-Java-Sorting-Queue-Capstone/
├── SRC/
│   └── Main.java
├── Screenshots-documentations.pdf
└── README.md
