package yunjae.cho.job.config

import mu.KotlinLogging
import org.springframework.batch.core.*
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.job.DefaultJobParametersValidator
import org.springframework.batch.core.job.builder.FlowBuilder
import org.springframework.batch.core.job.flow.Flow
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.text.SimpleDateFormat
import java.util.*


@Configuration
class JobConfiguration() {

    @Autowired
    private lateinit var jobBuilderFactory: JobBuilderFactory

    @Autowired
    private lateinit var stepBuilderFactory: StepBuilderFactory

    private val log = KotlinLogging.logger {}
    val format = SimpleDateFormat("yyyyMMdd-hhmmss")

    @Bean
    fun batchJob(): Job {
        return jobBuilderFactory.get("batchJob")
            .start(step1())
            .next(step2())
            .next(step3())
//            .validator(CustomJobParameterValidator())
//            .validator { parameter ->
//                if (parameter?.getString("name") == null) {
//                    throw JobParametersInvalidException("name parameters is not found")
//                }
//            }
//            .validator(DefaultJobParametersValidator(arrayOf("name", "date"), arrayOf("count")))
            .incrementer { _ ->
                val id: String = format.format(Date())
                return@incrementer JobParametersBuilder().addString("run.id", id).toJobParameters()
            }
            .build();

    }



    @Bean
    fun step1(): Step {
        return stepBuilderFactory
            .get("step1")
            .tasklet { contribution, chunkContext ->
                log.info("step1 has executed")
                return@tasklet RepeatStatus.FINISHED
            }.build()

    }

    @Bean
    fun step2(): Step {
        return stepBuilderFactory
            .get("step2")
            .tasklet { contribution, chunkContext ->
                log.info("step2 has executed")
                return@tasklet RepeatStatus.FINISHED
            }.build()

    }

    @Bean
    fun step3(): Step {
        return stepBuilderFactory
            .get("step3")
            .tasklet { contribution, chunkContext ->
                chunkContext.stepContext.stepExecution.status = BatchStatus.FAILED
                contribution.exitStatus = ExitStatus.STOPPED
                log.info("step3 has executed")
                return@tasklet RepeatStatus.FINISHED
            }.build()

    }


}