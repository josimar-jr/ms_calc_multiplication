package org.josimarjr.msmulti.multiplication

fun multiplicationController(factor1: Double, factor2: Double): Map<String, String> {
    return mapOf("result" to multiplicationService(factor1, factor2).toString())
}