import java.util.*;

class Process {
    int id;
    int arrivalTime;
    int burstTime;
    int remainingBurstTime;

    public Process(int id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingBurstTime = burstTime;
    }
}

class SchedulingAlgorithms {
    List<Process> processes;

    public SchedulingAlgorithms() {
        this.processes = new ArrayList<>();
    }

    public void addProcess(int id, int arrivalTime, int burstTime) {
        Process process = new Process(id, arrivalTime, burstTime);
        processes.add(process);
    }

    // First-Come-First-Serve (FCFS) Scheduling Algorithm
    public void fcfs() {
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime));

        printSchedule("FCFS");
    }

    // Shortest Job First (SJF) Preemptive Scheduling Algorithm
    public void sjfPreemptive() {
        List<Process> processOrder = new ArrayList<>();
        int currentTime = 0;

        while (!processes.isEmpty()) {
            Process shortestJob = getShortestJob(currentTime);
            if (shortestJob != null) {
                processOrder.add(shortestJob);
                currentTime += shortestJob.remainingBurstTime;
                shortestJob.remainingBurstTime = 0;
                processes.remove(shortestJob);
            } else {
                currentTime += 1; // No process is available, time is idling
            }
        }

        printSchedule(processOrder, "SJF Preemptive");
    }

    private Process getShortestJob(int currentTime) {
        Process shortestJob = null;
        int shortestBurstTime = Integer.MAX_VALUE;

        for (Process process : processes) {
            if (process.arrivalTime <= currentTime && process.remainingBurstTime < shortestBurstTime && process.remainingBurstTime > 0) {
                shortestJob = process;
                shortestBurstTime = process.remainingBurstTime;
            }
        }

        return shortestJob;
    }

    private void printSchedule(String algorithm) {
        System.out.println(algorithm + " Scheduling:");
        System.out.println("Process\tArrival Time\tBurst Time");
        for (Process process : processes) {
            System.out.println(process.id + "\t\t" + process.arrivalTime + "\t\t\t" + process.burstTime);
        }
        System.out.println();
    }

    private void printSchedule(List<Process> processOrder, String algorithm) {
        System.out.println(algorithm + " Scheduling:");
        System.out.println("Process\tArrival Time\tBurst Time");
        for (Process process : processOrder) {
            System.out.println(process.id + "\t\t" + process.arrivalTime + "\t\t\t" + process.burstTime);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        SchedulingAlgorithms scheduler = new SchedulingAlgorithms();

        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.println("Enter details for Process " + (i + 1) + ":");
            System.out.print("Arrival Time: ");
            int arrivalTime = scanner.nextInt();
            System.out.print("Burst Time: ");
            int burstTime = scanner.nextInt();

            scheduler.addProcess(i + 1, arrivalTime, burstTime);
        }

        scheduler.fcfs();
        scheduler.sjfPreemptive();
    }
}
