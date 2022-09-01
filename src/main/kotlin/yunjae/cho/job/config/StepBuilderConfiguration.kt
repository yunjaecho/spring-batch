package yunjae.cho.job.config

import mu.KotlinLogging
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.job.builder.FlowBuilder
import org.springframework.batch.core.job.flow.Flow
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.step.builder.SimpleStepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class StepBuilderConfiguration {
    @Autowired
    private lateinit var jobBuilderFactory: JobBuilderFactory

    @Autowired
    private lateinit var stepBuilderFactory: StepBuilderFactory

    private val log = KotlinLogging.logger {}

    @Bean
    fun batchJob(): Job {
        return this.jobBuilderFactory.get("batchJob")
            .incrementer(RunIdIncrementer())
            .start(step1())
            .next(step2())
            .next(step3())
            .build()

    }

    @Bean
    fun step1(): Step {
        return this.stepBuilderFactory.get("step1")
            .tasklet { contribution, chunkContext ->
                log.info("step1 has executed")
                return@tasklet RepeatStatus.FINISHED
            }.build()
    }

    @Bean
    fun step2(): Step  {
        return this.stepBuilderFactory.get("step2")
            .chunk<String, String>(3)
            .reader {
                return@reader null
            }
            .processor(ItemProcessor<String, String> { null; })
            .writer(ItemWriter {

            })
            .build()




    }

    @Bean
    fun step3(): Step {
        return this.stepBuilderFactory.get("step3")
            .partitioner(step1())
            .gridSize(2)
            .build()
    }

    @Bean
    fun step4(): Step {
        return this.stepBuilderFactory.get("step4")
            .job(job())
            .build()
    }

    @Bean
    fun step5(): Step {
        return this.stepBuilderFactory.get("step5")
            .flow(flow())
            .build()
    }

    @Bean
    fun job(): Job {
        return this.jobBuilderFactory.get("job")
            .start(step1())
            .next(step2())
            .next(step3())
            .build()
    }

    @Bean
    fun flow(): Flow {
        val flowBuilder = FlowBuilder<Flow>("flow")
        flowBuilder.start(step2()).end()
        return flowBuilder.build()
    }

}