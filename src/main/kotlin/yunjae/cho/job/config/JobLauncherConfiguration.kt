package yunjae.cho.job.config

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JobLauncherConfiguration(private val jobBuilderFactory: JobBuilderFactory,
                               private val stepBuilderFactory: StepBuilderFactory,
                               private val jobRepositoryListener: JobRepositoryListener
) {

    @Bean
    fun batchJob(): Job {
        return this.jobBuilderFactory.get("Job")
            .start(step1())
            .next(step2())
            .listener(jobRepositoryListener)
            .build()

    }

    @Bean
    fun step1(): Step {
        return stepBuilderFactory.get("step1")
            .tasklet { contribution, chunkContext ->
                return@tasklet RepeatStatus.FINISHED
            }
            .build()
    }


    @Bean
    fun step2(): Step {
        return stepBuilderFactory.get("step2")
            .tasklet { contribution, chunkContext ->
                return@tasklet RepeatStatus.FINISHED
            }
            .build()
    }


}