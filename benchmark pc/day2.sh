#!/bin/sh
lscpu > cpuspecs.txt
java -jar benchmarks.jar pc.phaseOne.cores -rf csv -rff cores.csv
java -jar benchmarks.jar pc.phaseTwo.seq -rf csv -rff seq2.csv
java -jar benchmarks.jar pc.phaseTwo.threshold -rf csv -rff threshold2.csv
java -jar benchmarks.jar pc.phaseTwo.speedup -rf csv -rff speedup2.csv

