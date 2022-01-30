package br.com.zumbolovsky.fateapp.service

@Retention
@Target(AnnotationTarget.FUNCTION)
annotation class Timeout(val value: Long = 5000L)
