package org.example;

public class TicketBoard {
    private final Ticket[] tickets;
    private int size;

    public TicketBoard(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("capacity must be > 0");
        this.tickets = new Ticket[capacity];
        this.size = 0;
    }

    // Array-of-objects operations
    public boolean add(Ticket t) {
        if (t == null) return false;
        if (size >= tickets.length) return false;
        tickets[size++] = t;
        return true;
    }

    public int size() { return size; }

    public Ticket get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
        return tickets[index];
    }

    public void printAll() {
        for (int i = 0; i < size; i++) {
            System.out.println(tickets[i]);
        }
    }
    public void sortByUrgencyDesc() {
        if (size <= 1) return;
        Ticket[] temp = new Ticket[size];
        mergeSort(tickets, temp, 0, size);
    }

    private void mergeSort(Ticket[] arr, Ticket[] temp, int left, int rightExclusive) {
        int n = rightExclusive - left;
        if (n <= 1) return;

        int mid = left + n / 2;
        mergeSort(arr, temp, left, mid);
        mergeSort(arr, temp, mid, rightExclusive);
        merge(arr, temp, left, mid, rightExclusive);
    }

    private void merge(Ticket[] arr, Ticket[] temp, int left, int mid, int rightExclusive) {
        int i = left;      // left half pointer
        int j = mid;       // right half pointer
        int k = left;      // temp pointer

        while (i < mid && j < rightExclusive) {
            int leftScore = arr[i].urgencyScore();
            int rightScore = arr[j].urgencyScore();

            // DESCENDING by score
            // STABLE tie-break: if equal, take from LEFT half first.
            if (leftScore >= rightScore) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }

        while (i < mid) {
            temp[k++] = arr[i++];
        }
        while (j < rightExclusive) {
            temp[k++] = arr[j++];
        }

        // copy back to original array
        for (int idx = left; idx < rightExclusive; idx++) {
            arr[idx] = temp[idx];
        }
    }
    // ---------------------------------------------------------
    // TODO #4 (Algorithm Completion): Binary Search by ID
    // Precondition: tickets are sorted by ID ASCENDING.
    // Return the Ticket with matching id, or null if not found.
    // Implement iterative binary search on the array.
    // ---------------------------------------------------------
    public Ticket findByIdBinarySearch(int id) {
        // TODO #5
        int low = 0;
        int high = size - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            int midId = tickets[mid].getId();

            if (midId == id) {
                return tickets[mid];
            } else if (midId < id) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return null;
    }

    // Utility: sort by ID ascending (already implemented)
    // (Used only to prepare for binary search)
    public void sortByIdAsc() {
        // Simple insertion sort to keep the assignment focused
        for (int i = 1; i < size; i++) {
            Ticket key = tickets[i];
            int j = i - 1;
            while (j >= 0 && tickets[j].getId() > key.getId()) {
                tickets[j + 1] = tickets[j];
                j--;
            }
            tickets[j + 1] = key;
        }
    }
}