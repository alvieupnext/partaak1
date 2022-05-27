package pc.phaseTwo;

import data.Reader;
import data.models.Metrics;
import data.models.Patient;
import org.openjdk.jmh.annotations.*;
import solutions.CovidAnalyser;
import solutions.ParallelAnalyser;

import java.util.concurrent.TimeUnit;

// netid: avargasg

@Fork(value = 5, warmups = 1)
public class threshold {
    @org.openjdk.jmh.annotations.State(Scope.Thread)
    public static class State {
        @Param({"100", "1000", "2500", "5000", "7500", "10000", "25000", "50000", "75000", "100000", "250000", "500000", "750000", "1000000", "2500000", "5000000", "10000000"})
        private int T;

        @Setup(Level.Trial)
        public void setup(){
            parallel = new ParallelAnalyser(1, T);
            patients = Reader.generateData(110000000);
            metrics = parallel.phaseOne(patients);
            females = Math.round((metrics.female * 75.0) / 100.0);
        }
        public Patient[] patients;
        public Metrics metrics;
        public long females;
        public long ICU = 2500;
        public CovidAnalyser parallel;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public long benchmark(State pat) {
        return pat.parallel.phaseTwo(pat.patients, pat.metrics, pat.females, pat.ICU);
    }
}