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
  
  static def NAMED_CARD= '''
    <cards>
      <card>
        <name>testId</name>
      </card>
    </cards>
  '''
}