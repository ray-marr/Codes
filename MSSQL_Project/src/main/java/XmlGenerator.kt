class XmlGenerator {

    fun csvToXML(csv : String):String{
        var sb = StringBuilder()
        var lines: List<String> = csv.split("\n")
        var lineCounter = 0
        var colNames: MutableList<String> = ArrayList()

        sb.append("<Result>\n")
        for(line in lines){
          if (lineCounter == 0){
              colNames = line.split(",").toMutableList()
          }else{
              sb.append("\t<Row>\n")
              var rowCounter = 0
              var colValues = line.split(",").toMutableList()
              for(value in colValues){
                  sb.append("\t\t<${colNames[rowCounter]}>${value.trim()}</${colNames[rowCounter++]}>\n")
              }
              sb.append("\t</Row>\n")
          }


            lineCounter++
        }
        sb.append("</Result>")


        return sb.toString()
    }

}
