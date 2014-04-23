
public class DownloadManager {

    /**
     * @param args program arguments
     * args[0] = flag (--pre-dl / --post-dl)
     * args[1] = config filename
     */
    public static void main(String[] args) {
        if(args.length != 2 && args.length != 5) {
            System.out.println("download-manager: incorrect amount of arguments");
            System.out.println("download-manager: usage: <--post-dl / --pre-dl> <config> [<tv-shows config> <username> <password>]");
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
            case "--post-dl": {
                if (args.length == 2)
                    return new PostDL(args[1]);
                else if (args.length == 5)
                    return new PostDL(args[1], args[2], args[3], args[4]);
                else
                    throw new RuntimeException("Error");
            }
            case "--pre-dl":
                return new PreDL(args[1], args[2], args[3], args[4]);
            default:
                throw new RuntimeException("download-manager: unknown flag (" + args[0] + ")");
        }
    }
}
