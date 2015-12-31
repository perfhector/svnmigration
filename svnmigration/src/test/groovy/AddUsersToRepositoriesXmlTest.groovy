/**
 *
 * @author Cedric Levasseur <celevasseur@fr.imshealth.com>
 */

import groovy.xml.*

class AddUsersToRepositoriesXmlTest extends GroovyTestCase {
    
    public void testHello(){
        println("test")
        assertTrue(true)
    }

    public void testWriteXML(){

        //def reposMap =  ['repo1':['false','user','READ']]
        def reposMap =['reponame':['user1':'WRITE', 'user2':'WRITE', 'user3':'WRITE']]
        
        def xmlContent="<?xml version=\"1.0\" ?><repositorydb><repositories><repository><name>repo1</name><permissions><permission><groupPermission>false</groupPermission></permission><name>moi</name><type>OWNER</type></permissions></repository></repositories></repositorydb>"
        
        AddUsersToRepositoriesXml clazz  = new AddUsersToRepositoriesXml();
        //String result = clazz.writeXML(reposMap, xmlContent);
        def result = clazz.wXML(reposMap, xmlContent);
        
        
        println result
        //println XmlUtil.serialize(result)
                
        
        assertTrue(result.indexOf("moi")>0)
        assertTrue(result.indexOf("user1" )>0)
        assertTrue(result.indexOf("user2" )>0)
        assertTrue(result.indexOf("user3" )>0)
        //assertFalse(result.indexOf("lui")>0)
    }
}
