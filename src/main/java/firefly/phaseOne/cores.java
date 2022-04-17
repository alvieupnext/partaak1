/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package firefly.phaseOne;

import data.Reader;
import data.models.Metrics;
import data.models.Patient;
import org.openjdk.jmh.annotations.*;
import solutions.CovidAnalyser;
import solutions.ParallelAnalyser;

import java.util.concurrent.TimeUnit;

@Fork(value = 3, warmups = 1, jvmArgs = {"-Xmx128g" } )
public class cores {

    @org.openjdk.jmh.annotations.State(Scope.Thread)
    public static class State {
        @Param({"1", "2", "4", "8", "16", "32", "64", "96", "128"})
        private int p;
        @Setup(Level.Trial)
        public void setup(){
            patients = Reader.generateData(2000000000);
            parallel = new ParallelAnalyser(p, 750000); //TODO get firefly sequential threshold
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
