package utilities;

import java.util.function.Supplier;

public class RetryHelper {

    public static <T> T executeWithRetry(Supplier<T> action, int maxRetries, long initialDelayMillis) {
        int attempt = 0;
        while (true) {
            try {
                return action.get();  // try the API call
            } catch (Exception e) {
                attempt++;
                if (attempt > maxRetries) {
                    throw new RuntimeException("Failed after " + maxRetries + " retries", e);
                }
                long backoffTime = (long) (initialDelayMillis * Math.pow(2, attempt - 1));
                System.out.println("Attempt " + attempt + " failed. Retrying after " + backoffTime + "ms...");
                try {
                    Thread.sleep(backoffTime);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Thread interrupted during backoff", ie);
                }
            }
        }
    }
}
