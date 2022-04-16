#!/bin/sh
lscpu > cpuspecs.txt
java -jar benchmarks.jar pc.phaseTwo.cores -rf csv -rff cores2.csv

