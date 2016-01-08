/* be aware it rewrites the whole xml file will be lost */

import groovy.xml.*;


public class AddUsersToRepositoriesXml{
    
        public String listRepoFromXML(xmlFileContent){
        
            def xmlParsed = new XmlSlurper(false, true).parseText( xmlFileContent )
            println "listRepoFromXML"
            xmlParsed.repositories.repository.each(){
                print "»"+it.name+"« , "
            }
            println ""
        }    
        


    public String writeXML(reposMap, xmlFileContent){
        
        def xmlParsed = new XmlSlurper(false, true).parseText( xmlFileContent )
        println "reposMap----»"+reposMap
        xmlParsed.repositories.repository.each(){
            String reponame=it.name
            println "treating xmlrepo:$reponame"
            //println "get repo from name("+it.name+"-»"+reposMap.find(reponame)
            println "get repo from name("+it.name+"--»"+reposMap[reponame]
            def login=reposMap[reponame]
       
        reposMap[reponame].each { k,v ->
                println "{[$reponame]k=$k,v=$v}"
                def fragmentToAdd =   { 
                    permission {
                        name(k)
                        groupPermission("false")
                        type(v)
                    }
                }
                println "inserting xml"
                def nodeRepo = xmlParsed.repositories.repository.find{ it.name == reponame }
                //xmlParsed.repositories.repository.permissions.appendNode( fragmentToAdd )
                nodeRepo.permissions.appendNode( fragmentToAdd )
            }
        }  
        return XmlUtil.serialize(xmlParsed)
        
    }
    
}