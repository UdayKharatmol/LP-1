import java.util.*;

class PagingSimulation {
    private int pageFrameCapacity;
    private List<Integer> pageReferenceSequence;

    public PagingSimulation(int pageFrameCapacity, List<Integer> pageReferenceSequence) {
        this.pageFrameCapacity = pageFrameCapacity;
        this.pageReferenceSequence = pageReferenceSequence;
    }

    // First In Last Out (FIFO) Page Replacement Algorithm
    public int simulateFIFO() {
        Set<Integer> pageFrames = new LinkedHashSet<>();
        Queue<Integer> fifoQueue = new LinkedList<>();
        int pageFaultCount = 0;

        for (int page : pageReferenceSequence) {
            if (!pageFrames.contains(page)) {
                pageFaultCount++;

                if (pageFrames.size() == pageFrameCapacity) {
                    int removedPage = fifoQueue.poll();
                    pageFrames.remove(removedPage);
                }

                pageFrames.add(page);
                fifoQueue.offer(page);
            }
        }

        return pageFaultCount;
    }

    // Least Recently Used (LRU) Page Replacement Algorithm
    public int simulateLRU() {
        Set<Integer> pageFrames = new LinkedHashSet<>();
        Map<Integer, Integer> pageLastUsedTime = new HashMap<>();
        int time = 0;
        int pageFaultCount = 0;

        for (int page : pageReferenceSequence) {
            time++;

            if (!pageFrames.contains(page)) {
                pageFaultCount++;

                if (pageFrames.size() == pageFrameCapacity) {
                    int leastRecentlyUsedPage = Collections.min(pageLastUsedTime.entrySet(), Map.Entry.comparingByValue()).getKey();
                    pageFrames.remove(leastRecentlyUsedPage);
                    pageLastUsedTime.remove(leastRecentlyUsedPage);
                }

                pageFrames.add(page);
            }

            pageLastUsedTime.put(page, time);
        }

        return pageFaultCount;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of page frames: ");
        int pageFrameCapacity = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter the page reference sequence (space-separated): ");
        String[] inputSequence = scanner.nextLine().split(" ");
        List<Integer> pageReferenceSequence = Arrays.stream(inputSequence)
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        PagingSimulation pagingSimulation = new PagingSimulation(pageFrameCapacity, pageReferenceSequence);

        int fifoPageFaults = pagingSimulation.simulateFIFO();
        System.out.println("FIFO Page Replacement Algorithm - Page Faults: " + fifoPageFaults);

        int lruPageFaults = pagingSimulation.simulateLRU();
        System.out.println("LRU Page Replacement Algorithm - Page Faults: " + lruPageFaults);
    }
}
