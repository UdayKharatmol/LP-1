import java.util.*;

class PageSimulator {
    private int[] pageReferenceSequence;
    private int numFrames;
    private LinkedList<Integer> frameList;

    public PageSimulator(int[] pageReferenceSequence, int numFrames) {
        this.pageReferenceSequence = pageReferenceSequence;
        this.numFrames = numFrames;
        this.frameList = new LinkedList<>();
    }

    public void simulateFIFO() {
        System.out.println("FIFO Page Replacement Algorithm Simulation:");
        int pageFaults = 0;

        for (int page : pageReferenceSequence) {
            if (!frameList.contains(page)) {
                if (frameList.size() < numFrames) {
                    frameList.add(page);
                } else {
                    int removedPage = frameList.poll();
                    frameList.add(page);
                    System.out.println("Page fault: Replace " + removedPage + " with " + page);
                    pageFaults++;
                }
            }
        }

        System.out.println("Total page faults using FIFO: " + pageFaults);
    }

    public void simulateLRU() {
        System.out.println("\nLRU Page Replacement Algorithm Simulation:");
        int pageFaults = 0;
        LinkedHashMap<Integer, Integer> pageLastUsedMap = new LinkedHashMap<>(numFrames, 0.75f, true);

        for (int page : pageReferenceSequence) {
            if (!pageLastUsedMap.containsKey(page)) {
                if (pageLastUsedMap.size() < numFrames) {
                    pageLastUsedMap.put(page, 0);
                } else {
                    int leastRecentlyUsedPage = Collections.min(pageLastUsedMap.entrySet(), Map.Entry.comparingByValue()).getKey();
                    pageLastUsedMap.remove(leastRecentlyUsedPage);
                    pageLastUsedMap.put(page, 0);
                    System.out.println("Page fault: Replace " + leastRecentlyUsedPage + " with " + page);
                    pageFaults++;
                }
            }

            // Update the last used time of the current page
            for (int key : pageLastUsedMap.keySet()) {
                pageLastUsedMap.put(key, pageLastUsedMap.get(key) + 1);
            }
            pageLastUsedMap.put(page, 0);
        }

        System.out.println("Total page faults using LRU: " + pageFaults);
    }
}

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input: Number of frames
        System.out.print("Enter the number of frames: ");
        int numFrames = scanner.nextInt();

        // Input: Page reference sequence
        System.out.print("Enter the page reference sequence (space-separated): ");
        scanner.nextLine(); // Consume the newline character
        String[] inputSequence = scanner.nextLine().split(" ");
        int[] pageReferenceSequence = Arrays.stream(inputSequence).mapToInt(Integer::parseInt).toArray();

        // Create PageSimulator object
        PageSimulator pageSimulator = new PageSimulator(pageReferenceSequence, numFrames);

        // Simulate FIFO
        pageSimulator.simulateFIFO();

        // Simulate LRU
        pageSimulator.simulateLRU();
    }
}
