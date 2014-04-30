
public class DownloadManager {

    /**
     * @param args program arguments
     */
    public static void main(String[] args) {
        if(args.length != 2 && args.length != 4 && args.length != 5) {
            System.out.println("download-manager: incorrect amount of arguments");
            System.out.println("download-manager: usage option: --post-dl <config>");
            System.out.println("download-manager: usage option: --post-dl <config> <tv-shows-config> <username> <password>");
            System.out.println("download-manager: usage option: --pre-dl [mark-acquired | mark-watched] <config> <username> <password>");
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
                    return new PostDL(args[1]).withMarkAsAcquired(args[2], args[3], args[4]);
                else
                    throw new RuntimeException("Error");
            }
            case "--pre-dl": {
                switch (args[1]) {
                    case "mark-acquired":
                        return new PreDL(args[2], args[3], args[4]).withMarkAsAcquired();
                    case "mark-watched":
                        return new PreDL(args[2], args[3], args[4]).withMarkAsWatched();
                    default:
                        return new PreDL(args[1], args[2], args[3]);
                }
            }
            default: {
                throw new RuntimeException("download-manager: unknown flag (" + args[0] + ")");
            }
        }
    }
}
