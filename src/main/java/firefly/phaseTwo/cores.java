package firefly.phaseTwo;

import data.Reader;
import data.models.Metrics;
import data.models.Patient;
import org.openjdk.jmh.annotations.*;
import solutions.CovidAnalyser;
import solutions.ParallelAnalyser;

import java.util.concurrent.TimeUnit;

@Warmup(iterations = 5)
@Measurement(iterations = 15)
@Fork(value = 1, jvmArgs = {"-Xms64g","-Xmx128g" } )
public class cores {
    @org.openjdk.jmh.annotations.State(Scope.Thread)
    public static class State {
        @Param({"1", "2", "4", "8", "16", "32", "64", "96", "128"})
        private int p;
        @Setup(Level.Trial)
        public void setup(){
            patients = Reader.generateData(1100000000);
            parallel = new ParallelAnalyser(p, 500000); //TODO get optimal threshold
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
