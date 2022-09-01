package yunjae.cho.job.config

import mu.KotlinLogging
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.support.ListItemReader
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.context.annotation.Bean
import java.util.*

@AutoConfiguration
class TaskletStepConfiguration(private val jobBuilderFactory: JobBuilderFactory,
                               private val stepBuilderFactory: StepBuilderFactory) {
//    @Autowired
//    private lateinit var jobBuilderFactory: JobBuilderFactory
//
//    @Autowired
//    private lateinit var stepBuilderFactory: StepBuilderFactory

    private val log = KotlinLogging.logger {}

    @Bean
    fun batchJob(): Job {
        return this.jobBuilderFactory.get("batchJob")
            .incrementer(RunIdIncrementer())
            .start(chunkStep())
            .build()

    }

    fun taskStep(): Step {
        return stepBuilderFactory.get("taskStep")
            .tasklet { contribution, chunkContext ->
                log.info("step was executed")
                return@tasklet RepeatStatus.FINISHED
            }
            .build()
    }

    fun chunkStep(): Step {
        return stepBuilderFactory.get("chunkStep")
            .chunk<String, String>(10)
            .reader(ListItemReader(listOf("item1", "item2", "item3", "item4", "item5")))
            .processor(ItemProcessor<String, String> { item -> item.uppercase(Locale.getDefault()) })
            .writer { it.forEach { data -> println(data) } }
            .build()
    }

}