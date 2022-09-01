package yunjae.cho.job.config

import mu.KotlinLogging
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.stereotype.Component

@Component
class JobRepositoryListener(private val jobRepository: JobRepository): JobExecutionListener {

    private val log = KotlinLogging.logger {}

    override fun beforeJob(jobExecution: JobExecution) {
        TODO("Not yet implemented")
    }

    override fun afterJob(jobExecution: JobExecution) {
        val jobName = jobExecution.jobInstance.jobName

        val toJobParameters = JobParametersBuilder().addString("requestDate", "20210102").toJobParameters()

        val lastJobExecution = jobRepository.getLastJobExecution(jobName, toJobParameters)

        lastJobExecution?.let {
            lastJobExecution.stepExecutions.forEach {
                val status = it.status
                val stepName = it.stepName

                log.info("status : {}" , status)
                log.info("stepName : {}",  stepName)

            }
        }


    }
}