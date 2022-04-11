package solutions;

import data.models.Metrics;
import data.models.Patient;

import java.util.concurrent.ForkJoinPool;

/**
 * TODO: A parallel implementation of CovidAnalyser using Java Fork/Join. Carefully read the assignment for detailed instructions and requirements.
 */
public class ParallelAnalyser implements CovidAnalyser  {
    final int p; // The parallelism level (i.e. max. # cores that can be used by Java Fork/Join).
    final int T; // The sequential threshold.
    final ForkJoinPool pool;

    /**
     * Creates a parallel analyser with p worker threads and a sequential cut-off T.
     * @param p The parallelism level.
     * @param T The sequential cut-off.
     */
    public ParallelAnalyser(int p, int T) {
        this.p = p;
        this.T = T;
        //Hint: Initialise the Java Fork/Join framework here as well.
        this.pool = new ForkJoinPool(p);
    }

    @Override
    public Metrics phaseOne(Patient[] patients) {
        return pool.invoke(new phaseOneTask(patients, 0, patients.length, T));
    }

    @Override
    public Long phaseTwo(Patient[] patients, Metrics metrics, long numFemales, long numICU) {
        // TODO: Implement this method using Java Fork/Join.
        return null;
    }
}
