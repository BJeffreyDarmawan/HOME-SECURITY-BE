# MongoDB Archetype Reactive
Following are the steps to make archetype for Maven : 
1. <code>git clone</code>
2. <code>cd archetype-mongodb</code>
3. <code>mvn clean install</code>
4. <code>mvn archetype:create-from-project</code>
5. <code>cd target/generated-sources/archetype</code>
6. <code>mvn install</code>
7. Done!!
Now you can create a new Maven Project by just running <code>mvn archetype:generate -DarchetypeCatalog=local</code>


# Final Project BackEnd Details

Layout Structure: controller -> service -> dao(repository)
  <Class Models are used to be the models for requests, responses, and database collection fields>
    <requests and responses are in rest-web-models; collection model in entity>
<Talk about all the functions in all the controllers>
