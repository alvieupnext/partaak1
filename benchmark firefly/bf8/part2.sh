#!/bin/sh
java -jar benchmarks.jar firefly.phaseOne.cores -rf csv -rff cores.csv
java -jar benchmarks.jar firefly.phaseTwo.cores -rf csv -rff cores2.csv
