package b;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {
    public void exec() {
        int n = stdin.nextInt();
        int[] f = stdin.nextIntArray(n);

        List<Tuple> tuples = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Tuple tuple = new Tuple();
            tuple.index = i;
            tuple.value = f[i];
            tuples.add(tuple);
        }
        Collections.sort(tuples, Comparator.comparingInt(tuple -> tuple.value));

        long sum = 0;
        for (int i = 0; i < n - 1; i++) {
            sum += Math.abs(tuples.get(i).index - tuples.get(i + 1).index);
        }
        stdout.println(sum);
    }

    private static class Tuple {
        private int index;
        private int value;
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