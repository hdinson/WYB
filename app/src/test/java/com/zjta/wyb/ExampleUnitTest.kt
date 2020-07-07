package com.zjta.wyb

import android.text.TextUtils
import android.text.TextUtils.substring
import com.zjta.wyb.utils.DateUtils
import com.zjta.wyb.utils.MD5
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {

        val s = "b1804afeafa54b63b37ae9d4c7e2bc9c"
        val t=  buildDynamicCode(s)
        println(t)

    }

    private fun buildDynamicCode(token: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val time = dateFormat.format(Date())
        val md5 = MD5.encode("${token}$time${"ffseafs"}")
        println("md5: $md5")

        val re = Regex("[a-zA-Z]")
        var num = re.replace(md5,"")
        println("num: $num")
        while (num.length < 8) {
            num += num
        }
        return  num
    }
}
