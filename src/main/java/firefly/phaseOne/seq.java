package firefly.phaseOne;

import data.Reader;
import data.models.Metrics;
import data.models.Patient;
import org.openjdk.jmh.annotations.*;
import solutions.CovidAnalyser;
import solutions.SequentialAnalyser;

import java.util.concurrent.TimeUnit;

@Warmup(iterations = 5)
@Measurement(iterations = 15)
@Fork(value = 1, jvmArgs = {"-Xms64g","-Xmx128g" } )
public class seq {
    @org.openjdk.jmh.annotations.State(Scope.Thread)
    public static class State {
        @Setup(Level.Trial)
        public void setup(){
            patients = Reader.generateData(1100000000);
            seq = new SequentialAnalyser();
        }
        CovidAnalyser seq;
        public Patient[] patients;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public Metrics benchmark(State pat) {
        return pat.seq.phaseOne(pat.patients);
    }
}
