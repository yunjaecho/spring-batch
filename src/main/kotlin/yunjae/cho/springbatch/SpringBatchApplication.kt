package yunjae.cho.springbatch

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import
import yunjae.cho.job.config.JobLauncherConfiguration
import yunjae.cho.job.config.StepBuilderConfiguration
import yunjae.cho.job.config.TaskletStepConfiguration

@Import(JobLauncherConfiguration::class)
@EnableBatchProcessing
@SpringBootApplication
class SpringBatchApplication

fun main(args: Array<String>) {
    runApplication<SpringBatchApplication>(*args)
}
