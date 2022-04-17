package firefly.phaseTwo;

import data.Reader;
import data.models.Metrics;
import data.models.Patient;
import org.openjdk.jmh.annotations.*;
import solutions.CovidAnalyser;
import solutions.SequentialAnalyser;

import java.util.concurrent.TimeUnit;

@Fork(value = 3, warmups = 1, jvmArgs = {"-Xmx128g" } )
public class seq {
    @org.openjdk.jmh.annotations.State(Scope.Thread)
    public static class State {
        @Setup(Level.Trial)
        public void setup(){
            seq = new SequentialAnalyser();
            patients = Reader.generateData(2000000000);
            metrics = seq.phaseOne(patients);
            females = Math.round((metrics.female * 75.0) / 100.0);
        }
        public Patient[] patients;
        CovidAnalyser seq;
        public Metrics metrics;
        public long females;
        public long ICU = 2500;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public long benchmark(State pat) {
        return pat.seq.phaseTwo(pat.patients, pat.metrics, pat.females, pat.ICU);
    }
}