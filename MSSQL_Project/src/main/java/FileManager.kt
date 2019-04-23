import java.io.File
import java.io.FileWriter
import java.io.IOException

class FileManager {

    fun readFileAsLines(fileName: String): List<String>
            = File(fileName).readLines()






    fun appendFile(fileName: String, sb: StringBuilder) {
        val path = fileName
        val text = sb.toString()

        try {
            val fw = FileWriter(path, true)
            fw.write(text)
            fw.close()
        } catch (e: IOException) {
        }
    }
}
