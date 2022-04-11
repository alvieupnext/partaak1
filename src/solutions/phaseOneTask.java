package solutions;

import data.models.*;

import java.util.concurrent.RecursiveTask;

public class phaseOneTask extends RecursiveTask<Metrics> {
    Patient[] patients;
    int lo;
    int hi;
    int T; //sequential cutoff

    public phaseOneTask(Patient[] patients, int lo, int hi, int T) {
        this.patients = patients;
        this.lo = lo;
        this.hi = hi;
        this.T = T;
    }

    @Override
    protected Metrics compute() {
        if (hi - lo <= T){ //Sequential cut-off
            long labConfirmed = 0; //create counters to store result
            long male = 0;
            long female = 0;
            long aged = 0;
            long hospitalised = 0;
            long icu = 0;
            long deceased = 0;
            long comorbidities = 0;
            for (int i = lo; lo < hi; i++) { //increment counters when condition met (only the patients from low to high)
                Patient patient = patients[i];
                if (patient.status == Status.LAB_CONFIRMED) labConfirmed += 1;
                if (patient.sex == Sex.MALE) male += 1;
                else if (patient.sex == Sex.FEMALE) female += 1;
                if (patient.age == AgeGroup.SIXTIES || patient.age == AgeGroup.SEVENTIES || patient.age == AgeGroup.OVER_EIGHTY) aged += 1;
                if (patient.hospitalised) hospitalised += 1;
                if (patient.icu) icu += 1;
                if (patient.deceased) deceased += 1;
                if (patient.comorbidities) comorbidities += 1;
            } //return as a new Metrics object
            return new Metrics(labConfirmed, male, female, aged, hospitalised, icu, deceased, comorbidities);
        }
        else {
            int mid = (lo + hi)/2 ; //get middle
            phaseOneTask left = new phaseOneTask(patients, lo, mid, T); //create left and right task
            phaseOneTask right = new phaseOneTask(patients, mid, hi, T);
            left.fork(); //fork the left task (creating a new thread)
            Metrics rightMetric = right.compute(); //do this computation ourselves
            Metrics leftMetric= left.join(); //wait on the left task to complete
            return leftMetric.merge(rightMetric);
        }
    }
}
