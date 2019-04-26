3 methods of counting elements of a given name in an XML file using java.


  #1 countElementsXSL(element, filePath);
  
  Uses XSLT transform sheet and returns result to system.out, whilst this requires more code than other examples, 
  it is my favourite method as it uses XSLT language.
  
  
  #2 countElementsW3cDom(element, filePath);
  
  Very short and simple method possible with w3c.dom
  
  
  #3 countElementsRegex(element, filePath);
  
  This uses a Regex match, which counts closing tags of the element.
  This is not an ideal solution as it does not account for the case where an xml is open and closed in the same brackets 
  <elementname test="test" />, which is also valid xml.
