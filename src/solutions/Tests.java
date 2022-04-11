package solutions;


import data.Reader;
import data.models.Metrics;
import data.models.Patient;
import org.junit.Test;

import static junit.framework.TestCase.*;

/** Unit tests to help verify your implementation.
 * Do not modify these tests, and ensure that the tests pass.
 * You can add more tests if you want.
 */
public class Tests {

    @Test
    public void testPhaseOne() {
        CovidAnalyser sequential = new SequentialAnalyser();
        CovidAnalyser parallel = new ParallelAnalyser(4, 5000);

        for (int i = 1; i <= 10; i++) {
            Patient[] patients = Reader.generateData(i * 10000);
            Metrics mseq = sequential.phaseOne(patients);
            Metrics mpar = parallel.phaseOne(patients);

            assertEquals(mseq.labConfirmed, mpar.labConfirmed);
            assertEquals(mseq.male, mpar.male);
            assertEquals(mseq.female, mpar.female);
            assertEquals(mseq.aged, mpar.aged);
            assertEquals(mseq.hospitalised, mpar.hospitalised);
            assertEquals(mseq.intensiveCare, mpar.intensiveCare);
            assertEquals(mseq.deceased, mpar.deceased);
            assertEquals(mseq.comorbidities, mpar.comorbidities);
        }
    }

    @Test
    public void testPhaseTwo() {
        CovidAnalyser sequential = new SequentialAnalyser();
        CovidAnalyser parallel = new ParallelAnalyser(4, 5000);
        Patient[] patients = Reader.generateData(100000);

        Metrics mseq = sequential.phaseOne(patients);

        for (int i = 1; i <= 5; i++) {
            Long dseq = sequential.phaseTwo(patients, mseq, i * 10000, i * 500);
            Long dpar = parallel.phaseTwo(patients, mseq, i * 10000, i * 500);

            assertEquals(dseq, dpar);
        }
    }
}
