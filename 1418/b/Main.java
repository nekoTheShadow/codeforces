package _1418.b;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public void exec() {
        int t = stdin.nextInt();
        while (t-- > 0) {
            int n = stdin.nextInt();
            int[] a = stdin.nextIntArray(n);
            int[] l = stdin.nextIntArray(n);

            int[] b = IntStream.range(0, n).filter(i -> l[i]==0).map(i -> a[i]).sorted().toArray();
            ArrayUtils.reverse(b);
            int x = 0;
            for (int i = 0; i < n; i++) {
                if (l[i] == 0) a[i] = b[x++];
            }

            String ans = Arrays.stream(a).mapToObj(String::valueOf).collect(Collectors.joining(" "));
            stdout.println(ans);
        }
    }

    public static class ArrayUtils {
        private ArrayUtils() {}

        public static void reverse(int[] a) {
            for (int i = 0, n = a.length; i < n/2; i++) {
                int t = a[i];
                a[i] = a[n-i-1];
                a[n-i-1] = t;
            }
        }

        public static void reverse(long[] a) {
            for (int i = 0, n = a.length; i < n/2; i++) {
                long t = a[i];
                a[i] = a[n-i-1];
                a[n-i-1] = t;
            }
        }

        public static <T> void reverse(T[] a) {
            for (int i = 0, n = a.length; i < n/2; i++) {
                T t = a[i];
                a[i] = a[n-i-1];
                a[n-i-1] = t;
            }
        }

        public static int bisectLeft(int[] a, int x) {
            int ng = -1;
            int ok = a.length;
            while (Math.abs(ok-ng) > 1) {
                int mi = (ok+ng)/2;
                if (a[mi] >= x) {
                    ok = mi;
                } else {
                    ng = mi;
                }
            }
            return ok;
        }

        public static int bisectRight(int[] a, int x) {
            int ng = -1;
            int ok = a.length;
            while (Math.abs(ok-ng) > 1) {
                int mi = (ok+ng)/2;
                if (a[mi] > x) {
                    ok = mi;
                } else {
                    ng = mi;
                }
            }
            return ok;
        }

        public static int bisectLeft(long[] a, long x) {
            int ng = -1;
            int ok = a.length;
            while (Math.abs(ok-ng) > 1) {
                int mi = (ok+ng)/2;
                if (a[mi] >= x) {
                    ok = mi;
                } else {
                    ng = mi;
                }
            }
            return ok;
        }

        public static int bisectRight(long[] a, long x) {
            int ng = -1;
            int ok = a.length;
            while (Math.abs(ok-ng) > 1) {
                int mi = (ok+ng)/2;
                if (a[mi] > x) {
                    ok = mi;
                } else {
                    ng = mi;
                }
            }
            return ok;
        }

        public static <T> int bisectLeft(List<? extends Comparable<? super T>> a, T x) {
            int ng = -1;
            int ok = a.size();
            while (Math.abs(ok-ng) > 1) {
                int mi = (ok+ng)/2;
                if (a.get(mi).compareTo(x) >= 0) {
                    ok = mi;
                } else {
                    ng = mi;
                }
            }
            return ok;
        }

        public static <T> int bisectRight(List<? extends Comparable<? super T>> a, T x) {
            int ng = -1;
            int ok = a.size();
            while (Math.abs(ok-ng) > 1) {
                int mi = (ok+ng)/2;
                if (a.get(mi).compareTo(x) > 0) {
                    ok = mi;
                } else {
                    ng = mi;
                }
            }
            return ok;
        }
    }

    private static final Stdin stdin = new Stdin();
    private static final Stdout stdout = new Stdout();

    public static void main(String[] args) {
        try {
            new Main().exec();
        } finally {
            stdout.flush();
        }
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
                    if (line == null) {
                        throw new UncheckedIOException(new EOFException());
                    }
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
            String line = String.format(format, args);
            if (line.endsWith(System.lineSeparator())) {
                stdout.print(line);
            } else {
                stdout.println(line);
            }
        }

        public void println(Object ... objs) {
            String line = Arrays.stream(objs).map(Objects::toString).collect(Collectors.joining(" "));
            stdout.println(line);
        }

        public void printDebug(Object ... objs) {
            String line = Arrays.stream(objs).map(this::deepToString).collect(Collectors.joining(" "));
            stdout.printf("DEBUG: %s%n", line);
            stdout.flush();
        }

        private String deepToString(Object o) {
            if (o == null) {
                return "null";
            }

            // 配列の場合
            if (o.getClass().isArray()) {
                int len = Array.getLength(o);
                String[] tokens = new String[len];
                for (int i = 0; i < len; i++) {
                    tokens[i] = deepToString(Array.get(o, i));
                }
                return "{" + String.join(",", tokens) + "}";
            }

            return Objects.toString(o);
        }

        private void flush() {
            stdout.flush();
        }
    }
}