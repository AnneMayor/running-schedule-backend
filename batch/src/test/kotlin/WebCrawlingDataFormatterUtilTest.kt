import com.anne.batch.common.util.WebCrawlingDataFormatterUtil.formatHostInfo
import com.anne.batch.common.util.WebCrawlingDataFormatterUtil.formatPlace
import com.anne.batch.common.util.WebCrawlingDataFormatterUtil.formatRaceDate
import com.anne.batch.common.util.WebCrawlingDataFormatterUtil.formatRaceNameWithDistance
import com.anne.domain.common.util.CrawlerUtil
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.time.Instant

class WebCrawlingDataFormatterUtilTest : ShouldSpec({
    context("WebCrawlingDataFormatterUtil") {
        should("formatRaceDate") {
            val raceDocument =
                CrawlerUtil.getDocument("http://www.roadrun.co.kr/schedule/list.php?today=1710665734&todays=Y")

            val result = formatRaceDate(raceDocument)
            result shouldNotBe null
        }

        should("formatRaceNameWithDistance") {
            val raceDocument =
                CrawlerUtil.getDocument("http://www.roadrun.co.kr/schedule/list.php?today=1710665734&todays=Y")

            val result = formatRaceNameWithDistance(raceDocument)
            result shouldNotBe null
        }

        should("formatPlace") {
            val raceDocument =
                CrawlerUtil.getDocument("http://www.roadrun.co.kr/schedule/list.php?today=1710665734&todays=Y")

            val result = formatPlace(raceDocument)
            result shouldNotBe null
        }

        should("formatHostInfo") {
            val raceDocument =
                CrawlerUtil.getDocument("http://www.roadrun.co.kr/schedule/list.php?today=1710665734&todays=Y")

            val hostInfoElements =
                CrawlerUtil.selectElements(raceDocument, "td[width=30%]").select("div[align=right][valign=bottom]")

            val result = formatHostInfo(hostInfoElements)
            result shouldNotBe null
        }
    }
})
