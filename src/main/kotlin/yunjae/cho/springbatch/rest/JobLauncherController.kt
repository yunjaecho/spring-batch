package yunjae.cho.springbatch.rest

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class JobLauncherController(private val job: Job,
                            private val jobLauncher: JobLauncher) {

    @PostMapping("/batch")
    fun launch(@RequestBody member: Member): String {
        val jobParameter = JobParametersBuilder()
            .addString("id", member.id)
            .addDate("date", Date())
            .toJobParameters()

        jobLauncher.run(job, jobParameter)

        return "batch completed"
    }
}