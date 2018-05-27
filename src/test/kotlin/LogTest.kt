
import org.junit.Test
import org.slf4j.LoggerFactory

class LogTest{
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @Test
    fun logMe(){
        logger.debug("aaaaaa")
    }


}