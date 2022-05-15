#!/bin/sh
lscpu > cpuspecs.txt
java -jar benchmarks.jar firefly.phaseOne.seq -rf csv -rff seq.csv
java -jar benchmarks.jar firefly.phaseOne.speedup -rf csv -rff speedup.csv
java -jar benchmarks.jar firefly.phaseOne.threshold -rf csv -rff threshold.csv
java -jar benchmarks.jar firefly.phaseTwo.seq -rf csv -rff seq2.csv
java -jar benchmarks.jar firefly.phaseTwo.speedup -rf csv -rff speedup2.csv
java -jar benchmarks.jar firefly.phaseTwo.threshold -rf csv -rff threshold2.csv

