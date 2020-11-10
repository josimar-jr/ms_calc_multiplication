package org.josimarjr.msmulti.multiplication

fun multiplicationController(factor1: Double, factor2: Double): Map<String, Double> {
    return mapOf("result" to multiplicationService(factor1, factor2))
}