
public class DownloadManager {

    /**
     * @param args program arguments
     * args[0] = flag (--pre-dl / --post-dl)
     * args[1] = config filename
     */
    public static void main(String[] args) {
        if(args.length != 2) {
            System.out.println("download-manager: incorrect amount of arguments");
            System.out.println("download-manager: usage: <--post-dl / --pre-dl> <config filename>");
            return;
        }

        System.out.println("download-manager: starting...");
        System.out.println();

        getRunnable(args).run();

        System.out.println();
        System.out.println("download-manager: finished");
    }

    /**
     * @param args program arguments
     * @return runnable
     */
    private static Runnable getRunnable(String[] args) {
        switch (args[0]) {
            case "--post-dl":
                return new PostDL(args[1]);
            case "--pre-dl":
                return new PreDL(args[1]);
            default:
                throw new RuntimeException("download-manager: unknown flag (" + args[0] + ")");
        }
    }
}
