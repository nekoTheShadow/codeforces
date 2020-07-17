package d;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Main {
    public void exec() {
        int t = stdin.nextInt();
        for (int i = 0; i < t; i++) {
            int n = stdin.nextInt();
            long[] a = stdin.nextLongArray(n);
            stdout.println(solve(n, a));
        }
    }

    public long solve(int n, long[] a) {
        long sum = IntStream.iterate(0, x -> x < n, x -> x+2).mapToLong(x -> a[x]).sum();
        long[] p = IntStream.iterate(0, x -> x < n-1, x -> x+2).mapToLong(x -> a[x+1]-a[x]).toArray();
        long[] q = IntStream.iterate(1, x -> x < n-1, x -> x+2).mapToLong(x -> a[x]-a[x+1]).toArray();

        MaximumSubarray s = MaximumSubarray.solve(p);
        MaximumSubarray t = MaximumSubarray.solve(q);
        return sum + LongStream.of(0, s.getSum(), t.getSum()).max().getAsLong();
    }

    public static class MaximumSubarray {
        private long sum;
        private int start;
        private int end;

        public static MaximumSubarray solve(long[] array) {
            long bestSum = 0;
            long currentSum = 0;

            int currentStart = 0;
            int bestStart = -1;
            int bestEnd = -1;

            for (int currentEnd = 0; currentEnd < array.length; currentEnd++) {
                if (currentSum < 0) {
                    currentStart = currentEnd;
                    currentSum = array[currentEnd];
                } else {
                    currentSum += array[currentEnd];
                }

                if (currentSum > bestSum) {
                    bestSum = currentSum;
                    bestStart = currentStart;
                    bestEnd = currentEnd;
                }
            }

            MaximumSubarray maximumSubarray = new MaximumSubarray();
            maximumSubarray.sum = bestSum;
            maximumSubarray.start = bestStart;
            maximumSubarray.end = bestEnd;
            return maximumSubarray;
        }

        public long getSum() {
            return sum;
        }

        public int getStart() {
            return start;
        }

        public int getEnd() {
            return end;
        }
    }

    private static final Stdin stdin = new Stdin();
    private static final Stdout stdout = new Stdout();

    public static void main(String[] args) {
        new Main().exec();
        stdout.flush();
    }

    public static class Stdin {
        private BufferedReader stdin;
        private Deque<String> tokens;
        private Pattern delim;

        public Stdin() {
            stdin = new BufferedReader(new InputStreamReader(System.in));
            tokens = new ArrayDeque<>();
            delim = Pattern.compile(" ");
        }

        public String nextString() {
            try {
                if (tokens.isEmpty()) {
                    String line = stdin.readLine();
                    delim.splitAsStream(line).forEach(tokens::addLast);
                }
                return tokens.pollFirst();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        public int nextInt() {
            return Integer.parseInt(nextString());
        }

        public double nextDouble() {
            return Double.parseDouble(nextString());
        }

        public long nextLong() {
            return Long.parseLong(nextString());
        }

        public String[] nextStringArray(int n) {
            String[] a = new String[n];
            for (int i = 0; i < n; i++) a[i] = nextString();
            return a;
        }

        public int[] nextIntArray(int n) {
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = nextInt();
            return a;
        }

        public double[] nextDoubleArray(int n) {
            double[] a = new double[n];
            for (int i = 0; i < n; i++) a[i] = nextDouble();
            return a;
        }

        public long[] nextLongArray(int n) {
            long[] a = new long[n];
            for (int i = 0; i < n; i++) a[i] = nextLong();
            return a;
        }
    }

    public static class Stdout {
        private PrintWriter stdout;

        public Stdout() {
            stdout =  new PrintWriter(System.out, false);
        }

        public void printf(String format, Object ... args) {
            stdout.printf(format, args);
        }

        public void println(Object ... objs) {
            String line = Arrays.stream(objs).map(this::deepToString).collect(Collectors.joining(" "));
            stdout.println(line);
        }

        private String deepToString(Object o) {
            if (o == null || !o.getClass().isArray()) {
                return Objects.toString(o);
            }

            int len = Array.getLength(o);
            String[] tokens = new String[len];
            for (int i = 0; i < len; i++) {
                tokens[i] = deepToString(Array.get(o, i));
            }
            return "{" + String.join(",", tokens) + "}";
        }

        public void flush() {
            stdout.flush();
        }
    }
}