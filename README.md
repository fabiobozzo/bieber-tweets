# bieber-tweets #

## Who and When ##

|||
|---|---|
| Author        | __Fabio Bozzo__ |
| Creation Date | __2020-05-04__  |

## What ##

__PoC based on Twitter Streaming API__.

The app filters the realtime statuses by a keyword, up to a maximum of 100 tweets, or after 30 seconds of execution.

The keyword (default value: bieber) can be passed as a command-line argument. 

The 'default' application profile output processor will print to stdout (slf4j console appender) the list of authors
(sorted by their creation date, ascending) along with the list of tweets for each author (sorted by their creation date, ascending).

Changing the profile to 'file' will result in an output CSV file, in the current directory, containing author/tweets data. 

## How ##

`mvn clean package && docker build --tag bieber-tweets:1.0.0 . && docker run -i -t -p 8000:8080 --name bieber-tweets bieber-tweets:1.0.0`

Almost everything is configurable in properties file: /src/main/resources/application.yml 

## Why ##

I chose Spring Boot to get a "convention over configuration" minimal command-line application up and running. 

Jackson helped me with JSON deserialization. 
