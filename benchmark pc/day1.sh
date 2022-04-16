#!/bin/sh
lscpu > cpuspecs.txt
java -jar benchmarks.jar pc.phaseOne.seq -rf csv -rff seq.csv
java -jar benchmarks.jar pc.phaseOne.threshold -rf csv -rff threshold.csv
java -jar benchmarks.jar pc.phaseOne.speedup -rf csv -rff speedup.csv

