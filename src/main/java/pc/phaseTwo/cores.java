package pc.phaseTwo;

import data.Reader;
import data.models.Metrics;
import data.models.Patient;
import org.openjdk.jmh.annotations.*;
import solutions.CovidAnalyser;
import solutions.ParallelAnalyser;

import java.util.concurrent.TimeUnit;

@Fork(value = 5, warmups = 1)
public class cores {
    @org.openjdk.jmh.annotations.State(Scope.Thread)
    public static class State {
        @Param({"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"})
        private int p;
        @Setup(Level.Trial)
        public void setup(){
            patients = Reader.generateData(110000000);
            parallel = new ParallelAnalyser(p, 50); //TODO get optimal threshold
        }
        public Patient[] patients;
        public CovidAnalyser parallel;
    }

    @Benchmark @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public Metrics benchmark(State pat) { //using optimal threshold
        return pat.parallel.phaseOne(pat.patients);
    }
}
