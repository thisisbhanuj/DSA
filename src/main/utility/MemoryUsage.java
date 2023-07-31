package utility;

public class MemoryUsage {
    public static void calculateMemoryUsage(){
        Runtime rt = Runtime.getRuntime();
        long total_mem = rt.totalMemory();
        long free_mem = rt.freeMemory();
        long used_mem = total_mem - free_mem;
        System.out.println("Amount of used memory in " + used_mem);
    }
}
