#!/bin/sh
lscpu > cpuspecs.txt
java -jar benchmarks.jar pc.phaseOne.seq -rf csv -rff seq.csv
java -jar benchmarks.jar pc.phaseOne.cores -rf csv -rff cores.csv
java -jar benchmarks.jar pc.phaseOne.threshold -rf csv -rff threshold.csv
