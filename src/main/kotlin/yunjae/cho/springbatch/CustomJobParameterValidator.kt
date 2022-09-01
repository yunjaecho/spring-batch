package yunjae.cho.springbatch

import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersInvalidException
import org.springframework.batch.core.JobParametersValidator

class CustomJobParameterValidator: JobParametersValidator {
    override fun validate(parameters: JobParameters?) {
        if (parameters?.getString("name") == null) {
            throw JobParametersInvalidException("name parameters is not found")
        }
    }
}