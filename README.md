# ElasticSearchKit Framework
[![](https://jitpack.io/v/ZenMaster91/ElasticSearchKit.svg)](https://jitpack.io/#ZenMaster91/ElasticSearchKit)
  
The *ElasticSearch framework* is an open source software framework that makes it easy to create apps that uses Elasticsearch.

## Examples of use
### Executing a query.
```java
// Writing the settings for the connection.
// elasticsearch -> cluster name.
// 9300 -> port for the connection.
// tweets -> index name.
// localhost -> host name.
ESSettings settings = new ESSettings("elasticsearch", 9300, "tweets", "localhost");

// Creating the elasticsearch database from the settings created.
ESDatabase database = new ESDatabase(settings);

// Simple query that gets the today's tweets of mike123.
ESQuery statement = new ESQuery( "SELECT * FROM T.TWEETS WHERE user = ? AND date = ?" );
statement.setParam( "'mike123'" )
          .setParam( new Date() );
	
// We execute the query in the elasticsearch engine.
StringBuffer result = database.execute(statement);
	
System.out.println(result);
// Will print.
{
 tweets: {
  tweet: {
   id: "1980723423",
   date: "22-07-2017",
   user: "mike123",
   content: "Hey I'm in LA!",
   ...
  },
  ...
 }
}
 ```
