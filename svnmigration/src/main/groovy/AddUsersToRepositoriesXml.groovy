/* be aware it rewrites the whole xml file will be lost */

import groovy.xml.*;


public class AddUsersToRepositoriesXml{
    

    public String wXML(reposMap, xmlFileContent){
        
        def xmlParsed = new XmlSlurper(false, true).parseText( xmlFileContent )
        println reposMap.first
        println "~~~~~~~~~~"
        reposMap.each { login, perm ->
            println login
            println perm
            perm.each() { k,v ->
                println "k=$k,v=$v"
                def fragmentToAdd =   { 
                    permission {
                        name(k)
                        groupPermission("false")
                        type(v)
                    }
                }
                //println XmlUtil.serialize(fragmentToAdd)
                //xmlParsed.find { it.name() == 'permissions' }.children().add( 0, fragmentToAdd )
                xmlParsed.repositories.repository.permissions.appendNode( fragmentToAdd )
            }
        }    
        println XmlUtil.serialize(xmlParsed)
        println "^^^^^"
        return XmlUtil.serialize(xmlParsed)
        
//        println XmlUtil.serialize( xmlParsed )
//        
//        def newPerm = {
//            name("cedric")
//            groupPermission("false")
//            type("OWNER")
//        }
//        
//        xmlParsed.repositorydb.repository.appendNode{ 
//            newPerm()
//        }
//        
//        return XmlUtil.serialize(xmlParsed)
//        
    }
    
    
    public String writeXML(reposMap, xmlFileContent){
	
        def parser= new XmlParser();
        def xml = parser().parseText(xmlFileContent)
        
        def newPerm = xml.appendNode{  permissions(){  
                    name("cedric")
                    groupPermission("false")
                    type("OWNER")
        }}

        
        //def xml = new groovy.xml.MarkupBuilder()
//"<?xml version=\"1.0\" encoding=\"UTF-8\"?><repository-db>  <repositories>    <repository>      <name>repo1</name>      <permissions>        <groupPermission>false</groupPermission>        <name>moi</name>        <type>OWNER</type>      </permissions>      <permissions>        <name>toi</name>        <groupPermission>false</groupPermission>        <type>READ</type>     </permissions></repository></repositories></repository-db>")

//        xml.repository-db.repositories.repository.name("repo1").permissions {
//
  
//        }
//            
    
//        def permissionBuilder= new NodeBuilder()
//        
//        def permissionNode= permissionBuilder.permissions {
//                    name("cedric")
//                    groupPermission("false")
//                    type("OWNER")
//        }
//        
//        xml.append(permissionNode)
//        
//        return xml;
//        
/*        
        xml.repositories.repository.each{
            println "***"+it.name.text()+"***"
            if(reposMap.containsKey(it.name.text())){
                
                it.appendNode{
                    name("cedric")
                    groupPermission("false")
                    type("OWNER")
                }
  
        */
        
//                def aRepoMap = reposMap.subMap([it.name.text()])
//                def newPerm = new Node(it, 'permissions')
//                def newName = new Node (newPerm, 'name', aRepoMap[1] ) 
//                def groupPermission = new Node (newPerm, aRepoMap[0])
//                def newType = new Node (newPerm, 'type', aRepoMap[2])
                
         /*   } 
        }
        return xml*/
        //return XmlUtil.serialize(newPerm)
    }

}