import java.util.Scanner;

class MemoryPlacementSimulation {
    private int[] memoryBlocks;
    private int[] processSizes;

    public MemoryPlacementSimulation(int[] memoryBlocks, int[] processSizes) {
        this.memoryBlocks = memoryBlocks;
        this.processSizes = processSizes;
    }

    // First Fit Memory Placement Algorithm
    public void firstFit() {
        int[] allocatedBlocks = new int[processSizes.length];
        boolean[] blockAllocated = new boolean[memoryBlocks.length];

        for (int i = 0; i < processSizes.length; i++) {
            allocatedBlocks[i] = -1; // Initialize with -1 indicating not allocated

            for (int j = 0; j < memoryBlocks.length; j++) {
                if (!blockAllocated[j] && memoryBlocks[j] >= processSizes[i]) {
                    allocatedBlocks[i] = j;
                    blockAllocated[j] = true;
                    break;
                }
            }
        }

        printAllocation("First Fit", allocatedBlocks);
    }

    // Worst Fit Memory Placement Algorithm
    public void worstFit() {
        int[] allocatedBlocks = new int[processSizes.length];
        boolean[] blockAllocated = new boolean[memoryBlocks.length];

        for (int i = 0; i < processSizes.length; i++) {
            allocatedBlocks[i] = -1; // Initialize with -1 indicating not allocated

            int worstFitBlockIndex = -1;
            int worstFitSize = -1;

            for (int j = 0; j < memoryBlocks.length; j++) {
                if (!blockAllocated[j] && memoryBlocks[j] >= processSizes[i]) {
                    if (worstFitSize == -1 || memoryBlocks[j] > worstFitSize) {
                        worstFitBlockIndex = j;
                        worstFitSize = memoryBlocks[j];
                    }
                }
            }

            if (worstFitBlockIndex != -1) {
                allocatedBlocks[i] = worstFitBlockIndex;
                blockAllocated[worstFitBlockIndex] = true;
            }
        }

        printAllocation("Worst Fit", allocatedBlocks);
    }

    private void printAllocation(String strategy, int[] allocatedBlocks) {
        System.out.println(strategy + " Memory Placement:");

        for (int i = 0; i < processSizes.length; i++) {
            if (allocatedBlocks[i] != -1) {
                System.out.println("Process " + (i + 1) + " is allocated to Memory Block " + (allocatedBlocks[i] + 1));
            } else {
                System.out.println("Process " + (i + 1) + " cannot be allocated to any Memory Block");
            }
        }

        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of memory blocks: ");
        int numBlocks = scanner.nextInt();

        int[] memoryBlocks = new int[numBlocks];
        System.out.println("Enter the sizes of memory blocks:");

        for (int i = 0; i < numBlocks; i++) {
            System.out.print("Memory Block " + (i + 1) + ": ");
            memoryBlocks[i] = scanner.nextInt();
        }

        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();

        int[] processSizes = new int[numProcesses];
        System.out.println("Enter the sizes of processes:");

        for (int i = 0; i < numProcesses; i++) {
            System.out.print("Process " + (i + 1) + ": ");
            processSizes[i] = scanner.nextInt();
        }

        MemoryPlacementSimulation simulation = new MemoryPlacementSimulation(memoryBlocks, processSizes);

        simulation.firstFit();
        simulation.worstFit();
    }
}
