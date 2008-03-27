package net.sf.anathema.charm.cards;
class CardXmlSamples {
  static def EMPTYLIST = '''
    <cards/>
  '''
  
  static def SINGLECARD= '''
    <cards>
      <card />
    </cards>
  '''
  
  static def MULTICARD= '''
    <cards>
      <card />
      <card />
    </cards>
  '''
  
  static def CARD_WITH_NAME= '''
    <cards>
      <card>
        <name>testId</name>
      </card>
    </cards>
  '''
  
  static def CARD_WITH_OTHER_NAME= '''
    <cards>
      <card>
        <name>toastId</name>
      </card>
    </cards>
  '''
  
 
}