import java.util.*;

class PageReplacementSimulation {
    private int pageFrameCapacity;
    private List<Integer> pageReferenceSequence;

    public PageReplacementSimulation(int pageFrameCapacity, List<Integer> pageReferenceSequence) {
        this.pageFrameCapacity = pageFrameCapacity;
        this.pageReferenceSequence = pageReferenceSequence;
    }

    // Last In First Out (LIFO) Page Replacement Algorithm
    public int simulateLIFO() {
        Deque<Integer> pageFrames = new ArrayDeque<>(pageFrameCapacity);
        int pageFaultCount = 0;

        for (int page : pageReferenceSequence) {
            if (!pageFrames.contains(page)) {
                pageFaultCount++;

                if (pageFrames.size() == pageFrameCapacity) {
                    pageFrames.pollLast();
                }

                pageFrames.push(page);
            }
        }

        return pageFaultCount;
    }

    // Optimal Page Replacement Algorithm
    public int simulateOptimal() {
        List<Integer> pageFrames = new ArrayList<>(pageFrameCapacity);
        int pageFaultCount = 0;

        for (int page : pageReferenceSequence) {
            if (!pageFrames.contains(page)) {
                pageFaultCount++;

                if (pageFrames.size() < pageFrameCapacity) {
                    pageFrames.add(page);
                } else {
                    int indexToReplace = getOptimalReplacementIndex(pageFrames, pageReferenceSequence);
                    pageFrames.set(indexToReplace, page);
                }
            }
        }

        return pageFaultCount;
    }

    private int getOptimalReplacementIndex(List<Integer> pageFrames, List<Integer> pageReferenceSequence) {
        int farthestOccurrence = -1;
        int indexToReplace = -1;

        for (int i = 0; i < pageFrames.size(); i++) {
            int page = pageFrames.get(i);
            int nextOccurrence = pageReferenceSequence.indexOf(page);
            
            if (nextOccurrence == -1) {
                return i;
            }

            if (nextOccurrence > farthestOccurrence) {
                farthestOccurrence = nextOccurrence;
                indexToReplace = i;
            }
        }

        return indexToReplace;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of page frames: ");
        int pageFrameCapacity = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        System.out.print("Enter the length of the page reference sequence: ");
        int sequenceLength = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        List<Integer> pageReferenceSequence = new ArrayList<>();
        System.out.print("Enter the page reference sequence (space-separated): ");
        String[] inputSequence = scanner.nextLine().split(" ");
        for (String pageStr : inputSequence) {
            pageReferenceSequence.add(Integer.parseInt(pageStr));
        }

        PageReplacementSimulation simulation = new PageReplacementSimulation(pageFrameCapacity, pageReferenceSequence);

        int lifoPageFaults = simulation.simulateLIFO();
        System.out.println("LIFO Page Replacement Algorithm - Page Faults: " + lifoPageFaults);

        int optimalPageFaults = simulation.simulateOptimal();
        System.out.println("Optimal Page Replacement Algorithm - Page Faults: " + optimalPageFaults);
    }
}
