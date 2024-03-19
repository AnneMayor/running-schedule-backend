import com.anne.batch.race.reader.RaceItemReader
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.collections.beEmpty
import io.kotest.matchers.shouldNot
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [RaceItemReader::class])
class RaceItemReaderTest : FunSpec() {
    @Autowired
    private lateinit var raceItemReader: RaceItemReader

    init {
        extensions(SpringExtension)
        test("마라톤 대회 크롤링 배치 테스트 - Reader") {
            val raceList = raceItemReader.read()
            raceList shouldNot beEmpty()
        }
    }

}