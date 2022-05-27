package pc.phaseOne;

import data.Reader;
import data.models.Metrics;
import data.models.Patient;
import org.openjdk.jmh.annotations.*;
import solutions.CovidAnalyser;
import solutions.SequentialAnalyser;

import java.util.concurrent.TimeUnit;

// netid: avargasg

@Fork(value = 5, warmups = 1)
public class seq {
    @org.openjdk.jmh.annotations.State(Scope.Thread)
    public static class State {
        @Setup(Level.Trial)
        public void setup(){
            patients = Reader.generateData(110000000);
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
